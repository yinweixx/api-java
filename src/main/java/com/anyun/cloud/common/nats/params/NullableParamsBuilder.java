package com.anyun.cloud.common.nats.params;
import com.anyun.cloud.common.exception.ParamsBuilderException;

import java.util.Map;

public class NullableParamsBuilder implements ParamsBuilder {
    @Override
    public Object build(Map params) throws ParamsBuilderException {
        return null;
    }
}
