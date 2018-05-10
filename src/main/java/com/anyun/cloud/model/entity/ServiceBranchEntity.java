package com.anyun.cloud.model.entity;

import com.anyun.cloud.common.tools.db.AbstractEntity;
import com.iciql.Iciql.IQColumn;
import com.iciql.Iciql.IQTable;

@IQTable(name = "pro_branch",create = true)
public class ServiceBranchEntity extends AbstractEntity {

    @IQColumn(name = "id" ,primaryKey = true,autoIncrement = true)
    public int id;//服务分支表主键

    @IQColumn(name = "name",length = 45,nullable = false)
    public String name;//服务分支名称

    @IQColumn(name = "project_id",nullable = false)
    public int projectId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    @Override
    public String toString() {
        return "ServiceBranchEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", projectId='" + projectId + '\'' +
                '}';
    }
}
