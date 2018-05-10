package com.anyun.cloud.dao;

import com.anyun.cloud.common.exception.DatabaseException;
import com.anyun.cloud.model.dto.PageDto;
import com.anyun.cloud.model.entity.IpUseEntity;

/**
 * @author myb  mayanbin@proxzone.com
 * @version 1.0
 * @date 2018/2/1 19:10
 */
public interface IpUseDao {
    /**
     * 查询已是使用ip信息
     * @param id
     * @return
     * @throws DatabaseException
     */
    IpUseEntity selectDetailsById(int id)throws DatabaseException;

    /**
     * 添加使用Ip信息
     * @param p
     * @return
     * @throws DatabaseException
     */
    IpUseEntity insert(IpUseEntity p)throws DatabaseException;

    /**
     *
     * @param blockId
     * @param ip
     * @return
     */
    IpUseEntity selectIpUseEntityByBlockIdAndIp(int blockId,String ip);

    /**
     * 获取IP段所有已使用的ip
     * @param index
     * @param limit
     * @param sortBy
     * @param sortDirection
     * @param blockId
     * @return
     */
    PageDto<IpUseEntity> selectPageList(int index, int limit, String sortBy, String sortDirection, int blockId);

    /**
     * 解绑Ip
     * @param id
     * @throws DatabaseException
     */
    void deleteById( int id)throws DatabaseException;

    /**
     *
     * @param id
     * @return
     * @throws DatabaseException
     */
    long selectIpUsedNumByBlockId(int id)throws  DatabaseException;

}
