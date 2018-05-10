package com.anyun.cloud.service;

import com.anyun.cloud.common.sys.Response;

/**
 * @author myb  mayanbin@proxzone.com
 * @version 1.0
 * @date 2018/1/17 9:46
 */
public interface DataCenterService {
    /**
     * 根据Id获取数据中心详情
     * @param id
     * @return
     */
    Response getDetails (int id);
    /**
     * 根据id删除数据中心
     * @param id
     * @return
     */
    Response delete(int id);
    /**
     * 添加单个数据中心
     * @param body
     * @return
     */
    Response create(String body);
    /**
     * 更新数据中心
     * @param body
     * @return
     */
    Response update(String body);
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
     * 首页的根据Id查询数据中心宿主机信息
     * @param id
     * @return
     */
    Response getHostsDetails(int id);
}

