package com.anyun.cloud.service.impl;

import com.anyun.cloud.common.context.Context;
import com.anyun.cloud.common.json.JsonUtil;
import com.anyun.cloud.common.string.StringUtils;
import com.anyun.cloud.common.sys.Response;
import com.anyun.cloud.dao.HostManagementDao;
import com.anyun.cloud.dao.IpBlockDao;
import com.anyun.cloud.dao.IpUseDao;
import com.anyun.cloud.model.ErrorCode;
import com.anyun.cloud.model.dto.PageDto;
import com.anyun.cloud.model.entity.HostBaseEntity;
import com.anyun.cloud.model.entity.IpBlockEntity;
import com.anyun.cloud.model.entity.IpUseEntity;
import com.anyun.cloud.model.param.IpBlockCreateParam;
import com.anyun.cloud.model.param.IpBlockUpdateParam;
import com.anyun.cloud.service.IpBlockService;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author myb  mayanbin@proxzone.com
 * @version 1.0
 * @date 2018/1/22 10:28
 */
public class IpBlockServiceImpl implements IpBlockService {
    private final static Logger LOGGER = LoggerFactory.getLogger(IpBlockService.class);
    private IpBlockDao ipBlockDao;
    private IpUseDao ipUseDao;
    private HostManagementDao hostManagementDao;
    private Context context;
    private Response response;

    @Inject
    public IpBlockServiceImpl(Context context) {
        this.context = context;
        this.ipBlockDao = context.getBeanByClass(IpBlockDao.class);
        this.ipUseDao=context.getBeanByClass(IpUseDao.class);
        this.hostManagementDao=context.getBeanByClass(HostManagementDao.class);
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
            response = new Response<IpBlockEntity>();
            IpBlockEntity ipBlockEntity = ipBlockDao.selectDetailsById(id);
            response.setCode(ErrorCode.NO_ERROR.code());
            response.setContent(ipBlockEntity);
        } catch (Exception e) {
            response = new Response<String>();
            response.setCode(ErrorCode.QUERY_RESOURCE_ERROR.code());
            response.setContent(ErrorCode.QUERY_RESOURCE_ERROR.name() + e.getMessage());
        }
        return response;
    }

    @Override
    public Response queryByPoolId(int index, int limit, String sortBy, String sortDirection, int poolId) {
        try {
            response = new Response<PageDto<IpBlockEntity>>();
            response.setCode(ErrorCode.NO_ERROR.code());
            response.setContent(ipBlockDao.selectBlockList(index, limit, sortBy, sortDirection,poolId));
            return response;
        } catch (Exception e) {
            response = new Response<String>();
            response.setCode(ErrorCode.QUERY_RESOURCE_ERROR.code());
            response.setContent(ErrorCode.QUERY_RESOURCE_ERROR.name() + e.getMessage());
            return response;
        }
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
            response = new Response<IpBlockEntity>();
            long usedNum = ipUseDao.selectIpUsedNumByBlockId(id);
            long hostIpNumber = hostManagementDao.selectHostsNumByBlockId(id);
            long usedCount=usedNum+hostIpNumber;
            if(usedCount==0){
            ipBlockDao.deleteById(id);
            response.setCode(ErrorCode.NO_ERROR.code());
            response.setContent(null);
            return  response;
            }
            else {
                response = new Response<String>();
                response.setCode(ErrorCode.PARAMETER_ERROR.code());
                response.setContent(ErrorCode.PARAMETER_ERROR.name() + "此IP段存在已使用IP，不可删除");
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
        IpBlockCreateParam param;
        try {
            param = JsonUtil.fromJson(IpBlockCreateParam.class, body);
        } catch (Exception e) {
            response.setCode(ErrorCode.PARAMETER_ERROR.code());
            response.setContent(ErrorCode.PARAMETER_ERROR.name() + e.getMessage());
            return response;
        }

        try {
            response = new Response<>();
            IpBlockEntity p;
            p = new IpBlockEntity();
            p.setBlockGateway(param.getBlockGateway());
            p.setBlockNetMask(param.getBlockGateway());
            p.setBlockStartIp(param.getBlockStartIp());
            p.setBlockEndIp(param.getBlockEndIp());
            p.setBlockCategory(param.getBlockCategory());
            p.setPoolId(param.getPoolId());
            p = ipBlockDao.insert(p);
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

        IpBlockEntity p = new IpBlockEntity();
        try {
            IpBlockUpdateParam up = JsonUtil.fromJson(IpBlockUpdateParam.class, body);
            p.setBlockId(up.getBlockId());
            p.setBlockGateway(up.getBlockGateway());
            p.setBlockNetMask(up.getBlockNetMask());
            p.setBlockEndIp(up.getBlockEndIp());
            p.setBlockCategory(up.getBlockCategory());
            p.setPoolId(up.getPoolId());
        } catch (Exception e) {
            response = new Response<String>();
            response.setCode(ErrorCode.PARAMETER_ERROR.code());
            response.setContent(ErrorCode.PARAMETER_ERROR.name() + "格式转换错误！");
            return response;
        }

        try {
            response = new Response<IpBlockEntity>();
            response.setContent(ipBlockDao.update(p));
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
    public Response getPageList(int index, int limit, String sortBy, String sortDirection) {
        try {
            response = new Response<PageDto<IpBlockEntity>>();
            response.setCode(ErrorCode.NO_ERROR.code());
            response.setContent(ipBlockDao.selectPageList(index, limit, sortBy, sortDirection));
            return response;
        } catch (Exception e) {
            response = new Response<String>();
            response.setCode(ErrorCode.QUERY_RESOURCE_ERROR.code());
            response.setContent(ErrorCode.QUERY_RESOURCE_ERROR.name() + e.getMessage());
            return response;
        }
    }

    /**
     * 根据条件查询Ip段
     * @param index
     * @param limit
     * @param sortBy
     * @param sortDirection
     * @param poolId
     * @param blockStartIp
     * @param blockEndIp
     * @param blockGateway
     * @param blockNetMask
     * @param blockCategory
     * @return
     */
    @Override
    public Response getBlockListByCondition(int index, int limit, String sortBy, String sortDirection, int poolId, String blockStartIp, String blockEndIp, String blockGateway, String blockNetMask, String blockCategory) {
        try {
            response = new Response<PageDto<IpBlockEntity>>();
            response.setCode(ErrorCode.NO_ERROR.code());
            response.setContent(ipBlockDao.selectBlockListByCondition(index, limit, sortBy, sortDirection,poolId,blockStartIp,blockEndIp,blockGateway,blockNetMask,blockCategory));
            return response;
        } catch (Exception e) {
            response = new Response<String>();
            response.setCode(ErrorCode.QUERY_RESOURCE_ERROR.code());
            response.setContent(ErrorCode.QUERY_RESOURCE_ERROR.name() + e.getMessage());
            return response;
        }
        }
}



