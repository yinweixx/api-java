package com.anyun.cloud.dao.impl;

import com.anyun.cloud.common.db.Database;
import com.anyun.cloud.common.exception.DaoException;
import com.anyun.cloud.dao.ApiManagementDao;
import com.anyun.cloud.model.dto.ApiInfoDto;
import com.anyun.cloud.model.dto.ApiVersionDto;
import com.anyun.cloud.model.dto.PageDto;
import com.anyun.cloud.model.entity.*;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.ResultSet;
import java.util.List;

/**
 * Created by jt on 18-1-17.
 */
public class ApiManagementDaoImpl extends AbstractIciqlDao implements ApiManagementDao {
    final static Logger LOGGER = LoggerFactory.getLogger(ApiManagementDaoImpl.class);

    private boolean equals(String a,String b){
        return a.equals(b);
    }
    private String desc = "desc";
    private String isTrue ="true";
    private String isFalse = "false";
    private boolean isNotNull(String a){
        return a!=null&&!a.isEmpty();
    }

    @Inject
    public ApiManagementDaoImpl(Database database) {
        super(database);
    }


    @Override
    public void deleteApiById(long id) throws DaoException {
        LOGGER.debug("id:"+id);
        ApiInfoEntity apiInfoEntity = new ApiInfoEntity();
        db.from(apiInfoEntity).where(apiInfoEntity.getId()).is(id).delete();
    }

    @Override
    public void deleteApiDocumentationByApiVersionId(long id) throws DaoException {
        LOGGER.debug("id:"+id);
        ApiInfoDocumentationEntity apiInfoDocumentationEntity = new ApiInfoDocumentationEntity();
        db.from(apiInfoDocumentationEntity).where(apiInfoDocumentationEntity.getApiVersionId()).is(id).delete();
    }

    @Override
    public void deleteApiMediaTypeByApiVersionId(long id) throws DaoException {
        LOGGER.debug("id:"+id);
        ApiInfoMediaTypeEntity apiInfoMediaTypeEntity = new ApiInfoMediaTypeEntity();
        db.from(apiInfoMediaTypeEntity).where(apiInfoMediaTypeEntity.getApiVersionId()).is(id).delete();
    }

    @Override
    public void deleteApiInfoMethodParamByApiVersionId(long id) throws DaoException {
        LOGGER.debug("id:"+id);
        ApiInfoMethodParamEntity apiInfoMethodParamEntity = new ApiInfoMethodParamEntity();
        db.from(apiInfoMethodParamEntity).where(apiInfoMethodParamEntity.getApiVersionId()).is(id).delete();
    }

    @Override
    public void deleteApiVsersionByApiId(long id) throws DaoException {
        LOGGER.debug("id:"+id);
        ApiInfoVersionEntity apiInfoVersionEntity = new ApiInfoVersionEntity();
        db.from(apiInfoVersionEntity).where(apiInfoVersionEntity.getApiId()).is(id).delete();
    }

    @Override
    public void deleteApiInfoResponseByApiVersionId(long id) throws DaoException {
        ApiInfoResponseEntity apiInfoResponseEntity = new ApiInfoResponseEntity();
        db.from(apiInfoResponseEntity).where(apiInfoResponseEntity.getApiVersionId()).is(id).delete();
    }

    @Override
    public void deleteApiVsersionById(long id) throws DaoException {
        ApiInfoVersionEntity versionEntity = new ApiInfoVersionEntity();
        db.from(versionEntity).where(versionEntity.getId()).is(id).delete();
    }

    @Override
    public void deleteApiInfoTypePropByApiTypeId(long id) throws DaoException {
        LOGGER.debug("id:"+id);
        ApiInfoTypePropEntity apiInfoTypePropEntity = new ApiInfoTypePropEntity();
        db.from(apiInfoTypePropEntity).where(apiInfoTypePropEntity.getApiTypeId()).is(id).delete();
    }

    @Override
    public void deleteApiInfoRequestBody(long id) throws DaoException {
        LOGGER.debug("id:"+id);
        ApiInfoRequestBodyEntity apiInfoRequestBodyEntity = new ApiInfoRequestBodyEntity();
        db.from(apiInfoRequestBodyEntity).where(apiInfoRequestBodyEntity.getApiVersionId()).is(id).delete();
    }

    @Override
    public List<ApiInfoResponseEntity> queryApiResponseByApiVersionId(long id) throws DaoException {
        LOGGER.debug("id:"+id);
        ApiInfoResponseEntity apiInfoResponseEntity = new ApiInfoResponseEntity();
        Long count = db.from(apiInfoResponseEntity).where(apiInfoResponseEntity.getApiVersionId()).is(id).selectCount();
        if (count == 0){
            return null;}
        else
        {
            List<ApiInfoResponseEntity> apiInfoResponseEntityList = db.from(apiInfoResponseEntity).where(apiInfoResponseEntity.getApiVersionId()).is(id).select();
            LOGGER.debug("apiInfoResponseEntityList:"+apiInfoResponseEntityList);
            return apiInfoResponseEntityList;
        }

    }

