package com.anyun.cloud.common.nats.params;


import com.anyun.cloud.common.exception.ParamsBuilderException;

import java.util.List;
import java.util.Map;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 11/12/2017
 */
public abstract class AbstractParamsBuilder<T> implements ParamsBuilder<T> {
    protected Map<String, List<String>> params;

    @Override
    public final T build(Map<String, List<String>> params) throws ParamsBuilderException {
        this.params = params;
        return build();
    }

    public abstract T build() throws ParamsBuilderException;

    protected String getFirstValue(String key) throws ParamsBuilderException {
        List<String> values = params.get(key);
        if (values == null || values.size() == 0)
            throw new ParamsBuilderException("参数[" + key + "]没有定义");
        return values.get(0);
    }
}
