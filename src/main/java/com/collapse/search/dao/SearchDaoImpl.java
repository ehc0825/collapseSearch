package com.collapse.search.dao;


import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.MultiSearchRequest;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;


@Repository
public class SearchDaoImpl implements SearchDao{

    @Autowired
    RestHighLevelClient client;


    @Override
    public void insertSearchLog(IndexRequest indexRequest) throws IOException {
        client.index(indexRequest, RequestOptions.DEFAULT);
    }

    @Override
    public SearchResponse getSearchResponse(SearchRequest searchRequest) throws IOException {
        return client.search(searchRequest, RequestOptions.DEFAULT);
    }

    @Override
    public MultiSearchResponse getMultiSearchResponse(MultiSearchRequest multiSearchRequest) throws IOException{
        return client.msearch(multiSearchRequest, RequestOptions.DEFAULT);
    }
}
