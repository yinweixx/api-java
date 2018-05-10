package com.anyun.cloud.dao.impl;

import com.anyun.cloud.common.db.Database;
import com.anyun.cloud.common.exception.DatabaseException;
import com.anyun.cloud.common.json.JsonUtil;
import com.anyun.cloud.dao.ApiGatewayDao;

import com.anyun.cloud.model.dto.ApiVerificationQueryDto;
import com.anyun.cloud.model.dto.PageDto;
import com.anyun.cloud.model.entity.ApiGatewayEntity;

import com.anyun.cloud.model.entity.ApiInfoEntity;
import com.anyun.cloud.model.entity.ApiInfoVersionEntity;
import com.anyun.cloud.model.entity.ElasticSettingEntity;
import com.anyun.cloud.model.param.ElasticSettingParam;
import com.anyun.cloud.model.param.ElasticSettingUpdateParam;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.util.List;

public class ApiGatewayDaoImpl extends AbstractIciqlDao implements ApiGatewayDao {
    private final  static Logger  LOGGER = LoggerFactory.getLogger(ApiGatewayDaoImpl.class);
    @Inject
    public ApiGatewayDaoImpl(Database database) {
        super(database);
    }



    @Override
    public ApiGatewayEntity selectDetailsByName(String name) throws DatabaseException{
        ApiGatewayEntity a= new ApiGatewayEntity();
        List<ApiGatewayEntity> apiGatewayEntity = db.from(a).where(a.getGatewayName()).is(name).select();
        if (apiGatewayEntity == null || apiGatewayEntity.isEmpty() || apiGatewayEntity.size() ==0){
            return null;
        }
        return apiGatewayEntity.get(0);
    }

    @Override
    public PageDto<ApiGatewayEntity> selectPageList(int index, int limit, String sortBy, String sortDirection) {
        //定义变量
        PageDto<ApiGatewayEntity> pageDto = new PageDto<>();
        List<ApiGatewayEntity> data = null;
        ApiGatewayEntity a = new ApiGatewayEntity();
        int total = (int) db.from(a).selectCount();

        //判断有无数据
        if (total > 0) { //有数据
            if (sortDirection.equals("desc")) { //倒序
                data = db.from(a).orderByDesc(sortBy).limit(limit).offset((index - 1) * limit).select();
            } else {//正序
                data = db.from(a).orderBy(sortBy).limit(limit).offset((index - 1) * limit).select();
            }
        }
        pageDto.setIndex(index);
        pageDto.setLimit(limit);
        pageDto.setTotal(total);
        pageDto.setData(data);
        return pageDto;
    }

    @Override
    public List<ApiVerificationQueryDto> selectVerificationParam() throws DatabaseException {
        String sql = "select  a.id as apiId,a.bae_url as baseUrl,v.method as method,v.path as path, " +
                "v.name as version , v.service_id as serviceId,v.final_path as final from  " +
                "api as a  left  join api_version  as v on  a.id= v.api_id " +
                "where v.service_id is not null and v.service_id!=''and  v.status =1;";


        LOGGER.debug("sql[{}]",sql);
        ResultSet rs = db.executeQuery(sql);
        List<ApiVerificationQueryDto> list =db.buildObjects(ApiVerificationQueryDto.class,rs);
        LOGGER.debug("querylist:[{}]：", JsonUtil.toJson(list));
        return list;
    }

    @Override
    public ElasticSettingEntity insertElasticSetting(ElasticSettingParam param) throws DatabaseException {
        ElasticSettingEntity es = new ElasticSettingEntity();
        es.setMinLink(param.getMinLink());
        es.setMaxLink(param.getMaxLink());
        Long id = db.insertAndGetKey(es);
        return db.from(es).where(es.getId()).is(id).select().get(0);
    }

    @Override
    public ElasticSettingEntity updateElasticSetting(ElasticSettingUpdateParam param) throws DatabaseException {
        ElasticSettingEntity es = new ElasticSettingEntity();
        db.from(es).set(es.getMinLink()).to(param.getMinLink()).set(es.getMaxLink()).to(param.getMaxLink())
                .where(es.getId()).is(param.getId()).update();
        return db.from(es).where(es.getId()).is(param.getId()).selectFirst();
    }

    @Override
    public Integer selectElasticCount() throws DatabaseException {
        ElasticSettingEntity es = new ElasticSettingEntity();
        Long count =  db.from(es).selectCount();
        return count.intValue();
    }

    @Override
    public ElasticSettingEntity selectElasticSetting() throws DatabaseException {
        ElasticSettingEntity el = new ElasticSettingEntity();
        return db.from(el).select().get(0);
    }


}
