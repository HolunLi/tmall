package com.holun.tmall.util;

/**
 * 分页工具
 */
public class Page {
    //开始位置
    private int start;
    //每页显示多少条数据
    private int pageSize;
    //总共多少条数据
    private int total;
    //在属性管理页面，将所有属性进行分页显示时，提供参数
    private String param;
    //默认每页显示5条
    private static final int defaultPageSize = 5;

    public Page() {
        pageSize = defaultPageSize;
        param = "";
    }

    public Page(int start, int pageSize) {
        this.start = start;
        this.pageSize = pageSize;
    }

    /**
     * 根据总共多少条数据和每页显示多少个数据，计算页面总数
     */
    public int getTotalPage() {
        int totalPage;

        totalPage = total % pageSize == 0 ? (total / pageSize) : (total / pageSize + 1);

        if (totalPage == 0)
            totalPage = 1;

        return totalPage;
    }

    /**
     * 计算当前是第几页
     */
    public int getCurrentPage() {
        return start / pageSize + 1;
    }

    /**
     * 计算当前页是从第几个数据开始显示的
     */
    public int getCurrentPageStart(int currentPage) {
        return currentPage * pageSize - pageSize;
    }

    /**
     * 根据总共多少条数据和每页显示多少个数据，计算最后一页的数值是多少（简单讲就是最后一页，是从第几个数据开始显示的）
     */
    public int getLastPage() {
        int lastPage;

        lastPage = total % pageSize == 0 ? (total - pageSize) : (total - total % pageSize);
        lastPage = lastPage < 0 ? 0 : lastPage;

        return lastPage;
    }

    /**
     * 判断是否有上一页
     */
    public boolean isHasPrevious(){
        if(start == 0)
            return false;
        return true;
    }

    /**
     * 判断是否有下一页
     */
    public boolean isHasNext(){
        if(start == getLastPage())
            return false;
        return true;
    }

    /**
     *追加param
     */
   public void appendParam(String sort) {
       this.setParam(this.getParam() + "&sort=" + sort);
   }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

}
