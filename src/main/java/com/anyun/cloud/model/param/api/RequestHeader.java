package com.anyun.cloud.model.param.api;

import com.anyun.cloud.common.tools.db.AbstractEntity;

public class RequestHeader extends AbstractEntity{
    private String name;//名称
    private String description;//描述
    private boolean required=false;//是否必填
    private String type;//类型
    private String example;//示例

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
