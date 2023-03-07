package com.collapse.search.vo;


import com.collapse.search.config.SearchConfig;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
public class PageVo {

    private int currentPage;

    private int totalCount;

    private int naviSize = SearchConfig.NAVI_SIZE;

    private int pageSize;

    private int totalPage;

    private int beginPage;

    private int endPage;

    private boolean showPrev;

    private boolean showNext;

    public PageVo(int totalCount, int currentPage, int searchSize) {
        this.totalCount = totalCount;
        this.currentPage = currentPage;
        this.pageSize = setPageSize(searchSize);
        pagination(totalCount,currentPage);
    }


    private int setPageSize(int searchSize) {
        int pageSize = SearchConfig. SEARCH_SIZE;
        if(searchSize != 0) {
            pageSize = searchSize;
        }
        return pageSize;
    }


    private void pagination(int totalCount, int currentPage) {
        this.totalPage = (int) Math.ceil((double) totalCount/pageSize);
        this.beginPage = (currentPage -1) / naviSize * naviSize + 1;
        this.endPage = Math.min(beginPage + naviSize-1 , totalPage);
        this.showPrev = (beginPage != 1);
        this.showNext = (endPage != totalPage);
    }
}
