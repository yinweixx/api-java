package com.anyun.cloud.model.entity;

import com.anyun.cloud.common.tools.db.AbstractEntity;
import com.iciql.Iciql;

@Iciql.IQTable(name = "api_type_prop")
public class ApiInfoTypePropEntity extends AbstractEntity {
    @Iciql.IQColumn(name = "id", primaryKey = true, autoIncrement = true)
    private Long id;//主键

    @Iciql.IQColumn(name = "api_type_id")
    private Long apiTypeId;//api_type表主键

    @Iciql.IQColumn(name = "type", length = 20)
    private String type;

    @Iciql.IQColumn(name = "required", defaultValue = "false")
    private boolean required;

    @Iciql.IQColumn(name = "example", length = 50)
    private String example;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getApiTypeId() {
        return apiTypeId;
    }

    public void setApiTypeId(Long apiTypeId) {
        this.apiTypeId = apiTypeId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }
}
