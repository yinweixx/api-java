package com.anyun.cloud.model.entity;

import com.anyun.cloud.common.tools.db.AbstractEntity;
import com.iciql.Iciql.IQColumn;
import com.iciql.Iciql.IQTable;

import java.net.URL;

@IQTable(name = "pro_basic",create=true )
public class ServiceProjectEntity extends AbstractEntity {

    @IQColumn(name = "id" ,primaryKey = true  , autoIncrement = true)
    public int id;//项目id

    @IQColumn(name = "name",length = 200,nullable = false)
    public String name;//项目名称

    @IQColumn(name = "gitUrl",length = 200,nullable = false)
    public String gitUrl;

    @IQColumn(name = "shortUrl",length = 200,nullable = false)
    public String shortUrl;

    @IQColumn(name = "branch",length = 200,nullable = false)
    public String branch; //当前使用版本

    @IQColumn(name = "type",length = 200)
    public String type; //当前使用版本

    @IQColumn(name = "environmental",defaultValue = "true")
    public Boolean environmental;

    @IQColumn(name = "user",length = 200)
    public String user; // 用户

    @IQColumn(name = "app_id")
    public long appId;//应用表id

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


    public long getAppId() {
        return appId;
    }

    public void setAppId(long appId) {
        this.appId = appId;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
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

    public Boolean getEnvironmental() {
        return environmental;
    }

    public void setEnvironmental(Boolean environmental) {
        this.environmental = environmental;
    }

    @Override
    public String toString() {
        return "ServiceProjectEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", appId='" + appId + '\'' +"," +
                " user='" + user + '\'' +
                " gitURl='" + gitUrl + '\'' +
                " branch='" + branch + '\'' +
                " type='" + type + '\'' +
                '}';
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }
}
