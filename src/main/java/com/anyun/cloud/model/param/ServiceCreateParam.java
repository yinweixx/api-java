package com.anyun.cloud.model.param;

import com.anyun.cloud.common.tools.db.AbstractEntity;

import java.io.File;
import java.net.URL;

public class ServiceCreateParam extends AbstractEntity{
    private URL remote;
    private String branch;
    private long aid;
    private String user;

    public URL getRemote() {
        return remote;
    }

    public void setRemote(URL remote) {
        this.remote = remote;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public long getAid() {
        return aid;
    }

    public void setAid(long aid) {
        this.aid = aid;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
