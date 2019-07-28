package com.zy.common;

import java.util.ArrayList;
import java.util.List;

public class Page {
    /** 1.每页显示数量(everyPage) */
    private int size;
    /** 2.总记录数(totalCount) */
    private int total;
    /** 3.总页数(totalPage) */
    private int pages;
    /** 4.当前页(currentPage) */
    private int page;
    /** 5.起始点(beginIndex) */
    private int index;
    /** 6.是否有上一页(hasPrePage) */
    private boolean hasPrePage;
    /** 7.是否有下一页(hasNextPage) */
    private boolean hasNextPage;
    /** 当前页数据集 **/
    private List<?> rows = new ArrayList<Object>();

    /**
     * 
     * 创建一个新的实例 Page.
     *
     * @param rows 数据集
     * @param size 每页显示数量
     * @param total 总记录数
     * @param pages 总页数
     * @param page 当前页
     * @param index 起始点
     * @param hasPrePage 是否有上一页
     * @param hasNextPage 是否有下一页
     */
    public Page(List<?> rows, int size, int total, int pages, int page, int index, boolean hasPrePage,
            boolean hasNextPage) {
        this.size = size;
        this.total = total;
        this.pages = pages;
        this.page = page;
        this.index = index;
        this.hasPrePage = hasPrePage;
        this.hasNextPage = hasNextPage;
        if (rows != null)
            this.rows = rows;
    }

    /**
     * 
     * 创建一个新的实例 Page.
     *
     * @param rows 数据集
     * @param size 每页显示数量
     * @param total 总记录数
     * @param page 当前页
     */
    public Page(List<?> rows, int size, int total, int page) {
        this.total = total;
        this.size = size;
        this.page = page;
        if (rows != null)
            this.rows = rows;
        else
            this.rows = new ArrayList<Object>();
    }

    // 构造函数，默认
    public Page() {
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isHasPrePage() {
        return hasPrePage;
    }

    public void setHasPrePage(boolean hasPrePage) {
        this.hasPrePage = hasPrePage;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }

}
