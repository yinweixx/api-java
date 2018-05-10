package com.anyun.cloud.model.entity;

import com.anyun.cloud.common.tools.db.AbstractEntity;
import com.iciql.Iciql.IQColumn;
import com.iciql.Iciql.IQTable;

@IQTable(name= "elastic_setting")
public class ElasticSettingEntity extends AbstractEntity {
    @IQColumn(name = "id",primaryKey = true,autoIncrement = false)
    private Long id;              //id
    @IQColumn(name = "min_link",length = 20)
    private Integer minLink;        //最小链接数
    @IQColumn(name = "max_link",length = 20)
    private Integer maxLink;        //最大链接数


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMinLink() {
        return minLink;
    }

    public void setMinLink(Integer minLink) {
        this.minLink = minLink;
    }

    public Integer getMaxLink() {
        return maxLink;
    }

    public void setMaxLink(Integer maxLink) {
        this.maxLink = maxLink;
    }
}
