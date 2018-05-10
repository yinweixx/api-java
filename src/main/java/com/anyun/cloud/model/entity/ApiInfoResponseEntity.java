package com.anyun.cloud.model.entity;

import com.anyun.cloud.common.tools.db.AbstractEntity;
import com.iciql.Iciql;

@Iciql.IQTable(name = "api_response", create = true)
public class ApiInfoResponseEntity extends AbstractEntity {
    @Iciql.IQColumn(name = "id", primaryKey = true, autoIncrement = true)
    private Long id;// 主键
    @Iciql.IQColumn(name = "api_version_id", primaryKey = true)
    private Long apiVersionId;// api id
    @Iciql.IQColumn(name = "code")
    private int code;//错误代码
    @Iciql.IQColumn(name = "content_type")
    private String contentType;//参数格式
    @Iciql.IQColumn(name = "description", length = 4000)
    private String description;
//    private ApiInfoTypeEntity type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setApiVersionId(Long apiVersionId) {
        this.apiVersionId = apiVersionId;
    }

    public Long getApiVersionId() {
        return apiVersionId;
    }

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

//    public ApiInfoTypeEntity getType() {
//        return type;
//    }
//
//    public void setType(ApiInfoTypeEntity type) {
//        this.type = type;
//    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
