package com.anyun.cloud.service;

import com.anyun.cloud.common.sys.Response;

/**
 * @author myb  mayanbin@proxzone.com
 * @version 1.0
 * @date 2018/1/19 10:06
 */
public interface IpPoolService {

    /**
     *  获取ip池详情
     * @param id
     * @return
     */
    Response getDetails (int id);

    /**
     * 删除单个ip池
     * @param id
     * @return
     */
    Response delete(int id);

    /**
     *  添加单个Ip池
     * @param body
     * @return
     */
    Response create (String body);

    /**
     * 修改Ip池信息
     * @param body
     * @return
     */
    Response update(String body);

    /**
     * 分页显示查询ip池
     * @param index
     * @param limit
     * @param sortBy
     * @param sortDirection
     * @return
     */
    Response getPageList(int index, int limit, String sortBy, String sortDirection,int centerId);

    /**
     * 根据条件查询Ip池信息
     * @param index
     * @param limit
     * @param sortBy
     * @param sortDirection
     * @param poolName
     * @param environment
     * @param category
     * @param centerName
     * @return
     */
    Response getPoolListByCondition(int index, int limit, String sortBy, String sortDirection,String poolName,String  environment,String  category,int centerId);
}
