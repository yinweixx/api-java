package com.anyun.cloud.common.nats.format;

public interface MessageFormater {

    /**
     *
     * @param business
     * @param obj
     * @return
     */
    byte[] format(String business, Object obj);

    default String getApplication(){
        return "";
    }
}
