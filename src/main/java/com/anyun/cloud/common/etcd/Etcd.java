package com.anyun.cloud.common.etcd;

import com.anyun.cloud.common.exception.EtcdException;
import com.coreos.jetcd.data.KeyValue;


import java.util.List;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 08/12/2017
 */
public interface Etcd {

    /**
     * @param ttlSecond
     * @return
     * @throws EtcdException
     */
    long grantAndKeepAliveLease(long ttlSecond) throws EtcdException;

    /**
     * @param ttlSecond
     * @return
     * @throws EtcdException
     */
    long granLease(long ttlSecond) throws EtcdException;

    /**
     * @param leaseId
     * @param key
     * @param value
     * @throws EtcdException
     */
    void putKeyValueByLease(long leaseId, String key, String value) throws EtcdException;

    /**
     * @param key
     * @return
     * @throws EtcdException
     */
    boolean isKeyExist(String key) throws EtcdException;

    /**
     * @param key
     * @param keysOnly
     * @return
     * @throws EtcdException
     */
    List<KeyValue> getWithPrefix(String key, boolean keysOnly) throws EtcdException;
}
