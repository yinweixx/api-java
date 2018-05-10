package com.anyun.cloud.model.param;

import com.anyun.cloud.common.tools.db.AbstractEntity;

import java.util.Map;

public class ApiVerificationParam extends AbstractEntity{
    private String BUSINESS=BUSINESS_ENUM.getApiVerification.name(); //对应调用方法的名称
    private String url;                 //请求路径
    private String method;              //请求方式
    private Map<String,Object> params;  //请求参数
    private String contentType;         //参数格式
    private String acceptType;          //接受格式

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getAcceptType() {
        return acceptType;
    }

    public void setAcceptType(String acceptType) {
        this.acceptType = acceptType;
    }
}