    @Override
    public void deleteApiInfoTypeByResponseId(long apiResponseId) throws DaoException {
        LOGGER.debug("apiResponseId:"+apiResponseId);
        ApiInfoTypeEntity apiInfoTypeEntity =  new ApiInfoTypeEntity();
        db.from(apiInfoTypeEntity).where(apiInfoTypeEntity.getApiResponseId()).is(apiResponseId).delete();
    }


    @Override
    public void deleteApiInfoTypeByRequestBodyId(long apiRequestBodyId) throws DaoException {
        LOGGER.debug("apiRequestBodyId:"+apiRequestBodyId);
        ApiInfoTypeEntity apiInfoTypeEntity =  new ApiInfoTypeEntity();
        db.from(apiInfoTypeEntity).where(apiInfoTypeEntity.getApiRequestBodyId()).is(apiRequestBodyId).delete();
    }


    @Override
    public PageDto<ApiInfoEntity> queryApiList(int index, int limit, String sortBy, String sortDirection) throws DaoException {
        //定义变量
        PageDto<ApiInfoEntity> pageDto = new PageDto<>();
        List<ApiInfoEntity> data = null;
        ApiInfoEntity apiInfoEntity = new ApiInfoEntity();
        int total = (int) db.from(apiInfoEntity).selectCount();
        //判断有无数据
        if (total > 0) {
            if (equals(sortDirection,desc)) {
                /**
                 * 倒序操作
                 */
                data = db.from(apiInfoEntity).orderByDesc(sortBy).limit(limit).offset((index - 1) * limit).select();
            } else {
                /**
                 * 正序操作
                 */
                data = db.from(apiInfoEntity).orderBy(sortBy).limit(limit).offset((index - 1) * limit).select();
            }
        }
        pageDto.setIndex(index);
        pageDto.setLimit(limit);
        pageDto.setTotal(total);
        pageDto.setData(data);
        return pageDto;
    }

    @Override
    public ApiInfoEntity queryApiById(long id) throws DaoException {
        ApiInfoEntity apiInfoEntity = new ApiInfoEntity();
        Long count = db.from(apiInfoEntity).where(apiInfoEntity.getId()).is(id).selectCount();
        if (count == 0){
            return null;}
        else {
            return db.from(apiInfoEntity).where(apiInfoEntity.getId()).is(id).select().get(0);}
    }

    @Override
    public AppBasicsEntity queryAppByAppId(long id) throws DaoException {
        AppBasicsEntity appBasicsEntity = new AppBasicsEntity();
        Long count = db.from(appBasicsEntity).where(appBasicsEntity.getAppId()).is(id).selectCount();
        if (count == 0){
            return null;}
        else {
            return db.from(appBasicsEntity).where(appBasicsEntity.getAppId()).is(id).select().get(0);
        }
    }

    @Override
    public List<ApiInfoDocumentationEntity> queryApiInfoDocumentByApiVersionId(long id) throws DaoException {
        ApiInfoDocumentationEntity apiInfoDocumentationEntity = new ApiInfoDocumentationEntity();
        Long count = db.from(apiInfoDocumentationEntity).where(apiInfoDocumentationEntity.getApiVersionId()).is(id).selectCount();
        if (count == 0){
            return null;}
        else{
            return db.from(apiInfoDocumentationEntity).where(apiInfoDocumentationEntity.getApiVersionId()).is(id).select();}
    }

    @Override
    public List<ApiInfoMediaTypeEntity> queryapiInfoMediaTypeByApiVersionId(long id) throws DaoException {
        ApiInfoMediaTypeEntity apiInfoMediaTypeEntity = new ApiInfoMediaTypeEntity();
        Long count = db.from(apiInfoMediaTypeEntity).where(apiInfoMediaTypeEntity.getApiVersionId()).is(id).selectCount();
        if (count == 0){
            return null;}
        else{
            return db.from(apiInfoMediaTypeEntity).where(apiInfoMediaTypeEntity.getApiVersionId()).is(id).select();}
    }

    @Override
    public List<ApiInfoVersionEntity> queryApiVersionByApiId(long id) throws DaoException {
        ApiInfoVersionEntity apiInfoVersionEntity = new ApiInfoVersionEntity();
        Long count = db.from(apiInfoVersionEntity).where(apiInfoVersionEntity.getApiId()).is(id).selectCount();
        if (count == 0) {
            return null;
        } else {
            return db.from(apiInfoVersionEntity).where(apiInfoVersionEntity.getApiId()).is(id).select();
        }
    }

