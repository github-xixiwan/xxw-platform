package com.xxw.platform;

import cn.org.atool.generator.FileGenerator;
import cn.org.atool.generator.annotation.Table;
import cn.org.atool.generator.annotation.Tables;
import org.junit.jupiter.api.Test;

public class FastAutoGeneratorTest {

    // 数据源 url
    static final String url = "jdbc:mysql://106.14.132.211:3307/xxw-order-0?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai";
    // 数据库用户名
    static final String username = "order-0";
    // 数据库密码
    static final String password = "order-0";

    @Test
    public void fastBuild() {
        // 引用配置类，build方法允许有多个配置类
        FileGenerator.build(Empty.class);
    }

    @Tables(
            // 设置数据库连接信息
            url = url, username = username, password = password,
            // 设置entity类生成src目录, 相对于 user.dir
            srcDir = "src/main/java",
            // 设置entity类的package值
            basePack = "com.xxw.platform.module.fluentmybatis",
            // 设置dao接口和实现的src目录, 相对于 user.dir
            daoDir = "src/main/java",
            // 设置哪些表要生成Entity文件
            tables = {@Table(value = {"xxw_order"})})
    static class Empty { //类名随便取, 只是配置定义的一个载体
    }
}
