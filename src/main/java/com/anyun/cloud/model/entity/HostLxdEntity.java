package com.anyun.cloud.model.entity;

import com.anyun.cloud.common.tools.db.AbstractEntity;
import com.iciql.Iciql;

/**
 * @author myb  mayanbin@proxzone.com
 * @version 1.0
 * @date 2018/1/23 19:16
 */
@Iciql.IQTable(name = "host_lxd")
public class HostLxdEntity extends AbstractEntity {
    @Iciql.IQColumn(name = "id" ,primaryKey = true, autoIncrement = true)
    public int hostId; //宿主机Id
    @Iciql.IQColumn(name="host_ip")
    public String hostIp;  //宿主机Ip
    @Iciql.IQColumn(name = "lxdVersion")
    public String lxdVersion; //LXD版本信息

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

    public String getLxdVersion() {
        return lxdVersion;
    }

    public void setLxdVersion(String lxdVersion) {
        this.lxdVersion = lxdVersion;
    }
}
