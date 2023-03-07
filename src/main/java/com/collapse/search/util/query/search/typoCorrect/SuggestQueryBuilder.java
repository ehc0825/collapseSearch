package com.collapse.search.util.query.search.typoCorrect;

import com.collapse.search.config.IoStudioConfig;
import com.collapse.search.config.SearchConfig;
import com.collapse.search.dto.SearchSourceDto;
import lombok.experimental.UtilityClass;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.SuggestionBuilder;
import org.springframework.util.StringUtils;

@UtilityClass
public class SuggestQueryBuilder {

    public SearchSourceDto buildSuggestQuery(String query, SearchSourceDto searchSourceDto) {
        if(isQueryNotEmpty(query)) {
            SuggestionBuilder suggestionBuilder = SuggestBuilders.termSuggestion(IoStudioConfig.SUGGEST_FIELD).text(query);
            SuggestBuilder suggestBuilder = new SuggestBuilder().addSuggestion(IoStudioConfig.SUGGEST_NAME, suggestionBuilder);
            searchSourceDto.setSearchSourceBuilder(searchSourceDto.getSearchSourceBuilder().suggest(suggestBuilder));
        }
        return searchSourceDto;
    }

    private static boolean isQueryNotEmpty(String query) {
        return !StringUtils.isEmpty(query);
    }
}
