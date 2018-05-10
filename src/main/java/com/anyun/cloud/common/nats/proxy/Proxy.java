package com.anyun.cloud.common.nats.proxy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 08/12/2017
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Proxy {
    String name() default "";
}
