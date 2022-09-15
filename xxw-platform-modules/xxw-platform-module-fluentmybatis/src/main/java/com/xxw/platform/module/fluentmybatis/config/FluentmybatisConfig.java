package com.xxw.platform.module.fluentmybatis.config;

import cn.org.atool.fluent.mybatis.spring.MapperFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.xxw.platform.**.mapper")
public class FluentmybatisConfig {

    @Bean
    public MapperFactory fluentmybatisMapperFactory() {
        return new MapperFactory();
    }
}
