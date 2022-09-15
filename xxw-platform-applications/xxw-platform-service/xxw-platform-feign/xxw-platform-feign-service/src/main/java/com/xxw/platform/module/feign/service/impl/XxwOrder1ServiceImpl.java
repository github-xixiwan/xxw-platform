package com.xxw.platform.module.feign.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxw.platform.module.feign.mapper.XxwOrderMapper;
import com.xxw.platform.module.feign.entity.XxwOrder;
import com.xxw.platform.module.feign.service.IXxwOrder1Service;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xxw
 * @since 2021-09-06
 */
@Service
@DS("slave")
public class XxwOrder1ServiceImpl extends ServiceImpl<XxwOrderMapper, XxwOrder> implements IXxwOrder1Service {

}
