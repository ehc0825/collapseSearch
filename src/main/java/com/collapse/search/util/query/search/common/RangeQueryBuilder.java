package com.collapse.search.util.query.search.common;

import com.collapse.search.dto.SearchSourceDto;
import lombok.experimental.UtilityClass;
import org.elasticsearch.index.query.QueryBuilders;

@UtilityClass
public class RangeQueryBuilder {

    public SearchSourceDto searchSourceDto(String field, double min, double max, SearchSourceDto searchSourceDto) {
        if(isMinRange(min,max)) {
            buildMinRangeQuery(min,field,searchSourceDto);
        }
        else if(isBothRange(min,max)) {
            buildBothRangeQuery(min,max,field,searchSourceDto);
        }else if(isMaxRange(min,max)) {
            buildMaxRangeQuery(max,field,searchSourceDto);
        }
        return searchSourceDto;
    }

    public SearchSourceDto buildDateRangeQuery(String field, String startDate, String endDate, String dateFormat,
                                               SearchSourceDto searchSourceDto) {
        searchSourceDto.setBoolQueryBuilder(searchSourceDto.getBoolQueryBuilder()
                .must(QueryBuilders.rangeQuery(field).gte(startDate).lte(endDate).format(dateFormat)));
        return searchSourceDto;
    }

    private static void buildMaxRangeQuery(double max, String field, SearchSourceDto searchSourceDto) {
        searchSourceDto.setBoolQueryBuilder(searchSourceDto.getBoolQueryBuilder()
                .must(QueryBuilders.rangeQuery(field).lte(max)));
    }

    private static void buildBothRangeQuery(double min, double max, String field, SearchSourceDto searchSourceDto) {
        searchSourceDto.setBoolQueryBuilder(searchSourceDto.getBoolQueryBuilder()
                .must(QueryBuilders.rangeQuery(field).gte(min).lte(max)));
    }

    private static void buildMinRangeQuery(double min, String field, SearchSourceDto searchSourceDto) {
        searchSourceDto.setBoolQueryBuilder(searchSourceDto.getBoolQueryBuilder()
                .must(QueryBuilders.rangeQuery(field).gte(min)));
    }
    private static boolean isMinRange(double min, double max) {
        return min != 0 && max ==0;
    }
    private static boolean isBothRange(double min, double max) {
        return min !=0 && max !=0;
    }
    private static boolean isMaxRange(double min, double max) {
        return min == 0 && max !=0;
    }

}
