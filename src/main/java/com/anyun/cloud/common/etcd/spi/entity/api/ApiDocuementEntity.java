package com.anyun.cloud.common.etcd.spi.entity.api;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 31/05/2017
 */
public class ApiDocuementEntity {
    private String title = "";
    private String content = "";

    public ApiDocuementEntity() {
    }

    public ApiDocuementEntity(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "ApiDocuementEntity {" + "\n" +
                "    title='" + title + "\'\n" +
                "    content='" + content + "\'\n" +
                '}';
    }
}
