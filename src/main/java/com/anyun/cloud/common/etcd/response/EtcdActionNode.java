package com.anyun.cloud.common.etcd.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 25/05/2017
 */
public class EtcdActionNode implements Serializable {

    @SerializedName("key")
    private String key;

    @SerializedName("dir")
    private boolean isDir;

    @SerializedName("value")
    private String value;

    @SerializedName("modifiedIndex")
    private int modifiedIndex;

    @SerializedName("createdIndex")
    private int createdIndex;

    @SerializedName("nodes")
    private List<EtcdActionNode> actionNodes;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isDir() {
        return isDir;
    }

    public void setDir(boolean dir) {
        isDir = dir;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<EtcdActionNode> getActionNodes() {
        return actionNodes;
    }

    public void setActionNodes(List<EtcdActionNode> actionNodes) {
        this.actionNodes = actionNodes;
    }

    /**
     *
     * @param name
     * @return
     */
    public EtcdActionNode getNodeByName(String name) {
        if (actionNodes == null || actionNodes.isEmpty())
            return null;
        name = key + "/" + name;
        for (EtcdActionNode node : actionNodes) {
            if (node.getKey().equals(name))
                return node;
        }
        return null;
    }

    /**
     *
     * @param name
     * @return
     */
    public String getNodeValueByName(String name) {
        EtcdActionNode node = getNodeByName(name);
        if (node == null)
            return null;
        return node.getValue();
    }

    @Override
    public String toString() {
        return "EtcdActionNode{" +
                "key='" + key + '\'' +
                ", isDir=" + isDir +
                ", value='" + value + '\'' +
                ", modifiedIndex=" + modifiedIndex +
                ", createdIndex=" + createdIndex +
                ", actionNodes=" + actionNodes +
                '}';
    }
}
