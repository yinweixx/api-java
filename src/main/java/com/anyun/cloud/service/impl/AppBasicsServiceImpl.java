package com.anyun.cloud.service.impl;

import com.anyun.cloud.common.context.Context;
import com.anyun.cloud.common.json.JsonUtil;
import com.anyun.cloud.common.string.StringUtils;
import com.anyun.cloud.common.sys.Response;
import com.anyun.cloud.dao.AppBasicsDao;
import com.anyun.cloud.model.ErrorCode;
import com.anyun.cloud.model.dto.PageDto;
import com.anyun.cloud.model.entity.AppBasicsEntity;
import com.anyun.cloud.model.param.AppBasicsCreateParam;
import com.anyun.cloud.model.param.AppBasicsUpdateParam;
import com.anyun.cloud.service.AppBasicsService;
import com.anyun.cloud.service.RamlApiRamlParser;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AppBasicsServiceImpl implements AppBasicsService {
    private final static Logger LOGGER = LoggerFactory.getLogger(AppBasicsServiceImpl.class);
    private Context context;
    private Response response;
    private AppBasicsDao appBasicsDao;
    private RamlApiRamlParser ramlApiRamlParser;

    @Inject
    public AppBasicsServiceImpl(Context context) {
        this.context = context;
        this.appBasicsDao = context.getBeanByClass(AppBasicsDao.class);
        this.ramlApiRamlParser = context.getBeanByClass(RamlApiRamlParser.class);
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
        AppBasicsCreateParam param;
        try {
            param = JsonUtil.fromJson(AppBasicsCreateParam.class, body);
            LOGGER.debug("param:[{}]", param.asJson());
        } catch (Exception e) {
            response.setCode(ErrorCode.PARAMETER_ERROR.code());
            response.setContent(ErrorCode.PARAMETER_ERROR.name() + e.getMessage());
            return response;
        }

        AppBasicsEntity appBasicsEntity = appBasicsDao.selectDetailsByName(param.getName());
        if (appBasicsEntity != null) {
            response = new Response<String>();
            response.setCode(ErrorCode.PARAMETER_ERROR.code());
            response.setContent(ErrorCode.PARAMETER_ERROR.name() + ":应用名称不能重复");
            return response;
        }

        try {
            response = new Response<>();
            AppBasicsEntity a ;
            a= new AppBasicsEntity();
            a.setName(param.getName());
            a.setShortName(param.getShortName());
            a.setDesc(param.getDesc());

            //定义创建时间和最后修改时间格式
            Date date =new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String date1 = sdf.format(date);
            a.setCreateTime(date1);
            a.setLastModifyTime(date1);
            LOGGER.debug("AppBasicsEntity:[{}]", a.asJson());
            a = appBasicsDao.insert(a);
            response.setCode(ErrorCode.NO_ERROR.code());
            response.setContent(a);
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
        AppBasicsEntity au = new AppBasicsEntity();
        try {
            LOGGER.debug("body2:[{}]", body);
            AppBasicsUpdateParam param = JsonUtil.fromJson(AppBasicsUpdateParam.class,body);
            LOGGER.debug("param:[{}]", param.asJson());
            AppBasicsEntity name = appBasicsDao.selectAppNameById(param.getAppId());
            LOGGER.debug("name:[{}]", name.asJson());
            if(param.getName() != null && !param.getName().equals("") && !param.getName().equals(name.getName())){
                if (appBasicsDao.selectDetailsByName(param.getName()) != null ) {
                    response = new Response<String>();
                    response.setCode(ErrorCode.PARAMETER_ERROR.code());
                    response.setContent(ErrorCode.PARAMETER_ERROR.name() + ":应用名称不能重复");
                    return response;
                }
            }

            //定义创建时间和最后修改时间格式
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String date1 = sdf.format(date);
            LOGGER.debug("lastDate:[{}]", date1);
            au.setAppId(param.getAppId());
            au.setName(param.getName());
            au.setDesc(param.getDesc());
            au.setLastModifyTime(date1);
            LOGGER.debug("LastModifyTime:[{}]", au.getLastModifyTime());
            LOGGER.debug("AppBasicsEntity:[{}]", au.asJson());
        } catch (Exception e) {
            response = new Response<String>();
            response.setCode(ErrorCode.PARAMETER_ERROR.code());
            response.setContent(ErrorCode.PARAMETER_ERROR.name() + "格式转换错误！");
            return response;
        }

        try {
            response = new Response<AppBasicsEntity>();
            response.setContent(appBasicsDao.update(au));
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
    public Response delete(long id) {
        LOGGER.debug("id:[{}]", id);
        if (id == 0) {
            response = new Response<String>();
            response.setCode(ErrorCode.PARAMETER_ERROR.code());
            response.setContent(ErrorCode.PARAMETER_ERROR.name() + "id is empty");
        }
        try {
            response = new Response<AppBasicsEntity>();
            appBasicsDao.deleteById(id);
            response.setCode(ErrorCode.NO_ERROR.code());
            response.setContent(null);
        } catch (Exception e) {
            response = new Response<String>();
            response.setCode(ErrorCode.DELETE_RESOURCE_ERROR.code());
            response.setContent(ErrorCode.DELETE_RESOURCE_ERROR.name() + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getPageList(int index, int limit, String sortBy, String sortDirection) {
        try {
            response = new Response<PageDto<AppBasicsEntity>>();
            response.setCode(ErrorCode.NO_ERROR.code());
            response.setContent(appBasicsDao.selectPageList(index, limit, sortBy, sortDirection));
            return response;
        } catch (Exception e) {
            response = new Response<String>();
            response.setCode(ErrorCode.QUERY_RESOURCE_ERROR.code());
            response.setContent(ErrorCode.QUERY_RESOURCE_ERROR.name() + e.getMessage());
            return response;
        }
    }

    @Override
    public Response getVagueList(String condition) {
        LOGGER.debug("condition:[{}]", condition);
        if (StringUtils.isEmpty(condition)) {
            response = new Response<String>();
            response.setCode(ErrorCode.PARAMETER_ERROR.code());
            response.setContent(ErrorCode.PARAMETER_ERROR.name() + ":name is empty");
        }

        try {
            response = new Response<List<AppBasicsEntity>>();
            response.setCode(ErrorCode.NO_ERROR.code());
            response.setContent(appBasicsDao.selectVagueListByName(condition));
        } catch (Exception e) {
            response = new Response<String>();
            response.setCode(ErrorCode.QUERY_RESOURCE_ERROR.code());
            response.setContent(ErrorCode.QUERY_RESOURCE_ERROR.name() + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getAppListByCondition(int index, int limit, String sortBy, String sortDirection,String name, String shortName, String startTime, String endTime) {
        try {
            response = new Response<PageDto<AppBasicsEntity>>();
            response.setCode(ErrorCode.NO_ERROR.code());
            response.setContent(appBasicsDao.selectAppListByCondition(index, limit, sortBy, sortDirection,name,shortName,startTime,endTime));
            return response;
        } catch (Exception e) {
            response = new Response<String>();
            response.setCode(ErrorCode.QUERY_RESOURCE_ERROR.code());
            response.setContent(ErrorCode.QUERY_RESOURCE_ERROR.name() + e.getMessage());
            return response;
        }
    }

}
