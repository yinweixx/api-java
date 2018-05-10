package com.anyun.cloud.dao;

import com.anyun.cloud.common.exception.DatabaseException;

import com.anyun.cloud.model.dto.ApiVerificationQueryDto;
import com.anyun.cloud.model.dto.PageDto;
import com.anyun.cloud.model.entity.ApiGatewayEntity;
import com.anyun.cloud.model.entity.ApiInfoEntity;
import com.anyun.cloud.model.entity.ApiInfoVersionEntity;
import com.anyun.cloud.model.entity.ElasticSettingEntity;
import com.anyun.cloud.model.param.ElasticSettingParam;
import com.anyun.cloud.model.param.ElasticSettingUpdateParam;

import java.util.List;


public interface ApiGatewayDao {


    ApiGatewayEntity selectDetailsByName(String name)  throws DatabaseException;

    PageDto<ApiGatewayEntity> selectPageList(int index, int limit, String sortBy, String sortDirection);

    List<ApiVerificationQueryDto> selectVerificationParam() throws DatabaseException;

    ElasticSettingEntity insertElasticSetting(ElasticSettingParam param) throws DatabaseException;

    ElasticSettingEntity updateElasticSetting(ElasticSettingUpdateParam param) throws DatabaseException;

    Integer selectElasticCount() throws DatabaseException;

    ElasticSettingEntity selectElasticSetting() throws DatabaseException;
}
