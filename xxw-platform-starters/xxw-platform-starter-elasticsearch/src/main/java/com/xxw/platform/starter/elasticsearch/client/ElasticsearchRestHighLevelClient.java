package com.xxw.platform.starter.elasticsearch.client;

import com.xxw.platform.starter.elasticsearch.page.Page;
import com.xxw.platform.util.json.JsonUtil;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ElasticsearchRestHighLevelClient {

    @Resource
    private RestHighLevelClient restHighLevelClient;

    public UpdateResponse update(UpdateRequest updateRequest) throws IOException {
        return this.doUpdate(updateRequest, RequestOptions.DEFAULT);
    }

    public UpdateResponse update(UpdateRequest updateRequest, RequestOptions options) throws IOException {
        return this.doUpdate(updateRequest, options);
    }

    public <T> UpdateResponse update(String index, String type, String id, T entity) throws IOException {
        UpdateRequest updateRequest = new UpdateRequest(index, type, id);
        updateRequest.doc(JsonUtil.toJson(entity), XContentType.JSON);
        return update(updateRequest, RequestOptions.DEFAULT);
    }

    public <T> UpdateResponse update(String index, String type, String id, T entity, RequestOptions options) throws IOException {
        UpdateRequest updateRequest = new UpdateRequest(index, type, id);
        updateRequest.doc(JsonUtil.toJson(entity), XContentType.JSON);
        return update(updateRequest, options);
    }

    public UpdateResponse update(String index, String type, String id, Map<String, Object> document) throws IOException {
        UpdateRequest updateRequest = new UpdateRequest(index, type, id);
        updateRequest.doc(JsonUtil.toJson(document), XContentType.JSON);
        return update(updateRequest, RequestOptions.DEFAULT);
    }

    public UpdateResponse update(String index, String type, String id, Map<String, Object> document, RequestOptions options) throws IOException {
        UpdateRequest updateRequest = new UpdateRequest(index, type, id);
        updateRequest.doc(JsonUtil.toJson(document), XContentType.JSON);
        return update(updateRequest, options);
    }

    /**
     * 适用于简单字段更新
     *
     * @param index
     * @param query
     * @param document
     * @return
     */
    public BulkByScrollResponse updateByQuery(String index, QueryBuilder query, Map<String, Object> document) throws IOException {
        UpdateByQueryRequest updateByQueryRequest = new UpdateByQueryRequest(index);
        updateByQueryRequest.setQuery(query);

        StringBuilder script = new StringBuilder();
        Set<String> keys = document.keySet();
        for (String key : keys) {
            String appendValue = "";
            Object value = document.get(key);
            if (value instanceof Number) {
                appendValue = value.toString();
            } else if (value instanceof String) {
                appendValue = "'" + value.toString() + "'";
            } else if (value instanceof List) {
                appendValue = JsonUtil.toJson(value);
            } else {
                appendValue = value.toString();
            }
            script.append("ctx._source.").append(key).append("=").append(appendValue).append(";");
        }
        updateByQueryRequest.setScript(new Script(script.toString()));
        return updateByQuery(updateByQueryRequest, RequestOptions.DEFAULT);
    }

    public BulkByScrollResponse updateByQuery(UpdateByQueryRequest updateByQueryRequest, RequestOptions options) throws IOException {
        return restHighLevelClient.updateByQuery(updateByQueryRequest, options);
    }

    private UpdateResponse doUpdate(UpdateRequest updateRequest, RequestOptions options) throws IOException {
        return restHighLevelClient.update(updateRequest, options);
    }

    public SearchResponse search(SearchRequest searchRequest, RequestOptions options) throws IOException {
        return restHighLevelClient.search(searchRequest, options);
    }

    public <T> Page<T> searchByPage(SearchRequest searchRequest, Class<T> entityClass) throws IOException {
        return searchByPage(searchRequest, entityClass, RequestOptions.DEFAULT);
    }

    public <T> Page<T> searchByPage(int page, int pageSize, SearchRequest searchRequest, Class<T> entityClass) throws IOException {
        searchRequest.source().from(Page.page2Start(page, pageSize)).size(pageSize);
        return searchByPage(searchRequest, entityClass, RequestOptions.DEFAULT);
    }

    public <T> Page<T> searchByPage(SearchRequest searchRequest, Class<T> entityClass, RequestOptions options) throws IOException {
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, options);
        long totalHits = searchResponse.getHits().getTotalHits();
        List<T> datas = buildSearchResult(searchResponse, entityClass);
        SearchSourceBuilder searchSourceBuilder = searchRequest.source();
        return new Page(searchSourceBuilder.from(), searchSourceBuilder.size(), datas, totalHits);
    }

    public <T> List<T> search(SearchRequest searchRequest, Class<T> entityClass, RequestOptions options) throws IOException {
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, options);
        return buildSearchResult(searchResponse, entityClass);
    }

    private <T> List<T> buildSearchResult(SearchResponse searchResponse, Class<T> entityClass) {
        List<T> datas = new ArrayList<>();
        SearchHit[] hits = searchResponse.getHits().getHits();
        for (SearchHit hit : hits) {
            String source = hit.getSourceAsString();
            datas.add(JsonUtil.fromJson(source, entityClass));
        }
        return datas;
    }

    public <T> List<T> search(SearchRequest searchRequest, Class<T> entityClass) throws IOException {
        return search(searchRequest, entityClass, RequestOptions.DEFAULT);
    }

    public IndexResponse index(IndexRequest indexRequest) throws IOException {
        return index(indexRequest, RequestOptions.DEFAULT);
    }

    public IndexResponse index(IndexRequest indexRequest, RequestOptions options) throws IOException {
        return restHighLevelClient.index(indexRequest, options);
    }

    public <T> IndexResponse index(String index, T entity) throws IOException {
        IndexRequest indexRequest = new IndexRequest(index);
        indexRequest.source(JsonUtil.toJson(entity), XContentType.JSON);
        IndexResponse indexResponse = index(indexRequest, RequestOptions.DEFAULT);
        return indexResponse;
    }

    public <T> IndexResponse index(String index, T entity, RequestOptions options) throws IOException {
        IndexRequest indexRequest = new IndexRequest(index);
        indexRequest.source(JsonUtil.toJson(entity), XContentType.JSON);
        IndexResponse indexResponse = index(indexRequest, options);
        return indexResponse;
    }

    public <T> IndexResponse index(String index, T entity, String id) throws IOException {
        IndexRequest indexRequest = new IndexRequest(index);
        indexRequest.source(JsonUtil.toJson(entity), XContentType.JSON);
        IndexResponse indexResponse = index(indexRequest, RequestOptions.DEFAULT);
        return indexResponse;
    }

    public DeleteResponse delete(DeleteRequest deleteRequest) throws IOException {
        return delete(deleteRequest, RequestOptions.DEFAULT);
    }

    public DeleteResponse delete(DeleteRequest deleteRequest, RequestOptions options) throws IOException {
        return restHighLevelClient.delete(deleteRequest, options);
    }

    public DeleteResponse delete(String index) throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest(index);
        return delete(deleteRequest, RequestOptions.DEFAULT);
    }

    public DeleteResponse delete(String index, RequestOptions options) throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest(index);
        return delete(deleteRequest, options);
    }

    public CountResponse count(CountRequest countRequest, RequestOptions options) throws IOException {
        return restHighLevelClient.count(countRequest, options);
    }

    public CountResponse count(CountRequest countRequest) throws IOException {
        return count(countRequest, RequestOptions.DEFAULT);
    }

    public long countResult(CountRequest countRequest) throws IOException {
        return count(countRequest, RequestOptions.DEFAULT).getCount();
    }

    public long countResult(CountRequest countRequest, RequestOptions options) throws IOException {
        return count(countRequest, options).getCount();
    }

    public boolean existsSource(GetRequest getRequest, RequestOptions options) throws IOException {
        return restHighLevelClient.existsSource(getRequest, options);
    }

    public boolean existsSource(GetRequest getRequest) throws IOException {
        return existsSource(getRequest, RequestOptions.DEFAULT);
    }
}