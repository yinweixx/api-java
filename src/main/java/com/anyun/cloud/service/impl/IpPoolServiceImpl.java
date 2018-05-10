package com.anyun.cloud.service.impl;

import com.anyun.cloud.common.context.Context;
import com.anyun.cloud.common.json.JsonUtil;
import com.anyun.cloud.common.string.StringUtils;
import com.anyun.cloud.common.sys.Response;
import com.anyun.cloud.dao.DataCenterDao;
import com.anyun.cloud.dao.IpBlockDao;
import com.anyun.cloud.dao.IpPoolDao;
import com.anyun.cloud.model.ErrorCode;
import com.anyun.cloud.model.dto.PageDto;
import com.anyun.cloud.model.entity.IpBlockEntity;
import com.anyun.cloud.model.entity.IpPoolEntity;
import com.anyun.cloud.model.param.IpPoolCreateParam;
import com.anyun.cloud.model.param.IpPoolUpdateParam;
import com.anyun.cloud.service.IpPoolService;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author myb  mayanbin@proxzone.com
 * @version 1.0
 * @date 2018/1/19 10:10
 */
public class IpPoolServiceImpl implements IpPoolService {
    private  final static Logger LOGGER= LoggerFactory.getLogger(IpPoolServiceImpl.class);
    private IpPoolDao ipPoolDao;
    private IpBlockDao ipBlockDao;
    private Context context;
    private Response response;
    @Inject
    public IpPoolServiceImpl(Context context){
        this.context=context;
        this.ipPoolDao=context.getBeanByClass(IpPoolDao.class);
        this.ipBlockDao=context.getBeanByClass(IpBlockDao.class);

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
            response = new Response<IpPoolEntity>();
            IpPoolEntity ipPoolEntity = ipPoolDao.selectDetailsById(id);
            response.setCode(ErrorCode.NO_ERROR.code());
            response.setContent(ipPoolEntity);
        } catch (Exception e) {
            response = new Response<String>();
            response.setCode(ErrorCode.QUERY_RESOURCE_ERROR.code());
            response.setContent(ErrorCode.QUERY_RESOURCE_ERROR.name() + e.getMessage());
        }
        return response;
    }

    @Override
    public Response delete(int id) {
        LOGGER.debug("id:[{}]", id);
        if (id == 0) {
            response = new Response<String>();
            response.setCode(ErrorCode.PARAMETER_ERROR.code());
            response.setContent(ErrorCode.PARAMETER_ERROR.name() + "id is empty");
        }
        try {
            response = new Response<IpPoolEntity>();
            IpBlockEntity ipBlockEntity=ipBlockDao.selectIpBlockByPoolId(id);
            if(ipBlockEntity==null){
            ipPoolDao.deleteById(id);
            response.setCode(ErrorCode.NO_ERROR.code());
            response.setContent(null);
            return  response;
            }
            else{
                response = new Response<String>();
                response.setCode(ErrorCode.PARAMETER_ERROR.code());
                response.setContent(ErrorCode.PARAMETER_ERROR.name() + "此IP池还存在IP段，不可删除");
                return  response;
            }
        } catch (Exception e) {
            response = new Response<String>();
            response.setCode(ErrorCode.DELETE_RESOURCE_ERROR.code());
            response.setContent(ErrorCode.DELETE_RESOURCE_ERROR.name() + e.getMessage());
        }
        return response;
    }

    @Override
    public Response create(String body) {
        LOGGER.debug("body:[{}]", body);
        if (StringUtils.isEmpty(body)) {
            response = new Response<String>();
            response.setCode(ErrorCode.PARAMETER_ERROR.code());
            response.setContent(ErrorCode.PARAMETER_ERROR.name() + "body is empty");
            return response;
        }
        IpPoolCreateParam param;
        try {
            param = JsonUtil.fromJson(IpPoolCreateParam.class, body);
        } catch (Exception e) {
            response.setCode(ErrorCode.PARAMETER_ERROR.code());
            response.setContent(ErrorCode.PARAMETER_ERROR.name() + e.getMessage());
            return response;
        }

        try {
            response = new Response<>();
            IpPoolEntity p;
            p = new IpPoolEntity();
            p.setPoolName(param.getPoolName());
            p.setEnvironment(param.getEnvironment());
            p.setCategory(param.getCategory());
           // p.setCenterName(param.getCenterName());
            p.setCenterId(param.getCenterId());
            p = ipPoolDao.insert(p);
            response.setCode(ErrorCode.NO_ERROR.code());
            response.setContent(p);
        } catch (Exception e) {
            response = new Response<String>();
            response.setCode(ErrorCode.PUT_RESOURCE_ERROR.code());
            response.setContent(ErrorCode.PUT_RESOURCE_ERROR.name() + e.getMessage());
        }
        return response;
    }

    @Override
    public Response update(String body) {
        LOGGER.debug("body:[{}]", body);
        if (StringUtils.isEmpty(body)) {
            response = new Response<String>();
            response.setCode(ErrorCode.PARAMETER_ERROR.code());
            response.setContent(ErrorCode.PARAMETER_ERROR.name() + "body is empty");
            return response;
        }

        IpPoolEntity p = new IpPoolEntity();
        try {
            IpPoolUpdateParam up = JsonUtil.fromJson(IpPoolUpdateParam.class, body);
            p.setPoolId(up.getPoolId());
            p.setPoolName(up.getPoolName());
            p.setEnvironment(up.getEnvironment());
            p.setCategory(up.getCategory());
           // p.setCenterName(up.getCenterName());
            p.setCenterId(up.getCenterId());
        } catch (Exception e) {
            response = new Response<String>();
            response.setCode(ErrorCode.PARAMETER_ERROR.code());
            response.setContent(ErrorCode.PARAMETER_ERROR.name() + "格式转换错误！");
            return response;
        }

        try {
            response = new Response<IpPoolEntity>();
            response.setContent(ipPoolDao.update(p));
            response.setCode(ErrorCode.NO_ERROR.code());
            return response;
        } catch (Exception e) {
            response = new Response<String>();
            response.setCode(ErrorCode.POST_RESOURCE_ERROR.code());
            response.setContent(ErrorCode.POST_RESOURCE_ERROR.name() + e.getMessage());
            return response;
        }
    }

    @Override
    public Response getPageList(int index, int limit, String sortBy, String sortDirection,int centerId) {
        try {
            response = new Response<PageDto<IpPoolEntity>>();
            response.setCode(ErrorCode.NO_ERROR.code());
            response.setContent(ipPoolDao.selectPageList(index, limit, sortBy, sortDirection,centerId));
            return response;
        } catch (Exception e) {
            response = new Response<String>();
            response.setCode(ErrorCode.QUERY_RESOURCE_ERROR.code());
            response.setContent(ErrorCode.QUERY_RESOURCE_ERROR.name() + e.getMessage());
            return response;
        }
    }

    @Override
    public Response getPoolListByCondition(int index, int limit, String sortBy, String sortDirection, String poolName, String environment, String category,int centerId) {
        try {
            response = new Response<PageDto<IpPoolEntity>>();
            response.setCode(ErrorCode.NO_ERROR.code());
            response.setContent(ipPoolDao.selectPoolListByCondition(index, limit, sortBy, sortDirection,poolName,environment,category,centerId));
            return response;
        } catch (Exception e) {
            response = new Response<String>();
            response.setCode(ErrorCode.QUERY_RESOURCE_ERROR.code());
            response.setContent(ErrorCode.QUERY_RESOURCE_ERROR.name() + e.getMessage());
            return response;

    }
    }

}






