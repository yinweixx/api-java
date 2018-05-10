package com.anyun.cloud.model.param;

import com.anyun.cloud.common.tools.db.AbstractEntity;

/**
 * Created by jt on 18-2-2.
 */
public class IdAndName extends AbstractEntity {
    String value;
    String text;

    public String getText() {
        return text;
    }

    public String getValue() {
        return value;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
