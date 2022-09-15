package com.xxw.platform.module.elasticsearch.service;

import cn.hutool.db.PageResult;
import com.xxw.platform.module.elasticsearch.dto.ElasticsearchDTO;

import java.util.List;

public interface ElasticsearchService {

    /**
     * create Index
     *
     * @param index elasticsearch index name
     * @author xxw
     */
    void createIndex(String index);

    /**
     * delete Index
     *
     * @param index elasticsearch index name
     * @author xxw
     */
    void deleteIndex(String index);

    /**
     * insert document source
     *
     * @param index elasticsearch index name
     * @param list  data source
     * @author xxw
     */
    void insert(String index, List<ElasticsearchDTO> list);

    /**
     * update document source
     *
     * @param index elasticsearch index name
     * @param list  data source
     * @author xxw
     */
    void update(String index, List<ElasticsearchDTO> list);

    /**
     * delete document source
     *
     * @param id
     * @author xxw
     */
    void delete(String index, String id);

    /**
     * search all doc records
     *
     * @param index elasticsearch index name
     * @return person list
     * @author xxw
     */
    List<ElasticsearchDTO> searchAll(String index);

    ElasticsearchDTO searchOne(String index, String orderSn);

    //精准搜索
    List<ElasticsearchDTO> searchList(String index, Long userId);

    //模糊搜索
    List<ElasticsearchDTO> fuzzyList(String index, String consignee);

    PageResult<ElasticsearchDTO> page(String index, String consignee);

}
