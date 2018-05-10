package com.anyun.cloud.common.context;


import com.anyun.cloud.common.db.Database;
import com.anyun.cloud.common.elasticsearch.Elasticsearch;
import com.anyun.cloud.common.etcd.Etcd;
import com.anyun.cloud.common.exception.ContextInitException;
import com.anyun.cloud.common.nats.Nats;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 06/12/2017
 */
public interface Context {
    Database getDatabase();

    /**
     * @return
     * @throws ContextInitException
     */
    Context init() throws ContextInitException;

    /**
     * @return
     */
    String getDeviceId();

    /**
     * @return
     */
    Etcd getEtcd();

    /**
     * @return
     */
    Nats getNats();

    Elasticsearch getElasticsearch();

    /**
     * @param type
     * @param <T>
     * @return
     */
    <T> T getBeanByClass(Class<T> type);
}
