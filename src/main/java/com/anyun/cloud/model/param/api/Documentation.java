package com.anyun.cloud.model.param.api;

import com.anyun.cloud.common.tools.db.AbstractEntity;

/**
 * Documentation class
 *
 * @author jt
 * @date 2018/1/26
 */
public class Documentation extends AbstractEntity{
    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String content;

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
}
