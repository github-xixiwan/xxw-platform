package com.xxw.platform.module.dynamic.service;

import com.xxw.platform.module.dynamic.vo.DynamicVO;
import com.xxw.platform.module.common.rest.Result;

public interface DynamicService {

    Result<String> buyOrder(Integer id);

    Result<DynamicVO> getOrder(Integer id);

}
