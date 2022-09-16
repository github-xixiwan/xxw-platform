package com.xxw.platform.module.dynamic.service.impl;

import com.xxw.platform.module.dynamic.dao.intf.XxwOrderDao;
import com.xxw.platform.module.dynamic.entity.XxwOrderEntity;
import com.xxw.platform.module.dynamic.event.MsgDataEvent;
import com.xxw.platform.module.dynamic.event.OrderDataEvent;
import com.xxw.platform.module.dynamic.service.DynamicService;
import com.xxw.platform.module.dynamic.vo.DynamicVO;
import com.xxw.platform.module.util.json.JsonUtil;
import com.xxw.platform.module.util.rest.Result;
import com.xxw.platform.starter.redisson.cache.IGlobalRedisCache;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class DynamicServiceImpl implements DynamicService {

    @Resource
    private MapperFacade mapperFacade;

    @Resource
    private IGlobalRedisCache globalRedisCache;

    @Resource
    private RedissonClient redissonClient;

    @Resource
    private ApplicationContext applicationContext;

    @Resource
    @Qualifier("xxwOrder0DaoImpl")
    private XxwOrderDao xxwOrderDao0;

    @Resource
    @Qualifier("xxwOrder1DaoImpl")
    private XxwOrderDao xxwOrderDao1;

    @Override
    public Result<String> buyOrder(Integer id) {
        applicationContext.publishEvent(new OrderDataEvent(id));
        applicationContext.publishEvent(new MsgDataEvent("订单ID：" + id));
        return Result.success();
    }

    @Override
    public Result<DynamicVO> getOrder(Integer id) {
        DynamicVO vo = null;
        String index = "order";
        String name = "getOrder";
        long waitTime = 5;
        TimeUnit unit = TimeUnit.SECONDS;
        RLock rLock = redissonClient.getLock(name);
        try {
            boolean res = rLock.tryLock(waitTime, unit);
            if (res) {
                System.out.println("获取锁成功");
                String key = "getOrder_" + id;
                Object o = globalRedisCache.get(key);
                if (o == null) {
                    //缓存不存在查数据库
                    XxwOrderEntity xxwOrderEntity = xxwOrderDao0.selectById(id);
                    if (xxwOrderEntity != null) {
                        vo = mapperFacade.map(xxwOrderEntity, DynamicVO.class);
                        //放入redis
                        globalRedisCache.set(key, JsonUtil.toJson(vo), 60);
                        return Result.success(vo);
                    }
                }
                return Result.success(JsonUtil.fromJson(String.valueOf(o), DynamicVO.class));
            } else {
                System.out.println("获取锁失败");
            }
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
        return Result.success(vo);
    }
}
