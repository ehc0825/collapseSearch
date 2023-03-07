package com.collapse.search.util.query.search.common;


import com.collapse.search.config.SearchConfig;
import com.collapse.search.dto.SearchSourceDto;
import lombok.experimental.UtilityClass;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;

@UtilityClass
public class HighlightQueryBuilder {

    public SearchSourceDto buildQuery(SearchSourceDto searchSourceDto) {
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags(SearchConfig.HIGHLIGHT_PRE_TAG)
                .postTags(SearchConfig.HIGHLIGHT_POST_TAG)
                .numOfFragments(SearchConfig.HIGHLIGHT_NUM_OF_FRAGMENTS)
                .noMatchSize(SearchConfig.HIGHLIGHT_NO_MATCH_SIZE)
                .fragmentSize(SearchConfig.HIGHLIGHT_FRAGMENT_SIZE);
        searchSourceDto.setSearchSourceBuilder(searchSourceDto.getSearchSourceBuilder()
                .highlighter(setHighlightFields(highlightBuilder,searchSourceDto.getHighlightFields())));
        return searchSourceDto;
    }

    private static HighlightBuilder setHighlightFields(HighlightBuilder highlightBuilder, String[] highlightFields) {
        for(String field : highlightFields) {
            highlightBuilder.field(field);
        }
        return highlightBuilder;
    }
}
