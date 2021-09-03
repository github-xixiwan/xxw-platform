package com.xxw.platform.starter.elasticsearch.config;

import com.google.common.collect.Lists;
import com.xxw.platform.starter.elasticsearch.client.ElasticsearchRestHighLevelClient;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

@Configuration
@EnableConfigurationProperties(ElasticsearchProperties.class)
public class ElasticsearchConfig {

    @Resource
    private ElasticsearchProperties elasticsearchProperties;

    @Bean
    public RestHighLevelClient client() {
        Assert.state(StringUtils.isNotBlank(elasticsearchProperties.getHostAddress()), "hostAddress cannot be empty");
        List<HttpHost> hostList = Lists.newArrayList();
        String[] hostAddress = elasticsearchProperties.getHostAddress().split(",");
        for (String address : hostAddress) {
            Assert.state(address.split(":").length == 2, "Must be defined as 'host:port'");
            hostList.add(new HttpHost(address.split(":")[0], Integer.parseInt(address.split(":")[1]), elasticsearchProperties.getSchema()));
        }

        RestClientBuilder builder = RestClient.builder(hostList.toArray(new HttpHost[0]));
        // 异步httpclient连接延时配置
        builder.setRequestConfigCallback(requestConfigBuilder -> {
            requestConfigBuilder.setConnectTimeout(elasticsearchProperties.getConnectTimeout());
            requestConfigBuilder.setSocketTimeout(elasticsearchProperties.getSocketTimeout());
            return requestConfigBuilder;
        });
        // 异步httpclient连接数配置
        builder.setHttpClientConfigCallback(httpClientBuilder -> {
            if (StringUtils.isNotBlank(elasticsearchProperties.getUsername()) && StringUtils.isNotBlank(elasticsearchProperties.getPassword())) {
                CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
                credentialsProvider.setCredentials(AuthScope.ANY,
                        new UsernamePasswordCredentials(elasticsearchProperties.getUsername(), elasticsearchProperties.getPassword()));
                httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
            }
            httpClientBuilder.setMaxConnTotal(elasticsearchProperties.getMaxConnTotal());
            httpClientBuilder.setMaxConnPerRoute(elasticsearchProperties.getMaxConnPerRoute());
            return httpClientBuilder;
        });
        return new RestHighLevelClient(builder);
    }

    @Bean
    public ElasticsearchRestHighLevelClient elasticsearchRestHighLevelClient() {
        return new ElasticsearchRestHighLevelClient();
    }
}