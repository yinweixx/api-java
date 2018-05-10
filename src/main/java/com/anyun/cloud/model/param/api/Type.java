package com.anyun.cloud.model.param.api;

import com.anyun.cloud.common.tools.db.AbstractEntity;

import java.util.LinkedList;
import java.util.List;

public class Type extends AbstractEntity{
    private String name;
    private List<TypeProp> typePropList = new LinkedList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TypeProp> getTypePropList() {
        return typePropList;
    }

    public void setTypePropList(List<TypeProp> typePropList) {
        this.typePropList = typePropList;
    }
}
