package com.anyun.cloud.common.nats;

import com.anyun.cloud.common.exception.NatsInitialException;
import io.nats.client.Connection;
import io.nats.client.ConnectionFactory;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 08/12/2017
 */
public interface Nats {

    /**
     *
     * @throws NatsInitialException
     */
    void connect() throws NatsInitialException;

    /**
     *
     * @return
     */
    Connection getConnection();

    /**
     *
     * @return
     */
    ConnectionFactory getConnectionFactory();

    /**
     *
     * @return
     */
    NatsConfig getNatsConfig();


    /**
     * 启动nats消息监听程序
     */
    void startMessageMonitor();
}
