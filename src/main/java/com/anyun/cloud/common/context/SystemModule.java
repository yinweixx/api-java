package com.anyun.cloud.common.context;
import com.anyun.cloud.common.nats.proxy.DefaultProxyServiceMethodBuilder;
import com.anyun.cloud.common.nats.proxy.ProxyServiceMethodBuilder;
import com.google.inject.AbstractModule;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 08/12/2017
 */
public class SystemModule extends AbstractModule {

    private SystemEnvironment environment;

    public SystemModule(SystemEnvironment environment) {
        this.environment = environment;
    }

    @Override
    protected void configure() {
        bind(SystemEnvironment.class).toInstance(environment);
        bind(Context.class).to(DefaultContext.class);
        bind(ProxyServiceMethodBuilder.class).to(DefaultProxyServiceMethodBuilder.class);
    }

}
