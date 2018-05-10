package com.anyun.cloud.common.etcd.spi.entity.api;

import com.anyun.cloud.common.tools.db.AbstractEntity;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 01/06/2017
 */
public class ApiRequestBody extends AbstractEntity{
    private String contentType;
    private ApiTypeEntity apiTypeEntity;

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public ApiTypeEntity getApiTypeEntity() {
        return apiTypeEntity;
    }

    public void setApiTypeEntity(ApiTypeEntity apiTypeEntity) {
        this.apiTypeEntity = apiTypeEntity;
    }

    @Override
    public String toString() {
        return "ApiRequestBody {" + "\n" +
                "    contentType='" + contentType + "\'\n" +
                "    apiTypeEntity=" + apiTypeEntity + "\n" +
                '}';
    }
}
