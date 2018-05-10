package com.anyun.cloud.common.etcd.spi.impl;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 24/05/2017
 */
public class EtcdApiUrls {
    public static final String KEY_CONFIG = "/keys/config";
    public static final String KEY_CONFIG_ZK = "/keys/config/zookeeper";
    public static final String KEY_ZK_CONNECTION_STR = "connection-string";
    public static final String KEY_ZK_CONNECTION_TIMEOUT = "connection-timeout-ms";
    public static final String KEY_ZK_SESSION_TIMEOUT = "session-timeout-ms";
    public static final String KEY_ZK_RETRY_POLICY_SLEEP_TIME = "retry-policy-sleep-time-ms";
    public static final String KEY_ZK_RETRY_POLICY_MAX_RETRIES = "retry-policy-max-retries";
}
