package com.anyun.cloud.common.etcd.spi.entity.api;

import com.anyun.cloud.common.tools.db.AbstractEntity;

import java.util.LinkedList;
import java.util.List;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 01/06/2017
 */
public class ApiTypeEntity extends AbstractEntity{
    private String name;
    private List<ApiTypePropEntity> propEntities = new LinkedList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ApiTypePropEntity> getPropEntities() {
        return propEntities;
    }

    public void setPropEntities(List<ApiTypePropEntity> propEntities) {
        this.propEntities = propEntities;
    }

    public void addPropEntity(ApiTypePropEntity prop) {
        propEntities.add(prop);
    }

    @Override
    public String toString() {
        return "ApiTypeEntity {" + "\n" +
                "    name='" + name + "\'\n" +
                "    propEntities=" + propEntities + "\n" +
                '}';
    }
}
