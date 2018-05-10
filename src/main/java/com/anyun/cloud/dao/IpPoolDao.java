package com.anyun.cloud.dao;

import com.anyun.cloud.common.exception.DatabaseException;
import com.anyun.cloud.model.dto.PageDto;
import com.anyun.cloud.model.entity.DataCenterEntity;
import com.anyun.cloud.model.entity.HostBaseEntity;
import com.anyun.cloud.model.entity.IpPoolEntity;
import com.anyun.cloud.model.param.HostDistributionParam;

import java.util.List;

/**
 * @author myb  mayanbin@proxzone.com
 * @version 1.0
 * @date 2018/1/18 19:21
 */
public interface IpPoolDao {
    /**
     * 查询当前ip池
     * @param id
     * @return
     * @throws DatabaseException
     */
    IpPoolEntity selectDetailsById(int id) throws DatabaseException;

    /**
     * 根据Id删除单个ip池
     * @param id
     * @throws DatabaseException
     */
      void  deleteById(int id) throws DatabaseException;

    /**
     * 创建单个Ip池
     * @param p
     * @return
     * @throws DatabaseException
     */
    IpPoolEntity insert(IpPoolEntity p) throws  DatabaseException;

    /**
     * 更新某个Ip池
     * @param p
     * @return
     * @throws DatabaseException
     */
    IpPoolEntity update (IpPoolEntity p)throws DatabaseException;

    /**
     * 分页显示当前ip池
     * @param index
     * @param limit
     * @param sortBy
     * @param sortDirection
     * @return
     */
    PageDto<IpPoolEntity> selectPageList(int index, int limit, String sortBy, String sortDirection,int centerId);

    /**
     * 根据条件查询Ip池
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
    PageDto<IpPoolEntity> selectPoolListByCondition(int index, int limit, String sortBy, String sortDirection,String poolName, String environment, String category,int centerId);

    /**
     * 查询IP池id--（调度使用）
     * @param param
     * @return
     * @throws DatabaseException
     */
//    List<IpPoolEntity> selectPoolIdByCondition(HostDistributionParam param) throws DatabaseException;

    /**
     * 查询IP段信息--（调度使用）
     * @param blockCategory
     * @param poolId
     * @return
     * @throws DatabaseException
     */
//    List<IpBlockEntity> selectIpBlockByCondition(String blockCategory, int poolId) throws DatabaseException;

    /**
     * 查询宿主机信息--（调度使用）
     * @return
     * @throws DatabaseException
     */
//    List<HostBaseEntity> selectHostBase() throws DatabaseException;

    /**
     * 根据dns名称查询数据中心信息--（调度使用）
     * @param dnsName
     * @return
     * @throws DatabaseException
     */
    DataCenterEntity selectDataCenterByDns(String dnsName) throws DatabaseException;

    /**
     * 根据条件查询宿主机信息--（调度使用）
     * @param hostCategory
     * @param environment
     * @param centerName
     * @return
     * @throws DatabaseException
     */
    List<HostBaseEntity> selectHostBase(String hostCategory, String environment, String centerName) throws DatabaseException;
}
