package com.anyun.cloud.common.elasticsearch;

import com.anyun.cloud.common.context.SystemEnvironment;

/**
 * @author Shen Xutao
 * @project api-management
 * @email 314255358@qq.com
 * @date 2018/3/26 14:29 星期一
 */
public class ElasticsearchConifg {
    private String url;
    private String username;
    private String password;

    public ElasticsearchConifg(SystemEnvironment environment) {
        this.url = environment.getEnv(SystemEnvironment.ELASTICSEARCH_URL);
        this.username = environment.getEnv(SystemEnvironment.ELASTICSEARCH_USER_NAME);
        this.password = environment.getEnv(SystemEnvironment.ELASTICSEARCH_PASSWORD);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