    @Override
    public List<ApiInfoResponseEntity> queryApiInfoResponseByApiVersionId(long id) throws DaoException {
        ApiInfoResponseEntity apiInfoResponseEntity = new ApiInfoResponseEntity();
        Long count = db.from(apiInfoResponseEntity).where(apiInfoResponseEntity.getApiVersionId()).is(id).selectCount();
        if (count == 0) {
            return null;
        } else {
            return db.from(apiInfoResponseEntity).where(apiInfoResponseEntity.getApiVersionId()).is(id).select();
        }
    }

    @Override
    public ApiInfoTypeEntity queryApiInfoTypeByResponseId(long apiResponseId) throws DaoException {
        ApiInfoTypeEntity apiInfoTypeEntity = new ApiInfoTypeEntity();
        Long count = db.from(apiInfoTypeEntity).where(apiInfoTypeEntity.getApiResponseId()).is(apiResponseId).selectCount();
        if (count == 0) {
            return null;
        } else {
            return db.from(apiInfoTypeEntity).where(apiInfoTypeEntity.getApiResponseId()).is(apiResponseId).select().get(0);
        }
    }

    @Override
    public List<ApiInfoTypePropEntity> queryApiInfoTypePropByApiTypeId(long id) throws DaoException {
        ApiInfoTypePropEntity apiInfoTypePropEntity = new ApiInfoTypePropEntity();
        Long count = db.from(apiInfoTypePropEntity).where(apiInfoTypePropEntity.getApiTypeId()).is(id).selectCount();
        if (count == 0) {
            return null;
        } else {
            return db.from(apiInfoTypePropEntity).where(apiInfoTypePropEntity.getApiTypeId()).is(id).select();
        }
    }

    @Override
    public List<ApiInfoMethodParamEntity> queryApiInfoMethodParam(long id) throws DaoException {
        ApiInfoMethodParamEntity apiInfoMethodParamEntity = new ApiInfoMethodParamEntity();
        Long count = db.from(apiInfoMethodParamEntity).where(apiInfoMethodParamEntity.getApiVersionId()).is(id).selectCount();
        if (count == 0) {
            return null;
        } else {
            return db.from(apiInfoMethodParamEntity).where(apiInfoMethodParamEntity.getApiVersionId()).is(id).select();
        }
    }

    @Override
    public ApiInfoMethodParamEntity queryApiInfoMethodParamById(long id) throws DaoException {
        ApiInfoMethodParamEntity a = new ApiInfoMethodParamEntity();
        Long count = db.from(a).where(a.getId()).is(id).selectCount();
        if(count==0) {
            return null;
        } else {
            return db.from(a).where(a.getId()).is(id).select().get(0);
        }
    }

    @Override
    public ApiInfoRequestBodyEntity queryApiInfoRequestBodyByApiVersionId(long id) throws DaoException {
        ApiInfoRequestBodyEntity apiInfoRequestBodyEntity = new ApiInfoRequestBodyEntity();
        Long count = db.from(apiInfoRequestBodyEntity).where(apiInfoRequestBodyEntity.getApiVersionId()).is(id).selectCount();
        if (count == 0) {
            return null;
        } else {
            return db.from(apiInfoRequestBodyEntity).where(apiInfoRequestBodyEntity.getApiVersionId()).is(id).selectFirst();
        }
    }

    @Override
    public ApiInfoTypeEntity queryApiInfoTypeByapiRequestBodyId(long id) throws DaoException {
        ApiInfoTypeEntity apiInfoTypeEntity = new ApiInfoTypeEntity();
        Long count = db.from(apiInfoTypeEntity).where(apiInfoTypeEntity.getApiRequestBodyId()).is(id).selectCount();
        if (count == 0) {
            return null;
        } else {
            return db.from(apiInfoTypeEntity).where(apiInfoTypeEntity.getApiRequestBodyId()).is(id).select().get(0);
        }

    }

    @Override
    public ServiceEntity queryServiceById(String id) throws DaoException {
        ServiceEntity serviceEntity = new ServiceEntity();
        Long count = db.from(serviceEntity).where(serviceEntity.getId()).is(id).selectCount();
        if (count == 0) {
            return null;
        } else {
            return db.from(serviceEntity).where(serviceEntity.getId()).is(id).selectFirst();
        }
    }

