package com.anyun.cloud.service;

import com.anyun.cloud.common.sys.Response;

import java.io.File;
import java.net.URL;

public interface ServiceManagementService {
    /**
     * 编辑服务
     * @param body
     * @return
     */
    Response update(String body);

    /**
     * 查询服务详情
     * @param id
     * @return
     */
    Response getDetails(String id,String user);

    /**
     * 根据id删除服务
     * @param id
     * @return
     */
    Response delete(String id);

    /**
     * 查询所有服务（分页查询）
     * @param index
     * @param limit
     * @param sortBy
     * @param sortDirection
     * @return
     */
    Response getPageList(int index, int limit, String sortBy, String sortDirection);

    /**
     * 根据用户查询所有服务
     * @param index
     * @param limit
     * @param sortBy
     * @param sortDirection
     * @return
     */
    Response getPageListByUser(int index, int limit, String sortBy, String sortDirection,String user);

    /**
     * 检查出项目标签
     * @param remote
     *
     * @return
     */
    Response getBranch(URL remote);

    /**
     * 查询服务
     * @param
     * @return
     */
    Response getService(URL remote,String branch);

    /**
     * 服务回滚
     * @param body
     * @return
     */
    Response rollback(String body);

    /**
     * 根据条件查询服务
     * @param
     * @return
     */
    Response queryByCondition(int index, int limit, String sortBy, String sortDirection,String sname,String bname,String pname,String shortUrl,String user);

    /**
     * 根据id删除项目
     * @param id
     * @return
     */
    Response deleteProject(int id,String type,String user);

    /**
     * 项目列表
     * @param index
     * @param limit
     * @param sortBy
     * @param sortDirection
     * @param
     * @return
     */
    Response getPageListProject(int index, int limit, String sortBy, String sortDirection);

    /**
     * 服务的回滚
     * @param body
     * @return
     */
    Response rollbackService(String body);

    /**
     * 更改状态
     * @return
     */
    Response updatePrivate(String body);

    /**
     * 根据项目id查询对应的版本
     * @param id
     * @return
     */
    Response queryBranchByProjectId(int id);

    /**
     * 创建项目
     * @param body
     * @return
     */
    Response createProject(String body);

    /**
     * 根据项目id查询所有的服务
     * @param id
     * @return
     */
    Response queryServiceByProjectId(int id,String type,String user);

    /**
     *
     * @param index
     * @param limit
     * @param sortBy
     * @param sortDirection
     * @param pname
     * @param shortUrl
     * @param type
     * @param
     * @return
     */
    Response queryProjectByCondition(int index, int limit, String sortBy, String sortDirection, String pname, String shortUrl, String type);

    /**
     * 回滚中查找服务
     * @param gitUrl
     * @param branch
     * @return
     */
    Response rollbackGetService(String gitUrl, String branch);

    /**
     * 服务目录
     * @param index
     * @param limit
     * @param sortBy
     * @param sortDirection
     * @param sname
     * @param pname
     * @param environmental
     * @return
     */
    Response queryServiceCatalogByCondition(int index, int limit, String sortBy, String sortDirection, String sname, String environmental, String pname);

    Response updateProject(String body);
}
