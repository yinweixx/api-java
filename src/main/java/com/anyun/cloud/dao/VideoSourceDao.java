package com.anyun.cloud.dao;

import com.anyun.cloud.common.exception.DatabaseException;
import com.anyun.cloud.model.dto.PageDto;
import com.anyun.cloud.model.entity.VideoSourceEntity;

import java.util.List;


public interface VideoSourceDao {

    /**
     * 循环插入视频源信息
     * @param v
     * @return
     * @throws DatabaseException
     */
    void insertVideoSource(VideoSourceEntity v);

    /**
     * 查询视频源信息--分页
     * @param index
     * @param limit
     * @param sortBy
     * @param sortDirection
     * @return
     * @throws DatabaseException
     */
    PageDto<VideoSourceEntity> selectPageList(int index, int limit, String sortBy, String sortDirection) throws DatabaseException;

    /**
     * 根据id查视频
     * @param cameraId
     * @return
     */
    VideoSourceEntity selectVideoById(String cameraId) throws DatabaseException;

    /**
     * 查询所有视频
     * @return
     * @throws DatabaseException
     */
    List<VideoSourceEntity> selectVideoAll() throws DatabaseException;

    /**
     * 一键删除所有视频
     * @param ids
     * @throws DatabaseException
     */
    void deleteAll(List<String> ids) throws DatabaseException;


    /**
     * 根据条件查询视频
     * @param index
     * @param limit
     * @param sortBy
     * @param sortDirection
     * @param intersection
     * @param orientation
     * @param desc
     * @param product
     * @param vender
     * @param dataCenter
     * @return
     */
    PageDto<VideoSourceEntity> selectVideoByCondition(int index, int limit, String sortBy, String sortDirection, String intersection, String orientation, String desc, String product, String vender, String dataCenter) throws DatabaseException;

    /**
     * 更新视频数据
     * @param v
     * @throws DatabaseException
     */
    VideoSourceEntity updateVideoSource(VideoSourceEntity v) throws DatabaseException;

    /**
     * 插入单挑数据
     * @param v
     * @return
     * @throws DatabaseException
     */
    VideoSourceEntity insertVideoSourceByCondition(VideoSourceEntity v) throws DatabaseException;

    /**
     * 根据国标ID查询数据
     * @param nationalId
     * @return
     * @throws DatabaseException
     */
    VideoSourceEntity selectVideoByNationalId(String nationalId) throws DatabaseException;
}
