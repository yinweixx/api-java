package com.anyun.cloud.common.etcd.response;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2017/5/17
 */
public class VersionResponse extends AbstractEtcdResponse {
    private String etcdserver;
    private String etcdcluster;

    public String getEtcdserver() {
        return etcdserver;
    }

    public void setEtcdserver(String etcdserver) {
        this.etcdserver = etcdserver;
    }

    public String getEtcdcluster() {
        return etcdcluster;
    }

    public void setEtcdcluster(String etcdcluster) {
        this.etcdcluster = etcdcluster;
    }
}
