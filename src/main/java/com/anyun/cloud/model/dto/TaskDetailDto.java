package com.anyun.cloud.model.dto;

import com.anyun.cloud.common.tools.db.AbstractEntity;

public class TaskDetailDto extends AbstractEntity{
    public String taskName;//任务名称
    public Boolean status;//状态
    public String desc;//描述
    public String crontabExpression;//定时设置
    public String branchName;//版本名称
    public String projectName;//项目名称
    public String gitUrl;//git地址
    public String currentBranch;//当前版本

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCrontabExpression() {
        return crontabExpression;
    }

    public void setCrontabExpression(String crontabExpression) {
        this.crontabExpression = crontabExpression;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getGitUrl() {
        return gitUrl;
    }

    public void setGitUrl(String gitUrl) {
        this.gitUrl = gitUrl;
    }

    public String getCurrentBranch() {
        return currentBranch;
    }

    public void setCurrentBranch(String currentBranch) {
        this.currentBranch = currentBranch;
    }
}
