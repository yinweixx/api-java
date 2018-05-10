package com.anyun.cloud.common.context;


import com.anyun.cloud.common.etcd.DefaultEtcd;
import com.anyun.cloud.common.etcd.Etcd;
import com.anyun.cloud.common.etcd.EtcdConfig;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;

/**
 * ｅｔｃｄ　模块接口和实现绑定
 */
public class EtcdModule extends AbstractModule {
    private SystemEnvironment environment;

    @Inject
    public EtcdModule(SystemEnvironment environment) {
        this.environment = environment;
    }

    @Override
    protected void configure() {
        EtcdConfig etcdConfig = new EtcdConfig(environment);
        bind(EtcdConfig.class).toInstance(etcdConfig);
        bind(Etcd.class).to(DefaultEtcd.class);

    }
}
