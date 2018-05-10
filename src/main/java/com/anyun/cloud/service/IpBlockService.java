package com.anyun.cloud.service;

import com.anyun.cloud.common.sys.Response;

/**
 * @author myb  mayanbin@proxzone.com
 * @version 1.0
 * @date 2018/1/22 10:00
 */
public interface IpBlockService  {
    /**
     * 查询ip段
     * @param id
     * @return
     */
    Response getDetails(int id);
    /**
     * 根据ip池id获取ip段列表
     * @param index
     * @param limit
     * @param sortBy
     * @param sortDirection
     * @param poolId
     * @return
     */
    Response queryByPoolId(int index, int limit, String sortBy, String sortDirection,int poolId);
    /**
     * 删除Ip段
     * @param id
     * @return
     */
    Response delete(int id);
    /**
     * 添加Ip段
     * @param body
     * @return
     */
    Response create(String body);
    /**
     * 修改ip段信息
     * @param body
     * @return
     */
    Response update (String body);
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
     *根据条件查询Ip段
     * @param index
     * @param limit
     * @param sortBy
     * @param sortDirection
     * @param poolId
     * @param blockStartIp
     * @param blockEndIp
     * @param blockGateway
     * @param blockNetMask
     * @param blockCategory
     * @return
     */

    Response getBlockListByCondition(int index, int limit, String sortBy, String sortDirection,int poolId,String blockStartIp,String blockEndIp,String blockGateway,String blockNetMask,String blockCategory);
}
