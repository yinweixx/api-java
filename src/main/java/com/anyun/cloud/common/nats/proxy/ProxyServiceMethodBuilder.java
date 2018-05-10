package com.anyun.cloud.common.nats.proxy;

import com.anyun.cloud.common.exception.ProxyServiceMethodBuilderException;
import com.google.inject.Binding;
import com.google.inject.Key;

import java.util.List;
import java.util.Map;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 11/12/2017
 */
public interface ProxyServiceMethodBuilder {
    ProxyServiceMethodInstance build(Map<Key<?>, Binding<?>> bindings, String business, Map<String, List<String>> paramsMap)
            throws ProxyServiceMethodBuilderException;
}
