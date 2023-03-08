package com.collapse.search.util.query.search.common;

import com.collapse.search.config.SearchConfig;
import com.collapse.search.dto.SearchRequestDto;
import com.collapse.search.dto.SearchSourceDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SizeQueryBuilder {

    public SearchSourceDto buildSizeQuery(SearchSourceDto searchSourceDto, SearchRequestDto searchRequestDto) {
        int from = calCurrentPage(searchRequestDto);
        int searchSize = setSearchSize(searchRequestDto);
        searchSourceDto.setSearchSourceBuilder(searchSourceDto.getSearchSourceBuilder()
                .size(searchSize)
                .from(from));
        return searchSourceDto;

    }

    private static int calCurrentPage(SearchRequestDto searchRequestDto) {
        if(searchRequestDto.getCurrentPage() ==0 || searchRequestDto.getCurrentPage() ==1) {
            return 0;
        }
        else {
            int size = setSearchSize(searchRequestDto);
            return (searchRequestDto.getCurrentPage() -1) * size;
        }
    }
    private static int setSearchSize(SearchRequestDto searchRequestDto) {
        int searchSize = SearchConfig.SEARCH_SIZE;
        if(searchRequestDto.getSearchSize() != 0) {
            searchSize = searchRequestDto.getSearchSize();
        }
        return searchSize;
    }

    public static SearchSourceDto buildZeroSizeQuery(SearchSourceDto searchSourceDto) {
        searchSourceDto.setSearchSourceBuilder(searchSourceDto.getSearchSourceBuilder()
                .size(0));
        return searchSourceDto;
    }

    public SearchSourceDto buildCustomSizeQuery(SearchSourceDto searchSourceDto,int size) {
        searchSourceDto.setSearchSourceBuilder(searchSourceDto.getSearchSourceBuilder()
                .size(size).from(0));
        return searchSourceDto;
    }
}
