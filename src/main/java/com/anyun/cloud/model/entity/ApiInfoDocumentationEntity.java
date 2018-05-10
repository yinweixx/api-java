package com.anyun.cloud.model.entity;

import com.anyun.cloud.common.tools.db.AbstractEntity;
import com.iciql.Iciql;

/**
 * api 文档
 * 只有上传的zip raml  才有文档
 */
@Iciql.IQTable(name="api_documentation",create = true)
public class ApiInfoDocumentationEntity extends AbstractEntity {
    @Iciql.IQColumn(name = "id", primaryKey = true,autoIncrement = true)
    private Long id;// 主键
    @Iciql.IQColumn(name = "api_version_id")
    private Long apiVersionId;// 主键（api表主键）
    @Iciql.IQColumn(name = "title", length = 200)
    private String title; //标题
    @Iciql.IQColumn(name = "content", length = 500)
    private String content;//内容

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getApiVersionId() {
        return apiVersionId;
    }

    public void setApiVersionId(Long apiVersionId) {
        this.apiVersionId = apiVersionId;
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
}
