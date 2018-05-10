package com.anyun.cloud.common.sys;

import com.anyun.cloud.common.tools.db.AbstractEntity;

/**
 * Created by sxt on 8/28/17.
 */
public class Response<T> extends AbstractEntity {
    private T   content;
    private int code;

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
