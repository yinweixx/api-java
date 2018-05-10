package com.anyun.cloud.model.param;

import com.anyun.cloud.common.tools.db.AbstractEntity;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by jt on 18-3-7.
 */
public class ApiResponseUpdateParam extends AbstractEntity{
    /**
     *  主键
     */
    private Long id;
    /**
     *  错误代码
     */
    private String code;
    /**
     *  参数格式
     */
    private String contentType;

    private String name;
//    private List<ApiTypePropUpdateParam> typePropList = new LinkedList<>();
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
//
//    public List<ApiTypePropUpdateParam> getTypePropList() {
//        return typePropList;
//    }
//
//    public void setTypePropList(List<ApiTypePropUpdateParam> typePropList) {
//        this.typePropList = typePropList;
//    }
}
