package com.anyun.cloud.service;

import com.anyun.cloud.common.sys.Response;

/**
 * @author myb  mayanbin@proxzone.com
 * @version 1.0
 * @date 2018/2/1 20:15
 */
public interface IpUseService {
    /**
     * 查询已使用ip信息
     * @param id
     * @return
     */
    Response getDetails(int id);

    /**
     * 获取分配ip列表
     * @param count
     * @param blockId
     * @return
     */
    Response getIpList(int count, int blockId,String des);

    /**
     * 获取所有已分配Ip
     * @param index
     * @param limit
     * @param sortBy
     * @param sortDirection
     * @param blockId
     * @return
     */
    Response getPageList(int index, int limit, String sortBy, String sortDirection,int blockId);

    /**
     * 根据ID解绑已占用IP
     * @param id
     * @return
     */
    Response delete(int id);

}
