package com.anyun.cloud.common.etcd.spi.entity.api;

import com.anyun.cloud.common.tools.db.AbstractEntity;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 01/06/2017
 */
public class ApiTypePropEntity extends AbstractEntity{
    private String type;
    private boolean required;
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

    @Override
    public String toString() {
        return "ApiTypePropEntity {" + "\n" +
                "    type='" + type + "\'\n" +
                "    required=" + required + "\n" +
                "    example='" + example + "\'\n" +
                '}';
    }
}
