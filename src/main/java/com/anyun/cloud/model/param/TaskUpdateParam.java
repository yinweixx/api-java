package com.anyun.cloud.model.param;

import com.anyun.cloud.common.tools.db.AbstractEntity;

public class TaskUpdateParam extends AbstractEntity{
    public String id;//任务id
    public boolean status;//状态

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
