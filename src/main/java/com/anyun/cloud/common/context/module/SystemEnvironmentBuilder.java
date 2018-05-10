package com.anyun.cloud.common.context.module;


import com.anyun.cloud.common.context.SystemEnvironment;

import java.util.HashMap;
import java.util.Map;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 08/12/2017
 */
public class SystemEnvironmentBuilder {

    public SystemEnvironment build(Map<String, String> sysenv) {
        if (sysenv == null) {
            return new SystemEnvironment();
        }
        if (sysenv.size() == 0) {
            String uri = "jdbc:p6spy:mysql://192.168.254.243:3306/video-cloud-base-platform?useUnicode=true&characterEncoding=utf8&autoReconnect=true";
            Map<String, String> configMap = new HashMap<>();
            configMap.put(SystemEnvironment.DB_INIT_SIZE, "10");
            configMap.put(SystemEnvironment.DB_MAX_SIZE, "100");
            configMap.put(SystemEnvironment.DB_VALIDATION_SQL, "select 1=1");
            configMap.put(SystemEnvironment.DB_VALIDATION_TIMEOUT, "1800");
            configMap.put(SystemEnvironment.DB_JDBC_DRIVER, "com.p6spy.engine.spy.P6SpyDriver");
            configMap.put(SystemEnvironment.DB_JDBC_URI, uri);
            configMap.put(SystemEnvironment.DB_JDBC_USR, "root");
            configMap.put(SystemEnvironment.DB_JDBC_PWD, "1234qwer");
            configMap.put(SystemEnvironment.ETCD_URL, "http://server.etcd.service.dc-anyuncloud.consul:2379");
            configMap.put(SystemEnvironment.NATS_URL, "nats://message-business.service.dc-anyuncloud.consul:4222");
            SystemEnvironment environment = new SystemEnvironment(configMap);
            return environment;
        }
        return new SystemEnvironment(sysenv);
    }
}
