package com.anyun.cloud.model.entity;

import com.anyun.cloud.common.tools.db.AbstractEntity;
import com.iciql.Iciql;

/**
 * Api mediaType
 */
@Iciql.IQTable(name = "api_media_type", create = true)
public class ApiInfoMediaTypeEntity extends AbstractEntity{
    @Iciql.IQColumn(name = "id", primaryKey = true, autoIncrement = true)
    private Long id;//主键

    @Iciql.IQColumn(name = "api_version_id")
    private Long apiVersionId;//api id

    @Iciql.IQColumn(name = "name",length = 100)
    private String name;//api id

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
