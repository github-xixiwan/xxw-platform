package com.xxw.platform.module.sharding.dao.impl;

import com.xxw.platform.module.sharding.dao.base.XxwOrderBaseDao;
import com.xxw.platform.module.sharding.dao.intf.XxwOrderDao;
import com.xxw.platform.module.sharding.entity.XxwOrderEntity;
import com.xxw.platform.module.util.rest.Result;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * XxwOrderDaoImpl: 数据操作接口实现
 * <p>
 * 这只是一个减少手工创建的模板文件
 * 可以任意添加方法和实现, 更改作者和重定义类名
 * <p/>@author Powered By Fluent Mybatis
 */
@Repository
public class XxwOrderDaoImpl extends XxwOrderBaseDao implements XxwOrderDao {

    @Override
    public Result<List<XxwOrderEntity>> listOrder() {
        return Result.success(super.listEntity(query()));
    }
}
