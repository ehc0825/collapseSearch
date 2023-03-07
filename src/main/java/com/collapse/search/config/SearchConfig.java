package com.collapse.search.config;

public class SearchConfig {


    /**
     * 검색 인덱스
     */
    public static final String SEARCH_INDEX = "search-index";


    /**
     * 검색 필드
     */
    public static final String[] DEFAULT_SEARCH_FIELD = {"title","contents"};


    /**
     * page
     */
    public static final int SEARCH_HISTORY_SIZE = 10;
    public static final int SEARCH_SIZE = 10;
    public static final int NAVI_SIZE = 5;


    /**
     * collapse
     */
    public static final String FIELD_FOR_COLLAPSE = "collapse";
    public static final String COLLAPSE_COUNT_AGG_NAME = "collapse_count";

    public static final String[] STATS_AGGREGATION_FIELDS = {};

    /**
     * category
     */
    public static final String[] DEFAULT_SEARCH_CATEGORIES = {"20","21","31"};
    public static final String CATEGORY_FIELD = "category";

    /**
     * highlight
     */
    public static final String HIGHLIGHT_COLOR = "red";
    public static final String HIGHLIGHT_PRE_TAG = "<strong style=\"color:" + HIGHLIGHT_COLOR +";\">";
    public static final String HIGHLIGHT_POST_TAG = "</strong>";
    public static final int HIGHLIGHT_NUM_OF_FRAGMENTS = 1;
    public static final int HIGHLIGHT_NO_MATCH_SIZE = 250;
    public static final int HIGHLIGHT_FRAGMENT_SIZE = 250;
    public static final String HIGHLIGHT_FIELD_TAIL = "_highlight";

    /**
     * sort
     */
    public static final String DATE_FIELD = "created-date";

    public SearchConfig() {
        throw new IllegalStateException("Can not be instantiated");
    }

}
