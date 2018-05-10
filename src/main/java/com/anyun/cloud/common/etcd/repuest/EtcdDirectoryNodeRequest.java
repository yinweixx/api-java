package com.anyun.cloud.common.etcd.repuest;

import com.google.gson.annotations.SerializedName;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2017/5/19
 */
public class EtcdDirectoryNodeRequest extends AbstractEtcdV2Request {

    @SerializedName("node")
    private EtcdKeyDirNode node;

    public EtcdKeyDirNode getNode() {
        return node;
    }

    public void setNode(EtcdKeyDirNode node) {
        this.node = node;
    }
}
