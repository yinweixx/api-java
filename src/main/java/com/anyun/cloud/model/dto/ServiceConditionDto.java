package com.anyun.cloud.model.dto;

import com.anyun.cloud.common.tools.db.AbstractEntity;
import com.anyun.cloud.model.entity.ServiceBranchEntity;
import com.anyun.cloud.model.entity.ServiceEntity;
import com.anyun.cloud.model.entity.ServiceProjectEntity;

public class ServiceConditionDto extends AbstractEntity{
    public String id;
    public String sname;
    public Boolean isPrivate;
    public Boolean status;
    public int branchId;
    public String desc;
    public int bid;
    public String bname;
    public int projectId;
    public int pid;
    public String pname;
    public String gitUrl;
    public String shortUrl;
    public String branch;
    public String type;
    public Boolean environmental;




    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
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

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getGitUrl() {
        return gitUrl;
    }

    public void setGitUrl(String gitUrl) {
        this.gitUrl = gitUrl;
    }

    public Boolean getPrivate() {
        return isPrivate;
    }

    public void setPrivate(Boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getEnvironmental() {
        return environmental;
    }

    public void setEnvironmental(Boolean environmental) {
        this.environmental = environmental;
    }
}
