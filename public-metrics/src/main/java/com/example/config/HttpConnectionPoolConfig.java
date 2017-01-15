package com.example.config;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class HttpConnectionPoolConfig {

    @Value("${httpClient.clientPoolSize}")
    private int clientPoolSize;

    @Value("${httpClient.defaultMaxPerRoute}")
    private int defaultMaxPerRoute;

    @Value("${httpClient.validateAfterInactivity}")
    private int validateAfterInactivity;

    @Value("${httpClient.connectionRequestTimeout}")
    private int connectionRequestTimeout;

    @Value("${httpClient.connectTimeout}")
    private int connectTimeout;

    @Value("${httpClient.socketTimeout}")
    private int socketTimeout;

    @Bean
    public PoolingHttpClientConnectionManager initConnectionManager() {
        PoolingHttpClientConnectionManager poolManager = new PoolingHttpClientConnectionManager();
        poolManager.setMaxTotal(clientPoolSize);
        poolManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
        poolManager.setValidateAfterInactivity(validateAfterInactivity);
        return poolManager;
    }


    @Bean
    public CloseableHttpClient initHttpClient(PoolingHttpClientConnectionManager connectionManager) {
        SocketConfig socketConfig = SocketConfig.custom()
                .setSoKeepAlive(true)
                .setSoReuseAddress(true)
                .setTcpNoDelay(true)
                .build();

        // Immutable class encapsulating request configuration items
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(connectionRequestTimeout)
                .setConnectTimeout(socketTimeout)
                .setSocketTimeout(connectTimeout)
                .build();

        CloseableHttpClient result = HttpClientBuilder
                .create()
                .setConnectionManager(connectionManager)
                .setDefaultSocketConfig(socketConfig)
                .setDefaultRequestConfig(requestConfig)
                .build();
        return result;
    }


    @Bean
    public RestTemplate initRestTemplate(HttpClient httpClient) {
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);
        return new RestTemplate(requestFactory);
    }
}
