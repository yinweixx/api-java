package com.anyun.cloud.dao;

import com.anyun.cloud.common.exception.DatabaseException;
import com.anyun.cloud.model.dto.PageDto;
import com.anyun.cloud.model.entity.MiddlewareEntity;

/**
 * @author myb  mayanbin@proxzone.com
 * @version 1.0
 * @date 2018/1/23 13:53
 */
public interface MiddlewareDao {

    /**
     * 中间件查询
     * @param id
     * @return
     * @throws DatabaseException
     */
    MiddlewareEntity selectDetailsById(int id) throws DatabaseException;

    /**
     * 分页查询
     * @param index
     * @param limit
     * @param sortBy
     * @param sortDirection
     * @return
     */
    PageDto<MiddlewareEntity> selectPageList(int index, int limit, String sortBy, String sortDirection);

    /**
     * 根据条件查询中间件信息
     * @param index
     * @param limit
     * @param sortBy
     * @param sortDirection
     * @param middlewareIp
     * @param middlewareName
     * @param middlewareState
     * @param middlewareType
     * @return
     */
    PageDto<MiddlewareEntity> selectMiddlewareListByCondition(int index, int limit, String sortBy, String sortDirection,String middlewareIp,String middlewareName,String middlewareState,String middlewareType);
}
