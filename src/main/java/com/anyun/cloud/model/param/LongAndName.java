package com.anyun.cloud.model.param;

import com.anyun.cloud.common.tools.db.AbstractEntity;

/**
 * Created by jt on 18-2-2.
 */
public class LongAndName extends AbstractEntity {
    long value;
    String text;

    public String getText() {
        return text;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    public void setText(String text) {
        this.text = text;
    }

}
