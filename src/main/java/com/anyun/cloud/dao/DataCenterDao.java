package com.anyun.cloud.dao;

import com.anyun.cloud.common.exception.DatabaseException;
import com.anyun.cloud.model.dto.PageDto;
import com.anyun.cloud.model.entity.DataCenterEntity;


/**
 * @author myb  mayanbin@proxzone.com
 * @version 1.0
 * @date 2018/1/17 10:10
 */
public interface DataCenterDao {
    /**
     * 根据id查询详情
     * @param id
     * @return
     * @throws DatabaseException
     */
    DataCenterEntity selectDetailsById(int id) throws DatabaseException;

    /**
     *  根据id 删除数据中心
     * @param id
     * @throws DatabaseException
     */
    void deleteById(int id) throws DatabaseException;

    /**
     * 添加数据中心
     * @param p
     * @return
     * @throws DatabaseException
     */
    DataCenterEntity insert(DataCenterEntity p) throws DatabaseException;

    /**
     * 更新数据中心
     * @param p
     * @return
     * @throws DatabaseException
     */
    DataCenterEntity update( DataCenterEntity   p) throws DatabaseException;
    /**
     * 分页查询
     * @param index
     * @param limit
     * @param sortBy
     * @param sortDirection
     * @return
     */
    PageDto<DataCenterEntity> selectPageList(int index, int limit, String sortBy, String sortDirection);
    /**
     *
     * @param centerCategory
     * @return
     * @throws DatabaseException
     */
    DataCenterEntity selectDataCenterByCategory( String centerCategory)throws  DatabaseException;

    /**
     *
     * @param id
     * @return
     * @throws DatabaseException
     */
    long selectIpUsedByCenterId(int id)throws DatabaseException;

    /**
     *
     * @param centerName
     * @return
     * @throws DatabaseException
     */
    DataCenterEntity selectDetailsByName(String centerName) throws DatabaseException;


}
