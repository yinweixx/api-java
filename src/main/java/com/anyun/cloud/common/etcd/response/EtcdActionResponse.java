package com.anyun.cloud.common.etcd.response;

import com.google.gson.annotations.SerializedName;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 25/05/2017
 */
public class EtcdActionResponse extends AbstractEtcdResponse {
    @SerializedName("action")
    private String action;

    @SerializedName("node")
    private EtcdActionNode actionNode;

    @SerializedName("modifiedIndex")
    private int modifiedIndex;

    @SerializedName("createdIndex")
    private int createdIndex;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public EtcdActionNode getActionNode() {
        return actionNode;
    }

    public void setActionNode(EtcdActionNode actionNode) {
        this.actionNode = actionNode;
    }

    public int getModifiedIndex() {
        return modifiedIndex;
    }

    public void setModifiedIndex(int modifiedIndex) {
        this.modifiedIndex = modifiedIndex;
    }

    public int getCreatedIndex() {
        return createdIndex;
    }

    public void setCreatedIndex(int createdIndex) {
        this.createdIndex = createdIndex;
    }

    @Override
    public String toString() {
        return "EtcdActionResponse{" +
                "action='" + action + '\'' +
                ", actionNode=" + actionNode +
                ", modifiedIndex=" + modifiedIndex +
                ", createdIndex=" + createdIndex +
                '}';
    }
}
