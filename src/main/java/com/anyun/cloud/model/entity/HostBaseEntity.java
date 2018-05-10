package com.anyun.cloud.model.entity;

import com.anyun.cloud.common.tools.db.AbstractEntity;
import com.iciql.Iciql;
import com.iciql.Iciql.IQColumn;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author myb  mayanbin@proxzone.com
 * @version 1.0
 * @date 2018/1/23 16:29
 */
@Iciql.IQTable(name = "host_base")
public class HostBaseEntity extends AbstractEntity {
    @IQColumn(name = "id" ,primaryKey = true, autoIncrement = true)
    public int  hostId;//宿主机Id
    @IQColumn(name="block_id")
    public int blockId;
   // @IQColumn(name="pool_id")
    public int poolId;
    @IQColumn(name="host_ip")
    public String hostIp;  //宿主机Ip
    @IQColumn(name="createDate")
    public String createDate;//创建时间
    @IQColumn(name="centerName")
    public String  CenterName; //所属数据中心
    @IQColumn(name="centerId")
    public int centerId;
    @IQColumn(name="category")
    public String category;      //分类
   // @IQColumn(name="state")
    public String state;
    @IQColumn(name="environment")
    public String environment;
    private HostSoftwareEntity softwareEntity; //宿主机软件类信息
   // @IQColumn(name="hardware")
    private HostHardwareEntity hardwareEntity; //宿主机硬件类信息
   // @IQColumn(name="lxd")
    private HostLxdEntity lxdEntity; //宿主机Lxd版本信息
  //  @IQColumn(name="docker")
    private HostDockerEntity dockerEntity; //宿主机docker版本信息


    public HostBaseEntity() {
    }

    public int getHostId() {
        return hostId;
    }

    public void setHostId(int hostId) {
        this.hostId = hostId;
    }

    public int getBlockId() {
        return blockId;
    }

    public void setBlockId(int blockId) {
        this.blockId = blockId;
    }

    public int getPoolId() {
        return poolId;
    }

    public void setPoolId(int poolId) {
        this.poolId = poolId;
    }

    public String getHostIp() {
        return hostIp;
    }

    public void setHostIp(String hostIp) {
        this.hostIp = hostIp;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCenterName() {
        return CenterName;
    }

    public void setCenterName(String centerName) {
        CenterName = centerName;
    }

    public int getCenterId() {
        return centerId;
    }

    public void setCenterId(int centerId) {
        this.centerId = centerId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public HostSoftwareEntity getSoftwareEntity() {
        return softwareEntity;
    }

    public void setSoftwareEntity(HostSoftwareEntity softwareEntity) {
        this.softwareEntity = softwareEntity;
    }

    public HostHardwareEntity getHardwareEntity() {
        return hardwareEntity;
    }

    public void setHardwareEntity(HostHardwareEntity hardwareEntity) {
        this.hardwareEntity = hardwareEntity;
    }

    public HostLxdEntity getLxdEntity() {
        return lxdEntity;
    }

    public void setLxdEntity(HostLxdEntity lxdEntity) {
        this.lxdEntity = lxdEntity;
    }

    public HostDockerEntity getDockerEntity() {
        return dockerEntity;
    }

    public void setDockerEntity(HostDockerEntity dockerEntity) {
        this.dockerEntity = dockerEntity;
    }
}
