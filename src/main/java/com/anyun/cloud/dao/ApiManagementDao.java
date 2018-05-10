package com.anyun.cloud.dao;

import com.anyun.cloud.common.exception.DaoException;

import com.anyun.cloud.model.dto.ApiInfoDto;
import com.anyun.cloud.model.dto.ApiVersionDto;
import com.anyun.cloud.model.dto.PageDto;
import com.anyun.cloud.model.entity.*;

import java.util.List;

/**
 * Created by jt on 18-1-17.
 */
public interface ApiManagementDao {

    void deleteApiById(long id)throws DaoException;

    void deleteApiDocumentationByApiVersionId(long id) throws  DaoException;

    void deleteApiMediaTypeByApiVersionId(long id)throws DaoException;

    void deleteApiInfoMethodParamByApiVersionId(long id)throws DaoException;

    void deleteApiVsersionByApiId(long id)throws DaoException;

    void deleteApiInfoResponseByApiVersionId(long id) throws DaoException;
    void deleteApiVsersionById(long id) throws  DaoException;


    void deleteApiInfoTypePropByApiTypeId(long id)throws DaoException;

    void deleteApiInfoRequestBody(long id)throws DaoException;

    List<ApiInfoResponseEntity>  queryApiResponseByApiVersionId(long id)throws DaoException;

    void deleteApiInfoTypeByResponseId(long apiResponseId)throws DaoException;


    void deleteApiInfoTypeByRequestBodyId(long apiRequestBodyId)throws DaoException;

    /**
     * 5.API列表展示
     */
    PageDto<ApiInfoEntity> queryApiList(int index, int limit, String sortBy, String sortDirection)throws DaoException;

    /**
     * 6.查询api详情
     */
    ApiInfoEntity queryApiById(long id)throws DaoException;

    AppBasicsEntity queryAppByAppId(long id)throws DaoException;

    List<ApiInfoDocumentationEntity> queryApiInfoDocumentByApiVersionId(long id)throws DaoException;

    List<ApiInfoMediaTypeEntity> queryapiInfoMediaTypeByApiVersionId(long id)throws DaoException;

    List<ApiInfoVersionEntity> queryApiVersionByApiId(long id)throws DaoException;

    List<ApiInfoResponseEntity> queryApiInfoResponseByApiVersionId(long id)throws DaoException;

    ApiInfoTypeEntity queryApiInfoTypeByResponseId(long apiResponseId)throws DaoException;

    List<ApiInfoTypePropEntity> queryApiInfoTypePropByApiTypeId (long id)throws DaoException;

    List<ApiInfoMethodParamEntity> queryApiInfoMethodParam(long id)throws DaoException;
    ApiInfoMethodParamEntity queryApiInfoMethodParamById(long id)throws DaoException;

    ApiInfoRequestBodyEntity queryApiInfoRequestBodyByApiVersionId(long id)throws DaoException;

    ApiInfoTypeEntity queryApiInfoTypeByapiRequestBodyId(long id)throws DaoException;

    ServiceEntity queryServiceById(String id)throws DaoException;

    PageDto<ApiInfoEntity> queryApiByCondition(int index, int limit, String sortBy, String sortDirection, String displayName , String baseUrl, String description, String method ,String appName)throws DaoException;

    /**
     * 7.绑定服务
     */
    ApiInfoEntity updateApi(long apiId,String displayName,String description,String appId,String basurl ) throws DaoException;


    /**
     * 9..添加版本
     */
    ApiInfoVersionEntity createApiVersion(ApiInfoVersionEntity apiInfoVersionEntity)   ;
    ApiInfoVersionEntity queryApiVersionByIdAndName(String name ,long id)throws DaoException;

    /**
     * 10.修改版本
     */
    ApiInfoVersionEntity updateApiVersion(long id,String name,String status,String serviceId,String method,String finalpath) ;
    /**
     * 11.查询版本
     */
    ApiInfoVersionEntity queryApiVersionById(long id) ;

    PageDto<ApiInfoVersionEntity> queryApiVersionList(int index, int limit,long apiId, String sortDirection)throws DaoException;

    /**
     * 12.删除版本
     */
    void deleteApiVersion(long id) ;

    /**
     * 12.激活/停用版本
     */
    ApiInfoVersionEntity activationApiVersion(long id,boolean status) ;



    ApiInfoEntity insertApiInfoEntity(ApiInfoEntity apiInfoEntity) ;


    ApiInfoDocumentationEntity insertApiInfoDocumentationEntity(ApiInfoDocumentationEntity apiInfoDocumentationEntity) ;


    ApiInfoMethodParamEntity insertApiInfoMethodParamEntity(ApiInfoMethodParamEntity apiInfoMethodParamEntity) ;


    ApiInfoTypePropEntity insertApiInfoTypePropEntity(ApiInfoTypePropEntity apiInfoTypePropEntity) ;


     ApiInfoTypeEntity insertApiInfoTypeEntity(ApiInfoTypeEntity apiInfoTypeEntity) ;


    ApiInfoRequestBodyEntity insertApiInfoRequestBodyEntity(ApiInfoRequestBodyEntity apiInfoRequestBodyEntity) ;


    ApiInfoResponseEntity insertApiInfoResponseEntity(ApiInfoResponseEntity apiInfoResponseEntity);


    ApiInfoDocumentationEntity exportApiDocumentation(long id);

    ApiInfoMediaTypeEntity insertApiInfoMediaTypeEntity(ApiInfoMediaTypeEntity mediaType);

    List<ServiceEntity> queryServiceList()throws DaoException;

    List<ApiInfoEntity> queryApis()throws DaoException;

    ApiInfoEntity queryApiByBasurlAndDisplayname(String baseUrl,String diaplayname) throws DaoException;

    ApiInfoDocumentationEntity queryApiInfoDocumentById(long id)throws DaoException;

    ApiInfoDocumentationEntity updateApiDocument(long id,String title,String content);

    ApiInfoMediaTypeEntity queryapiInfoMediaTypeById (long id)throws DaoException;

    ApiInfoMediaTypeEntity updateApiMediaById (long id,String name)throws DaoException;
    ApiInfoMethodParamEntity updateApiMethodParam(long id,String name,String description,String required,String type,String example)throws DaoException;

    ApiInfoRequestBodyEntity updateApiRequestBody (long apiVersionId,String contentType)throws DaoException;
    ApiInfoTypeEntity updateApiTypeByRequestBody(long apiRequestBodyId,String name)throws DaoException;
    ApiInfoTypePropEntity updateApiTpyeProp(long id,String type,String required,String example)throws DaoException;
    ApiInfoResponseEntity updateApiResponse(long id,String contentType,String code,String description)throws DaoException;

    ApiInfoTypeEntity updateApiTypeByResponseId(long apiResponseId,String name)throws DaoException;

    ApiInfoVersionEntity queryApiVersionByApiIdAndName(long apiId,String apiVersionName)throws DaoException;

    ApiInfoVersionEntity queryApiVersionByFinalPath(String finalPath)throws DaoException;

    List<ApiInfoEntity> queryApiByAppIdIsNull()throws DaoException;
    List<ApiInfoEntity> queryApiByAppId(long appId)throws DaoException;
    PageDto<ApiVersionDto> queryApiVersionByCondition(int index, int limit, String sortBy,
                                                      String sortDirection, String displayName, String appName)throws DaoException;
}
