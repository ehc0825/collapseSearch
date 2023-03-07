package com.collapse.search.util.query.search.common.category;

import com.collapse.search.dto.SearchSourceDto;
import com.collapse.search.vo.CategoryVo;
import lombok.experimental.UtilityClass;
import org.elasticsearch.index.query.BoolQueryBuilder;

@UtilityClass
public class CategoryQueryBuilder {

    public SearchSourceDto buildCategoryQuery(SearchSourceDto searchSourceDto, CategoryVo categoryVo) {
        BoolQueryBuilder boolQueryBuilder = CategoryEnum.buildCategoryQueryByCategory(categoryVo.getCategory(),
                searchSourceDto.getBoolQueryBuilder());
        return searchSourceDto;
    }

}
