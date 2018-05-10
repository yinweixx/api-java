package com.anyun.cloud.common.nats.params;


import com.anyun.cloud.common.exception.ParamsBuilderException;

import java.util.List;
import java.util.Map;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 08/12/2017
 */
public interface ParamsBuilder<T> {
    T build(Map<String, List<String>> params) throws ParamsBuilderException;
}
