package com.anyun.cloud.common;

import com.anyun.cloud.common.json.JsonUtil;
import com.anyun.cloud.common.tools.db.AbstractEntity;

public class Configuration  extends  AbstractEntity{
    private String platformAddress;
    private int port = 8989;
    private String baseUrl=null;
    private boolean https = false;

    public static Configuration buildConfiguration(String jsonBody) {
        return JsonUtil.fromJson(Configuration.class,jsonBody);
    }

    public Configuration() {
    }

    public String getPlatformAddress() {
        return platformAddress;
    }

    public void setPlatformAddress(String platformAddress) {
        this.platformAddress = platformAddress;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public boolean isHttps() {
        return https;
    }

    public void setHttps(boolean https) {
        this.https = https;
    }


}
