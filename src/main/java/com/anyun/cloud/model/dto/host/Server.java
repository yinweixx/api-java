package com.anyun.cloud.model.dto.host;

import com.anyun.cloud.common.tools.db.AbstractEntity;

/**
 * @author myb  mayanbin@proxzone.com
 * @version 1.0
 * @date 2018/1/29 15:18
 */
public class Server extends AbstractEntity {
    public String     Version;
    public String     ApiVersion;
    public String     MinAPIVersion;
    public String     GitCommit;
    public String     GoVersion;
    public String     BuildTime;

    public String getVersion() {
        return Version;
    }

    public void setVersion(String version) {
        Version = version;
    }

    public String getApiVersion() {
        return ApiVersion;
    }

    public void setApiVersion(String apiVersion) {
        ApiVersion = apiVersion;
    }

    public String getMinAPIVersion() {
        return MinAPIVersion;
    }

    public void setMinAPIVersion(String minAPIVersion) {
        MinAPIVersion = minAPIVersion;
    }

    public String getGitCommit() {
        return GitCommit;
    }

    public void setGitCommit(String gitCommit) {
        GitCommit = gitCommit;
    }

    public String getGoVersion() {
        return GoVersion;
    }

    public void setGoVersion(String goVersion) {
        GoVersion = goVersion;
    }

    public String getBuildTime() {
        return BuildTime;
    }

    public void setBuildTime(String buildTime) {
        BuildTime = buildTime;
    }
}
