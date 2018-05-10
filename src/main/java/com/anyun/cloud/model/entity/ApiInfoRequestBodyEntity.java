package com.anyun.cloud.model.entity;

import com.anyun.cloud.common.tools.db.AbstractEntity;
import com.iciql.Iciql;

@Iciql.IQTable(name = "api_request_body")
public class ApiInfoRequestBodyEntity extends AbstractEntity {
    @Iciql.IQColumn(name = "api_version_id", primaryKey = true)
    private Long apiVersionId;// 主键  (api表主键)

    @Iciql.IQColumn(name = "content_type", length = 300)
    private String contentType;//请求body 类型

    @Iciql.IQColumn(name = "description", length = 4000)
    private String description;
//    private ApiInfoTypeEntity type;

    public void setApiVersionId(Long apiVersionId) {
        this.apiVersionId = apiVersionId;
    }

    public Long getApiVersionId() {
        return apiVersionId;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
//
//    public ApiInfoTypeEntity getType() {
//        return type;
//    }
//
//    public void setType(ApiInfoTypeEntity type) {
//        this.type = type;
//    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
