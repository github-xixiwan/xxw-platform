package com.xxw.platform.module.dynamic.service;

import com.xxw.platform.module.dynamic.vo.DynamicVO;
import com.xxw.platform.module.util.rest.Result;

public interface DynamicService {

    Result<DynamicVO> getOrder(Integer id);

}
