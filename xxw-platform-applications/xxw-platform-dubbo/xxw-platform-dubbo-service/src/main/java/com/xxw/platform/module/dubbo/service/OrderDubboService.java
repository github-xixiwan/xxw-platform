package com.xxw.platform.module.dubbo.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import com.xxw.platform.dubbo.api.order.OrderDubboApi;
import com.xxw.platform.dubbo.api.order.model.dto.OrderDubboDTO;
import com.xxw.platform.dubbo.api.order.model.vo.OrderDubboVO;
import com.xxw.platform.module.dubbo.model.entity.XxwOrder;
import com.xxw.platform.module.util.json.JsonUtil;
import com.xxw.platform.module.util.rest.Result;
import com.xxw.platform.starter.redisson.cache.IGlobalRedisCache;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@DubboService
public class OrderDubboService implements OrderDubboApi {

    @Resource
    private MapperFacade mapperFacade;

    @Resource
    private IGlobalRedisCache globalRedisCache;

    @Resource
    private RedissonClient redissonClient;

    @Resource
    private IXxwOrder0Service xxwOrder0Service;

    @Override
    public Result<Void> addOrder(OrderDubboDTO dto) {
        xxwOrder0Service.save(mapperFacade.map(dto, XxwOrder.class));
        return Result.success();
    }

    @Override
    public Result<List<OrderDubboVO>> getOrder(OrderDubboDTO dto) {
        String index = "order";
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
                    //缓存不存在查数据库
                    LambdaQueryWrapper<XxwOrder> wrapper = new LambdaQueryWrapper<XxwOrder>().eq(XxwOrder::getOrderSn, dto.getOrderSn());
                    List<XxwOrder> list = xxwOrder0Service.list(wrapper);
                    if (!CollectionUtils.isEmpty(list)) {
                        //放入redis
                        globalRedisCache.set(key, JsonUtil.toJson(list), 60);
                        return Result.success(mapperFacade.mapAsList(list, OrderDubboVO.class));
                    }
                }
                return Result.success(JsonUtil.fromJsonAsArray(String.valueOf(o), OrderDubboVO.class));
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
