package com.anyun.cloud.model.param;

import com.anyun.cloud.common.tools.db.AbstractEntity;

public class TaskCronParam extends AbstractEntity {
    private String id;
    private String cron;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }
}
