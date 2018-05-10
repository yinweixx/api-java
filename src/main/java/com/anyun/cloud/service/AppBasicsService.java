package com.anyun.cloud.service;

import com.anyun.cloud.common.sys.Response;
import com.anyun.cloud.model.entity.AppBasicsEntity;

public interface AppBasicsService {

    /**
     * 创建应用
     * @param body
     * @return
     */
    Response create(String body);

    /**
     * 更新应用
     * @param body
     * @return
     */
    Response update(String body);

    /**
     * 删除应用
     * @param id
     * @return
     */
    Response delete(long id);

    /**
     * 分页查询应用列表
     * @param index
     * @param limit
     * @param sortBy
     * @param sortDirection
     * @return
     */
    Response getPageList(int index, int limit, String sortBy, String sortDirection);

    /**
     * 根据名称查询应用--模糊查询
     * @param condition
     * @return
     */
    Response getVagueList(String condition);

    /**
     * 根据条件查询应用列表
     * @param name
     * @param shortName
     * @param startTime
     * @param endTime
     * @return
     */
    Response getAppListByCondition(int index, int limit, String sortBy, String sortDirection,String name, String shortName, String startTime, String endTime);

}
