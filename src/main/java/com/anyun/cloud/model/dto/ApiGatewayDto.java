package com.anyun.cloud.model.dto;

import com.anyun.cloud.common.tools.db.AbstractEntity;

public class ApiGatewayDto extends AbstractEntity{
    private String id;
    private String name;
    private String nodeAmount;
    private boolean linkStatus;
    private String linkAmount;
    private String cpuConsume;
    private String memoryConsume;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
