package com.anyun.cloud.common.tools.db;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;

/**
 * Created by sxt on 8/28/17.
 */
public class AbstractEntity implements BaseEntity<AbstractEntity>,Serializable {

    @Override
    public String asJson() {
        GsonBuilder gb = new GsonBuilder();
        gb.disableHtmlEscaping();
        gb.setPrettyPrinting();
        Gson gson = gb.create();
        return gson.toJson(this);
    }

    @Override
    public AbstractEntity build() {
        return this;
    }
}
