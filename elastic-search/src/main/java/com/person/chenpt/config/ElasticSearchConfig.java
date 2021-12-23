package com.person.chenpt.config;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * es客户端配置  带用户名密码
 * @Author: chenpt
 * @Description:
 * @Date: Created in 2021-11-24 10:53
 * @Modified By:
 */
@Configuration
public class ElasticSearchConfig {
    @Value("${static.elasticsearch.cluster-nodes}")
    private String clusterNodes;

    @Value("${static.elasticsearch.username}")
    private String username;

    @Value("${static.elasticsearch.password}")
    private String password;

    @Value("${static.elasticsearch.connTimeout:-1}")
    private int connTimeout;

    @Value("${static.elasticsearch.socketTimeout:-1}")
    private int socketTimeout;

    @Value("${static.elasticsearch.connectionRequestTimeout:-1}")
    private int connectionRequestTimeout;

    @Bean
    public RestHighLevelClient elasticsearchClient() {
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(username, password));

        RestClientBuilder builder = RestClient.builder(nodeHttpHost())
                .setRequestConfigCallback(requestConfigBuilder -> requestConfigBuilder
                        .setConnectTimeout(connTimeout)
                        .setSocketTimeout(socketTimeout)
                        .setConnectionRequestTimeout(connectionRequestTimeout))
                .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                    @Override
                    public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                        httpClientBuilder.disableAuthCaching();
                        return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                    }
                });
        RestHighLevelClient client = new RestHighLevelClient(builder);
        return client;
    }

    /**
     * 添加es节点
     */
    private HttpHost[] nodeHttpHost(){
        Assert.hasText(clusterNodes, "Cluster nodes source must not be null or empty!");

        String[] nodes = StringUtils.delimitedListToStringArray(clusterNodes, ",");

        List<HttpHost> collect = Arrays.stream(nodes).map(node -> {

            String[] segments = StringUtils.delimitedListToStringArray(node, ":");

            Assert.isTrue(segments.length == 2,
                    () -> String.format("Invalid cluster node %s in %s! Must be in the format host:port!", node, clusterNodes));

            String host = segments[0].trim();
            String port = segments[1].trim();

            Assert.hasText(host, () -> String.format("No host name given cluster node %s!", node));
            Assert.hasText(port, () -> String.format("No port given in cluster node %s!", node));
            return new HttpHost(host, Integer.valueOf(port), "http");
        }).collect(Collectors.toList());

        Assert.notEmpty(collect,"Cluster nodes source must not be null or empty!");

        return collect.toArray(new HttpHost[0]);
    }


}
