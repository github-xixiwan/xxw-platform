package com.xxw.platform.pay.module.pay.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import com.xxw.platform.api.pay.PayApi;
import com.xxw.platform.api.pay.model.dto.SuccessKilledDTO;
import com.xxw.platform.api.pay.model.vo.ElasticsearchVO;
import com.xxw.platform.api.pay.model.vo.SuccessKilledVO;
import com.xxw.platform.pay.module.pay.model.entity.XxwSuccessKilled;
import com.xxw.platform.starter.elasticsearch.client.ElasticsearchRestHighLevelClient;
import com.xxw.platform.starter.redisson.cache.IGlobalRedisCache;
import com.xxw.platform.util.json.JsonUtil;
import com.xxw.platform.util.rest.Result;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@DubboService
@RefreshScope
public class PayService implements PayApi {

    @Value("${name:word}")
    private String name;

    @Resource
    private MapperFacade mapperFacade;

    @Resource
    private IGlobalRedisCache globalRedisCache;

    @Resource
    private RedissonClient redissonClient;

    @Resource
    private ElasticsearchRestHighLevelClient elasticsearchRestHighLevelClient;

    @Resource
    private IXxwSuccessKilledService successKilledService;

    @Override
    public Result<String> hello() {
        return Result.success(name);
    }

    @Override
    public Result<List<SuccessKilledVO>> getListBySeckillId(Long seckillId) {
        String name = "getListBySeckillId";
        long waitTime = 5;
        TimeUnit unit = TimeUnit.SECONDS;
        RLock rLock = redissonClient.getLock(name);
        try {
            boolean res = rLock.tryLock(waitTime, unit);
            if (res) {
                System.out.println("获取锁成功");
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String key = "success_killed_" + seckillId;
                Object o = globalRedisCache.get(key);
                if (o == null) {
                    LambdaQueryWrapper<XxwSuccessKilled> wrapper = new LambdaQueryWrapper<XxwSuccessKilled>()
                            .eq(XxwSuccessKilled::getSeckillId, seckillId);
                    List<XxwSuccessKilled> list = successKilledService.list(wrapper);
                    if (!CollectionUtils.isEmpty(list)) {
                        globalRedisCache.set(key, JsonUtil.toJson(list), 60);
                        return Result.success(mapperFacade.mapAsList(list, SuccessKilledVO.class));
                    }
                } else {
                    return Result.success(JsonUtil.fromJsonAsArray(String.valueOf(o), SuccessKilledVO.class));
                }
                return Result.success(Lists.newArrayList());
            } else {
                System.out.println("获取锁失败");
            }
            return Result.success(Lists.newArrayList());
        } catch (InterruptedException e) {
            log.error("getListBySeckillId name:{} waitTime:{} unit:{} 错误:{}", name, waitTime, unit, ExceptionUtils.getStackTrace(e));
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

    @Override
    public Result<List<ElasticsearchVO>> getElasticsearchByTraceId(String index, Long traceId) {
        List<ElasticsearchVO> list = Lists.newArrayList();
        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("traceId", traceId));
        searchRequest.source(searchSourceBuilder);
        try {
            list = elasticsearchRestHighLevelClient.search(searchRequest, ElasticsearchVO.class, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("elasticsearch search Exception:{}", ExceptionUtils.getStackTrace(e));
        }
        return Result.success(list);
    }

    @Override
    public Result<String> addSuccessKilled(SuccessKilledDTO dto) {
        XxwSuccessKilled successKilled = mapperFacade.map(dto, XxwSuccessKilled.class);
        successKilledService.save(successKilled);
        System.out.println(1 / 0);
        return Result.success();
    }

}