    @Override
    public PageDto<ApiInfoEntity> queryApiByCondition(int index, int limit, String sortBy, String sortDirection, String displayName, String baseUrl, String description, String method ,String appName)throws DaoException{
        List<ApiInfoEntity> apiInfoEntities;
        PageDto<ApiInfoEntity> pageDto = new PageDto<>();
        String sql = "SELECT *,app_id as appId,bae_url as baseUrl,display_name as displayName,short_name as shortName,create_time as createTime,last_modify_time as lastModifyTime FROM api a left join app_basics b on b.id = a.app_id where 1=1";
        if(isNotNull(displayName)){
            sql +=" and a.display_name like '%"+displayName+"%'" ;
        }
        if(isNotNull(baseUrl)){
            sql +=" and a.bae_url like '%"+baseUrl+"%'" ;
        }
        if(isNotNull(description)){
            sql +=" and a.description like '%"+description+"%'" ;
        }
        if(isNotNull(method)){
            sql +=" and a.method like '%"+method+"%'" ;
        }
        if(isNotNull(appName)){
            sql +=" and b.name like '%"+appName+"%'" ;
        }else {
            if(appName==null) {
                sql +=" and b.name is null";}
            else {
                sql +="";}
        }

        if (equals(sortDirection,desc)){
            if (sortBy.isEmpty()) {
                sortBy = "a.id";
            }
            sql +=" order by "+sortBy+""+" desc "+" limit  "+(index-1)*limit+","+limit;
        }
        else {
            if (sortBy==null||sortBy.isEmpty()) {
                sortBy = "a.id";
            }
            sql +=" order by "+sortBy+" limit  "+(index-1)*limit+","+limit;
        }
        LOGGER.debug("sql[{}]",sql);
        ResultSet rs = db.executeQuery(sql);
        apiInfoEntities = db.buildObjects(ApiInfoEntity.class, rs);
        LOGGER.debug("List:"+apiInfoEntities.toString());



        String sql1 = "SELECT *,app_id as appId,bae_url as baseUrl,display_name as displayName,short_name as shortName,create_time as createTime,last_modify_time as lastModifyTime  FROM api a left join app_basics b on b.id = a.app_id where 1=1";
        if(isNotNull(displayName)){
            sql1 +=" and a.display_name like '%"+displayName+"%'" ;
        }
        if(isNotNull(baseUrl)){
            sql1 +=" and a.bae_url like '%"+baseUrl+"%'" ;
        }
        if(isNotNull(description)){
            sql1 +=" and a.description like '%"+description+"%'" ;
        }
        if(isNotNull(method)){
            sql1 +=" and a.method like '%"+method+"%'" ;
        }

        if(isNotNull(appName)){
            sql1 +=" and b.name like '%"+appName+"%'" ;
        }else {
            if(appName==null) {
                sql1 +=" and b.name is null";}
            else {
                sql1 +="";}
        }
        LOGGER.debug("SQL:"+sql1);
        int total ;
        ResultSet rs1 = db.executeQuery(sql1);
        List<ApiInfoEntity>  l= db.buildObjects(ApiInfoEntity.class,rs1);
        if(l==null){
            total=0;
        }else{
            total  = l.size();
        }
        LOGGER.debug("total:[{}]：",total);
        pageDto.setIndex(index);
        pageDto.setLimit(limit);
        pageDto.setTotal(total);
        pageDto.setData(apiInfoEntities);
        LOGGER.debug("pagedto:[{}]：",pageDto.asJson());
        return pageDto;

    }


    @Override
    public ApiInfoEntity updateApi(long apiId,String displayName,String description,String appId,String basurl ) throws DaoException {
        ApiInfoEntity a = new ApiInfoEntity();
        if(isNotNull(displayName)) {
            db.from(a).set(a.getDisplayName()).to(displayName).where(a.getId()).is(apiId).update();
        }
        if(isNotNull(description)) {
            db.from(a).set(a.getDescription()).to(description).where(a.getId()).is(apiId).update();
        }
        if(isNotNull(appId))
        {
            long appId1 = Long.parseLong(appId);
            db.from(a).set(a.getAppId()).to(appId1).where(a.getId()).is(apiId).update();
        }
        if(isNotNull(basurl))
        {
            db.from(a).set(a.getBaseUrl()).to(basurl).where(a.getId()).is(apiId).update();
        }

        return db.from(a).where(a.getId()).is(apiId).select().get(0);
    }


    @Override
    public ApiInfoVersionEntity createApiVersion(ApiInfoVersionEntity apiInfoVersionEntity) {
        long key = db.insertAndGetKey(apiInfoVersionEntity);
        ApiInfoVersionEntity a=new ApiInfoVersionEntity();
        return db.from(a).where(a.getId()).is(key).selectFirst();
    }

    @Override
    public ApiInfoVersionEntity queryApiVersionByIdAndName(String name, long id) throws DaoException {
        ApiInfoVersionEntity apiInfoVersionEntity = new ApiInfoVersionEntity();
        Long count = db.from(apiInfoVersionEntity).where(apiInfoVersionEntity.getApiId()).is(id).and(apiInfoVersionEntity.getName()).is(name).selectCount();
        if (count == 0) {
            return null;
        } else {
            return db.from(apiInfoVersionEntity).where(apiInfoVersionEntity.getApiId()).is(id).and(apiInfoVersionEntity.getName()).is(name).select().get(0);
        }
    }

