package com.anyun.cloud.model.entity;

import com.anyun.cloud.common.tools.db.AbstractEntity;
import com.iciql.Iciql;

import java.util.List;

/**
 * Api 版本
 */
@Iciql.IQTable(name = "api_version", create = true)
public class ApiInfoVersionEntity extends AbstractEntity {
    @Iciql.IQColumn(name = "id", primaryKey = true, autoIncrement = true)
    private Long id;// 主键id

    @Iciql.IQColumn(name = "api_id")
    private Long apiId;//api 表 id

    @Iciql.IQColumn(name = "name", length = 20)
    private String name;// 版本名称

    @Iciql.IQColumn(name = "service_id", length = 50)
    private String serviceId; // 绑定的服务服务id　

    private String serviceName;

    @Iciql.IQColumn(name = "status", defaultValue = "true")
    private boolean status; // true:可用(默认)   false:不可用

    @Iciql.IQColumn(name = "method", length = 10)
    private String method;//请求方式 　GET、POST、PUT、DELETE

    //路径　
    @Iciql.IQColumn(name = "path", length = 500)
    private String path;

    //最终路径
    @Iciql.IQColumn(name = "final_path", length = 500)
    private String finalPath;



    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    private List<ApiInfoDocumentationEntity> docList;// 文档列表

    private List<ApiInfoMediaTypeEntity> mediaTypeList;//媒体类型列表

    private List<ApiInfoMethodParamEntity> parametersList;// get/delete 请求hender列表

    private ApiInfoRequestBodyEntity requestBody;//put / post 求body


    List<ApiInfoResponseEntity> responseList;//返回列表

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

    public List<ApiInfoResponseEntity> getResponseList() {
        return responseList;
    }

    public void setResponseList(List<ApiInfoResponseEntity> responseList) {
        this.responseList = responseList;
    }

    public List<ApiInfoMethodParamEntity> getParametersList() {
        return parametersList;
    }

    public void setParametersList(List<ApiInfoMethodParamEntity> parametersList) {
        this.parametersList = parametersList;
    }

    public ApiInfoRequestBodyEntity getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(ApiInfoRequestBodyEntity requestBody) {
        this.requestBody = requestBody;
    }
    public List<ApiInfoDocumentationEntity> getDocList() {
        return docList;
    }

    public void setDocList(List<ApiInfoDocumentationEntity> docList) {
        this.docList = docList;
    }

    public List<ApiInfoMediaTypeEntity> getMediaTypeList() {
        return mediaTypeList;
    }

    public void setMediaTypeList(List<ApiInfoMediaTypeEntity> mediaTypeList) {
        this.mediaTypeList = mediaTypeList;
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
