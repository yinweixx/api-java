package com.anyun.cloud.model.entity;

import com.anyun.cloud.common.tools.db.AbstractEntity;
import com.iciql.Iciql;

/**
 * @author myb  mayanbin@proxzone.com
 * @version 1.0
 * @date 2018/1/23 19:13
 */
@Iciql.IQTable(name = "host_software")
public class HostSoftwareEntity extends AbstractEntity {
    @Iciql.IQColumn(name = "id" ,primaryKey = true,autoIncrement = true)
    public int hostId;
    @Iciql.IQColumn(name = "host_ip")
    public String hostIp; //代理版本
    @Iciql.IQColumn(name = "agentVersion")
    public String agentVersion; //代理版本
    @Iciql.IQColumn(name = "agentBuildTime")
    public String agentBuildTime; //代理创建时间
    @Iciql.IQColumn(name = "agentGoVersion")
    public String agentGoVersion; //代理Go 版本
    @Iciql.IQColumn(name = "osDistributor")
    public String osDistributor; //操作系统经销商
    @Iciql.IQColumn(name = "osDescription")
    public String osDescription; //操作系统描述
    @Iciql.IQColumn(name = "osRelease")
    public String osRelease; //系统发行版本
    @Iciql.IQColumn(name = "osCodename")
    public String osCodename;  //系统发行代号
    @Iciql.IQColumn(name = "osArch")
    public String osArch;  //操作系统架构
    @Iciql.IQColumn(name = "osKernelName")
    public String osKernelName;  //内核名称
    @Iciql.IQColumn(name = "osKernelVersion")
    public String osKernelVersion; //内核版本
    @Iciql.IQColumn(name = "osUptime")
    public String osUptime; //系统正常运行时间

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

    public String getAgentVersion() {
        return agentVersion;
    }

    public void setAgentVersion(String agentVersion) {
        this.agentVersion = agentVersion;
    }

    public String getAgentBuildTime() {
        return agentBuildTime;
    }

    public void setAgentBuildTime(String agentBuildTime) {
        this.agentBuildTime = agentBuildTime;
    }

    public String getAgentGoVersion() {
        return agentGoVersion;
    }

    public void setAgentGoVersion(String agentGoVersion) {
        this.agentGoVersion = agentGoVersion;
    }

    public String getOsDistributor() {
        return osDistributor;
    }

    public void setOsDistributor(String osDistributor) {
        this.osDistributor = osDistributor;
    }

    public String getOsDescription() {
        return osDescription;
    }

    public void setOsDescription(String osDescription) {
        this.osDescription = osDescription;
    }

    public String getOsRelease() {
        return osRelease;
    }

    public void setOsRelease(String osRelease) {
        this.osRelease = osRelease;
    }

    public String getOsCodename() {
        return osCodename;
    }

    public void setOsCodename(String osCodename) {
        this.osCodename = osCodename;
    }

    public String getOsArch() {
        return osArch;
    }

    public void setOsArch(String osArch) {
        this.osArch = osArch;
    }

    public String getOsKernelName() {
        return osKernelName;
    }

    public void setOsKernelName(String osKernelName) {
        this.osKernelName = osKernelName;
    }

    public String getOsKernelVersion() {
        return osKernelVersion;
    }

    public void setOsKernelVersion(String osKernelVersion) {
        this.osKernelVersion = osKernelVersion;
    }

    public String getOsUptime() {
        return osUptime;
    }

    public void setOsUptime(String osUptime) {
        this.osUptime = osUptime;
    }

}
