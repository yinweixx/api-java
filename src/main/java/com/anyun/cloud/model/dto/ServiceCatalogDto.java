package com.anyun.cloud.model.dto;

import com.anyun.cloud.common.tools.db.AbstractEntity;

public class ServiceCatalogDto extends AbstractEntity{
    public String  id;
    public Integer nodes;//节点数量
    public String  messagePipeline;//消息管道
    public String  datacenter;//数据中心

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getNodes() {
        return nodes;
    }

    public void setNodes(Integer nodes) {
        this.nodes = nodes;
    }

    public String getMessagePipeline() {
        return messagePipeline;
    }

    public void setMessagePipeline(String messagePipeline) {
        this.messagePipeline = messagePipeline;
    }

    public String getDatacenter() {
        return datacenter;
    }

    public void setDatacenter(String datacenter) {
        this.datacenter = datacenter;
    }
}
