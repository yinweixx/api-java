package com.anyun.cloud.dao;

import com.anyun.cloud.common.sys.Response;
import com.anyun.cloud.model.dto.PageDto;
import com.anyun.cloud.model.dto.ServiceConditionDto;
import com.anyun.cloud.model.dto.ServiceListDto;
import com.anyun.cloud.model.dto.TaskListDto;
import com.anyun.cloud.model.entity.ServiceBranchEntity;
import com.anyun.cloud.model.entity.ServiceEntity;
import com.anyun.cloud.model.entity.ServiceProjectEntity;
import com.anyun.cloud.model.entity.TaskBasicsEntity;

import java.net.URL;
import java.util.List;

public interface ServiceManagementDao {

    /**
     * 更新服务表
     * @param
     * @return
     */
    void updateService(String id,boolean status);



    /**
     * 删除服务
     * @param id
     */
    void deletedService(String id);



    /**
     * 查询所有服务（分页查询）
     * @param index
     * @param limit
     * @param sortBy
     * @param sortDirection
     * @return
     */
    Object selectPageList(int index, int limit, String sortBy, String sortDirection);

    /**
     * 查询服务详情
     * @param id
     * @return
     */
    Object selectServiceDetail(String id,String user);

    /**
     * 查询用户的所有服务
     * @param index
     * @param limit
     * @param sortBy
     * @param sortDirection
     * @return
     */
    Object selectPageListByUser(int index, int limit, String sortBy, String sortDirection,String user);


    /**
     * 项目存入数据库
     * @param p
     */
    int insertProject(ServiceProjectEntity p);

    /**
     * 版本存入数据库
     * @param entityList
     */
    void insertBranch(List<ServiceBranchEntity> entityList);

    /**
     * 服务存入数据库
     * @param serviceList
     */
    void insertService(List<ServiceEntity> serviceList);

    /**
     * 查询项目id
     * @param s
     * @return
     */
    int selectProjectId(String s);

    /**
     * 查找版本id
     *
     * @return
     */
    int selectBranchId(String s,int pid);

    /**
     * 删除版本（创建出错使用）
     * @param pid
     */
    void deletedBranch(int pid);

    /**
     * 删除项目（创建出错使用）
     * @param s
     */
    void deleteProject(int s);

    /**
     * 查看项目是否存在
     * @param s
     * @return
     */
    int selectProjectName(String s);

    /**
     * 服务回滚
     * @param pid
     * @param name
     */
    void rollbackService(int pid, String name);

    /**
     * 根据条件查询服务
     * @param
     * @return
     */
    PageDto<ServiceConditionDto> QueryConditionService(int index, int limit, String sortBy, String sortDirection, String sname, String bname, String pname, String user, String shortUrl);

    /**
     * 删除服务（根据项目id删除）
     * @param id
     */
    void deletedService1(int id);

    /**
     * 删除版本（根据项目id删除）
     * @param id
     */
    void deletedBranch1(int id);

    /**
     * 删除项目（根据项目id删除）
     * @param id
     */
    void deleteProject1(int id);

    /**
     * 根据项目id查询项目下所有的分支id
     * @param id
     * @return
     */
    List<Integer> queryBranchId(int id);

    /**
     * 根据项目id查询用户
     * @param id
     * @return
     */
    String queryUserById(int id);

    /**
     * 根据服务id查询用户
     * @param id
     * @return
     */
    Integer queryUserByServiceId(String id);

    /**
     * 根据版本id查询用户
     * @param id
     * @return
     */
    String queryUserByBranchId(int id);

    /**
     * 项目列表
     * @param index
     * @param limit
     * @param sortBy
     * @param sortDirection
     * @param
     * @return
     */
    Object selectPageProjectList(int index, int limit, String sortBy, String sortDirection);

    /**
     * 根据版本名称查询版本id
     * @param name
     * @return
     */
    int selectBranchIdByName(String name,int id,String user);

    /**
     * 更新版本id
     * @param a
     */
    void updateBranchId(int a,int bid);

    /**
     * 根据项目id查出项目git地址
     * @param id
     * @return
     */
    String selectProjectGitId(int id);

    /**
     * 更改是否私有
     * @param id
     * @param aPrivate
     * @param user
     */
    void updatePrivate(String id, boolean aPrivate, String user);

    /**
     * 根据项目id查询版本信息
     * @param id
     */
    List<String> queryBranchByProjectId(int id);


    List<ServiceListDto> selectServiceList(Integer integer);

    /**
     * 更改项目中版本信息
     * @param branch
     */
    void updateProjectBranch(String branch,int id);

    /**
     * 根据条件查询项目
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
    Object QueryConditionProject(int index, int limit, String sortBy, String sortDirection, String pname, String shortUrl, String type);

    /**
     * 更新项目的类型
     * @param type
     */
    void updateProjectType(String type,int pid);

    /**
     * 插入任务
     * @param taskList
     */
    void insertTask(List<TaskBasicsEntity> taskList);

    /**
     * 删除任务
     * @param id
     */
    void deleteTask(int id);

    void updateProjectTypeIsTask(String type, int pid);

    /**
     * 根据项目id查询任务
     * @param integer
     * @return
     */
    List<TaskListDto> selectTypeList(Integer integer);

    PageDto QueryConditionService(int index, int limit, String sortBy, String sortDirection, String sname,String pname,String environmental);

    void updateProject(int id, boolean environmental);
}
