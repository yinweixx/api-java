package com.anyun.cloud.common.nats.proxy;

import com.anyun.cloud.common.context.ControllerIOC;
import com.anyun.cloud.common.exception.ProxyServiceMethodBuilderException;
import com.anyun.cloud.common.nats.format.MessageFormater;
import com.anyun.cloud.common.nats.params.ParamsBuilder;
import com.google.inject.Binding;
import com.google.inject.Key;
import com.iciql.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 11/12/2017
 */
public class DefaultProxyServiceMethodBuilder implements ProxyServiceMethodBuilder {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultProxyServiceMethodBuilder.class);
    private static Map<Class<? extends ParamsBuilder>, ParamsBuilder> builders;
    private static Map<Class<? extends MessageFormater>, MessageFormater> formaters;

    public DefaultProxyServiceMethodBuilder() {
        builders = new HashMap<>();
        formaters = new HashMap<>();
    }

    @Override
    public ProxyServiceMethodInstance build(Map<Key<?>, Binding<?>> bindings, String business, Map<String, List<String>> paramsMap)
            throws ProxyServiceMethodBuilderException {
        AtomicReference<ProxyServiceMethodInstance> instance = new AtomicReference<>();
        bindings.forEach((key, bind) -> {
            Class<?> aClass = bind.getProvider().get().getClass();
            Proxy proxy = aClass.getAnnotation(Proxy.class);
            if (proxy == null)
                return;
            String serviceProxyName = proxy.name();
            if (StringUtils.isNullOrEmpty(serviceProxyName))
                serviceProxyName = aClass.getSimpleName();
            String finalServiceProxyName = serviceProxyName;
            AtomicBoolean isFirstDefineServiceFinish = new AtomicBoolean(false);
            Arrays.asList(aClass.getMethods()).forEach((method) -> {
                if (isFirstDefineServiceFinish.get())
                    return;
                ProxyMethod proxyMethod = method.getAnnotation(ProxyMethod.class);
                if (proxyMethod == null)
                    return;
                String methodBusiness = finalServiceProxyName + "-" + proxyMethod.business();
                if (proxyMethod == null || !methodBusiness.equals(business)) {
                    return;
                }
                isFirstDefineServiceFinish.set(true);
                Class<? extends ParamsBuilder> builderClass = proxyMethod.build();
                LOGGER.debug("service [{}],method[{}],param builder[{}]"
                        , aClass.getCanonicalName(), method.getName(), proxyMethod.build());
                Object service = ControllerIOC.getIOC().getInstance(aClass);
                try {
                    ParamsBuilder paramsBuilder = builders.get(builderClass);
                    if (paramsBuilder == null) {
                        paramsBuilder = builderClass.newInstance();
                        builders.put(builderClass, paramsBuilder);
                    }
                    MessageFormater formater = formaters.get(proxyMethod.formater());
                    if (formater == null) {
                        formater = proxyMethod.formater().newInstance();
                        formaters.put(proxyMethod.formater(), formater);
                    }
                    Object params = paramsBuilder.build(paramsMap);
                    instance.set(new ProxyServiceMethodInstance(method, params, service,formater));
                } catch (Exception ex) {
                    LOGGER.warn("bad param format:\n{}", ex.getMessage(), ex);
                }
            });
        });
        return instance.get();
    }
}