    @Override
    public ApiInfoVersionEntity updateApiVersion(long id, String name, String status,String serviceId,String method,String finalpath) {
        ApiInfoVersionEntity a = new ApiInfoVersionEntity();
        db.from(a).set(a.getName()).to(name).where(a.getId()).is(id).update();
        if(isNotNull(serviceId)) {
            db.from(a).set(a.getServiceId()).to(serviceId).where(a.getId()).is(id).update();
        }
        if(equals(status,isTrue)) {
            boolean status1 =true;
            db.from(a).set(a.isStatus()).to(status1).where(a.getId()).is(id).update();
        }
        if(equals(status,isFalse)) {
            boolean status1 = false;
            db.from(a).set(a.isStatus()).to(status1).where(a.getId()).is(id).update();
        }
        db.from(a).set(a.getMethod()).to(method).where(a.getId()).is(id).update();

        db.from(a).set(a.getFinalPath()).to(finalpath).where(a.getId()).is(id).update();
        return db.from(a).where(a.getId()).is(id).select().get(0);
    }

    @Override
    public ApiInfoVersionEntity queryApiVersionById(long id) {
        ApiInfoVersionEntity apiInfoVersionEntity = new ApiInfoVersionEntity();
        Long count = db.from(apiInfoVersionEntity).where(apiInfoVersionEntity.getId()).is(id).selectCount();
        if (count == 0) {
            return null;
        } else {
            return db.from(apiInfoVersionEntity).where(apiInfoVersionEntity.getId()).is(id).select().get(0);
        }
    }

    @Override
    public PageDto<ApiInfoVersionEntity> queryApiVersionList(int index, int limit,long apiId, String sortDirection) throws DaoException {
        //定义变量
        PageDto<ApiInfoVersionEntity> pageDto = new PageDto<>();
        List<ApiInfoVersionEntity> data = null;
        ApiInfoVersionEntity apiInfoVersionEntity = new ApiInfoVersionEntity();
        int total = (int) db.from(apiInfoVersionEntity).selectCount();

        //判断有无数据
        if (total > 0) {
            if (equals(sortDirection,desc)) {
                /**
                 * 倒序操作
                 */
                data = db.from(apiInfoVersionEntity).where(apiInfoVersionEntity.getApiId()).is(apiId).orderByDesc(apiInfoVersionEntity.getId()).limit(limit).offset((index - 1) * limit).select();
            } else {
                /**
                 * 正序操作
                 */
                data = db.from(apiInfoVersionEntity).where(apiInfoVersionEntity.getApiId()).is(apiId).orderBy(apiInfoVersionEntity.getId()).limit(limit).offset((index - 1) * limit).select();
            }
        }
        pageDto.setIndex(index);
        pageDto.setLimit(limit);
        pageDto.setTotal(total);
        pageDto.setData(data);
        return pageDto;
    }


    @Override
    public void deleteApiVersion(long id) {
        ApiInfoVersionEntity apiInfoVersionEntity = new ApiInfoVersionEntity();
        db.from(apiInfoVersionEntity).where(apiInfoVersionEntity.getId()).is(id).delete();
    }

    @Override
    public ApiInfoVersionEntity activationApiVersion(long id, boolean status) {
        ApiInfoVersionEntity a = new ApiInfoVersionEntity();
        db.from(a).set(a.isStatus()).to(status).where(a.getId()).is(id).update();
        return db.from(a).where(a.getId()).is(id).select().get(0);
    }

    @Override
    public ApiInfoEntity insertApiInfoEntity(ApiInfoEntity apiInfoEntity) {
        ApiInfoEntity a = new ApiInfoEntity();
        db.from(a).select();
        Long key = db.insertAndGetKey(apiInfoEntity);
        return db.from(a).where(a.getId()).is(key).selectFirst();
    }

    @Override
    public ApiInfoDocumentationEntity insertApiInfoDocumentationEntity(ApiInfoDocumentationEntity apiInfoDocumentationEntity) {
        Long key= db.insertAndGetKey(apiInfoDocumentationEntity);
        ApiInfoDocumentationEntity a=new ApiInfoDocumentationEntity();
        return db.from(a).where(a.getId()).is(key).selectFirst();
    }

    @Override
    public ApiInfoMethodParamEntity insertApiInfoMethodParamEntity(ApiInfoMethodParamEntity apiInfoMethodParamEntity) {
        ApiInfoMethodParamEntity a = new ApiInfoMethodParamEntity();
        Long key = db.insertAndGetKey(apiInfoMethodParamEntity);
        return db.from(a).where(a.getId()).is(key).selectFirst();
    }

    @Override
    public ApiInfoTypePropEntity insertApiInfoTypePropEntity(ApiInfoTypePropEntity apiInfoTypePropEntity) {
        ApiInfoTypePropEntity a = new ApiInfoTypePropEntity();
        Long key = db.insertAndGetKey(apiInfoTypePropEntity);
        return db.from(a).where(a.getId()).is(key).selectFirst();
    }

