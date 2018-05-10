package com.anyun.cloud.service;

import com.anyun.cloud.common.sys.Response;

public interface TaskManagementService {

    /**
     * 更新任务
     * @param body
     * @return
     */
    Response update(String body);

    /**
     * 任务列表，条件查询
     * @param index
     * @param limit
     * @param sortBy
     * @param sortDirection
     * @param taskName
     * @param shortUrl
     * @param branchName
     * @param projectName
     * @return
     */
    Response queryTaskByCondition(int index, int limit, String sortBy, String sortDirection, String taskName, String shortUrl, String branchName, String projectName);

    /**
     * 查看任务详情
     * @param id
     * @return
     */
    Response getDetail(String id);

    /**
     * cron表达式解析
     * @param
     * @return
     */
    Response TaskCronAnalysis(String body);
}
