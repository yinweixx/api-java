package com.anyun.cloud.service;

import com.anyun.cloud.common.sys.Response;

/**
 * @author myb  mayanbin@proxzone.com
 * @version 1.0
 * @date 2018/1/23 14:56
 */
public interface MiddlewareService {
    /**
     * 获取中间件信息
     * @param id
     * @return
     */
    Response getDetails(int id);

    /**
     * 分页查询
     * @param index
     * @param limit
     * @param sortBy
     * @param sortDirection
     * @return
     */
    Response getPageList(int index, int limit, String sortBy, String sortDirection);

    /**
     * 根据条件查询中间件
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
    Response getMiddlewareListByCondition(int index, int limit, String sortBy, String sortDirection,String middlewareIp,String middlewareName,String middlewareState,String middlewareType);

}
