package com.anyun.cloud.model.dto.host;

import com.anyun.cloud.common.tools.db.AbstractEntity;

/**
 * @author myb  mayanbin@proxzone.com
 * @version 1.0
 * @date 2018/1/29 15:01
 */
public class Agent extends AbstractEntity{
    public String Version;
    public String BuildTime;
    public String GoVersion;

    public String getVersion() {
        return Version;
    }

    public void setVersion(String version) {
        Version = version;
    }

    public String getBuildTime() {
        return BuildTime;
    }

    public void setBuildTime(String buildTime) {
        BuildTime = buildTime;
    }

    public String getGoVersion() {
        return GoVersion;
    }

    public void setGoVersion(String goVersion) {
        GoVersion = goVersion;
    }
}
