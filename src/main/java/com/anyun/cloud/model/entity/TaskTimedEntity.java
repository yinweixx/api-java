package com.anyun.cloud.model.entity;

import com.anyun.cloud.common.tools.db.AbstractEntity;
import com.iciql.Iciql;

@Iciql.IQTable(name = "task_timed")
public class TaskTimedEntity extends AbstractEntity {
    @Iciql.IQColumn(name = "id",primaryKey = true,length = 100)
    private String id;

    @Iciql.IQColumn(name = "timed",length = 100)
    private String timed;

    @Iciql.IQColumn(name = "task_id",length = 200)
    private String taskId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTimed() {
        return timed;
    }

    public void setTimed(String timed) {
        this.timed = timed;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
