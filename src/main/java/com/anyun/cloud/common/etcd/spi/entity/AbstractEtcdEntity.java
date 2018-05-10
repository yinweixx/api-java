package com.anyun.cloud.common.etcd.spi.entity;

import com.anyun.cloud.common.etcd.EtcdErrorResponseException;
import com.anyun.cloud.common.etcd.response.EtcdActionResponse;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 25/05/2017
 */
public abstract class AbstractEtcdEntity<T extends AbstractEtcdEntity> implements BaseEntity {

    public abstract T buildFromEtcdActionResponse(EtcdActionResponse response)
            throws EtcdErrorResponseException;

    /**
     * @param response
     * @param name
     * @return
     * @throws EtcdErrorResponseException
     */
    protected String getStringValue(EtcdActionResponse response, String name) throws EtcdErrorResponseException {
        return response.getActionNode().getNodeValueByName(name);
    }

    /**
     * @param response
     * @param name
     * @return
     * @throws EtcdErrorResponseException
     */
    protected Long getLongValue(EtcdActionResponse response, String name) throws EtcdErrorResponseException {
        return Long.valueOf(getStringValue(response, name));
    }

    /**
     * @param response
     * @param name
     * @return
     * @throws EtcdErrorResponseException
     */
    protected Integer getIntValue(EtcdActionResponse response, String name) throws EtcdErrorResponseException {
        return Integer.valueOf(getStringValue(response, name));
    }

    /**
     * @param response
     * @param name
     * @return
     * @throws EtcdErrorResponseException
     */
    protected Boolean getBooleanValue(EtcdActionResponse response, String name) throws EtcdErrorResponseException {
        return Boolean.valueOf(getStringValue(response, name));
    }
}
