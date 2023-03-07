package com.collapse.search.util.query.aggregation;

import com.collapse.search.dto.SearchSourceDto;
import lombok.experimental.UtilityClass;
import org.elasticsearch.search.aggregations.metrics.StatsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;

@UtilityClass
public class StatsAggBuilder {

    public SearchSourceDto buildStatsAgg(SearchSourceDto searchSourceDto, String field) {
        SearchSourceBuilder searchSourceBuilder = searchSourceDto.getSearchSourceBuilder();
        searchSourceBuilder = addStatsAggregator(searchSourceBuilder,field);
        searchSourceDto.setSearchSourceBuilder(searchSourceBuilder);
        return searchSourceDto;
    }

    private static SearchSourceBuilder addStatsAggregator(SearchSourceBuilder searchSourceBuilder, String field) {
        StatsAggregationBuilder statsAggregationBuilder = new StatsAggregationBuilder(field);
        statsAggregationBuilder.field(field);
        searchSourceBuilder.aggregation(statsAggregationBuilder);
        return searchSourceBuilder;
    }
}
