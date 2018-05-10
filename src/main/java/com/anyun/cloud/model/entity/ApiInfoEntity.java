package com.anyun.cloud.model.entity;

import com.anyun.cloud.common.tools.db.AbstractEntity;
import com.iciql.Iciql;
import com.iciql.Iciql.IQColumn;

import java.util.List;

/**
 * Api
 */
@Iciql.IQTable(name = "api", create = true)
public class ApiInfoEntity extends AbstractEntity {

    //主键
    @IQColumn(name = "id", primaryKey = true, autoIncrement = true)
    private Long id;

    //应用主键
    @IQColumn(name = "app_id", length = 100)
    private Long appId;

    //基本路径
    @IQColumn(name = "bae_url", length = 100)
    private String baseUrl;

    //该api 的名称
    @IQColumn(name = "display_name", length = 100)
    private String displayName;

    //该api  描述
    @IQColumn(name = "description", length = 3000)
    private String description;




    private String appName;

    //版本列表　
    List<ApiInfoVersionEntity> versionList;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public List<ApiInfoVersionEntity> getVersionList() {
        return versionList;
    }

    public void setVersionList(List<ApiInfoVersionEntity> versionList) {
        this.versionList = versionList;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
}
