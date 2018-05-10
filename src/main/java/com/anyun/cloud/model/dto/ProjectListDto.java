package com.anyun.cloud.model.dto;

import com.anyun.cloud.common.tools.db.AbstractEntity;

public class ProjectListDto extends AbstractEntity{
    public int  pid;//项目id；
    public String projectName;
    public String gitUrl;
    public String shortUrl;
    public String branch;
    public String type;
    public boolean environmental;
    public String user;


    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getGitUrl() {
        return gitUrl;
    }

    public void setGitUrl(String gitUrl) {
        this.gitUrl = gitUrl;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public boolean isEnvironmental() {
        return environmental;
    }

    public void setEnvironmental(boolean environmental) {
        this.environmental = environmental;
    }
}
