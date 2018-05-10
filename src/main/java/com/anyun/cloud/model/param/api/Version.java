package com.anyun.cloud.model.param.api;

import com.anyun.cloud.common.tools.db.AbstractEntity;

import java.util.List;

public class Version extends AbstractEntity {
    /**
     * api表主键
     */
    private  long apiId;
    /**
     *  版本名称作为主键
     */
    public String name;
    /**
     * 方式 　GET、POST、PUT、DELETE（不能为空 且格式要正确）
     */
    private String method;

    private String serviceId;
    private  boolean status = false;

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isStatus() {
        return status;
    }

    /**
     * 路径　 (不能为空)
     */
    private String path;
    /**
     * 媒体类型 （不能为空并且至少要一个信息）
     */
    private List<MediaType> mediaTypeList;
    /**
     * 文档（可以为空）
     */
    private List<Documentation> documentationList;
    /**
     * // 返回  （可以为空）
     */
    private List<Response> responseList;
    /**（可以为空）
     */
    private List<RequestHeader> headerList;
    /**
     * （可以为空）
     */
    private RequestBody requestBody;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public List<MediaType> getMediaTypeList() {
        return mediaTypeList;
    }

    public void setMediaTypeList(List<MediaType> mediaTypeList) {
        this.mediaTypeList = mediaTypeList;
    }


    public List<Documentation> getDocumentationList() {
        return documentationList;
    }

    public void setDocumentationList(List<Documentation> documentationList) {
        this.documentationList = documentationList;
    }

    public List<Response> getResponseList() {
        return responseList;
    }

    public void setResponseList(List<Response> responseList) {
        this.responseList = responseList;
    }

    public List<RequestHeader> getHeaderList() {
        return headerList;
    }

    public void setHeaderList(List<RequestHeader> headerList) {
        this.headerList = headerList;
    }

    public RequestBody getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(RequestBody requestBody) {
        this.requestBody = requestBody;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setApiId(long apiId) {
        this.apiId = apiId;
    }

    public long getApiId() {
        return apiId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
