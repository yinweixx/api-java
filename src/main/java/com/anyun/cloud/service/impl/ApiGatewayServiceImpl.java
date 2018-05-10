package com.anyun.cloud.service.impl;

import com.anyun.cloud.common.context.Context;
import com.anyun.cloud.common.json.JsonUtil;
import com.anyun.cloud.common.string.StringUtils;
import com.anyun.cloud.common.sys.Response;
import com.anyun.cloud.dao.ApiGatewayDao;
import com.anyun.cloud.model.ErrorCode;
import com.anyun.cloud.model.dto.PageDto;
import com.anyun.cloud.model.entity.ApiGatewayEntity;
import com.anyun.cloud.model.entity.ElasticSettingEntity;
import com.anyun.cloud.model.param.ElasticSettingParam;
import com.anyun.cloud.model.param.ElasticSettingUpdateParam;
import com.anyun.cloud.service.ApiGatewayService;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.transform.Result;


public class ApiGatewayServiceImpl implements ApiGatewayService {
    private final static Logger LOGGER = LoggerFactory.getLogger(ApiGatewayServiceImpl.class);
    private Context context;
    private ApiGatewayDao apiGatewayDao;
    private Response response;

    @Inject
    public ApiGatewayServiceImpl(Context context) {
        this.context = context;
        this.apiGatewayDao = context.getBeanByClass(ApiGatewayDao.class);
    }

    @Override
    public Response getPageList(int index, int limit, String sortBy, String sortDirection) {
        try {
            response = new Response<PageDto<ApiGatewayEntity>>();
            response.setCode(ErrorCode.NO_ERROR.code());
            response.setContent(apiGatewayDao.selectPageList(index, limit, sortBy, sortDirection));
            return response;
        } catch (Exception e) {
            response = new Response<String>();
            response.setCode(ErrorCode.QUERY_RESOURCE_ERROR.code());
            response.setContent(ErrorCode.QUERY_RESOURCE_ERROR.name() + e.getMessage());
            return response;
        }
    }

    @Override
    public Response getDetails(String name) {
        LOGGER.debug("name:[{}]", name);
        if (StringUtils.isEmpty(name)) {
            response = new Response<String>();
            response.setCode(ErrorCode.PARAMETER_ERROR.code());
            response.setContent(ErrorCode.PARAMETER_ERROR.name() + "name is empty");
            return response;
        }

        try {
            response = new Response<ApiGatewayEntity>();
            ApiGatewayEntity apiGatewayEntity = apiGatewayDao.selectDetailsByName(name);
            response.setCode(ErrorCode.NO_ERROR.code());
            response.setContent(apiGatewayEntity);
        } catch (Exception e) {
            response = new Response<String>();
            response.setCode(ErrorCode.QUERY_RESOURCE_ERROR.code());
            response.setContent(ErrorCode.QUERY_RESOURCE_ERROR.name() + e.getMessage());
        }
        return response;
    }

    @Override
    public Response updateElasticSetting(String body) {
        LOGGER.debug("body:[{}]", body);
        if (StringUtils.isEmpty(body)) {
            response = new Response<String>();
            response.setCode(ErrorCode.PARAMETER_ERROR.code());
            response.setContent(ErrorCode.PARAMETER_ERROR.name() + "body is empty");
            return response;
        }
        ElasticSettingUpdateParam param;
        try {
            param = JsonUtil.fromJson(ElasticSettingUpdateParam.class, body);
            LOGGER.debug("param:[{}]", param.asJson());
        } catch (Exception e) {
            response.setCode(ErrorCode.PARAMETER_ERROR.code());
            response.setContent(ErrorCode.PARAMETER_ERROR.name() + e.getMessage());
            return response;
        }
        ElasticSettingEntity el = apiGatewayDao.selectElasticSetting();
        if (el ==null){
            try {
                ElasticSettingParam cparam  = new ElasticSettingParam();
                cparam.setMinLink(param.getMinLink());
                cparam.setMaxLink(param.getMaxLink());
                ElasticSettingEntity ec = apiGatewayDao.insertElasticSetting(cparam);
                response = new Response<>();
                response.setCode(ErrorCode.NO_ERROR.code());
                response.setContent(ec);
            }catch (Exception e){
                response = new Response<String>();
                response.setCode(ErrorCode.PUT_RESOURCE_ERROR.code());
                response.setContent(ErrorCode.PUT_RESOURCE_ERROR.name() + e.getMessage());
            }

        }
        try {
            response = new Response<>();
            ElasticSettingEntity es = apiGatewayDao.updateElasticSetting(param);
            response.setCode(ErrorCode.NO_ERROR.code());
            response.setContent(es);
        }catch (Exception e){
            response = new Response<String>();
            response.setCode(ErrorCode.POST_RESOURCE_ERROR.code());
            response.setContent(ErrorCode.POST_RESOURCE_ERROR.name() + e.getMessage());
        }
        return response;
    }

    @Override
    public Response queryElasticSetting() {
        try {
            response = new Response<ElasticSettingEntity>();
            ElasticSettingEntity elasticSettingEntity = apiGatewayDao.selectElasticSetting();
            response.setCode(ErrorCode.NO_ERROR.code());
            response.setContent(elasticSettingEntity);
        } catch (Exception e) {
            response = new Response<String>();
            response.setCode(ErrorCode.QUERY_RESOURCE_ERROR.code());
            response.setContent(ErrorCode.QUERY_RESOURCE_ERROR.name() + e.getMessage());
        }
        return response;
    }

}
