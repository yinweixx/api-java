package com.anyun.cloud.model.entity;

import com.anyun.cloud.common.tools.db.AbstractEntity;
import com.iciql.Iciql;

/**
 * @author myb  mayanbin@proxzone.com
 * @version 1.0
 * @date 2018/1/23 19:16
 */
@Iciql.IQTable(name = "host_docker")
public class HostDockerEntity extends AbstractEntity{
    @Iciql.IQColumn(name = "id" ,primaryKey = true,autoIncrement = true)
    public int hostId;//宿主机Id
    @Iciql.IQColumn(name="host_ip")
    public String hostIp;  //宿主机Ip
    @Iciql.IQColumn(name="docker_version")
    public String dockerVersion; //docker版本
    @Iciql.IQColumn(name="docker_apiVersion")
    public String dockerApiVersion; //docker Api 版本
    @Iciql.IQColumn(name="docker_minAPIVersion")
    public String dockerMinAPIVersion;//docker 最低api版本
    @Iciql.IQColumn(name="docker_gitCommit")
    public String gitCommit;//git版本
    @Iciql.IQColumn(name="docker_goVersion")
    public String goVersion; //go 版本
    @Iciql.IQColumn(name="docker_buildTime")
    public String buildTime; //容器构建时间

    public int getHostId() {
        return hostId;
    }

    public void setHostId(int hostId) {
        this.hostId = hostId;
    }

    public String getHostIp() {
        return hostIp;
    }

    public void setHostIp(String hostIp) {
        this.hostIp = hostIp;
    }

    public String getDockerVersion() {
        return dockerVersion;
    }

    public void setDockerVersion(String dockerVersion) {
        this.dockerVersion = dockerVersion;
    }

    public String getDockerApiVersion() {
        return dockerApiVersion;
    }

    public void setDockerApiVersion(String dockerApiVersion) {
        this.dockerApiVersion = dockerApiVersion;
    }

    public String getDockerMinAPIVersion() {
        return dockerMinAPIVersion;
    }

    public void setDockerMinAPIVersion(String dockerMinAPIVersion) {
        this.dockerMinAPIVersion = dockerMinAPIVersion;
    }

    public String getGitCommit() {
        return gitCommit;
    }

    public void setGitCommit(String gitCommit) {
        this.gitCommit = gitCommit;
    }

    public String getGoVersion() {
        return goVersion;
    }

    public void setGoVersion(String goVersion) {
        this.goVersion = goVersion;
    }

    public String getBuildTime() {
        return buildTime;
    }

    public void setBuildTime(String buildTime) {
        this.buildTime = buildTime;
    }
}
