package com.anyun.cloud.model.param.api;

import com.anyun.cloud.common.tools.db.AbstractEntity;

import java.util.List;


/**
 * CreateParam class
 *
 * @author jt
 * @date 2018/1/26
 */
public class CreateParam extends AbstractEntity {

    /**
     * 应用主键      （appId 不能为空 并且应用要存在）
     */
    private Long appId;

    /**
     * 该api 的名称（可以为空）
     */
    private String displayName;
    /**
     * 该api  描述（可以为空）
     */
    private String description;

    /**
     * 版本  （不能为空，至少要一个版本信息）
     */
    private List<Version> versionList;

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
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



    public List<Version> getVersionList() {
        return versionList;
    }

    public void setVersionList(List<Version> versionList) {
        this.versionList = versionList;
    }
}
