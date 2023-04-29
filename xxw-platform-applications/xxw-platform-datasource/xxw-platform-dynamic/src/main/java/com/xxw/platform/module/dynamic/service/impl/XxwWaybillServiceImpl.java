package com.xxw.platform.module.dynamic.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxw.platform.module.dynamic.entity.XxwWaybill;
import com.xxw.platform.module.dynamic.mapper.XxwWaybillMapper;
import com.xxw.platform.module.dynamic.service.IXxwWaybillService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xxw
 * @since 2023-04-28
 */
@Service
@DS("ds1")
public class XxwWaybillServiceImpl extends ServiceImpl<XxwWaybillMapper, XxwWaybill> implements IXxwWaybillService {

}
