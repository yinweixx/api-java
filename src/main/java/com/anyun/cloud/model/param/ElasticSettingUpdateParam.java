package com.anyun.cloud.model.param;

import com.anyun.cloud.common.tools.db.AbstractEntity;

public class ElasticSettingUpdateParam extends AbstractEntity {
    private Long id;
    private Integer minLink;        //最小链接数
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
