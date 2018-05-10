package com.anyun.cloud.common.context;

import com.anyun.cloud.common.nats.DefaultMessageHandler;
import com.anyun.cloud.common.nats.DefaultNats;
import com.anyun.cloud.common.nats.Nats;
import com.anyun.cloud.common.nats.NatsConfig;
import com.google.inject.AbstractModule;
import io.nats.client.MessageHandler;

/**
 * ｎａｔｓ　模块接口和实现绑定
 */
public class NatsModule extends AbstractModule {
    private SystemEnvironment environment;

    public NatsModule(SystemEnvironment environment) {
        this.environment = environment;
    }

    @Override
    protected void configure() {
        bind(NatsConfig.class).toInstance(new NatsConfig(environment));
        bind(Nats.class).to(DefaultNats.class);
        bind(MessageHandler.class).to(DefaultMessageHandler.class);
    }
}
