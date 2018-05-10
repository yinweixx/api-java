package com.anyun.cloud.dao;

import com.anyun.cloud.common.exception.DatabaseException;
import com.anyun.cloud.model.dto.PageDto;
import com.anyun.cloud.model.dto.UnregisteredHostsDto;
import com.anyun.cloud.model.entity.*;

import java.util.List;

/**
 * @author myb  mayanbin@proxzone.com
 * @version 1.0
 * @date 2018/1/23 17:23
 */
public interface HostManagementDao {

    /**
     * 查询已注册宿主机列表
     *
     * @param id
     * @return
     * @throws DatabaseException
     */
    HostBaseEntity selectDetailsById(int id) throws DatabaseException;

    /**
     * 注册宿主机基础信息
     *
     * @param h
     * @return
     * @throws DatabaseException
     */
    HostBaseEntity insert(HostBaseEntity h) throws DatabaseException;

    /**
     * 获取宿主机LXD 信息
     *
     * @param id
     * @return
     */
    HostLxdEntity selectLxdDetailsById(int id);

    /**
     * 注册宿主机Lxd信息
     *
     * @param h
     * @return
     * @throws DatabaseException
     */
    HostLxdEntity insert(HostLxdEntity h) throws DatabaseException;

    /**
     * 注册宿主机Docker client信息
     *
     * @param h
     * @return
     * @throws DatabaseException
     */
    HostDockerEntity insert(HostDockerEntity h) throws DatabaseException;

    /**
     * 根据id查询宿主机Docker信息
     *
     * @param id
     * @return
     */
    HostDockerEntity selectDockerDetailsById(int id);

    /**
     * 查询未注册宿主机信息
     *
     * @param ip
     * @return
     */
    HostBaseEntity selectHostBaseEntityByHostIp(String ip);

    /**
     * 注册宿主机软件信息
     *
     * @param h
     * @return
     * @throws DatabaseException
     */
    HostSoftwareEntity insert(HostSoftwareEntity h) throws DatabaseException;

    /**
     * 查询宿主机软件信息
     *
     * @param id
     * @return
     */
    HostSoftwareEntity selectSoftwareDetailsById(int id);

    /**
     * 注册宿主机硬件信息
     *
     * @param h
     * @return
     * @throws DatabaseException
     */
    HostHardwareEntity insert(HostHardwareEntity h) throws DatabaseException;

    /**
     * 显示宿主机硬件信息列表
     *
     * @param id
     * @return
     */
    HostHardwareEntity selectHardwareDetailsById(int id);

    /**
     * 分页查询
     *
     * @param index
     * @param limit
     * @param sortBy
     * @param sortDirection
     * @return
     */
    PageDto<HostBaseEntity> selectPageList(int index, int limit, String sortBy, String sortDirection);

    /**
     * 注销宿主机
     *
     * @param id
     * @throws DatabaseException
     */
    void cancelById(int id) throws DatabaseException;

    /**
     * 根据条件模糊查询宿主机
     *
     * @param index
     * @param limit
     * @param sortBy
     * @param sortDirection
     * @param ip
     * @return
     */
    PageDto<HostBaseEntity> selectHostsListByCondition(int index, int limit, String sortBy, String sortDirection, String ip, String environment, String category, String centerName);
    /**
     *
     * @param id
     * @return
     * @throws DatabaseException
     */
    long selectHostsNumByBlockId(int id)throws DatabaseException;

    /**
     *
     * @param centerId
     * @return
     * @throws DatabaseException
     */
    List<HostBaseEntity> selectHostsListByCenterName(int centerId)throws DatabaseException;
    /**
     *
     * @param h
     * @return
     * @throws DatabaseException
     */
    IpUseEntity insert(IpUseEntity h) throws DatabaseException;

    /**
     *
     * @param index
     * @param limit
     * @return
     */
    PageDto<UnregisteredHostsDto> selectHostsList(int index, int limit);

}
