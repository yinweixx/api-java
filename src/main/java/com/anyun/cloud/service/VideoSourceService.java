package com.anyun.cloud.service;

import com.anyun.cloud.common.sys.Response;

import javax.servlet.http.HttpServletRequest;

public interface VideoSourceService {

    /**
     * excel表格上传
     * @param request
     * @return
     */
    Response upload(HttpServletRequest request);

    /**
     * 查询视频源信息--分页
     * @param index
     * @param limit
     * @param sortBy
     * @param sortDirection
     * @return
     */
    Response getPageList(int index, int limit, String sortBy, String sortDirection);

    /**
     * 一键删除所有视频数据
     * @return
     */
    Response deleteAllVideo();


    /**
     * 根据条件查询视频
     * @param index
     * @param limit
     * @param sortBy
     * @param sortDirection
     * @param intersection
     * @param orientation
     * @param description
     * @param product
     * @param vender
     * @param dataCenter
     * @return
     */
    Response queryVideoByCondition(int index, int limit, String sortBy, String sortDirection, String intersection, String orientation, String description, String product, String vender, String dataCenter);

    /**
     * 插入单条视频记录
     * @param body
     * @return
     */
    Response insertVideo(String body);

    /**
     * 更新视频数据
     * @param body
     * @return
     */
    Response updateVideo(String body);


}
