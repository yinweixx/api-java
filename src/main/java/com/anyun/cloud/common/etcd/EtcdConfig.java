package com.anyun.cloud.common.etcd;

import com.anyun.cloud.common.context.SystemEnvironment;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 08/12/2017
 */
public class EtcdConfig {
    private String url;

    public EtcdConfig(SystemEnvironment environment) {
        this.url = environment.getEnv(SystemEnvironment.ETCD_URL);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