    @Override
    public ApiInfoTypeEntity insertApiInfoTypeEntity(ApiInfoTypeEntity apiInfoTypeEntity) {
        Long key= db.insertAndGetKey(apiInfoTypeEntity);
        ApiInfoTypeEntity a = new ApiInfoTypeEntity();
        return db.from(a).where(a.getId()).is(key).selectFirst();
    }

    @Override
    public ApiInfoRequestBodyEntity insertApiInfoRequestBodyEntity(ApiInfoRequestBodyEntity apiInfoRequestBodyEntity) {
        db.insert(apiInfoRequestBodyEntity);
        ApiInfoRequestBodyEntity a = new ApiInfoRequestBodyEntity();
        return db.from(a).where(a.getApiVersionId()).is(apiInfoRequestBodyEntity.getApiVersionId()).selectFirst();
    }

    @Override
    public ApiInfoResponseEntity insertApiInfoResponseEntity(ApiInfoResponseEntity apiInfoResponseEntity) {
        Long key= db.insertAndGetKey(apiInfoResponseEntity);
        ApiInfoResponseEntity a = new ApiInfoResponseEntity();
        return db.from(a).where(a.getId()).is(key).selectFirst();
    }

    @Override
    public ApiInfoDocumentationEntity exportApiDocumentation(long id) {
        ApiInfoDocumentationEntity apiInfoVersionEntity = new ApiInfoDocumentationEntity();
        return db.from(apiInfoVersionEntity).where(apiInfoVersionEntity.getId()).is(id).select().get(0);

    }

    @Override
    public ApiInfoMediaTypeEntity insertApiInfoMediaTypeEntity(ApiInfoMediaTypeEntity mediaType) {
        Long key = db.insertAndGetKey(mediaType);
        ApiInfoMediaTypeEntity a = new ApiInfoMediaTypeEntity();
        Long count = db.from(a).where(a.getId()).is(key).selectCount();
        if (count == 0) {
            return null;
        } else {
            return db.from(a).where(a.getId()).is(key).selectFirst();
        }
    }

    @Override
    public List<ServiceEntity> queryServiceList() throws DaoException {
        ServiceEntity serviceEntity = new ServiceEntity();
        long count = db.from(serviceEntity).selectCount();
        if (count==0) {
            return null;
        } else {
            return db.from(serviceEntity).select();
        }

    }

    @Override
    public List<ApiInfoEntity> queryApis() throws DaoException {
        ApiInfoEntity apiInfoEntity = new ApiInfoEntity();
        long count = db.from(apiInfoEntity).selectCount();
        if (count==0) {
            return null;
        } else {
            return db.from(apiInfoEntity).select();
        }
    }

    @Override
    public ApiInfoEntity queryApiByBasurlAndDisplayname(String baseUrl,String displayname) throws DaoException {
        ApiInfoEntity apiInfoEntity = new ApiInfoEntity();
        Long count = db.from(apiInfoEntity).where(apiInfoEntity.getBaseUrl()).is(baseUrl).and(apiInfoEntity.getDisplayName()).is(displayname).selectCount();
        if (count==0) {
            return null;
        } else {
            return db.from(apiInfoEntity).where(apiInfoEntity.getBaseUrl()).is(baseUrl).and(apiInfoEntity.getDisplayName()).is(displayname).select().get(0);
        }
    }

    @Override
    public ApiInfoDocumentationEntity queryApiInfoDocumentById(long id) throws DaoException {
        ApiInfoDocumentationEntity doc = new ApiInfoDocumentationEntity();
        Long count = db.from(doc).where(doc.getId()).is(id).selectCount();
        if(count == 0) {
            return null;
        } else {
            return db.from(doc).where(doc.getId()).is(id).select().get(0);
        }
    }

    @Override
    public ApiInfoDocumentationEntity updateApiDocument(long id, String title, String content) {
        ApiInfoDocumentationEntity doc = new ApiInfoDocumentationEntity();
        if(isNotNull(title)){
            db.from(doc).set(doc.getTitle()).to(title).where(doc.getId()).is(id).update();
        }
        if(isNotNull(content)){
            db.from(doc).set(doc.getContent()).to(content).where(doc.getId()).is(id).update();
        }
        return db.from(doc).where(doc.getId()).is(id).select().get(0);
    }

    @Override
    public ApiInfoMediaTypeEntity queryapiInfoMediaTypeById(long id) throws DaoException {
        ApiInfoMediaTypeEntity media = new ApiInfoMediaTypeEntity();
        long count = db.from(media).where(media.getId()).is(id).selectCount();
        if(count == 0) {
            return null;
        } else {
            return db.from(media).where(media.getId()).is(id).select().get(0);
        }
    }

