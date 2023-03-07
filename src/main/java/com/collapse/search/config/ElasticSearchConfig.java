package com.collapse.search.config;


import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Configuration
public class ElasticSearchConfig {


    @Value("${elasticsearch.id}")
    private String elasticId;

    @Value("${elasticsearch.password}")
    private String elasticPassword;

    @Value("${elasticsearch.urls}")
    private List<String> urls;

    @Bean(name = "client", destroyMethod = "close")
    public RestHighLevelClient highLevelClient() {
        RestHighLevelClient client;
        List<HttpHost> hostList = new ArrayList<>();

        final String regex ="^([.0-9a-z]+)(?:\\:(\\d+))?";
        String hostName = "";
        String port = "";
        Pattern pattern;
        CredentialsProvider credentialsProvider;
        boolean esAuthentication = false;

        if((StringUtils.hasText(elasticId)) && StringUtils.hasText(elasticPassword)) {
            credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(AuthScope.ANY,
                    new UsernamePasswordCredentials(elasticId,elasticPassword));
            esAuthentication = true;
        }
        else {
            credentialsProvider = null;
        }
        for(String url : urls) {
            if(!StringUtils.hasText(url)) {
                continue;
            }
            url = url.trim();
            pattern = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(url);
            if(matcher.find()) {
                hostName = matcher.group(1);
                port = matcher.groupCount() ==2 ? matcher.group(2) : "29200";
            }
            hostList.add(new HttpHost(hostName,Integer.parseInt(port),"http"));
        }

        RestClientBuilder restClientBuilder = null;
        if(esAuthentication) {
            restClientBuilder = RestClient.builder(hostList.toArray(new HttpHost[hostList.size()]))
                    .setHttpClientConfigCallback(
                            h -> h.setDefaultCredentialsProvider(credentialsProvider));
        }
        client = new RestHighLevelClient(restClientBuilder);
        return client;
    }

    @Bean(name = "lowClient", destroyMethod = "close")
    public RestClient lowLevelClient() {
        return highLevelClient().getLowLevelClient();
    }

}
