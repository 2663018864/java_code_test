package com.milo.domain;

import java.util.List;
//分页对象
public class BeanPage<T> {
    private Integer totalCount; //总记录数
    private Integer totalPage; //总页数
    private List<T> list; //当前页展示的数据
    private Integer currentPage; //当前页数
    private Integer rows; //每页展示的条数

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BeanPage{");
        sb.append("totalCount=").append(totalCount);
        sb.append(", totalPage=").append(totalPage);
        sb.append(", list=").append(list);
        sb.append(", currentPage=").append(currentPage);
        sb.append(", rows=").append(rows);
        sb.append('}');
        return sb.toString();
    }
}
