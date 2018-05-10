package com.anyun.cloud.dao;

import com.anyun.cloud.common.exception.DatabaseException;
import com.anyun.cloud.model.dto.PageDto;
import com.anyun.cloud.model.entity.IpBlockEntity;

/**
 * @author myb  mayanbin@proxzone.com
 * @version 1.0
 * @date 2018/1/22 8:59
 */
public interface IpBlockDao {
    /**
     * 根据id查询Ip段信息
     * @param id
     * @return
     * @throws DatabaseException
     */
    IpBlockEntity selectDetailsById(int id)throws DatabaseException;

    /**
     * 根据ip池id查询ip段列表
     * @param index
     * @param limit
     * @param sortBy
     * @param sortDirection
     * @param poolId
     * @return
     */
    PageDto<IpBlockEntity> selectBlockList(int index, int limit, String sortBy, String sortDirection,int poolId);

    /**
     * 删除单个Ip段
     * @param id
     * @throws DatabaseException
     */
    void deleteById (int id)throws DatabaseException;

    /**
     * 创建Ip段
     * @param p
     * @return
     * @throws DatabaseException
     */
    IpBlockEntity  insert (IpBlockEntity p)throws DatabaseException;

    /**
     * 更新Ip段
     * @param p
     * @return
     * @throws DatabaseException
     */
    IpBlockEntity update(IpBlockEntity p)throws DatabaseException;

    /**
     *列表显示IP段列表
     * @param index
     * @param limit
     * @param sortBy
     * @param sortDirection
     * @return
     */
    PageDto<IpBlockEntity> selectPageList(int index, int limit, String sortBy, String sortDirection);

    /**
     *根据条件显示Ip段
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
    PageDto<IpBlockEntity> selectBlockListByCondition(int index, int limit, String sortBy, String sortDirection,int poolId,String blockStartIp,String blockEndIp,String blockGateway,String blockNetMask,String blockCategory);

    /**
     *
     * @param id
     * @return
     * @throws DatabaseException
     */
    IpBlockEntity selectIpBlockByPoolId(int id)throws DatabaseException;
}
