package com.xxw.platform.module.dynamic.dubbo;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import com.xxw.platform.module.api.order.OrderApi;
import com.xxw.platform.module.api.order.model.dto.OrderDTO;
import com.xxw.platform.module.api.order.model.vo.OrderVO;
import com.xxw.platform.module.dynamic.model.entity.XxwOrder;
import com.xxw.platform.module.dynamic.service.IXxwOrder0Service;
import com.xxw.platform.module.dynamic.service.IXxwOrder1Service;
import com.xxw.platform.module.util.json.JsonUtil;
import com.xxw.platform.module.util.rest.Result;
import com.xxw.platform.starter.elasticsearch.client.ElasticsearchRestHighLevelClient;
import com.xxw.platform.starter.redisson.cache.IGlobalRedisCache;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@DubboService
public class OrderDubbo implements OrderApi {

    @Resource
    private MapperFacade mapperFacade;

    @Resource
    private IGlobalRedisCache globalRedisCache;

    @Resource
    private RedissonClient redissonClient;

    @Resource
    private ElasticsearchRestHighLevelClient elasticsearchRestHighLevelClient;

    @Resource
    private IXxwOrder0Service xxwOrder0Service;

    @Resource
    private IXxwOrder1Service xxwOrder1Service;

    @Override
    public Result<Void> addOrder(OrderDTO dto) {
        xxwOrder0Service.save(mapperFacade.map(dto, XxwOrder.class));
        xxwOrder1Service.save(mapperFacade.map(dto, XxwOrder.class));
        return Result.success();
    }

    @Override
    public Result<List<OrderVO>> getOrder(OrderDTO dto) {
        String name = "getOrder";
        long waitTime = 5;
        TimeUnit unit = TimeUnit.SECONDS;
        RLock rLock = redissonClient.getLock(name);
        try {
            boolean res = rLock.tryLock(waitTime, unit);
            if (res) {
                System.out.println("获取锁成功");
                String key = "getOrder_" + dto.getOrderSn();
                Object o = globalRedisCache.get(key);
                if (o == null) {
                    //缓存不存在查es
                    List<OrderVO> list = Lists.newArrayList();
                    SearchRequest searchRequest = new SearchRequest();
                    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
                    searchSourceBuilder.query(QueryBuilders.matchQuery("orderSn", dto.getOrderSn()));
                    searchRequest.source(searchSourceBuilder);
                    list = elasticsearchRestHighLevelClient.search(searchRequest, OrderVO.class, RequestOptions.DEFAULT);
                    if (CollectionUtils.isEmpty(list)) {
                        //查数据库
                        LambdaQueryWrapper<XxwOrder> wrapper = new LambdaQueryWrapper<XxwOrder>()
                                .eq(XxwOrder::getOrderSn, dto.getOrderSn());
                        List<XxwOrder> orderList = xxwOrder0Service.list(wrapper);
                        if (!CollectionUtils.isEmpty(list)) {
                            //放入redis
                            globalRedisCache.set(key, JsonUtil.toJson(list), 60);
                            //放入es
                            UpdateRequest updateRequest = new UpdateRequest();
                            return Result.success(mapperFacade.mapAsList(list, OrderVO.class));
                        }
                    }
                    return Result.success(list);
                }
                return Result.success(JsonUtil.fromJsonAsArray(String.valueOf(o), OrderVO.class));
            } else {
                System.out.println("获取锁失败");
            }
            return Result.success(Lists.newArrayList());
        } catch (Exception e) {
            log.error("getOrder name:{} waitTime:{} unit:{} 错误:{}", name, waitTime, unit, ExceptionUtils.getStackTrace(e));
        } finally {
            //解锁
            if (rLock != null) {
                if (rLock.isLocked() && rLock.isHeldByCurrentThread()) {
                    rLock.unlock();
                }
            }
        }
        return Result.success(Lists.newArrayList());
    }
}
