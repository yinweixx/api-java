package com.anyun.cloud.common.tools.db;

import java.io.Serializable;

/**
 * Created by sxt on 8/28/17.
 */
    public interface BaseEntity<T> extends Serializable {
        String asJson();
        T build();
    }
