package com.anyun.cloud.common.etcd.spi.entity.api;

import java.util.LinkedList;
import java.util.List;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 31/05/2017
 */
public class ApiResourceEntity {
    private long deployDateTime;
    private String path;
    private String name;
    private String desc;
    private String method;
    private List<ApiMethodParamEntity> parameters = new LinkedList<>();
    private List<ApiResponseEntity> responses = new LinkedList<>();
    private ApiRequestBody requestBody;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public List<ApiMethodParamEntity> getParameters() {
        return parameters;
    }

    public void setParameters(List<ApiMethodParamEntity> parameters) {
        this.parameters = parameters;
    }

    public void addParameter(ApiMethodParamEntity paramEntity) {
        parameters.add(paramEntity);
    }

    public ApiRequestBody getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(ApiRequestBody requestBody) {
        this.requestBody = requestBody;
    }

    public List<ApiResponseEntity> getResponses() {
        return responses;
    }

    public void setResponses(List<ApiResponseEntity> responses) {
        this.responses = responses;
    }

    public void addResponse(ApiResponseEntity responseEntity) {
        responses.add(responseEntity);
    }

    public long getDeployDateTime() {
        return deployDateTime;
    }

    public void setDeployDateTime(long deployDateTime) {
        this.deployDateTime = deployDateTime;
    }

    @Override
    public String toString() {
        return "ApiResourceEntity {" + "\n" +
                "    path='" + path + "\'\n" +
                "    name='" + name + "\'\n" +
                "    desc='" + desc + "\'\n" +
                "    method='" + method + "\'\n" +
                "    parameters=" + parameters + "\n" +
                "    responses=" + responses + "\n" +
                "    requestBody=" + requestBody + "\n" +
                '}';
    }
}
