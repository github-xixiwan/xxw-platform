package com.xxw.platform.module.mybatis.config;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.xxw.platform.**.mapper")
@AutoConfigureBefore(MybatisPlusAutoConfiguration.class)
public class MyBatisPlusConfig {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        // 使用分页插插件
        interceptor.addInnerInterceptor(paginationInterceptor());

        // 使用乐观锁插件
        interceptor.addInnerInterceptor(optimisticLockerInnerInterceptor());

        return interceptor;
    }

    /**
     * 分页插件
     *
     * @author liaoxiting
     * @date 2020/11/30 22:41
     */
    @Bean
    public PaginationInnerInterceptor paginationInterceptor() {
        return new PaginationInnerInterceptor();
    }

    /**
     * 乐观锁插件
     *
     * @author liaoxiting
     * @date 2021/10/28 17:52
     */
    @Bean
    public OptimisticLockerInnerInterceptor optimisticLockerInnerInterceptor() {
        return new OptimisticLockerInnerInterceptor();
    }

}
