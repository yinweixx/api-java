package com.anyun.cloud.model.entity;

import com.anyun.cloud.common.tools.db.AbstractEntity;
import com.iciql.Iciql;

import java.util.LinkedList;
import java.util.List;

@Iciql.IQTable(name = "api_type")
public class ApiInfoTypeEntity extends AbstractEntity {
    @Iciql.IQColumn(name = "id", primaryKey = true, autoIncrement = true)
    private Long id;// 主键

    @Iciql.IQColumn(name = "api_response_id")
    private Long apiResponseId;

    @Iciql.IQColumn(name = "api_request_body_id")
    private Long apiRequestBodyId;  //apiversiomId

    @Iciql.IQColumn(name = "name", length = 20)
    private String name;

    private List<ApiInfoTypePropEntity> typePropList = new LinkedList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getApiResponseId() {
        return apiResponseId;
    }

    public void setApiResponseId(Long apiResponseId) {
        this.apiResponseId = apiResponseId;
    }

    public Long getApiRequestBodyId() {
        return apiRequestBodyId;
    }

    public void setApiRequestBodyId(Long apiRequestBodyId) {
        this.apiRequestBodyId = apiRequestBodyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ApiInfoTypePropEntity> getTypePropList() {
        return typePropList;
    }

    public void setTypePropList(List<ApiInfoTypePropEntity> typePropList) {
        this.typePropList = typePropList;
    }
}
