package com.anyun.cloud.common.etcd.repuest;

import com.google.gson.annotations.SerializedName;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2017/5/18
 */
public abstract class AbstractEtcdV2Request implements BaseEtcdRequest {
    @SerializedName("action")
    private String action;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public BaseEtcdRequest build() {
        return null;
    }
}
