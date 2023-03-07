package com.collapse.search.util.query.search.common.category;

import com.collapse.search.config.SearchConfig;
import org.elasticsearch.index.query.BoolQueryBuilder;

public abstract class AbstractCategory {

    protected String category;

    protected String categoryName;

    protected String categoryField = SearchConfig.CATEGORY_FIELD;

    public abstract BoolQueryBuilder buildCategoryQuery(BoolQueryBuilder boolQueryBuilder);

}
