package com.xxw.platform.module.elasticsearch.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.db.PageResult;
import com.google.common.collect.Lists;
import com.xxw.platform.module.elasticsearch.dto.ElasticsearchDTO;
import com.xxw.platform.module.elasticsearch.service.ElasticsearchService;
import com.xxw.platform.starter.elasticsearch.service.BaseElasticsearchService;
import org.apache.lucene.search.TotalHits;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.core.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class ElasticsearchServiceImpl extends BaseElasticsearchService implements ElasticsearchService {

    @Override
    public void createIndex(String index) {
        createIndexRequest(index);
    }

    @Override
    public void deleteIndex(String index) {
        deleteIndexRequest(index);
    }

    @Override
    public void insert(String index, List<ElasticsearchDTO> list) {
        try {
            list.forEach(order -> {
                IndexRequest request = buildIndexRequest(index, String.valueOf(order.getOrderSn()), order);
                try {
                    client.index(request, COMMON_OPTIONS);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void update(String index, List<ElasticsearchDTO> list) {
        list.forEach(order -> {
            updateRequest(index, String.valueOf(order.getOrderSn()), order);
        });
    }

    @Override
    public void delete(String index, String id) {
        deleteRequest(index, id);
    }

    @Override
    public List<ElasticsearchDTO> searchAll(String index) {
        SearchResponse searchResponse = search(index);
        SearchHit[] hits = searchResponse.getHits().getHits();
        List<ElasticsearchDTO> orderList = new ArrayList<>();
        Arrays.stream(hits).forEach(hit -> {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            ElasticsearchDTO order = BeanUtil.toBean(sourceAsMap, ElasticsearchDTO.class);
            orderList.add(order);
        });
        return orderList;
    }

    @Override
    public ElasticsearchDTO searchOne(String index, String orderSn) {
        GetRequest getRequest = new GetRequest(index, orderSn);
        GetResponse getResponse = null;
        ElasticsearchDTO order = null;
        try {
            getResponse = client.get(getRequest, COMMON_OPTIONS);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (getResponse != null) {
            order = BeanUtil.toBean(getResponse.getSource(), ElasticsearchDTO.class);
        }
        return order;
    }

    @Override
    public List<ElasticsearchDTO> searchList(String index, Long userId) {
        SearchRequest searchRequest = new SearchRequest(index);
        //searchRequest.indices("wlp-index");//同时也可以这样指定查询的索引
        /**
         * 条件查询 如果查询条件为中文 需要在属性添加 .keyword,不加就查不到
         * 例如： QueryBuilders.termQuery("name.keyword","张三");
         * termQuery:精确查询 matchQuery:模糊查询
         */
//        TermQueryBuilder queryBuilder = QueryBuilders.termQuery("address.keyword", "880 Holmes Lane");
        TermQueryBuilder queryBuilder = QueryBuilders.termQuery("userId", userId);
        //构件搜索条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //分页 size:每页几条(默认10) from从第几条开始查
        searchSourceBuilder.size(20);
        searchSourceBuilder.from(0);
        searchSourceBuilder.query(queryBuilder);
        //设置查询超时时间
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = null;
        List<ElasticsearchDTO> list = Lists.newArrayList();
        try {
            searchResponse = client.search(searchRequest, COMMON_OPTIONS);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (searchResponse != null) {
            //遍历输出
            SearchHit[] hits = searchResponse.getHits().getHits();
            Arrays.stream(hits).forEach(documentFields -> {
                ElasticsearchDTO order = BeanUtil.toBean(documentFields.getSourceAsMap(), ElasticsearchDTO.class);
                list.add(order);
            });
        }
        return list;
    }

    @Override
    public List<ElasticsearchDTO> fuzzyList(String index, String consignee) {
        SearchRequest searchRequest = new SearchRequest(index);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("consignee", consignee));
        //超时时间
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = null;
        List<ElasticsearchDTO> list = Lists.newArrayList();
        try {
            searchResponse = client.search(searchRequest, COMMON_OPTIONS);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (searchResponse != null) {
            //遍历输出
            SearchHit[] hits = searchResponse.getHits().getHits();
            Arrays.stream(hits).forEach(documentFields -> {
                ElasticsearchDTO order = BeanUtil.toBean(documentFields.getSourceAsMap(), ElasticsearchDTO.class);
                list.add(order);
            });
        }
        return list;
    }

    @Override
    public PageResult<ElasticsearchDTO> page(String index, String consignee) {
        SearchRequest searchRequest = new SearchRequest(index);
        TermQueryBuilder queryBuilder = QueryBuilders.termQuery("consignee.keyword", consignee);
        //构件搜索条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //分页 size:每页几条(默认10) from从第几条开始查
        searchSourceBuilder.size(20);
        searchSourceBuilder.from(0);
        searchSourceBuilder.trackTotalHits(true);
        searchSourceBuilder.query(queryBuilder);
        //设置查询超时时间
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = null;
        PageResult<ElasticsearchDTO> page = new PageResult<>(0, 20);
        try {
            searchResponse = client.search(searchRequest, COMMON_OPTIONS);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (searchResponse != null) {
            TotalHits totalHits = searchResponse.getHits().getTotalHits();
            int total = (int) totalHits.value;
            page.setTotal(total);
            List<ElasticsearchDTO> list = Lists.newArrayList();
            //遍历输出
            SearchHit[] hits = searchResponse.getHits().getHits();
            Arrays.stream(hits).forEach(documentFields -> {
                ElasticsearchDTO order = BeanUtil.toBean(documentFields.getSourceAsMap(), ElasticsearchDTO.class);
                list.add(order);
            });
            page.addAll(list);
        }
        return page;
    }
}
