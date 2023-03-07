package com.collapse.search.dao;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.MultiSearchRequest;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;

import java.io.IOException;

public interface SearchDao {

    void insertSearchLog(IndexRequest indexRequest) throws IOException;

    SearchResponse getSearchResponse(SearchRequest searchRequest) throws IOException;

    MultiSearchResponse getMultiSearchResponse(MultiSearchRequest multiSearchRequest) throws IOException;
}
