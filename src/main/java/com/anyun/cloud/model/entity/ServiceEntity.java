package com.anyun.cloud.model.entity;

import com.anyun.cloud.common.tools.db.AbstractEntity;
import com.iciql.Iciql.IQColumn;
import com.iciql.Iciql.IQTable;


@IQTable(name = "ser_basics") //自定义的表名
public class ServiceEntity extends AbstractEntity {
    @IQColumn(name = "id" ,primaryKey = true ,length = 200)
    public String id; //主键 信息表id

    @IQColumn(name = "name" ,nullable = false , length = 45)
    public String name; //服务名称

    @IQColumn(name = "is_private",defaultValue = "true")
    public Boolean isPrivate; //服务是否私有

    @IQColumn(name = "status" ,defaultValue = "true")
    public Boolean status; //服务激活的状态

    @IQColumn(name = "branch_id",length = 45)
    public int branchId; //服务分支表id

    @IQColumn(name = "desc",length = 300)
    public String desc;//项目描述

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

    public Boolean getPrivate() {
        return isPrivate;
    }

    public void setPrivate(Boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
