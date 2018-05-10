package com.anyun.cloud.dao;

import com.anyun.cloud.common.exception.DatabaseException;
import com.anyun.cloud.model.dto.PageDto;
import com.anyun.cloud.model.entity.ProductEntity;

import java.util.List;

public interface ProductDao {

    /**
     * 根据id查询详情
     */
    ProductEntity selectDetailsById(int id) throws DatabaseException;

    /**
     * 多条件查询
     */
    List<ProductEntity> selectList(int id,String name,String category) throws DatabaseException;

    /**
     * 根据id 删除记录
     */
    void deleteById(int id) throws DatabaseException;

    /**
     * 添加一条记录
     */
    ProductEntity insert(ProductEntity p) throws DatabaseException;

    /**
     * 批量添加
     */
    List<ProductEntity> insert(List<ProductEntity> list) throws DatabaseException;


    /**
     * 更新一条记录
     */
    ProductEntity update(ProductEntity p) throws DatabaseException;

    /**
     * 批量更新
     */
    List<ProductEntity> update(List<ProductEntity> list) throws DatabaseException;

    /**
     * 分页查询
     */
    PageDto<ProductEntity> selectPageList(int index, int limit, String sortBy, String sortDirection);
}
