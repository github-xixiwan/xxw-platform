package com.xxw.platform.pay.module.pay.service;

import com.xxw.platform.api.pay.PayApi;
import com.xxw.platform.api.pay.model.dto.SuccessKilledDTO;
import com.xxw.platform.api.pay.model.vo.SuccessKilledVO;
import com.xxw.platform.pay.module.pay.model.entity.XxwSuccessKilled;
import com.xxw.platform.util.rest.Result;
import ma.glasnost.orika.MapperFacade;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import javax.annotation.Resource;

@DubboService
@RefreshScope
public class PayService implements PayApi {

    @Value("${name:word}")
    private String name;

    @Resource
    private MapperFacade mapperFacade;

    @Resource
    private IXxwSuccessKilledService successKilledService;

    @Override
    public Result<String> hello() {
        return Result.success(name);
    }

    @Override
    public Result<SuccessKilledVO> getById(Long id) {
        XxwSuccessKilled successKilled = successKilledService.getById(id);
        return Result.success(successKilled != null ? mapperFacade.map(successKilled, SuccessKilledVO.class) : null);
    }

    @Override
    public Result<String> addSuccessKilled(SuccessKilledDTO dto) {
        XxwSuccessKilled successKilled = mapperFacade.map(dto, XxwSuccessKilled.class);
        successKilledService.save(successKilled);
        return Result.success();
    }

}
