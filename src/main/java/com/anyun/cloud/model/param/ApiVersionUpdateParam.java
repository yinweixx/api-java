package com.anyun.cloud.model.param;

import com.anyun.cloud.common.etcd.spi.entity.api.ApiResponseEntity;
import com.anyun.cloud.common.tools.db.AbstractEntity;
import com.anyun.cloud.model.entity.ApiInfoMethodParamEntity;
import com.anyun.cloud.model.entity.ApiInfoRequestBodyEntity;
import com.anyun.cloud.model.entity.ApiInfoResponseEntity;
import com.anyun.cloud.model.param.api.Response;

import java.util.List;

/**
 * Created by jt on 18-3-7.
 */
public class ApiVersionUpdateParam extends AbstractEntity{
    private Long id;

    private String name;

    private String serviceId;

    private String serviceName;

    private String status;

    private String path;

    private String method;



    List<ApiInfoMethodParamEntity> parametersList;// get/delete 请求hender列表

    ApiInfoRequestBodyEntity requestBody;//put / post 求body

    private List<Response> responseList;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
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


    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setResponseList(List<Response> responseList) {
        this.responseList = responseList;
    }

    public List<Response> getResponseList() {
        return responseList;
    }
}
