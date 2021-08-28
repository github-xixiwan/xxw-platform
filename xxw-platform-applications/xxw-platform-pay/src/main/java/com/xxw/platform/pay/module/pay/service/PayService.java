package com.xxw.platform.pay.module.pay.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import com.xxw.platform.api.pay.PayApi;
import com.xxw.platform.api.pay.model.dto.SuccessKilledDTO;
import com.xxw.platform.api.pay.model.vo.SuccessKilledVO;
import com.xxw.platform.pay.module.pay.model.entity.XxwSuccessKilled;
import com.xxw.platform.starter.redisson.cache.IGlobalCache;
import com.xxw.platform.starter.redisson.lock.DistributedLock;
import com.xxw.platform.util.json.JsonUtil;
import com.xxw.platform.util.rest.Result;
import ma.glasnost.orika.MapperFacade;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

@DubboService
@RefreshScope
public class PayService implements PayApi {

    @Value("${name:word}")
    private String name;

    @Resource
    private MapperFacade mapperFacade;

    @Resource
    private IGlobalCache globalCache;

    @Resource
    private DistributedLock distributedLock;

    @Resource
    private IXxwSuccessKilledService successKilledService;

    @Override
    public Result<String> hello() {
        return Result.success(name);
    }

    public void test() {
        System.out.println("111111");
    }

    @Override
    public Result<List<SuccessKilledVO>> getListBySeckillId(Long seckillId) {
        List<SuccessKilledVO> voList = distributedLock.lock("getListBySeckillId", 1000, 1000, () -> {
            System.out.println("获取锁成功");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String key = "success_killed_" + seckillId;
            Object o = globalCache.get(key);
            if (o == null) {
                LambdaQueryWrapper<XxwSuccessKilled> wrapper = new LambdaQueryWrapper<XxwSuccessKilled>()
                        .eq(XxwSuccessKilled::getSeckillId, seckillId);
                List<XxwSuccessKilled> list = successKilledService.list(wrapper);
                if (!CollectionUtils.isEmpty(list)) {
                    globalCache.set(key, JsonUtil.toJson(list), 60);
                    return mapperFacade.mapAsList(list, SuccessKilledVO.class);
                }
            } else {
                return JsonUtil.fromJsonAsArray(String.valueOf(o), SuccessKilledVO.class);
            }
            return Lists.newArrayList();
        }, () -> {
            System.out.println("获取锁失败");
            return Lists.newArrayList();
        });
        return Result.success(voList);
    }

    @Override
    public Result<String> addSuccessKilled(SuccessKilledDTO dto) {
        XxwSuccessKilled successKilled = mapperFacade.map(dto, XxwSuccessKilled.class);
        successKilledService.save(successKilled);
        System.out.println(1 / 0);
        return Result.success();
    }

}
