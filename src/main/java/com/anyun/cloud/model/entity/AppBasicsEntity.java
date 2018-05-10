package com.anyun.cloud.model.entity;

import com.anyun.cloud.common.tools.db.AbstractEntity;
import com.iciql.Iciql.IQTable;
import com.iciql.Iciql.IQColumn;
import org.joda.time.DateTime;

import java.util.Date;

@IQTable(name = "app_basics")       //定义表名称（如果表名称一直不需要帝定义,默认插入数据的时候如果不存在自动创建表）
public class AppBasicsEntity extends AbstractEntity {
    @IQColumn(name = "id",primaryKey = true,autoIncrement = true)
    private Long appId;          //应用id（表主键）

    @IQColumn(name = "name",length = 50,trim = true)
    private String name;            //应用名称

    @IQColumn(name = "short_name",length = 50)
    private String shortName;       //应用短名

    @IQColumn(name = "desc",length = 200,trim = true)
    private String desc;            //应用描述

    @IQColumn(name = "create_time")
    private String createTime;        //创建时间

    @IQColumn(name = "last_modify_time")
    private String lastModifyTime;    //最后修改时间

    private Long apiNum;          //应用下所有api数量（关联查询不记录在应用表中）

    private Long serviceNum;      //应用下所有服务数量（关联查询不记录在应用表中）

    private Long taskNum;         //应用下所有任务数量（关联查询不记录在应用表中）

    public AppBasicsEntity() {
        // 默认的构造函数
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(String lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    public Long getApiNum() {
        return apiNum;
    }

    public void setApiNum(Long apiNum) {
        this.apiNum = apiNum;
    }

    public Long getServiceNum() {
        return serviceNum;
    }

    public void setServiceNum(Long serviceNum) {
        this.serviceNum = serviceNum;
    }

    public Long getTaskNum() {
        return taskNum;
    }

    public void setTaskNum(Long taskNum) {
        this.taskNum = taskNum;
    }

    //重写　toString()
    @Override
    public String toString() {
        return "AppBasicsEntity{" +
                "appId=" + appId +
                ", name='" + name + '\'' +
                ",shortName ='" + shortName + '\'' +
                ", desc=" + desc +
                ", createTime=" + createTime +
                ", lastModifyTime=" + lastModifyTime +
                ", apiNum=" + apiNum +
                ", serviceNum=" + serviceNum +
                ", taskNum=" + taskNum +
                '}';
    }
}
