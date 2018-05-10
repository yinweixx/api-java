package com.anyun.cloud.model.param;

import com.anyun.cloud.common.tools.db.AbstractEntity;

public class ServiceRollBackParam extends AbstractEntity{
    private int pid;//项目id
    private String name;//新版本名称
    private int  bid;//旧版本id
    private String user;//用户


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
