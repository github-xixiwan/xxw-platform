package com.xxw.platform.starter.redisson.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class Knife4jConfig {

    @Bean
    public Docket restfulApis() {
        return new Docket(DocumentationType.OAS_30).enable(true)
                .apiInfo(new ApiInfoBuilder()
                        .title("xxw-platform RESTful APIs")
                        .description("# xxw-platform RESTful APIs")
                        .termsOfServiceUrl("http://www.xixiwan.cloud/")
                        .contact(new Contact("喜喜玩", "http://www.xixiwan.cloud/", "373002314@qq.com"))
                        .version("0.0.1-SNAPSHOT")
                        .build())
                //分组名称
                .groupName("0.0.1-SNAPSHOT")
                .select()
                //这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.xxw.platform"))
                .paths(PathSelectors.any())
                .build();
    }
}
