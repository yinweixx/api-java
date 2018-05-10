package com.anyun.cloud.model.entity;

import com.anyun.cloud.common.tools.db.AbstractEntity;
import com.iciql.Iciql;

/**
 * Api 参数
 */
@Iciql.IQTable(name = "api_method_param", create = true)
public class   ApiInfoMethodParamEntity  extends AbstractEntity {
    @Iciql.IQColumn(name = "id", primaryKey = true, autoIncrement = true)
    private Long id; //主键

    @Iciql.IQColumn(name = "api_version_id")
    private Long apiVersionId; //Api 表 主键

    @Iciql.IQColumn(name = "name", length = 20)
    private String name;//名称

    @Iciql.IQColumn(name = "description", length = 100)
    private String description;//描述

    @Iciql.IQColumn(name = "required", defaultValue = "false")
    private boolean required;//是否必填

    @Iciql.IQColumn(name = "type", length = 20)
    private String type;//类型

    @Iciql.IQColumn(name = "example", length = 20)
    private String example;//示例

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getApiVersionId() {
        return apiVersionId;
    }

    public void setApiVersionId(Long apiVersionId) {
        this.apiVersionId = apiVersionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }
}
