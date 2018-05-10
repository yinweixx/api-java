package com.anyun.cloud.common.nats;
import com.anyun.cloud.common.context.SystemEnvironment;

public class NatsConfig {
    public static final String DEFAULT_SUBJECT_SERVICE = "API_CONTROLLER_CHANNEL_TEST";
    private String url;
    private String subject;

    public NatsConfig(SystemEnvironment environment) {
        this.url = environment.getEnv(SystemEnvironment.NATS_URL);
        this.subject = DEFAULT_SUBJECT_SERVICE;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
