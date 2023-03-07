package com.collapse.search.config;

public class IoStudioConfig {

    /**
     * 오타교정
     */
    public static final String TYPO_CORRECT_INDEX = "typocorrect-dict";

    public static final String SUGGEST_FIELD = "keyword.suggest";

    public static final String SUGGEST_NAME = "typo_correct";


    /**
     * search Log
     */
    public static final String SEARCH_DOMAIN = "0번사전";

    public static final String SEARCH_LOG_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String SEARCH_LOG_INDEX = "search-log";


    /**
     * auto complete
     */
    public static final String SEARCH_AUTOCOMPLETE_INDEX = "autocomplete-index";

    public static final String SEARCH_AUTOCOMPLETE_FIELD = "keyword.ngram";

    public static final String SEARCH_AUTOCOMPLETE_SEARCH_FIELD = "keyword";

    public static final String SEARCH_AUTOCOMPLETE_LENGTH_FIELD = "len";

    /**
     * search history
     */
    public static final String SEARCH_HISTORY_KEYWORD_FIELD = "query";

    public static final String SEARCH_HISTORY_USER_FIELD = "user";


    public IoStudioConfig() {
        throw new IllegalStateException("Can not be instantiated");
    }

}
