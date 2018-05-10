package com.anyun.cloud.model.param;

import com.anyun.cloud.common.tools.db.AbstractEntity;

/**
 * Created by jt on 18-3-7.
 */
public class ApiMethodParamUpdateParam extends AbstractEntity {
    /**
     * ａｐｉ主键
     */
    private Long id;
    /**
     * 名称
     */
    private String name;
    /**
     * 描述
     */
    private String description;
    /**
     *是否必填(true or false)
     */
    private String required;
    /**
     * 类型
     */
    public String type;
    /**
     * 示例
     */
    private String example;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String isRequired() {
        return required;
    }

    public void setRequired(String required) {
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
