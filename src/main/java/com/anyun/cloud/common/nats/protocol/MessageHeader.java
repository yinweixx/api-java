package com.anyun.cloud.common.nats.protocol;

import com.google.gson.annotations.SerializedName;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 24/11/2017
 */
public class MessageHeader {
    public static final String TYPE_REQ = "req";
    public static final String TYPE_RESP = "resp";
    @SerializedName("time")
    private long time;
    @SerializedName("version")
    private String version;
    @SerializedName("type")
    private String type;

    @SerializedName("app")
    private String application;


    public MessageHeader() {
    }

    public MessageHeader(long time, String version, String type, String application) {
        this.time = time;
        this.version = version;
        this.type = type;
        this.application = application;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    @Override
    public String toString() {
        return "MessageHeader{" +
                "time=" + time +
                ", version='" + version + '\'' +
                ", type='" + type + '\'' +
                ", application='" + application + '\'' +
                '}';
    }
}
