package me.ianhe.isite.model;

import java.util.Collection;

/**
 * 分页信息
 *
 * @author iHelin
 * @since 2017/11/13 17:30
 */
public class Pagination<T> {

    /**
     * 总个数
     */
    private long totalCount;
    /**
     * 总页数
     */
    private int totalPageNum;
    /**
     * 当前页
     */
    private int currentPage;
    /**
     * 状态
     */
    private String status;

    /**
     * 数据长度
     */
    private int pageLength;

    /**
     * 数据
     */
    private Collection data;

    public Pagination(Collection<? extends T> data, long totalCount, int currentPage, int pageLength) {
        if (totalCount <= 0) {
            totalCount = 0;
        }
        if (currentPage <= 0) {
            currentPage = 1;
        }
        if (pageLength <= 0) {
            pageLength = 1;
        }
        this.pageLength = pageLength;
        this.totalCount = totalCount;
        this.currentPage = currentPage;
        this.data = data;
        this.status = "success";
        this.totalPageNum = (int) Math.ceil((double) totalCount / pageLength);
    }

    public long getTotalCount() {
        return totalCount;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getTotalPageNum() {
        return totalPageNum;
    }

    public Collection getData() {
        return data;
    }

    public String getStatus() {
        return status;
    }

    public int getPageLength() {
        return pageLength;
    }
}
