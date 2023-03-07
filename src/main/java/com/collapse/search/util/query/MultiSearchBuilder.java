package com.collapse.search.util.query;

import com.collapse.search.config.SearchConfig;
import com.collapse.search.dto.SearchRequestDto;
import com.collapse.search.util.query.search.SourceBuilder;
import com.collapse.search.vo.CollapseVo;
import lombok.experimental.UtilityClass;
import org.elasticsearch.action.search.MultiSearchRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.search.builder.SearchSourceBuilder;

@UtilityClass
public class MultiSearchBuilder {

    public MultiSearchRequest buildAllCategoryQuery(SearchRequestDto searchRequestDto) {
        MultiSearchRequest multiSearchRequest = new MultiSearchRequest();
        String[] searchCategories = SearchConfig.DEFAULT_SEARCH_CATEGORIES;
        for(String searchCategory : searchCategories) {
            SearchRequest searchRequest = new SearchRequest(SearchConfig.SEARCH_INDEX);
            SearchSourceBuilder searchSourceBuilder = SourceBuilder.buildSearchSourceByCategory(searchRequestDto,searchCategory);
            searchRequest.source(searchSourceBuilder);
            multiSearchRequest.add(searchRequest);
        }
        return multiSearchRequest;
    }


    public static MultiSearchRequest buildAllStateRequest(CollapseVo collapseVo) {
        MultiSearchRequest multiSearchRequest = new MultiSearchRequest();
        for(String collapsedFieldValue : collapseVo.getCollapsedFieldValues()) {
            SearchRequest searchRequest = new SearchRequest(SearchConfig.SEARCH_INDEX);
            SearchSourceBuilder searchSourceBuilder = SourceBuilder.buildSearchSourceForStatsAgg(collapsedFieldValue,collapseVo);
            searchRequest.source(searchSourceBuilder);
            multiSearchRequest.add(searchRequest);
        }
        return multiSearchRequest;
    }
}
