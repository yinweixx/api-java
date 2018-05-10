package com.anyun.cloud.dao;

import com.anyun.cloud.common.exception.DatabaseException;
import com.anyun.cloud.model.dto.PageDto;
import com.anyun.cloud.model.entity.AppBasicsEntity;

import java.util.Date;
import java.util.List;

public interface AppBasicsDao {
    /**
     * 创建应用
     * @param a
     * @return
     * @throws DatabaseException
     */
    AppBasicsEntity insert(AppBasicsEntity a) throws DatabaseException;

    /**
     * 更新应用
     * @param a
     * @return
     * @throws DatabaseException
     */
    AppBasicsEntity update(AppBasicsEntity a) throws DatabaseException;

    /**
     * 删除应用
     * @param id
     * @throws DatabaseException
     */
    void deleteById(long id) throws DatabaseException;

    /**
     * 分页查询应用
     * @param index
     * @param limit
     * @param sortBy
     * @param sortDirection
     * @return
     */
    PageDto<AppBasicsEntity> selectPageList(int index, int limit, String sortBy, String sortDirection);

    /**
     * 根据名称查询应用详情
     * @param name
     * @return
     * @throws DatabaseException
     */
    AppBasicsEntity selectDetailsByName(String name) throws DatabaseException;

    /**
     * 验证app名称是否重复
     * @param appId
     * @return
     */
    AppBasicsEntity selectAppNameById(Long appId) throws DatabaseException;

    /**
     * 根据名称查询应用--模糊查询
     * @param condition
     * @return
     * @throws DatabaseException
     */
    List<AppBasicsEntity> selectVagueListByName(String condition) throws DatabaseException;

    /**
     * 根据条件查询应用列表
     * @param name
     * @param shortName
     * @param startTime
     * @param endTime
     * @return
     */
    PageDto<AppBasicsEntity> selectAppListByCondition(int index, int limit, String sortBy, String sortDirection,String name, String shortName, String startTime, String endTime);


    AppBasicsEntity selectAppBasicsEntityById(Long appId);
}


