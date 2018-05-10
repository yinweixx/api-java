package com.anyun.cloud.common.nats.proxy;


import com.anyun.cloud.common.nats.format.MessageFormater;

import java.lang.reflect.Method;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 11/12/2017
 */
public class ProxyServiceMethodInstance<T> {
    private Method method;
    private T param;
    private Object instance;
    private MessageFormater formater;

    public ProxyServiceMethodInstance(Method method, T param, Object instance, MessageFormater formater) {
        this.method = method;
        this.param = param;
        this.instance = instance;
        this.formater = formater;
    }

    public Method getMethod() {
        return method;
    }

    public T getParam() {
        return param;
    }

    public Object getInstance() {
        return instance;
    }

    public Object invoke() throws Exception {
        if (param == null)
            return method.invoke(instance);
        return method.invoke(instance, param);
    }

    public MessageFormater getFormater() {
        return formater;
    }
}
