package com.anyun.cloud.dao;

import com.anyun.cloud.common.exception.DatabaseException;
import com.anyun.cloud.model.entity.TaskTimedEntity;

import java.util.List;

public interface TaskManagementDao {
    /**
     * 更新任务
     * @param id
     * @param status
     */
    void updateService(String id, boolean status);

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
    Object QueryConditionService(int index, int limit, String sortBy, String sortDirection, String taskName, String shortUrl, String branchName, String projectName);

    /**
     * 查看任务详情
     * @param id
     * @return
     */
    Object selectTaskDetail(String id);

    /**
     * 更新从容表达式
     * @param id
     * @param cron
     */
    void updateTaskCron(String id, String cron,String timeds);

    /**
     * 插入时间
     * @param t
     * @throws DatabaseException
     */
    void insertTaskTimed(TaskTimedEntity t) throws DatabaseException;

    /**
     * 查询时间
     * @param id
     * @return
     * @throws DatabaseException
     */
    List<TaskTimedEntity> selectTaskTimed(String id) throws DatabaseException;

    void deleteTime(String id);
}
