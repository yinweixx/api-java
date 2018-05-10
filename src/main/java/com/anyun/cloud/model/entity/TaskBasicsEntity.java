package com.anyun.cloud.model.entity;

import com.anyun.cloud.common.tools.db.AbstractEntity;
import com.iciql.Iciql.IQColumn;
import com.iciql.Iciql.IQTable;


@IQTable(name = "task_basics") //自定义的表名
public class TaskBasicsEntity extends AbstractEntity {
    @IQColumn(name = "id" ,primaryKey = true ,length = 200)
    public String id;           //主键 信息表id

    @IQColumn(name = "name" ,nullable = false , length = 45)
    public String name;         //任务名称

    @IQColumn(name = "status" ,defaultValue = "true")
    public Boolean status;      //任务激活的状态

    @IQColumn(name = "branch_id",length = 45)
    public int branchId;        //任务分支表id

    @IQColumn(name = "desc",length = 300)
    public String desc;         //任务描述

    @IQColumn(name = "crontabExpression",length = 300)
    public String crontabExpression;

    @IQColumn(name = "timed",length = 300)
    private String timed;       //定时时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCrontabExpression() {
        return crontabExpression;
    }

    public void setCrontabExpression(String crontabExpression) {
        this.crontabExpression = crontabExpression;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public String getTimed() {
        return timed;
    }

    public void setTimed(String timed) {
        this.timed = timed;
    }
}
