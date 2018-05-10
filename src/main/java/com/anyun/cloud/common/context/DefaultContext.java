package com.anyun.cloud.common.context;
import com.anyun.cloud.common.db.Database;
import com.anyun.cloud.common.elasticsearch.Elasticsearch;
import com.anyun.cloud.common.etcd.Etcd;
import com.anyun.cloud.common.exception.ContextInitException;
import com.anyun.cloud.common.nats.Nats;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 06/12/2017
 */
@Singleton
public class DefaultContext implements Context {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultContext.class);
    private Database database;
    private Etcd etcd;
    private Nats nats;
    private Elasticsearch elasticsearch;
    private String deviceId;

    @Inject
    public DefaultContext(Database database, Etcd etcd, Nats nats,Elasticsearch elasticsearch) {
        this.database = database;
        this.etcd = etcd;
        this.nats = nats;
        this.elasticsearch=elasticsearch;
    }

    @Override
    public Database getDatabase() {
        return database;
    }

    @Override
    public Context init() throws ContextInitException {
        try {
            database.connect();
            nats.connect();
            nats.startMessageMonitor();
        } catch (Exception ex) {
            throw new ContextInitException(ex);
        }
        return this;
    }

    @Override
    public String getDeviceId() {
        return deviceId;
    }

    @Override
    public Etcd getEtcd() {
        return etcd;
    }

    @Override
    public Nats getNats() {
        return nats;
    }

    @Override
    public Elasticsearch getElasticsearch() {
        return elasticsearch;
    }

    @Override
    public <T> T getBeanByClass(Class<T> type) {
        return ControllerIOC.getIOC().getInstance(type);
    }
}
