package com.collapse.search.util.query.search;

import com.collapse.search.config.IoStudioConfig;
import com.collapse.search.config.SearchConfig;
import com.collapse.search.dto.SearchSourceDto;
import com.collapse.search.util.query.search.common.sort.SortEnum;
import com.collapse.search.vo.AutoCompleteVo;
import lombok.experimental.UtilityClass;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;

@UtilityClass
public class AutoCompleteQueryBuilder {


    public SearchSourceDto buildAutoCompleteQuery(AutoCompleteVo autoCompleteVo, SearchSourceDto searchSourceDto) {
        BoolQueryBuilder autoCompleteBoolQuery = new BoolQueryBuilder();
        autoCompleteBoolQuery.should(QueryBuilders.matchQuery(autoCompleteVo.getQuery(), autoCompleteVo.getQuery())
                .operator(Operator.AND));
        autoCompleteBoolQuery.should(QueryBuilders.matchQuery(IoStudioConfig.SEARCH_AUTOCOMPLETE_FIELD,
                autoCompleteVo.getQuery()).operator(Operator.AND));
        searchSourceDto.setBoolQueryBuilder(searchSourceDto.getBoolQueryBuilder().must(autoCompleteBoolQuery));
        searchSourceDto.getSearchSourceBuilder().from(autoCompleteVo.getFrom()).size(autoCompleteVo.getSize());
        searchSourceDto.setSearchSourceBuilder(SortEnum.buildSortOrderBySort("short",searchSourceDto.getSearchSourceBuilder()));
        return searchSourceDto;
    }
}
