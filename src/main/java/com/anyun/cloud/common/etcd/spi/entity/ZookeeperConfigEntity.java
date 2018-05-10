package com.anyun.cloud.common.etcd.spi.entity;

import com.anyun.cloud.common.etcd.EtcdErrorResponseException;
import com.anyun.cloud.common.etcd.response.EtcdActionResponse;
import com.anyun.cloud.common.etcd.spi.impl.EtcdApiUrls;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 25/05/2017
 */
public class ZookeeperConfigEntity extends AbstractEtcdEntity<ZookeeperConfigEntity> {
    private String connectingString = null;
    private int connectionTimeout = 5000;
    private int sessionTimeout = 5000;
    private int retryPolicySleepTime = 1000;
    private int retryPolicyMaxRetries = 3;

    public ZookeeperConfigEntity buildFromEtcdActionResponse(EtcdActionResponse response) throws EtcdErrorResponseException {
        ZookeeperConfigEntity config = new ZookeeperConfigEntity();
        try {
            config.connectingString = getStringValue(response, EtcdApiUrls.KEY_ZK_CONNECTION_STR);
            config.connectionTimeout = getIntValue(response, EtcdApiUrls.KEY_ZK_CONNECTION_TIMEOUT);
            config.sessionTimeout = getIntValue(response, EtcdApiUrls.KEY_ZK_SESSION_TIMEOUT);
            config.retryPolicySleepTime = getIntValue(response, EtcdApiUrls.KEY_ZK_RETRY_POLICY_SLEEP_TIME);
            config.retryPolicyMaxRetries = getIntValue(response, EtcdApiUrls.KEY_ZK_RETRY_POLICY_MAX_RETRIES);
            return config;
        } catch (Exception ex) {
            throw new EtcdErrorResponseException(ex);
        }
    }

    public String getConnectingString() {
        return connectingString;
    }

    public void setConnectingString(String connectingString) {
        this.connectingString = connectingString;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public int getSessionTimeout() {
        return sessionTimeout;
    }

    public void setSessionTimeout(int sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }

    public int getRetryPolicySleepTime() {
        return retryPolicySleepTime;
    }

    public void setRetryPolicySleepTime(int retryPolicySleepTime) {
        this.retryPolicySleepTime = retryPolicySleepTime;
    }

    public int getRetryPolicyMaxRetries() {
        return retryPolicyMaxRetries;
    }

    public void setRetryPolicyMaxRetries(int retryPolicyMaxRetries) {
        this.retryPolicyMaxRetries = retryPolicyMaxRetries;
    }

    @Override
    public String toString() {
        return "ZookeeperConfigEntity{" +
                "connectingString='" + connectingString + '\'' +
                ", connectionTimeout=" + connectionTimeout +
                ", sessionTimeout=" + sessionTimeout +
                ", retryPolicySleepTime=" + retryPolicySleepTime +
                ", retryPolicyMaxRetries=" + retryPolicyMaxRetries +
                '}';
    }
}
