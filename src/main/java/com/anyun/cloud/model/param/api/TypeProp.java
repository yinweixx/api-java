package com.anyun.cloud.model.param.api;

import com.anyun.cloud.common.tools.db.AbstractEntity;

public class TypeProp extends AbstractEntity {
    private String type;
    public boolean required=false;
    private String example;

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
