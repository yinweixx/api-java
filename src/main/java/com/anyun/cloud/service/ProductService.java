package com.anyun.cloud.service;

import com.anyun.cloud.common.sys.Response;

import javax.servlet.http.HttpServletRequest;

public interface ProductService {
    /**
     * 获取详情
     */
    Response getDetails(int id);

    /**
     * 根据id删除产品记录
     */
    Response delete(int id);

    /**
     * 添加单个
     */
    Response create(String body);

    /**
     * 批量添加
     */
    Response batchCreate(String body);

    /**
     * 更新一条记录
     */
    Response update(String body);

    /**
     * 批量更新
     */
    Response batchUpdate(String body);

    /**
     * 分页查询
     */
    Response getPageList(int index, int limit, String sortBy, String sortDirection);

    /**
     * 上传文件
     * @param request
     */
    Response upload(HttpServletRequest request);
}
