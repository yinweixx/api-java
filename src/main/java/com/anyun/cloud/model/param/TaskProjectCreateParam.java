package com.anyun.cloud.model.param;

import com.anyun.cloud.common.tools.db.AbstractEntity;

import java.net.URL;

public class TaskProjectCreateParam extends AbstractEntity{
    private URL remote;//git地址
    private String branch;//版本
    private long appId;//应用id

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

    public long getAppId() {
        return appId;
    }

    public void setAppId(long appId) {
        this.appId = appId;
    }
}
