package com.xxw.platform.module.elasticsearch.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxw.platform.module.elasticsearch.entity.XxwOrder;

import java.util.List;

public interface OrderService {

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
    void insert(String index, List<XxwOrder> list);

    /**
     * update document source
     *
     * @param index elasticsearch index name
     * @param list  data source
     * @author xxw
     */
    void update(String index, List<XxwOrder> list);

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
    List<XxwOrder> searchAll(String index);

    XxwOrder searchOne(String index, String orderSn);

    //精准搜索
    List<XxwOrder> searchList(String index, Long userId);

    //模糊搜索
    List<XxwOrder> fuzzyList(String index, String consignee);

    Page<XxwOrder> page(String index, String consignee);

}
