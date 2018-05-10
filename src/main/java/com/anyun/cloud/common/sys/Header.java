package com.anyun.cloud.common.sys;

public class Header {
    /*
    * 服务器消息发出的时间戳
    */
    private long time;

    /*
    * 版本信息
    */
    private String version;


    /*
    *
    * 类型
    * req 请求
    * res 应答
    */
    private String type;


    /*
    *
    * 应用信息
    * 如 storage-management
    */
    private String app;


    public Header(long time, String version, String type, String app) {
        this.time = time;
        this.version = version;
        this.type = type;
        this.app = app;
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

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }
}
