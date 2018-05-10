package com.anyun.cloud.model.dto;

import com.anyun.cloud.common.tools.db.AbstractEntity;

import java.util.List;

/**
 * 分页统一返回格式
 */
public class PageDto <T> extends AbstractEntity {
    private int index;      // 页码
    private int limit;      // 每页记录数目
    private int total;      // 总记录
    private List<T> data;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
