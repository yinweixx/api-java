package com.anyun.cloud.service.impl;

import com.anyun.cloud.common.context.Context;
import com.anyun.cloud.common.sys.Response;
import com.anyun.cloud.dao.MiddlewareDao;
import com.anyun.cloud.model.ErrorCode;
import com.anyun.cloud.model.dto.PageDto;
import com.anyun.cloud.model.entity.MiddlewareEntity;
import com.anyun.cloud.service.MiddlewareService;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author myb  mayanbin@proxzone.com
 * @version 1.0
 * @date 2018/1/23 14:58
 */
public class MiddlewareServiceImpl implements MiddlewareService {
    private final static Logger LOGGER = LoggerFactory.getLogger(MiddlewareServiceImpl.class);
    private MiddlewareDao middlewareDao;
    private Context context;
    private Response response;

    @Inject
    public MiddlewareServiceImpl(Context context) {
        this.context = context;
        this.middlewareDao = context.getBeanByClass(MiddlewareDao.class);
    }

    @Override
    public Response getDetails(int id) {
        LOGGER.debug("id:[{}]", id);
        if (id == 0) {
            response = new Response<String>();
            response.setCode(ErrorCode.PARAMETER_ERROR.code());
            response.setContent(ErrorCode.PARAMETER_ERROR.name() + "id is empty");
        }

        try {
            response = new Response<MiddlewareEntity>();
            MiddlewareEntity middlewareEntity = middlewareDao.selectDetailsById(id);
            response.setCode(ErrorCode.NO_ERROR.code());
            response.setContent(middlewareEntity);
        } catch (Exception e) {
            response = new Response<String>();
            response.setCode(ErrorCode.QUERY_RESOURCE_ERROR.code());
            response.setContent(ErrorCode.QUERY_RESOURCE_ERROR.name() + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getPageList(int index, int limit, String sortBy, String sortDirection) {
        try {
            response = new Response<PageDto<MiddlewareEntity>>();
            response.setCode(ErrorCode.NO_ERROR.code());
            response.setContent(middlewareDao.selectPageList(index, limit, sortBy, sortDirection));
            return response;
        } catch (Exception e) {
            response = new Response<String>();
            response.setCode(ErrorCode.QUERY_RESOURCE_ERROR.code());
            response.setContent(ErrorCode.QUERY_RESOURCE_ERROR.name() + e.getMessage());
            return response;
        }
    }

    @Override
    public Response getMiddlewareListByCondition(int index, int limit, String sortBy, String sortDirection, String middlewareIp, String middlewareName, String middlewareState, String middlewareType) {
        try {
            response = new Response<PageDto<MiddlewareEntity>>();
            response.setCode(ErrorCode.NO_ERROR.code());
            response.setContent(middlewareDao.selectMiddlewareListByCondition(index, limit, sortBy, sortDirection,middlewareIp,middlewareName,middlewareState,middlewareType));
            return response;
        } catch (Exception e) {
            response = new Response<String>();
            response.setCode(ErrorCode.QUERY_RESOURCE_ERROR.code());
            response.setContent(ErrorCode.QUERY_RESOURCE_ERROR.name() + e.getMessage());
            return response;

        }
    }

}
