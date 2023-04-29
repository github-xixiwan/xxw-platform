package com.xxw.platform.module.dynamic.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxw.platform.module.dynamic.entity.XxwOrder;
import com.xxw.platform.module.dynamic.mapper.XxwOrderMapper;
import com.xxw.platform.module.dynamic.service.IXxwOrderService;
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
@DS("ds0")
public class XxwOrderServiceImpl extends ServiceImpl<XxwOrderMapper, XxwOrder> implements IXxwOrderService {

}
