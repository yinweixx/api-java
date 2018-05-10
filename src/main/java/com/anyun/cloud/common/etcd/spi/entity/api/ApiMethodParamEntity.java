package com.anyun.cloud.common.etcd.spi.entity.api;

import com.anyun.cloud.common.tools.db.AbstractEntity;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 31/05/2017
 */
public class ApiMethodParamEntity extends AbstractEntity{
    private String name = "";
    private String description = "";
    private boolean required = false;
    private String type = "";
    private String example = "";

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

    @Override
    public String toString() {
        return "ApiMethodParamEntity {" + "\n" +
                "    name='" + name + "\'\n" +
                "    description='" + description + "\'\n" +
                "    required=" + required + "\n" +
                "    type='" + type + "\'\n" +
                "    example='" + example + "\'\n" +
                '}';
    }
}