    @Override
    public ApiInfoMediaTypeEntity updateApiMediaById(long id, String name) throws DaoException {
        ApiInfoMediaTypeEntity a = new ApiInfoMediaTypeEntity();
        if(isNotNull(name)) {
            db.from(a).set(a.getName()).to(name).where(a.getId()).is(id).update();
        }
        return db.from(a).where(a.getId()).is(id).select().get(0);
    }

    @Override
    public ApiInfoMethodParamEntity updateApiMethodParam(long id, String name, String description, String required, String type, String example) {
       ApiInfoMethodParamEntity m = new ApiInfoMethodParamEntity();
       if(isNotNull(name)){
           db.from(m).set(m.getName()).to(name).where(m.getId()).is(id).update();
       }
        if(isNotNull(description)){
            db.from(m).set(m.getDescription()).to(description).where(m.getId()).is(id).update();
        }
        if(isNotNull(required)){
            if(equals(required,isTrue)){
                db.from(m).set(m.isRequired()).to(true).where(m.getId()).is(id).update();
            }
            if(equals(required,isFalse)){
                db.from(m).set(m.isRequired()).to(false).where(m.getId()).is(id).update();
            }
        }
        if(isNotNull(type)){
            db.from(m).set(m.getType()).to(type).where(m.getId()).is(id).update();
        }
        if(isNotNull(example)){
            db.from(m).set(m.getExample()).to(example).where(m.getId()).is(id).update();
        }
        return  db.from(m).where(m.getId()).is(id).select().get(0);
    }

    @Override
    public ApiInfoRequestBodyEntity updateApiRequestBody(long apiVersionId, String contentType) {
        ApiInfoRequestBodyEntity r = new ApiInfoRequestBodyEntity();
        if(isNotNull(contentType)){
            db.from(r).set(r.getContentType()).to(contentType).where(r.getApiVersionId()).is(apiVersionId);
        }
        return  db.from(r).where(r.getApiVersionId()).is(apiVersionId).select().get(0);
    }

    @Override
    public ApiInfoTypeEntity updateApiTypeByRequestBody(long apiRequestBodyId, String name) {
        ApiInfoTypeEntity apiInfoTypeEntity = new ApiInfoTypeEntity();
        if(isNotNull(name)){
            db.from(apiInfoTypeEntity).set(apiInfoTypeEntity.getName()).to(name).where(apiInfoTypeEntity.getApiRequestBodyId()).is(apiRequestBodyId);
        }
        return db.from(apiInfoTypeEntity).where(apiInfoTypeEntity.getApiRequestBodyId()).is(apiRequestBodyId).select().get(0);
    }

    @Override
    public ApiInfoTypePropEntity updateApiTpyeProp(long id, String type, String required, String example) {
        ApiInfoTypePropEntity t = new ApiInfoTypePropEntity();
        if(isNotNull(type)){
            db.from(t).set(t.getType()).to(type).where(t.getId()).is(id);
        }
        if(isNotNull(required)){
            if(equals(required,isTrue)) {
                db.from(t).set(t.isRequired()).to(true).where(t.getId()).is(id);
            }
            if(equals(required,isFalse)) {
                db.from(t).set(t.isRequired()).to(false).where(t.getId()).is(id);
            }
        }
        if(isNotNull(example)){
            db.from(t).set(t.getExample()).to(example).where(t.getId()).is(id);
        }
        return db.from(t).where(t.getId()).is(id).select().get(0);
    }

    @Override
    public ApiInfoResponseEntity updateApiResponse(long id, String contentType, String code,String description) throws DaoException {
        ApiInfoResponseEntity r = new ApiInfoResponseEntity();
        if(isNotNull(contentType)){
            db.from(r).set(r.getContentType()).to(contentType).where(r.getId()).is(id).update();
        }
        if(isNotNull(code)){
            int code1 = Integer.parseInt(code);
            db.from(r).set(r.getCode()).to(code1).where(r.getId()).is(id).update();
        }
        if(isNotNull(description)){
            db.from(r).set(r.getDescription()).to(description).where(r.getId()).is(id).update();
        }
        return db.from(r).where(r.getId()).is(id).select().get(0);
    }

    @Override
    public ApiInfoTypeEntity updateApiTypeByResponseId(long apiResponseId, String name) throws DaoException {
        ApiInfoTypeEntity apiInfoTypeEntity = new ApiInfoTypeEntity();
        if(isNotNull(name)){
            db.from(apiInfoTypeEntity).set(apiInfoTypeEntity.getName()).to(name).where(apiInfoTypeEntity.getApiResponseId()).is(apiResponseId).update();
        }
        return db.from(apiInfoTypeEntity).where(apiInfoTypeEntity.getApiResponseId()).is(apiResponseId).select().get(0);
    }

