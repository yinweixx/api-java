package com.anyun.cloud.model.param;

import com.anyun.cloud.common.tools.db.AbstractEntity;

public class ServiceUpdateParam extends AbstractEntity{
    private String id;
    private boolean status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
