package com.anyun.cloud.model.param;

import com.anyun.cloud.common.tools.db.AbstractEntity;

public class ElasticSettingParam extends AbstractEntity {
    private Integer minLink;        //最小链接数
    private Integer maxLink;        //最大链接数

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
