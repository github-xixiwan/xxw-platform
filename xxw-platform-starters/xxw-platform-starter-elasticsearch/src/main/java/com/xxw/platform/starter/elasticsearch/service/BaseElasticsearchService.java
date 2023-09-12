package com.xxw.platform.starter.elasticsearch.service;

import cn.hutool.core.bean.BeanUtil;
import com.xxw.platform.module.common.exception.ElasticsearchException;
import com.xxw.platform.starter.elasticsearch.config.ElasticsearchProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.HttpAsyncResponseConsumerFactory;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.NestedQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;

import javax.annotation.Resource;
import java.io.IOException;

@Slf4j
public abstract class BaseElasticsearchService {

    @Resource
    protected RestHighLevelClient client;

    @Resource
    private ElasticsearchProperties elasticsearchProperties;

    protected static final RequestOptions COMMON_OPTIONS;

    static {
        RequestOptions.Builder builder = RequestOptions.DEFAULT.toBuilder();

        // 默认缓冲限制为100MB，此处修改为30MB。
        builder.setHttpAsyncResponseConsumerFactory(new HttpAsyncResponseConsumerFactory.HeapBufferedResponseConsumerFactory(30 * 1024 * 1024));
        COMMON_OPTIONS = builder.build();
    }

    /**
     * create elasticsearch index (asyc)
     *
     * @param index elasticsearch index
     * @author xxw
     */
    protected void createIndexRequest(String index) {
        try {
            CreateIndexRequest request = new CreateIndexRequest(index);
            // Settings for this index
            request.settings(Settings.builder().put("index.number_of_shards", elasticsearchProperties.getIndex().getNumberOfShards()).put("index.number_of_replicas", elasticsearchProperties.getIndex().getNumberOfReplicas()));

            CreateIndexResponse createIndexResponse = client.indices().create(request, COMMON_OPTIONS);

            log.info(" whether all of the nodes have acknowledged the request : {}", createIndexResponse.isAcknowledged());
            log.info(" Indicates whether the requisite number of shard copies were started for each shard in the index before timing out :{}", createIndexResponse.isShardsAcknowledged());
        } catch (IOException e) {
            throw new ElasticsearchException(1, "创建索引 {" + index + "} 失败");
        }
    }

    /**
     * delete elasticsearch index
     *
     * @param index elasticsearch index name
     * @author xxw
     */
    protected void deleteIndexRequest(String index) {
        DeleteIndexRequest deleteIndexRequest = buildDeleteIndexRequest(index);
        try {
            client.indices().delete(deleteIndexRequest, COMMON_OPTIONS);
        } catch (IOException e) {
            throw new ElasticsearchException(2, "删除索引 {" + index + "} 失败");
        }
    }

    /**
     * build DeleteIndexRequest
     *
     * @param index elasticsearch index name
     * @author xxw
     */
    private static DeleteIndexRequest buildDeleteIndexRequest(String index) {
        return new DeleteIndexRequest(index);
    }

    /**
     * build IndexRequest
     *
     * @param index  elasticsearch index name
     * @param id     request object id
     * @param object request object
     * @return {@link IndexRequest}
     * @author xxw
     */
    protected static IndexRequest buildIndexRequest(String index, String id, Object object) {
        return new IndexRequest(index).id(id).source(BeanUtil.beanToMap(object), XContentType.JSON);
    }

    /**
     * exec updateRequest
     *
     * @param index  elasticsearch index name
     * @param id     Document id
     * @param object request object
     * @author xxw
     */
    protected void updateRequest(String index, String id, Object object) {
        try {
            UpdateRequest updateRequest = new UpdateRequest(index, id).doc(BeanUtil.beanToMap(object), XContentType.JSON);
            client.update(updateRequest, COMMON_OPTIONS);
        } catch (IOException e) {
            throw new ElasticsearchException(3, "更新索引 {" + index + "} 数据 {" + object + "} 失败");
        }
    }

    /**
     * exec deleteRequest
     *
     * @param index elasticsearch index name
     * @param id    Document id
     * @author xxw
     */
    protected void deleteRequest(String index, String id) {
        try {
            DeleteRequest deleteRequest = new DeleteRequest(index, id);
            client.delete(deleteRequest, COMMON_OPTIONS);
        } catch (IOException e) {
            throw new ElasticsearchException(4, "删除索引 {" + index + "} 数据id {" + id + "} 失败");
        }
    }

