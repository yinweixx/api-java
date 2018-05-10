package com.anyun.cloud.model.param.api;

import com.anyun.cloud.common.tools.db.AbstractEntity;

public class Response extends AbstractEntity {
    /**
     * 错误代码
     */
    private int code;

    /**
     * 参数格式
     */
    private String contentType;
    private String description;
//    private Type type;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

//    public Type getType() {
//        return type;
//    }
//
//    public void setType(Type type) {
//        this.type = type;
//    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
