package com.anyun.cloud.model.dto;

import com.anyun.cloud.common.tools.db.AbstractEntity;

/**
 * Created by jt on 18-1-31.
 */
public class ApiInfoDto extends AbstractEntity {
    public long id;//应用主键

    public long   appId;

    public String baseUrl; //基本路径

    public String displayName;//该api 的名称

    public String description;//该api  描述

    public String path; //路径　

    public String method;//请求方式 　GET、POST、PUT、DELETE

    public long apiVersionId;

    public long apiId;

    public String name;// 版本名称作为主键

    public String serviceId; // 绑定的服务服务id　
    String appName;
    String serviceName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setAppId(long appId) {
        this.appId = appId;
    }

    public long getApiVersionId() {
        return apiVersionId;
    }

    public void setApiVersionId(long apiVersionId) {
        this.apiVersionId = apiVersionId;
    }

    public long getApiId() {
        return apiId;
    }

    public void setApiId(long apiId) {
        this.apiId = apiId;
    }

    public boolean status; // true:可用(默认)   false:不可用





    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public String getPath() {
        return path;
    }

    public String getMethod() {
        return method;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public Long getAppId() {
        return appId;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public String getServiceId() {
        return serviceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppName() {
        return appName;
    }
}
