package com.anyun.cloud.model.entity;

import com.anyun.cloud.common.tools.db.AbstractEntity;
import com.iciql.Iciql;

@Iciql.IQTable(name ="api_gateway")
public class ApiGatewayEntity extends AbstractEntity {
    @Iciql.IQColumn(name = "id",primaryKey = true,autoIncrement = true)
    private Integer id;

    @Iciql.IQColumn(name = "name",length = 50,nullable = false)
    private String gatewayName;

    @Iciql.IQColumn(name = "node_amount",length = 50,trim = true)
    private String nodeAmount;

    @Iciql.IQColumn(name = "link_status")
    private boolean linkStatus;

    @Iciql.IQColumn(name = "link_amount",length = 50)
    private String linkAmount;

    @Iciql.IQColumn(name = "cpu_consume",length = 50)
    private String cpuConsume;

    @Iciql.IQColumn(name = "memory_consume",length = 50)
    private String memoryConsume;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGatewayName() {
        return gatewayName;
    }

    public void setGatewayName(String gatewayName) {
        this.gatewayName = gatewayName;
    }

    public String getNodeAmount() {
        return nodeAmount;
    }

    public void setNodeAmount(String nodeAmount) {
        this.nodeAmount = nodeAmount;
    }

    public boolean isLinkStatus() {
        return linkStatus;
    }

    public void setLinkStatus(boolean linkStatus) {
        this.linkStatus = linkStatus;
    }

    public String getLinkAmount() {
        return linkAmount;
    }

    public void setLinkAmount(String linkAmount) {
        this.linkAmount = linkAmount;
    }

    public String getCpuConsume() {
        return cpuConsume;
    }

    public void setCpuConsume(String cpuConsume) {
        this.cpuConsume = cpuConsume;
    }

    public String getMemoryConsume() {
        return memoryConsume;
    }

    public void setMemoryConsume(String memoryConsume) {
        this.memoryConsume = memoryConsume;
    }
}
