package com.collapse.search.util.query.aggregation;

import com.collapse.search.config.IoStudioConfig;
import com.collapse.search.config.SearchConfig;
import com.collapse.search.dto.SearchSourceDto;
import lombok.experimental.UtilityClass;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.aggregations.metrics.ScriptedMetricAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.collapse.CollapseBuilder;

import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class CollapseCountAggBuilder {

    private static final Map<String,Object> scriptParamMap = createScriptParamMap();

    public SearchSourceDto buildCollapseCountAgg(SearchSourceDto searchSourceDto) {
        SearchSourceBuilder searchSourceBuilder = searchSourceDto.getSearchSourceBuilder();
        searchSourceBuilder = buildCollapseQuery(searchSourceBuilder);
        searchSourceBuilder = buildCountAggregation(searchSourceBuilder);
        searchSourceDto.setSearchSourceBuilder(searchSourceBuilder);
        return searchSourceDto;
    }
    public SearchSourceBuilder buildCollapseQuery(SearchSourceBuilder searchSourceBuilder, String collapseField) {
        CollapseBuilder collapseBuilder = new CollapseBuilder(collapseField);
        searchSourceBuilder.collapse(collapseBuilder);
        return searchSourceBuilder;
    }

    public SearchSourceDto buildCollapseForHistory(SearchSourceDto searchSourceDto) {
        SearchSourceBuilder searchSourceBuilder = searchSourceDto.getSearchSourceBuilder();
        searchSourceBuilder = buildCollapseQuery(searchSourceBuilder, IoStudioConfig.SEARCH_HISTORY_KEYWORD_FIELD);
        searchSourceDto.setSearchSourceBuilder(searchSourceBuilder);
        return searchSourceDto;
    }

    private static SearchSourceBuilder buildCountAggregation(SearchSourceBuilder searchSourceBuilder) {
        ScriptedMetricAggregationBuilder scriptedMetricAggregationBuilder
                = new ScriptedMetricAggregationBuilder(SearchConfig.COLLAPSE_COUNT_AGG_NAME);
        scriptedMetricAggregationBuilder.params(
                scriptParamMap
        );
        scriptedMetricAggregationBuilder.initScript(
                new Script("state.list = []")
        );
        scriptedMetricAggregationBuilder.mapScript(
                new Script("if(doc[params.fieldName] != null)\n" +
                        "            {\n"+
                        "                 state.list.add(doc[params.fieldName].value);\n" +
                        "            }")
        );
        scriptedMetricAggregationBuilder.combineScript(
                new Script("return state.list;")
        );
        scriptedMetricAggregationBuilder.reduceScript(
                new Script("Map uniqueValueMap = new HashMap();\n" +
                        "                 int count = 0;\n" +
                        "                 for (shardList in states)\n" +
                        "                 {\n" +
                        "                     if(shardList != null)\n" +
                        "                     {\n" +
                        "                       for(key in shardList) {\n" +
                        "                          if(!uniqueValueMap.containsKey(key)) {\n" +
                        "                             count +=1;\n" +
                        "                             uniqueValueMap.put(key, key);\n" +
                        "                           }\n" +
                        "                     }\n" +
                        "                   }\n" +
                        "                 }\n" +
                        "                 return count;")
        );
        searchSourceBuilder.aggregation(scriptedMetricAggregationBuilder);
        return searchSourceBuilder;
    }

    private static SearchSourceBuilder buildCollapseQuery(SearchSourceBuilder searchSourceBuilder) {
        CollapseBuilder collapseBuilder = new CollapseBuilder(SearchConfig.FIELD_FOR_COLLAPSE);
        searchSourceBuilder.collapse(collapseBuilder);
        return searchSourceBuilder;
    }

    private static Map<String, Object> createScriptParamMap() {
        Map<String,Object> scriptParamMap = new HashMap<>();
        scriptParamMap.put("fieldName", SearchConfig.FIELD_FOR_COLLAPSE);
        return scriptParamMap;
    }
}
