package com.anyun.cloud.model.dto;

import com.anyun.cloud.common.tools.db.AbstractEntity;

/**
 * Created by jt on 18-3-22.
 */
public class ApiVersionDto extends AbstractEntity {

    //主键
    private Long id;

    //应用主键
    private Long appId;

    //基本路径
    private String baseUrl;

    //该api 的名称
    private String displayName;

    //该api  描述
    private String description;

    private Long apiVersionid;// 主键idI

    private Long apiId;//api 表 id


    private String name;// 版本名称


    private String serviceId; // 绑定的服务服务id　




    private boolean status; // true:可用(默认)   false:不可用

    private String method;//请求方式 　GET、POST、PUT、DELETE

    //路径　
    private String path;

    //最终路径
    private String finalPath;


    private String appName;

    private String serviceName;

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }




    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getApiVersionid() {
        return apiVersionid;
    }

    public void setApiVersionid(Long apiVersionid) {
        this.apiVersionid = apiVersionid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getApiId() {
        return apiId;
    }

    public void setApiId(Long apiId) {
        this.apiId = apiId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }


    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getFinalPath() {
        return finalPath;
    }

    public void setFinalPath(String finalPath) {
        this.finalPath = finalPath;
    }
}
