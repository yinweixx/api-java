package com.anyun.cloud.common.sys;

import com.anyun.cloud.common.tools.db.AbstractEntity;

/**
 * 统一请求格式
 */
public class Requests<T> extends AbstractEntity {
    /**
     * 头信息
     */
    private Header header;

    /**
     * 业务名称
     */
    private String business;

    /**
     * 参数
     */
    private T content;


    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}
