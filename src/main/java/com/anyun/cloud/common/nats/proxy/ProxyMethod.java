package com.anyun.cloud.common.nats.proxy;

import com.anyun.cloud.common.nats.format.DefaultJsonMessageFormat;
import com.anyun.cloud.common.nats.format.MessageFormater;
import com.anyun.cloud.common.nats.params.NullableParamsBuilder;
import com.anyun.cloud.common.nats.params.ParamsBuilder;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 08/12/2017
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface ProxyMethod {

    /**
     * @return
     */
    Class<? extends ParamsBuilder> build() default NullableParamsBuilder.class;

    /**
     *
     * @return
     */
    Class<? extends MessageFormater> formater() default DefaultJsonMessageFormat.class;

    /**
     * @return
     */
    String business();
}