    @Override
    public ApiInfoVersionEntity queryApiVersionByApiIdAndName(long apiId, String apiVersionName) {
        ApiInfoVersionEntity apiInfoVersionEntity = new ApiInfoVersionEntity();
        Long count;
        count = db.from(apiInfoVersionEntity).where(apiInfoVersionEntity.getApiId()).is(apiId).and(apiInfoVersionEntity.getName())
                .is(apiVersionName).selectCount();
        LOGGER.debug("数量"+count);
        if(count ==0){
            return null;
        }else {
            return  db.from(apiInfoVersionEntity).where(apiInfoVersionEntity.getApiId()).is(apiId).and(apiInfoVersionEntity.getName())
                    .is(apiVersionName).selectFirst();
        }

    }
    @Override
    public ApiInfoVersionEntity queryApiVersionByFinalPath(String finalPath){
        ApiInfoVersionEntity v = new ApiInfoVersionEntity();
        Long count;
        count= db.from(v).where(v.getFinalPath()).is(finalPath).selectCount();
        if(count==0)
        {return null;}
        else {
            return db.from(v).where(v.getFinalPath()).is(finalPath).select().get(0);
        }
    }

    @Override
    public List<ApiInfoEntity> queryApiByAppIdIsNull() throws DaoException {
        ApiInfoEntity a = new ApiInfoEntity();
        long count =  db.from(a).where(a.getAppId()).isNull().selectCount();
        if(count==0)
        {  return null;}
        else {
            return db.from(a).where(a.getAppId()).isNull().select();
        }
    }

    @Override
    public List<ApiInfoEntity> queryApiByAppId(long appId) throws DaoException {
        ApiInfoEntity a = new ApiInfoEntity();
        long count = db.from(a).where(a.getAppId()).is(appId).selectCount();
        if(count==0)
        {  return null;}
        else
            { return db.from(a).where(a.getAppId()).is(appId).select();}
    }

    @Override
    public PageDto<ApiVersionDto> queryApiVersionByCondition(int index, int limit, String sortBy, String sortDirection, String displayName, String appName) throws DaoException {
        List<ApiVersionDto> apiInfoEntities;
        PageDto<ApiVersionDto> pageDto = new PageDto<>();
        String sql = "SELECT *,app_id as appId,bae_url as baseUrl,display_name as displayName," +
                "short_name as shortName,create_time as createTime,last_modify_time as lastModifyTime," +
                "api_id as apiId,service_id as serviceId,final_path as finalPath " +
                "FROM (api a inner join api_version v on v.api_id = a.id ) left join app_basics b on b.id = a.app_id where ";
        sql += "status = true";
        if(isNotNull(displayName)){
            sql +=" and a.display_name like '%"+displayName+"%'" ;
        }
        if(appName!=null&&!appName.isEmpty()){
            sql +=" and b.name like '%"+appName+"%'" ;
        }else {
            if(appName==null) {
                sql +=" and b.name is null";}
            else {
                sql +="";}
        }
        if (equals(sortDirection,desc)){
            if (sortBy.isEmpty()) {
                sortBy = "a.id";
            }
            sql +=" order by "+sortBy+""+" desc "+" limit  "+(index-1)*limit+","+limit;
        }
        else {
            if (sortBy==null||sortBy.isEmpty()) {
                sortBy = "a.id";
            }
            sql +=" order by "+sortBy+" limit  "+(index-1)*limit+","+limit;
        }
        LOGGER.debug("sql[{}]",sql);
        ResultSet rs = db.executeQuery(sql);
        apiInfoEntities = db.buildObjects(ApiVersionDto.class, rs);
        LOGGER.debug("List:"+apiInfoEntities.toString());


        String sql1 = "SELECT *,app_id as appId,bae_url as baseUrl,display_name as displayName," +
                "short_name as shortName,create_time as createTime,last_modify_time as lastModifyTime," +
                "api_id as apiId,service_id as serviceId,final_path as finalPath " +
                "FROM (api a inner join api_version v on v.api_id = a.id ) left join app_basics b on b.id = a.app_id where ";
        sql1 += "status = true";
        if(isNotNull(displayName)){
            sql1 +=" and a.display_name like '%"+displayName+"%'" ;
        }
        if(appName!=null&&!appName.isEmpty()){
            sql1 +=" and b.name like '%"+appName+"%'" ;
        }else {
            if(appName==null) {
                sql1 +=" and b.name is null";}
            else {
                sql1 +="";}
        }
        LOGGER.debug("SQL:"+sql1);
        int total ;
        ResultSet rs1 = db.executeQuery(sql1);
        List<ApiVersionDto>  l= db.buildObjects(ApiVersionDto.class,rs1);
        if(l==null){
            total=0;
        }else{
            total  = l.size();
        }
        LOGGER.debug("total:[{}]：",total);
        pageDto.setIndex(index);
        pageDto.setLimit(limit);
        pageDto.setTotal(total);
        pageDto.setData(apiInfoEntities);
        LOGGER.debug("pagedto:[{}]：",pageDto.asJson());
        return pageDto;
    }


}