    /**
     * search all
     *
     * @param index elasticsearch index name
     * @return {@link SearchResponse}
     * @author xxw
     */
    protected SearchResponse search(String index) {
        SearchRequest searchRequest = new SearchRequest(index);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = null;
        try {
            searchResponse = client.search(searchRequest, COMMON_OPTIONS);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return searchResponse;
    }

    /**
     * 单条件精确查询
     *
     * @throws IOException
     */
    public void search0() throws IOException {
        // 创建请求
        SearchSourceBuilder builder = new SearchSourceBuilder().query(QueryBuilders.termsQuery("name", "赵里"));

        //搜索
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("cs_index");
        searchRequest.types("_doc");
        searchRequest.source(builder);
        // 执行请求
        SearchResponse response = client.search(searchRequest, COMMON_OPTIONS);
        // 解析查询结果
        System.out.println(response.toString());
    }

    /**
     * 多条件精确查询，取并集
     *
     * @throws IOException
     */
    public void search1() throws IOException {
        // 创建请求
        SearchSourceBuilder builder = new SearchSourceBuilder().query(QueryBuilders.termsQuery("name", "张", "陈"));

        //搜索
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("cs_index");
        searchRequest.types("_doc");
        searchRequest.source(builder);
        // 执行请求
        SearchResponse response = client.search(searchRequest, COMMON_OPTIONS);
        // 解析查询结果
        System.out.println(response.toString());
    }

    /**
     * 范围查询，包括from、to
     *
     * @throws IOException
     */
    public void search2() throws IOException {
        // 创建请求
        SearchSourceBuilder builder = new SearchSourceBuilder().query(QueryBuilders.rangeQuery("age").from(20).to(32));

        //搜索
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("cs_index");
        searchRequest.types("_doc");
        searchRequest.source(builder);
        // 执行请求
        SearchResponse response = client.search(searchRequest, COMMON_OPTIONS);
        // 解析查询结果
        System.out.println(response.toString());
    }


    /**
     * 范围查询，不包括from、to
     *
     * @throws IOException
     */
    public void search3() throws IOException {
        // 创建请求
        SearchSourceBuilder builder = new SearchSourceBuilder().query(QueryBuilders.rangeQuery("age").from(20, false).to(30, false));

        //搜索
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("cs_index");
        searchRequest.types("_doc");
        searchRequest.source(builder);
        // 执行请求
        SearchResponse response = client.search(searchRequest, COMMON_OPTIONS);
        // 解析查询结果
        System.out.println(response.toString());
    }


    /**
     * 范围查询, lt:小于，gt:大于
     *
     * @throws IOException
     */
    public void search4() throws IOException {
        // 创建请求
        SearchSourceBuilder builder = new SearchSourceBuilder().query(QueryBuilders.rangeQuery("age").lt(30).gt(20));

        //搜索
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("cs_index");
        searchRequest.types("_doc");
        searchRequest.source(builder);
        // 执行请求
        SearchResponse response = client.search(searchRequest, COMMON_OPTIONS);
        // 解析查询结果
        System.out.println(response.toString());
    }

    /**
     * 模糊查询，支持通配符
     *
     * @throws IOException
     */
    public void search5() throws IOException {
        // 创建请求
        SearchSourceBuilder builder = new SearchSourceBuilder().query(QueryBuilders.wildcardQuery("name", "张三"));

        //搜索
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("cs_index");
        searchRequest.types("_doc");
        searchRequest.source(builder);
        // 执行请求
        SearchResponse response = client.search(searchRequest, COMMON_OPTIONS);
        // 解析查询结果
        System.out.println(response.toString());
    }

    /**
     * 不使用通配符的模糊查询，左右匹配
     *
     * @throws IOException
     */
    public void search6() throws IOException {
        // 创建请求
        SearchSourceBuilder builder = new SearchSourceBuilder().query(QueryBuilders.queryStringQuery("张三").field("name"));

        //搜索
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("cs_index");
        searchRequest.types("_doc");
        searchRequest.source(builder);
        // 执行请求
        SearchResponse response = client.search(searchRequest, COMMON_OPTIONS);
        // 解析查询结果
        System.out.println(response.toString());
    }

    /**
     * 多字段模糊查询
     *
     * @throws IOException
     */
    public void search7() throws IOException {
        // 创建请求
        SearchSourceBuilder builder = new SearchSourceBuilder().query(QueryBuilders.multiMatchQuery("长", "name", "city"));

        //搜索
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("cs_index");
        searchRequest.types("_doc");
        searchRequest.source(builder);
        // 执行请求
        SearchResponse response = client.search(searchRequest, COMMON_OPTIONS);
        // 解析查询结果
        System.out.println(response.toString());
    }

    /**
     * 分页搜索
     *
     * @throws IOException
     */
    public void search8() throws IOException {
        // 创建请求
        SearchSourceBuilder builder = new SearchSourceBuilder().from(0).size(2);

        //搜索
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("cs_index");
        searchRequest.types("_doc");
        searchRequest.source(builder);
        // 执行请求
        SearchResponse response = client.search(searchRequest, COMMON_OPTIONS);
        // 解析查询结果
        System.out.println(response.toString());
    }

    /**
     * 排序，字段的类型必须是：integer、double、long或者keyword
     *
     * @throws IOException
     */
    public void search9() throws IOException {
        // 创建请求
        SearchSourceBuilder builder = new SearchSourceBuilder().sort("createTime", SortOrder.ASC);

        //搜索
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("cs_index");
        searchRequest.types("_doc");
        searchRequest.source(builder);
        // 执行请求
        SearchResponse response = client.search(searchRequest, COMMON_OPTIONS);
        // 解析查询结果
        System.out.println(response.toString());
    }

    /**
     * 精确统计筛选文档数,查询性能有所降低
     *
     * @throws IOException
     */
    public void search10() throws IOException {
        // 创建请求
        SearchSourceBuilder builder = new SearchSourceBuilder().trackTotalHits(true);

        //搜索
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("cs_index");
        searchRequest.types("_doc");
        searchRequest.source(builder);
        // 执行请求
        SearchResponse response = client.search(searchRequest, COMMON_OPTIONS);
        // 解析查询结果
        System.out.println(response.toString());
    }

    /**
     * 设置源字段过虑,第一个参数结果集包括哪些字段，第二个参数表示结果集不包括哪些字段
     *
     * @throws IOException
     */
    public void search11() throws IOException {
        // 创建请求
        SearchSourceBuilder builder = new SearchSourceBuilder().fetchSource(new String[]{"name", "age", "city", "createTime"}, new String[]{});

        //搜索
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("cs_index");
        searchRequest.types("_doc");
        searchRequest.source(builder);
        // 执行请求
        SearchResponse response = client.search(searchRequest, COMMON_OPTIONS);
        // 解析查询结果
        System.out.println(response.toString());
    }

    /**
     * 根据id精确匹配
     *
     * @throws IOException
     */
    public void search12() throws IOException {
        String[] ids = new String[]{"1", "2"};
        // 创建请求
        SearchSourceBuilder builder = new SearchSourceBuilder().query(QueryBuilders.termsQuery("_id", ids));

        //搜索
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("cs_index");
        searchRequest.types("_doc");
        searchRequest.source(builder);
        // 执行请求
        SearchResponse response = client.search(searchRequest, COMMON_OPTIONS);
        // 解析查询结果
        System.out.println(response.toString());
    }

    /**
     * matchAllQuery搜索全部
     *
     * @throws IOException
     */
    public void search21() throws IOException {
        // 创建请求
        SearchSourceBuilder builder = new SearchSourceBuilder().query(QueryBuilders.matchAllQuery());

        //搜索
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("cs_index");
        searchRequest.types("_doc");
        searchRequest.source(builder);
        // 执行请求
        SearchResponse response = client.search(searchRequest, COMMON_OPTIONS);
        // 解析查询结果
        System.out.println(response.toString());
    }

    /**
     * match搜索匹配
     *
     * @throws IOException
     */
    public void search22() throws IOException {
        // 创建请求
        SearchSourceBuilder builder = new SearchSourceBuilder().query(QueryBuilders.matchQuery("name", "张王"));

        //搜索
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("cs_index");
        searchRequest.types("_doc");
        searchRequest.source(builder);
        // 执行请求
        SearchResponse response = client.search(searchRequest, COMMON_OPTIONS);
        // 解析查询结果
        System.out.println(response.toString());
    }

    /**
     * bool组合查询
     *
     * @throws IOException
     */
    public void search23() throws IOException {
        // 创建请求
        SearchSourceBuilder builder = new SearchSourceBuilder();

        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.must(QueryBuilders.matchQuery("name", "张王"));
        boolQueryBuilder.must(QueryBuilders.rangeQuery("age").lte(30).gte(20));
        builder.query(boolQueryBuilder);

        //搜索
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("cs_index");
        searchRequest.types("_doc");
        searchRequest.source(builder);
        // 执行请求
        SearchResponse response = client.search(searchRequest, COMMON_OPTIONS);
        // 解析查询结果
        System.out.println(response.toString());
    }

    /**
     * nested类型嵌套查询
     *
     * @throws IOException
     */
    public void search24() throws IOException {
        // 创建请求
        SearchSourceBuilder builder = new SearchSourceBuilder();

        //条件查询
        BoolQueryBuilder mainBool = new BoolQueryBuilder();
        mainBool.must(QueryBuilders.matchQuery("name", "赵六"));

        //nested类型嵌套查询
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.must(QueryBuilders.matchQuery("products.brand", "A"));
        boolQueryBuilder.must(QueryBuilders.matchQuery("products.title", "巧克力"));
        NestedQueryBuilder nested = QueryBuilders.nestedQuery("products", boolQueryBuilder, ScoreMode.None);
        mainBool.must(nested);

        builder.query(mainBool);

        //搜索
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("cs_index");
        searchRequest.types("_doc");
        searchRequest.source(builder);
        // 执行请求
        SearchResponse response = client.search(searchRequest, COMMON_OPTIONS);
        // 解析查询结果
        System.out.println(response.toString());
    }

    /**
     * 多条件查询 + 排序 + 分页
     *
     * @throws IOException
     */
    public void search29() throws IOException {
        // 创建请求
        SearchSourceBuilder builder = new SearchSourceBuilder();

        //条件搜索
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.must(QueryBuilders.matchQuery("name", "张王"));
        boolQueryBuilder.must(QueryBuilders.rangeQuery("age").lte(30).gte(20));
        builder.query(boolQueryBuilder);

        //结果集合分页
        builder.from(0).size(2);

        //排序
        builder.sort("createTime", SortOrder.ASC);

        //搜索
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("cs_index");
        searchRequest.types("_doc");
        searchRequest.source(builder);
        // 执行请求
        SearchResponse response = client.search(searchRequest, COMMON_OPTIONS);
        // 解析查询结果
        System.out.println(response.toString());
    }

    /**
     * 聚合查询 sum
     *
     * @throws IOException
     */
    public void search30() throws IOException {
        // 创建请求
        SearchSourceBuilder builder = new SearchSourceBuilder();

        //条件搜索
        builder.query(QueryBuilders.matchAllQuery());
        //聚合查询
        AggregationBuilder aggregation = AggregationBuilders.sum("sum_age").field("age");
        builder.aggregation(aggregation);

        //搜索
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("cs_index");
        searchRequest.types("_doc");
        searchRequest.source(builder);
        // 执行请求
        SearchResponse response = client.search(searchRequest, COMMON_OPTIONS);
        // 解析查询结果
        System.out.println(response.toString());
    }

    /**
     * 聚合查询 avg
     *
     * @throws IOException
     */
    public void search31() throws IOException {
        // 创建请求
        SearchSourceBuilder builder = new SearchSourceBuilder();

        //条件搜索
        builder.query(QueryBuilders.matchAllQuery());
        //聚合查询
        AggregationBuilder aggregation = AggregationBuilders.avg("avg_age").field("age");
        builder.aggregation(aggregation);

        //搜索
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("cs_index");
        searchRequest.types("_doc");
        searchRequest.source(builder);
        // 执行请求
        SearchResponse response = client.search(searchRequest, COMMON_OPTIONS);
        // 解析查询结果
        System.out.println(response.toString());
    }

    /**
     * 聚合查询 count
     *
     * @throws IOException
     */
    public void search32() throws IOException {
        // 创建请求
        SearchSourceBuilder builder = new SearchSourceBuilder();

        //条件搜索
        builder.query(QueryBuilders.matchAllQuery());
        //聚合查询
        AggregationBuilder aggregation = AggregationBuilders.count("count_age").field("age");
        builder.aggregation(aggregation);

        //搜索
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("cs_index");
        searchRequest.types("_doc");
        searchRequest.source(builder);
        // 执行请求
        SearchResponse response = client.search(searchRequest, COMMON_OPTIONS);
        // 解析查询结果
        System.out.println(response.toString());
    }

    /**
     * 聚合查询 分组
     *
     * @throws IOException
     */
    public void search33() throws IOException {
        // 创建请求
        SearchSourceBuilder builder = new SearchSourceBuilder();

        //条件搜索
        builder.query(QueryBuilders.matchAllQuery());
        //聚合查询
        AggregationBuilder aggregation = AggregationBuilders.terms("tag_createTime").field("createTime").subAggregation(AggregationBuilders.count("count_age").field("age")) //计数
                .subAggregation(AggregationBuilders.sum("sum_age").field("age")) //求和
                .subAggregation(AggregationBuilders.avg("avg_age").field("age")); //求平均值

        builder.aggregation(aggregation);

        //不输出原始数据
        builder.size(0);

        //搜索
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("cs_index");
        searchRequest.types("_doc");
        searchRequest.source(builder);
        // 执行请求
        SearchResponse response = client.search(searchRequest, COMMON_OPTIONS);
        // 解析查询结果
        System.out.println(response.toString());
    }
}
