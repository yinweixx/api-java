package com.anyun.cloud.common.context;


import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 06/12/2017
 */
public class ControllerIOC {
    private static ControllerIOC module;
    private Injector injector;

    private ControllerIOC() {
    }

    public static ControllerIOC getIOC() {
        synchronized (ControllerIOC.class) {
            if (module == null)
                module = new ControllerIOC();
        }
        return module;
    }

    public ControllerIOC build(AbstractModule... modules) {
        injector = Guice.createInjector(modules);
        return this;
    }

    public <T> T getInstance(Class<T> type) {
        return injector.getInstance(type);
    }

    public Injector getInjector() {
        return injector;
    }
}
