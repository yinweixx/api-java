package com.anyun.cloud.model.param;

import com.anyun.cloud.common.tools.db.AbstractEntity;

public class ProjectUpdateParam extends AbstractEntity {

    private int id;
    private boolean environmental;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isEnvironmental() {
        return environmental;
    }

    public void setEnvironmental(boolean environmental) {
        this.environmental = environmental;
    }
}
