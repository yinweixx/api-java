package com.anyun.cloud.service;
import com.anyun.cloud.common.sys.Response;
import com.anyun.cloud.model.dto.host.Host;

import java.util.List;

/**
 * @author myb  mayanbin@proxzone.com
 * @version 1.0
 * @date 2018/1/24 11:15
 */
public interface HostManagementService {
    /**
     * 获取宿主机基础信息
     * @param id
     * @return
     */
    Response getRegisteredHosts(int id);

    /**
     * 根据Ip获取宿主机lxd信息
     * @param id
     * @return
     */
    Response getHostLxdDetails(int id);
    /**
     *  获取宿主机Docker信息
     * @param id
     * @return
     */
    Response getHostDockerDetails(int id);
    /**
     *  获取宿主机软件信息
     * @param id
     * @return
     */
    Response getHostSoftwareDetails(int id);
    /**
     * 获取宿主机硬件信息
     * @param id
     * @return
     */
    Response getHostHardwareDetails(int id);
    /**
     * 查询未注册宿主机列表
     * @return
     */
    Response getUnregisteredHosts();
    /**
     * 注册宿主机
     * @param ip
     * @return
     */
    Response registeredHosts(String ip);
    /**
     * 分页查询
     * @param index
     * @param limit
     * @param sortBy
     * @param sortDirection
     * @return
     */
    Response getPageList(int index, int limit, String sortBy, String sortDirection);
    /**
     * 注销宿主机
     * @param id
     * @return
     */
    Response cancel (int id);
    /**
     * 根据条件模糊查询已注册宿主机
     *
     * @param
     * @param
     * @param sortBy
     * @param sortDirection
     * @param ip
     * @return
     */
    Response getHostsListByCondition(int index, int limit, String sortBy, String sortDirection, String ip,String environment,String category,String centerName);

    /**
     * 从etcd获取未注册宿主机信息
     * @return
     */
    List<Host> getHosts();
    /**
     *
     * @param centerId
     * @return
     */
    Response getHostsByCenterName(int centerId);

    /**
     *
     * @param index
     * @param limit
     * @return
     */
    Response getUnregisteredHostsList(int index, int limit);


}
