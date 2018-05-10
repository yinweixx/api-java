package com.anyun.cloud.service.impl;

import com.anyun.cloud.common.ZipUtils;
import com.anyun.cloud.common.context.Context;
import com.anyun.cloud.common.json.JsonUtil;
import com.anyun.cloud.common.string.StringUtils;
import com.anyun.cloud.common.sys.Response;
import com.anyun.cloud.common.FileUtil;
import com.anyun.cloud.dao.ApiManagementDao;
import com.anyun.cloud.dao.AppBasicsDao;
import com.anyun.cloud.model.ErrorCode;
import com.anyun.cloud.model.dto.ApiInfoDto;
import com.anyun.cloud.model.dto.ApiVersionDto;
import com.anyun.cloud.model.dto.PageDto;
import com.anyun.cloud.model.entity.*;
import com.anyun.cloud.model.param.*;
import com.anyun.cloud.model.param.api.CreateParam;
import com.anyun.cloud.model.param.api.MediaType;
import com.anyun.cloud.model.param.api.Version;
import com.anyun.cloud.service.ApiManagementService;
import com.anyun.cloud.service.RamlApiRamlParser;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * Created by jt on 18-1-17.
 */
public class ApiManagementServiceImpl implements ApiManagementService {
    private final static Logger LOGGER = LoggerFactory.getLogger(ApiManagementService.class);
    private ApiManagementDao apiManagementDao;
    private Context context;
    private Response response;
    private RamlApiRamlParser ramlApiRamlParser;
    private AppBasicsDao appBasicsDao;
    private String errorDescription;
    private String get = "GET";
    private String post = "POST";
    private String delete = "DELETE";
    private String put = "PUT";

    @Inject
    public ApiManagementServiceImpl(Context context) {
        this.context = context;
        this.apiManagementDao = context.getBeanByClass(ApiManagementDao.class);
        this.ramlApiRamlParser = context.getBeanByClass(RamlApiRamlParser.class);
        this.appBasicsDao = context.getBeanByClass(AppBasicsDao.class);

    }

    /**
     *参数验证失败
     **/
    private Map<Boolean, Response> validateFail(String s){
        Map<Boolean, Response> map;
        map = new HashMap<>();
        response = new Response<String>();
        response.setCode(ErrorCode.PARAMETER_ERROR.code());
        response.setContent(ErrorCode.PARAMETER_ERROR.name() + "  " + s);
        LOGGER.debug(response.asJson());
        map.put(false, response);
        return map;
    }

    private Response errorMessagePrint(String s){
        response = new Response<String>();
        response.setCode(ErrorCode.PARAMETER_ERROR.code());
        response.setContent(ErrorCode.PARAMETER_ERROR.name() + "  " + s);
        LOGGER.debug(response.asJson());
        return response;
    }
    /**
     *判断是否为空
     **/
    private boolean isNull(String a){
        return a==null||a.isEmpty();
    }
    private boolean isNotNull(String a){return a!=null&&!a.isEmpty();}

    /**
     * 参数校验
     */
    /**
     * 创建api 参数校验
     *
     * @return Map
     * @parm CreateParam param
     *
     */
    private Map<Boolean, Response> parameterVerification(CreateParam param) {
        Map<Boolean, Response> map;
        //CreateParam param
        if (param == null) {
            errorDescription =  "CreateParam param is empty";
            return validateFail(errorDescription);
        }

        //Long appId
        if (isNull(String.valueOf(param.getAppId())) || param.getAppId() == 0) {
            errorDescription = "appId is empty";
            return validateFail(errorDescription);
        }
        Long appId = param.getAppId();
        AppBasicsEntity appBasicsEntity = appBasicsDao.selectAppBasicsEntityById(appId);
        if (appBasicsEntity == null) {
            errorDescription = "appId does not exists";
            return validateFail(errorDescription);
        }



        //List<Version> versionList
        List<Version> versionList = param.getVersionList();
        //版本为空直接终止,并返回错误信息
        if (versionList == null || versionList.isEmpty() || versionList.size() < 1) {
            errorDescription = "List<Version> versionList is empty";
            return validateFail(errorDescription);
        }
        for (Version version : versionList) {

            return versionParameterVerification(version);
        }
        map = new HashMap<>();
        map.put(true, null);
        return map;
    }

    /**
     * 创建api版本 参数校验
     *
     * @return Map
     * @parm CreateParam param
     *
     */
    private Map<Boolean, Response> versionParameterVerification(Version version){
        Map<Boolean, Response> map;
        if (version == null) {
            errorDescription = "Version  is empty";
            return validateFail(errorDescription);
        }else {
        //version name
        if (isNull(version.getName())) {
            errorDescription = "Version name is empty";
            return validateFail(errorDescription);
        }
        String path = version.getPath();
        if (isNull(path)) {
            errorDescription = "path is empty";
            return validateFail(errorDescription);
        }

        //method
        String method = version.getMethod();
        //请求方式为空
        if (isNull(method)) {
            errorDescription = "method is empty";
            return validateFail(errorDescription);
        }
        if (!method.trim().toUpperCase().matches("GET|PUT|DELETE|POST")) {
            errorDescription = "The method  is  not in  'GET|PUT|DELETE|POST' ";
            return validateFail(errorDescription);
        }
        //List<MediaType> mediaTypeList
        List<MediaType> mediaTypeList = version.getMediaTypeList();
        if (mediaTypeList == null || mediaTypeList.size() < 1) {
            errorDescription = "mediaTypeList is empty";
            return validateFail(errorDescription);
        }
        for (MediaType mediaType : mediaTypeList) {
            if (mediaType == null) {
                errorDescription = "MediaType is empty";
                return validateFail(errorDescription);
            }
            //mediaType name
            if (isNull(mediaType.getName())) {
                errorDescription = "MediaType name is empty";
                return validateFail(errorDescription);
            }
        }}
        map = new HashMap<>();
        map.put(true, null);
        return map;
    }


    /**
     * post类ａｐｉ错误信息捕捉
     *
     * @return Response
     * @parm CreateParam param
     *
     */
    private Response putResourseError(Exception e){
        response = new Response<String>();
        response.setCode(ErrorCode.POST_RESOURCE_ERROR.code());
        response.setContent(ErrorCode.POST_RESOURCE_ERROR.name() + e.getMessage());
        return response;
    }

    /**
     * post类ａｐｉ错误信息捕捉
     *
     * @return Response
     * @parm CreateParam param
     *
     */
    private Response postResourseError(Exception e){
        response = new Response<String>();
        response.setCode(ErrorCode.PUT_RESOURCE_ERROR.code());
        response.setContent(ErrorCode.PUT_RESOURCE_ERROR.name() + e.getMessage());
        return response;
    }

    /**
     * post类ａｐｉ错误信息捕捉
     *
     * @return Response
     * @parm CreateParam param
     *
     */
    private Response getResourseError(Exception e){
        response = new Response<String>();
        response.setCode(ErrorCode.QUERY_RESOURCE_ERROR.code());
        response.setContent(ErrorCode.QUERY_RESOURCE_ERROR.name() + e.getMessage());
        return response;
    }

    /**
     * post类ａｐｉ错误信息捕捉
     *
     * @return Response
     * @parm CreateParam param
     *
     */
    private Response deleteResourseError(Exception e){
        response = new Response<String>();
        response.setCode(ErrorCode.DELETE_RESOURCE_ERROR.code());
        response.setContent(ErrorCode.DELETE_RESOURCE_ERROR.name() + e.getMessage());
        return response;
    }



    //创建区域

    /**
     * 创建api版本（非接口）
     * @return ApiInfoVersionEntity
     * @parm Version v
     *
     */
    private ApiInfoVersionEntity createApiVersion(Version v,String apiId,String baseUrl){


        ApiInfoVersionEntity version;
        version = new ApiInfoVersionEntity();
        if(isNotNull(apiId)){
        version.setApiId(Long.parseLong(apiId));
        }
        else {
            version.setApiId(v.getApiId());
        }
        if(isNull(baseUrl)){
            baseUrl = apiManagementDao.queryApiById(version.getApiId()).getBaseUrl();
        }
        LOGGER.debug(""+baseUrl);
            StringBuilder finalPath = new StringBuilder("");
        finalPath.append(baseUrl);
        finalPath.append("/"+v.getName());
        finalPath.append(v.getPath());
        LOGGER.debug(""+finalPath);
        version.setName(v.getName());
        version.setStatus(true);
        version.setMethod(v.getMethod());
        version.setServiceId(v.getServiceId());
        version.setFinalPath(finalPath.toString());
        version.setPath(v.getPath());
        version.setStatus(v.isStatus());
        version = apiManagementDao.createApiVersion(version);
        LOGGER.debug("ApiInfoVersionEntity version:[{}]", version.asJson());
        Long apiVersionId = version.getId();


        //MediaType
        if (v.getMediaTypeList() != null && v.getMediaTypeList().size() > 0) {
            List<ApiInfoMediaTypeEntity> mediaTypeList = new ArrayList<>();
            v.getMediaTypeList().forEach(media -> {
                ApiInfoMediaTypeEntity mediaType;
                mediaType = new ApiInfoMediaTypeEntity();
                mediaType.setApiVersionId(apiVersionId);
                mediaType.setName(media.getName());
                mediaType = apiManagementDao.insertApiInfoMediaTypeEntity(mediaType);
                LOGGER.debug("ApiInfoMediaTypeEntity mediaType:[{}]", mediaType.asJson());
                mediaTypeList.add(mediaType);
            });
            version.setMediaTypeList(mediaTypeList);
        }



        //documentation
        if (v.getDocumentationList() != null && v.getDocumentationList().size() > 0) {
            List<ApiInfoDocumentationEntity> docList = new ArrayList<>();
            v.getDocumentationList().forEach(d -> {
                if (d != null) {
                    ApiInfoDocumentationEntity doc;
                    doc = new ApiInfoDocumentationEntity();
                    doc.setApiVersionId(apiVersionId);
                    doc.setTitle(d.getTitle());
                    doc.setContent(d.getContent());
                    doc = apiManagementDao.insertApiInfoDocumentationEntity(doc);
                    LOGGER.debug("ApiInfoDocumentationEntity  doc:[{}]", doc.asJson());
                    docList.add(doc);
                }
            });
            version.setDocList(docList);
        }

        //response
        if (v.getResponseList() != null && v.getResponseList().size() > 0) {
            List<ApiInfoResponseEntity> responseList = new ArrayList<>();
            v.getResponseList().forEach(r -> {
                if (r != null) {
                    ApiInfoResponseEntity resp;
                    resp = new ApiInfoResponseEntity();
                    resp.setApiVersionId(apiVersionId);
                    resp.setContentType(r.getContentType());
                    resp.setCode(r.getCode());
                    resp.setDescription(r.getDescription());
                    resp = apiManagementDao.insertApiInfoResponseEntity(resp);
                    LOGGER.debug("ApiInfoResponseEntity newResp:[{}]", resp.asJson());
                    responseList.add(resp);


                }
            });
            version.setResponseList(responseList);
        }


        //headerList
        if (v.getMethod().equals(get) || v.getMethod().equals(delete)) {
            if (v.getHeaderList() != null && v.getHeaderList().size() > 0) {
                List<ApiInfoMethodParamEntity> apList = new ArrayList<>();
                v.getHeaderList().forEach(z -> {
                    if (z != null) {
                        ApiInfoMethodParamEntity ap;
                        ap = new ApiInfoMethodParamEntity();
                        ap.setApiVersionId(apiVersionId);
                        ap.setName(z.getName());
                        ap.setDescription(z.getDescription());
                        ap.setType(z.getType());
                        ap.setRequired(z.isRequired());
                        ap.setExample(z.getExample());
                        LOGGER.debug("ApiInfoMethodParamEntity  ap:[{}]", ap.asJson());
                        ap = apiManagementDao.insertApiInfoMethodParamEntity(ap);
                        apList.add(ap);
                    }
                });
                version.setParametersList(apList);
            }

            //requestBody
        } else {
            if (v.getRequestBody() != null) {
                ApiInfoRequestBodyEntity rb;
                rb = new ApiInfoRequestBodyEntity();
                rb.setApiVersionId(apiVersionId);
                rb.setContentType(v.getRequestBody().getContentType());
                rb.setDescription(v.getRequestBody().getDescription());
                rb = apiManagementDao.insertApiInfoRequestBodyEntity(rb);
                LOGGER.debug("ApiInfoRequestBodyEntity  rb:[{}]", rb.asJson());
                version.setRequestBody(rb);


            }
        }
        return version;
    }

    @Override
    public Response createApi(String body) {
        final int[] code = {0};
        StringBuffer responseDescription = new StringBuffer("");

        LOGGER.debug("body:[{}]", body);
        try {
            //----------------------------------------------------------------------------------------------------------
            LOGGER.debug("参数验证开始。。。。。");
            CreateParam c = JsonUtil.fromJson(CreateParam.class, body);
            Map<Boolean, Response> map = parameterVerification(c);
            map.forEach((key, value) -> {
                LOGGER.debug("key:[{}]----value:[{}]", key, value);
            });
            if (map.containsKey(false)) {
                LOGGER.debug("创建 api 参数验证失败！");
                return map.get(false);
            } else {
                LOGGER.debug("创建 api 参数验证通过！");
            }
            LOGGER.debug("参数验证结束。。。。。");
            //----------------------------------------------------------------------------------------------------------

            assert c != null;
            AppBasicsEntity appBasicsEntity = appBasicsDao.selectAppBasicsEntityById(c.getAppId());
            ApiInfoEntity a = new ApiInfoEntity();
            a.setAppId(c.getAppId());
            a.setBaseUrl("/"+appBasicsEntity.getShortName());
            a.setDisplayName(c.getDisplayName());
            a.setDescription(c.getDescription());
            ApiInfoEntity apiInfoEntity = apiManagementDao.queryApiByBasurlAndDisplayname(a.getBaseUrl(), a.getDisplayName());
            if (apiInfoEntity != null) {
                response = new Response<String>();
                response.setCode(ErrorCode.PARAMETER_ERROR.code());
                response.setContent("{" + ErrorCode.PARAMETER_ERROR.name() + "该ＡＰＩ已存在：" + a.getDisplayName() + "}");
                return response;
            }
            a = apiManagementDao.insertApiInfoEntity(a);
            LOGGER.debug("ApiInfoEntity a:[{}]", a.asJson());
            String apiId = String.valueOf(a.getId()); //api 基本信息 Id
            String baseUrl = a.getBaseUrl();

            //version
            List<ApiInfoVersionEntity> versionList = new ArrayList<>();
            if (c.getVersionList() != null && c.getVersionList().size() > 0)
            {
                response = new Response<List<ApiInfoVersionEntity>>();
                c.getVersionList().forEach(v -> {
                StringBuilder finalPath = new StringBuilder("");
                finalPath.append(baseUrl);
                finalPath.append("/"+v.getName());
                finalPath.append(v.getPath());
                ApiInfoVersionEntity apiInfoVersionEntity = apiManagementDao.queryApiVersionByFinalPath(finalPath.toString());
                if (apiInfoVersionEntity != null) {
                    code[0] += ErrorCode.QUERY_RESOURCE_ERROR.code();
                    responseDescription.append("{" + ErrorCode.PARAMETER_ERROR.name() + "该ＡＰＩ版本已存在"+finalPath+"}");
                }else {
                ApiInfoVersionEntity version;
                version = createApiVersion(v,apiId,baseUrl);
                versionList.add(version);}
            });}
            a.setVersionList(versionList);

            if(code[0]>0){
                response = new Response<List<ApiInfoEntity>>();
                response.setCode(code[0]);
                response.setContent(responseDescription);
                LOGGER.debug("response:[{}]", response.asJson());
                return response;
            }else {
            response = new Response<List<ApiInfoEntity>>();
            response.setCode(ErrorCode.NO_ERROR.code());
            response.setContent(a);
            LOGGER.debug("response:[{}]", response.asJson());
            return response;}
        } catch (Exception e) {
            return postResourseError(e);
        }
    }

    @Override
    public Response upload(HttpServletRequest request) {
        final int[] code = new int[]{0};
        final String[] responseDescription = new String[]{""};
        int contentSize = request.getContentLength();//文件流
        LOGGER.debug("Upload content size:[{}]", contentSize);
        if (contentSize > 0) {
            LOGGER.debug("--------------------------------------------------");
            //获取用户home 路径 例如 /home/anyun
            String userHomeDir = System.getProperty("user.home");
            LOGGER.debug("userHomeDir:[{}]", System.getProperty("user.home"));
            //定义上传文件目录
            String uploadDir = userHomeDir + "/upload/api/yamlzip".trim();
            LOGGER.debug("uploadDir:[{}]", uploadDir);
            File fileDir = new File(uploadDir);
            if (fileDir.exists()) {
                //存在
                LOGGER.debug("FileDir already exist");
            } else {
                //不存在
                LOGGER.debug("FileDir does not exists");
                fileDir.mkdirs(); //级联创建
                if (fileDir.exists()) {
                    LOGGER.debug("FileDir create success!");
                } else {
                    response = new Response<String>();
                    response.setCode(ErrorCode.POST_RESOURCE_ERROR.code());
                    response.setContent(ErrorCode.POST_RESOURCE_ERROR.name() + " " + "FileDir create fail!");
                    LOGGER.debug("response:[{}]", response.asJson());
                    return response;
                }
            }
            LOGGER.debug("AbsolutePath:[{}]", fileDir.getAbsolutePath());
            LOGGER.debug("Path:[{}]", fileDir.getPath());
            LOGGER.debug("isDirectory:[{}]", fileDir.isDirectory());
            String expression = "yyyyMMdd-HHmmss";
            String filePrefix = "/" + StringUtils.formatDate(new Date(), expression) + "-";//文件前缀
            String randomFileName = StringUtils.uuidGen().trim().toUpperCase();//获取生成文件名
            LOGGER.debug("randomFileName:[{}]", randomFileName);
            String fileName = uploadDir + filePrefix + randomFileName;//生成文件全路径（包含文件名称和路径）
            LOGGER.debug("Upload file [{}]", fileName);
            try {
                FileUtil.write(fileName, request.getInputStream(), false);//将文件流写入指定文件
            } catch (IOException e) {
                e.printStackTrace();
                return new Response<String>() {
                    {
                        setCode(ErrorCode.POST_RESOURCE_ERROR.code());
                        setContent(ErrorCode.POST_RESOURCE_ERROR.name() + e.getMessage() + " " + "FileUtil.write()  fail！");
                    }
                };
            }
            String extractDir = uploadDir + "/../extract/" + filePrefix + randomFileName; //获取文件解压目录
            LOGGER.debug("Unzip directory: {}", extractDir);
            FileUtil.mkdir(extractDir, false);//创建文件解压目录
            try {
                ZipUtils.extractZipData(fileName, new File(extractDir));//解压zip 包
            } catch (Exception e) {
                e.printStackTrace();
                if (!(e instanceof FileNotFoundException)) {
                    try {//删除错误格式的文件和目录
                        FileUtil.rm(fileName, true);
                        FileUtil.rm(extractDir, true);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
                return new Response<String>() {
                    {
                        setCode(ErrorCode.POST_RESOURCE_ERROR.code());
                        setContent(ErrorCode.POST_RESOURCE_ERROR.name() + e.getMessage() + " " + "ZipUtils.extractZipData fail ！");
                    }
                };
            }

            try {
                File[] files = new File(extractDir).listFiles(new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String name) {
                        if (name.toLowerCase().trim().endsWith(".raml"))
                        {    return true;}
                       else{ return false;}
                    }
                });
                if (files == null || files.length == 0) {
                    LOGGER.error("Not find api define file");
                    return new Response<String>() {
                        {
                            setCode(ErrorCode.POST_RESOURCE_ERROR.code());
                            setContent(ErrorCode.POST_RESOURCE_ERROR.name() + " " + "ZNot find api define file ！");
                        }
                    };
                }

                List<ApiInfoEntity> list = new ArrayList<>();
                for (File file : new File(extractDir).listFiles()) {
                    com.anyun.cloud.common.etcd.spi.entity.api.ApiEntity oldApi = ramlApiRamlParser.withEncoding(RamlApiRamlParser.ENCODING).withRamlFile(file).buildApi();
                    LOGGER.debug("oldApi:"+oldApi.toString());
                    oldApi.getResources().forEach(resource -> {
                        {
                            /**
                             * api 信息
                             */
                            LOGGER.debug("api 信息--------------------");
                            ApiInfoEntity api;
                            ApiInfoEntity apiInfoEntity;
                            api = new ApiInfoEntity();
                            String basuri[];
                            basuri= oldApi.getBaseUrl().split("/");
                            api.setBaseUrl("/" + basuri[0]);
                            api.setDisplayName(resource.getName());
                            api.setDescription(resource.getDesc());
                            apiInfoEntity = apiManagementDao.queryApiByBasurlAndDisplayname(api.getBaseUrl(), api.getDisplayName());
                            if (apiInfoEntity != null) {
                                code[0] += ErrorCode.PARAMETER_ERROR.code();
                                responseDescription[0] += ("{" + ErrorCode.PARAMETER_ERROR.name() + "该ＡＰＩ已存在：" + api.getDisplayName() + "}");
                                return;
                            }
                            api = apiManagementDao.insertApiInfoEntity(api);
                            LOGGER.debug("ApiInfoEntity api:[{}]", api.asJson());

                            //API ID
                            Long apiId = api.getId();
                            // apiVersion id
                            Long apiVersionId;


                            /**
                             * api版本
                             */
                            LOGGER.debug("api版本---------------------");
                            String a = "";
                            if (oldApi.getVersion() != null && !oldApi.getVersion().equals(a)) {
                                List<ApiInfoVersionEntity> versionList = new ArrayList<>();
                                ApiInfoVersionEntity apiVersion;
                                apiVersion = new ApiInfoVersionEntity();
                                apiVersion.setApiId(apiId);
                                apiVersion.setName(oldApi.getVersion());
                                apiVersion.setStatus(true);
                                apiVersion.setPath(resource.getPath());
                                apiVersion.setMethod(resource.getMethod().trim().toUpperCase());
                                String finalPath = "" + api.getBaseUrl() +"/"+apiVersion.getName()+ apiVersion.getPath();
                                apiVersion.setFinalPath(finalPath);
                                apiVersion = apiManagementDao.createApiVersion(apiVersion);
                                LOGGER.debug("ApiInfoVersionEntity apiVersion:[{}]", apiVersion.asJson());
                                versionList.add(apiVersion);
                                apiVersionId = apiVersion.getId();


                                /**
                                 *  api 支持的媒体类型
                                 */
                                if (oldApi.getMimeTypeList() != null && oldApi.getMimeTypeList().size() > 0) {
                                    List<ApiInfoMediaTypeEntity> mList = new ArrayList<>();
                                    oldApi.getMimeTypeList().forEach(m -> {
                                        ApiInfoMediaTypeEntity mediaType;
                                        mediaType = new ApiInfoMediaTypeEntity();
                                        mediaType.setApiVersionId(apiVersionId);
                                        mediaType.setName(m.value());
                                        mediaType = apiManagementDao.insertApiInfoMediaTypeEntity(mediaType);
                                        LOGGER.debug("ApiInfoMediaTypeEntity  mediaType:[{}]", mediaType.asJson());
                                        mList.add(mediaType);
                                    });
                                    apiVersion.setMediaTypeList(mList);
                                }

                                /**
                                 * api 文档
                                 */
                                LOGGER.debug("api文档---------------------");
                                if (oldApi.getDocuments() != null && oldApi.getDocuments().size() > 0) {
                                    List<ApiInfoDocumentationEntity> docList = new ArrayList<>();
                                    oldApi.getDocuments().forEach(f -> {
                                        ApiInfoDocumentationEntity apiDocument;
                                        apiDocument = new ApiInfoDocumentationEntity();
                                        apiDocument.setApiVersionId(apiVersionId);
                                        apiDocument.setTitle(f.getTitle());
                                        apiDocument.setContent(f.getContent());
                                        apiDocument = apiManagementDao.insertApiInfoDocumentationEntity(apiDocument);
                                        LOGGER.debug("ApiInfoDocumentationEntity apiDocument:[{}]", apiDocument.asJson());
                                        docList.add(apiDocument);
                                    });
                                    apiVersion.setDocList(docList);
                                }


                                /**
                                 * 请求参数 （GET/DELETE）
                                 */
                                LOGGER.debug("请求参数（GET/DELETE）------------------");
                                if (resource.getMethod().trim().toUpperCase().equals(get) || resource.getMethod().trim().toUpperCase().equals(delete)) {
                                    if (resource.getParameters() != null && resource.getParameters().size() > 0) {
                                        List<ApiInfoMethodParamEntity> paramList = new ArrayList<>();
                                        resource.getParameters().forEach(x -> {
                                            ApiInfoMethodParamEntity apiParam;
                                            apiParam = new ApiInfoMethodParamEntity();
                                            apiParam.setApiVersionId(apiVersionId);
                                            apiParam.setName(x.getName());
                                            apiParam.setDescription(x.getDescription());
                                            apiParam.setType(x.getType());
                                            apiParam.setRequired(x.isRequired());
                                            apiParam.setExample(x.getExample());
                                            apiParam = apiManagementDao.insertApiInfoMethodParamEntity(apiParam);
                                            LOGGER.debug("ApiInfoMethodParamEntity apiParam:[{}]", apiParam.asJson());
                                            paramList.add(apiParam);
                                        });
                                        apiVersion.setParametersList(paramList);
                                    }


                                    /**
                                     * 请求参数 （POST/PUT）
                                     */
                                    LOGGER.debug("请求参数 （POST/PUT）-----------------------");
                                } else {
                                    if (resource.getRequestBody() != null) {
                                        ApiInfoRequestBodyEntity apiRequestBody;
                                        apiRequestBody = new ApiInfoRequestBodyEntity();
                                        apiRequestBody.setApiVersionId(apiVersionId);
                                        apiRequestBody.setContentType(resource.getRequestBody().getContentType());
                                        apiRequestBody.setDescription(resource.getRequestBody().getApiTypeEntity().asJson());
//                                        apiRequestBody = apiManagementDao.insertApiInfoRequestBodyEntity(apiRequestBody);
//                                        LOGGER.debug("ApiInfoRequestBodyEntity apiRequestBody:[{}]", apiRequestBody.asJson());
//
//                                        ApiInfoTypeEntity apiType;
//                                        apiType = new ApiInfoTypeEntity();
//                                        apiType.setApiRequestBodyId(apiRequestBody.getApiVersionId());
//                                        apiType.setName(resource.getRequestBody().getApiTypeEntity().getName());
//                                        apiType = apiManagementDao.insertApiInfoTypeEntity(apiType);
//                                        Long apiTypeId = apiType.getId();
//                                        LOGGER.debug("ApiInfoTypeEntity apiType:[{}]", apiType.asJson());
//
//                                        if (resource.getRequestBody().getApiTypeEntity().getPropEntities() != null) {
//                                            List<ApiInfoTypePropEntity> apiTypePropList = new LinkedList<>();
//                                            resource.getRequestBody().getApiTypeEntity().getPropEntities().forEach(x -> {
//                                                ApiInfoTypePropEntity apiTypeProp;
//                                                apiTypeProp = new ApiInfoTypePropEntity();
//                                                apiTypeProp.setApiTypeId(apiTypeId);
//                                                apiTypeProp.setType(x.getType());
//                                                apiTypeProp.setRequired(x.isRequired());
//                                                apiTypeProp.setExample(x.getExample());
//                                                apiTypeProp = apiManagementDao.insertApiInfoTypePropEntity(apiTypeProp);
//                                                LOGGER.debug("ApiInfoTypePropEntity apiTypeProp.:[{}]", apiTypeProp.asJson());
//                                                apiTypePropList.add(apiTypeProp);
//                                            });
//                                            apiType.setTypePropList(apiTypePropList);
//                                        }
//                                        apiRequestBody.setType(apiType);
                                        apiVersion.setRequestBody(apiRequestBody);
                                    }
                                }

                                /**
                                 * 返回格式
                                 */
                                LOGGER.debug("返回格式-------------------------");
                                if (resource.getResponses() != null && resource.getResponses().size() > 0) {
                                    List<ApiInfoResponseEntity> responseList = new ArrayList<>();
                                    resource.getResponses().forEach(e -> {
                                        ApiInfoResponseEntity apiResponse;
                                        apiResponse = new ApiInfoResponseEntity();
                                        apiResponse.setApiVersionId(apiVersionId);
                                        apiResponse.setCode(e.getCode());
                                        apiResponse.setContentType(e.getContentType());
                                        apiResponse.setDescription(e.getTypeEntity().asJson());
                                        apiResponse = apiManagementDao.insertApiInfoResponseEntity(apiResponse);
//                                        Long apiResponseId = apiResponse.getId();
//                                        LOGGER.debug(" ApiInfoResponseEntity apiResponse:[{}]", apiResponse.asJson());
//                                        ApiInfoTypeEntity apiType;
//                                        apiType = new ApiInfoTypeEntity();
//                                        apiType.setApiResponseId(apiResponseId);
//                                        apiType.setName(e.getTypeEntity().getName());
//                                        apiType = apiManagementDao.insertApiInfoTypeEntity(apiType);
//                                        Long apiTypeId = apiType.getId();
//                                        LOGGER.debug("ApiInfoTypeEntity apiType:[{}]", apiType.asJson());
//                                        List<ApiInfoTypePropEntity> list1 = new ArrayList<>();
//                                        e.getTypeEntity().getPropEntities().forEach(x -> {
//                                            ApiInfoTypePropEntity apiTypeProp;
//                                            apiTypeProp = new ApiInfoTypePropEntity();
//                                            apiTypeProp.setApiTypeId(apiTypeId);
//                                            apiTypeProp.setType(x.getType());
//                                            apiTypeProp.setExample(x.getExample());
//                                            apiTypeProp.setRequired(x.isRequired());
//                                            apiTypeProp = apiManagementDao.insertApiInfoTypePropEntity(apiTypeProp);
//                                            LOGGER.debug("ApiInfoTypePropEntity apiTypeProp:[{}]", apiTypeProp.asJson());
//                                            list1.add(apiTypeProp);
//                                        });
                                        responseList.add(apiResponse);
                                    });
                                    apiVersion.setResponseList(responseList);
                                }
                                api.setVersionList(versionList);

                            }
                            list.add(api);
                        }
                    });
                }
                LOGGER.debug(" List<ApiInfoEntity> size:[{}]", list.size());
                list.forEach(x -> {
                    LOGGER.debug("api->:[{}]", x.asJson());
                });
                response = new Response<List<ApiInfoEntity>>();
                response.setCode(code[0]);
                if(code[0]==0)
                { response.setContent(list);}
                else
                { response.setContent(responseDescription);}
                LOGGER.debug("上传成功");
                LOGGER.debug("response:[{}]", response.asJson());
                return response;
            } catch (Exception e) {
                e.printStackTrace();
                return new Response<String>() {
                    {
                        setCode(ErrorCode.POST_RESOURCE_ERROR.code());
                        setContent(ErrorCode.POST_RESOURCE_ERROR.name() + e.getMessage() + " " + "deploy() fail ！");
                    }
                };
            }

        } else {
            return new Response<String>() {
                {
                    setCode(ErrorCode.POST_RESOURCE_ERROR.code());
                    setContent(ErrorCode.POST_RESOURCE_ERROR.name() + " " + "zip 包上传失败 内容为空!");
                }
            };
        }
    }


    @Override
    public Response createApiVersion(String body) {


        LOGGER.debug("body:[{}]", body);
        try {
            //----------------------------------------------------------------------------------------------------------
            LOGGER.debug("参数验证开始。。。。。");
            Version v = JsonUtil.fromJson(Version.class, body);
            Map<Boolean, Response> map = versionParameterVerification(v);
            map.forEach((key, value) -> {
                LOGGER.debug("key:[{}]----value:[{}]", key, value);
            });
            if (map.containsKey(false)) {
                LOGGER.debug("创建 api 版本 参数验证失败！");
                return map.get(false);
            } else {
                LOGGER.debug("创建 api 版本 参数验证通过！");
            }
            LOGGER.debug("参数验证结束。。。。。");
            //----------------------------------------------------------------------------------------------------------

            if(apiManagementDao.queryApiById(v.getApiId())==null){
                response = new Response<String>();
                response.setCode(ErrorCode.PARAMETER_ERROR.code());
                response.setContent("{" + ErrorCode.PARAMETER_ERROR.name() + "该ＡＰＩ不存在： ");
                return response;
            }

            StringBuilder finalPath = new StringBuilder("");
            finalPath.append(apiManagementDao.queryApiById(v.getApiId()).getBaseUrl());
            finalPath.append("/"+v.getName());
            finalPath.append(v.getPath());
            ApiInfoVersionEntity apiInfoVersionEntity = apiManagementDao.queryApiVersionByFinalPath(finalPath.toString());
            if (apiInfoVersionEntity != null) {
                response = new Response<List<String>>();
                response.setCode( ErrorCode.QUERY_RESOURCE_ERROR.code());
                response.setContent("{" + ErrorCode.PARAMETER_ERROR.name() + "该ＡＰＩ版本已存在"+finalPath+"}");
                return response;
            }


            ApiInfoVersionEntity version;
            version = createApiVersion(v,"","");

            response = new Response<ApiInfoVersionEntity>();
            response.setCode(ErrorCode.NO_ERROR.code());
            response.setContent(version);
            LOGGER.debug("response:[{}]", response.asJson());
            return response;
        } catch (Exception e) {
            return postResourseError(e);
        }
    }



   //删除

    @Override
    public Response deleteApiById(String id) {
        int code= 0;
        String responseDescription= "";
        LOGGER.debug("id:[{}]", id);


        try {
            response = new Response<ApiInfoEntity>();
            String idList[] ;
            idList= id.split(",");
            for (int i = 0; i < idList.length; i++) {
                long id1 = (int) Double.parseDouble(idList[i]);
                ApiInfoEntity apiInfoEntity = apiManagementDao.queryApiById(id1);
                if(apiInfoEntity==null){
                    code += ErrorCode.PARAMETER_ERROR.code();
                    responseDescription += ("{"+ErrorCode.PARAMETER_ERROR.name() + "查无此api:"+id1+"}");
                }
                else
                {
                    String name = apiInfoEntity.getDisplayName();
                    List<ApiInfoVersionEntity> apiInfoVersionEntityList = apiManagementDao.queryApiVersionByApiId(id1);
                    LOGGER.debug("开始删除api版本*********************************");
                    if(apiInfoVersionEntityList!=null&&apiInfoVersionEntityList.size()>0)
                    {  apiInfoVersionEntityList.forEach(apiInfoVersionEntity -> {
                        Long apiVersionId = apiInfoVersionEntity.getId();
                        //删除文档
                        LOGGER.debug("开始删除文档*********************************");
                        apiManagementDao.deleteApiDocumentationByApiVersionId(apiVersionId);
                        LOGGER.debug("删除完毕******************************");
                        //删除媒体类型
                        LOGGER.debug("开始删除媒体类型*************************");
                        apiManagementDao.deleteApiMediaTypeByApiVersionId(apiVersionId);
                        LOGGER.debug("删除完毕************************");
                        //删除get/delete 请求hender列表
                        LOGGER.debug("开始删除get/delete 请求hender列表***********************8");
                        apiManagementDao.deleteApiInfoMethodParamByApiVersionId(apiVersionId);
                        LOGGER.debug("删除完毕*****************************8");
                        //删除 put / post 求body
                        LOGGER.debug("开始删除 put / post 求body***************************8");
                        ApiInfoRequestBodyEntity apiInfoRequestBodyEntity = apiManagementDao.queryApiInfoRequestBodyByApiVersionId(apiVersionId);
                        if(apiInfoRequestBodyEntity!=null){
//                        ApiInfoTypeEntity apiInfoTypeEntity = apiManagementDao.queryApiInfoTypeByapiRequestBodyId(apiVersionId);
//                        if (apiInfoTypeEntity != null) {
//                            LOGGER.debug("ApiInfoTypeEntity"+apiInfoTypeEntity.asJson());
//                            List<ApiInfoTypePropEntity> apiInfoTypePropEntityList = apiManagementDao.queryApiInfoTypePropByApiTypeId(apiInfoTypeEntity.getId());
//                            if(apiInfoTypePropEntityList!=null &&apiInfoTypePropEntityList.size()>0)
//                            {   apiManagementDao.deleteApiInfoTypePropByApiTypeId(apiInfoTypeEntity.getId());}
//                            apiManagementDao.deleteApiInfoTypeByRequestBodyId(apiVersionId);
//                        }
                        apiManagementDao.deleteApiInfoRequestBody(apiVersionId);
                    }
                        LOGGER.debug("删除完毕***************************");
                        //删除返回列表
                        LOGGER.debug("开始删除返回列表********************************8*");
                        List<ApiInfoResponseEntity> apiResponseEntityList= apiManagementDao.queryApiResponseByApiVersionId(apiVersionId);
                        if(apiResponseEntityList!=null&&apiResponseEntityList.size()>0)
                        {  apiResponseEntityList.forEach(apiInfoResponseEntity -> {
//                            ApiInfoTypeEntity apiInfoTypeEntity1 = apiManagementDao.queryApiInfoTypeByResponseId(apiInfoResponseEntity.getId());
//                            if(apiInfoTypeEntity1 !=null){
//                                List<ApiInfoTypePropEntity> apiInfoTypePropEntityList = apiManagementDao.queryApiInfoTypePropByApiTypeId(apiInfoTypeEntity1.getId());
//                                if(apiInfoTypePropEntityList!=null &&apiInfoTypePropEntityList.size()>0)
//                                {apiInfoTypePropEntityList.forEach(apiInfoTypePropEntity -> {
//                                        apiManagementDao.deleteApiInfoTypePropByApiTypeId(apiInfoTypePropEntity.getId());
//                                    });}
//                                apiManagementDao.deleteApiInfoTypeByResponseId(apiInfoResponseEntity.getId());
//                            }
                            apiManagementDao.deleteApiInfoResponseByApiVersionId(apiVersionId);

                        });}
                        apiManagementDao.deleteApiVsersionById(apiVersionId);
                    });}
                    LOGGER.debug("删除完毕**************************8**");
                    apiManagementDao.deleteApiById(id1);
                    code += ErrorCode.NO_ERROR.code();
                    responseDescription +="{API:"+name+"删除成功}";
                }
                }
            response.setCode(code);
            response.setContent(responseDescription);
            return response;
        } catch (Exception e) {
            return deleteResourseError(e);
        }

    }


    @Override
    public Response deleteApiVersion(String id) {
        int code= 0;
        String responseDescription= "";
        LOGGER.debug("id:[{}]", id);


        try {
            response = new Response<ApiInfoEntity>();
            String idList[];
                    idList= id.split(",");
            for (int i = 0; i < idList.length; i++) {
                long id1 = (int) Double.parseDouble(idList[i]);
                ApiInfoVersionEntity apiInfoVersionEntity = apiManagementDao.queryApiVersionById(id1);
                if(apiInfoVersionEntity==null){
                    code += ErrorCode.PARAMETER_ERROR.code();
                    responseDescription += ("{"+ErrorCode.PARAMETER_ERROR.name() + "查无此api版本:"+id1+"}");
                }
                else
                {
                    String name = apiInfoVersionEntity.getName();
                        Long apiVersionId = id1;
                        //删除文档
                        apiManagementDao.deleteApiDocumentationByApiVersionId(apiVersionId);
                        //删除媒体类型
                        apiManagementDao.deleteApiMediaTypeByApiVersionId(apiVersionId);
                        //删除g et/delete 请求hender列表
                        apiManagementDao.deleteApiInfoMethodParamByApiVersionId(apiVersionId);
                        //删除 put / post 求body
                        ApiInfoRequestBodyEntity apiInfoRequestBodyEntity = apiManagementDao.queryApiInfoRequestBodyByApiVersionId(apiVersionId);
                        if(apiInfoRequestBodyEntity!=null){
//                            ApiInfoTypeEntity apiInfoTypeEntity = apiManagementDao.queryApiInfoTypeByapiRequestBodyId(apiVersionId);
//                            if (apiInfoTypeEntity != null) {
//                                List<ApiInfoTypePropEntity> apiInfoTypePropEntityList = apiManagementDao.queryApiInfoTypePropByApiTypeId(apiInfoTypeEntity.getId());
//                                if(apiInfoTypePropEntityList!=null &&apiInfoTypePropEntityList.size()>0)
//                                {   apiManagementDao.deleteApiInfoTypePropByApiTypeId(apiInfoTypeEntity.getId());}
//                                apiManagementDao.deleteApiInfoTypeByRequestBodyId(apiVersionId);
//                            }
                            apiManagementDao.deleteApiInfoRequestBody(apiVersionId);
                        }
                        //删除返回列表
                        List<ApiInfoResponseEntity> apiResponseEntityList= apiManagementDao.queryApiResponseByApiVersionId(apiVersionId);
                        if(apiResponseEntityList!=null&&apiResponseEntityList.size()>0)
                        {  apiResponseEntityList.forEach(apiInfoResponseEntity -> {
//                            ApiInfoTypeEntity apiInfoTypeEntity1 = apiManagementDao.queryApiInfoTypeByResponseId(apiInfoResponseEntity.getId());
//                            if(apiInfoTypeEntity1 !=null){
//                                List<ApiInfoTypePropEntity> apiInfoTypePropEntityList = apiManagementDao.queryApiInfoTypePropByApiTypeId(apiInfoTypeEntity1.getId());
//                                if(apiInfoTypePropEntityList!=null &&apiInfoTypePropEntityList.size()>0)
//                                        apiManagementDao.deleteApiInfoTypePropByApiTypeId(apiInfoTypeEntity1.getId());
//
//                                a piManagementDao.deleteApiInfoTypeByResponseId(apiInfoResponseEntity.getId());
//                            }
                            apiManagementDao.deleteApiInfoResponseByApiVersionId(apiVersionId);

                        });}

                        apiManagementDao.deleteApiVsersionById(id1);
                    code += ErrorCode.NO_ERROR.code();
                    responseDescription +="{API版本:"+name+"删除成功}";
                }
            }
            response.setCode(code);
            response.setContent(responseDescription);
            return response;
        } catch (Exception e) {
            return deleteResourseError(e);
        }


    }

   //更新

    private Response bodyIsEmpty(String body){
        LOGGER.debug("body:[{}]", body);
        if (StringUtils.isEmpty(body)) {
            response = new Response<String>();
            response.setCode(ErrorCode.PARAMETER_ERROR.code());
            response.setContent(ErrorCode.PARAMETER_ERROR.name() + "body is empty");
            return response;
        }
        return null;
    }


    @Override
    public Response updateApi(String body) {
        bodyIsEmpty(body);

        long apiId;
        String displayName;
        String description;
        String appId;
        String basurl = null;

        try {
            ApiUpdateParam apiInfoEntity = JsonUtil.fromJson(ApiUpdateParam.class, body);
            assert apiInfoEntity != null;
            apiId = apiInfoEntity.getId();
            displayName = apiInfoEntity.getDisplayName();
            description = apiInfoEntity.getDescription();
            appId = apiInfoEntity.getAppId();
            LOGGER.debug("ApiUpdateParm:"+apiInfoEntity.asJson());
        } catch (Exception e) {
            errorDescription = "格式转换错误！";
            return errorMessagePrint(errorDescription);
        }

        try {
            ApiInfoEntity a = apiManagementDao.queryApiById(apiId);
            LOGGER.debug("apienty:"+a.asJson());
            if(a==null){
                errorDescription = "该api不存在";
                return errorMessagePrint(errorDescription);
            }
            if(isNotNull(appId))
            {AppBasicsEntity appBasicsEntity = appBasicsDao.selectAppBasicsEntityById(Long.parseLong(appId));
            if (appBasicsEntity == null) {
                errorDescription = "该应用不存在";
                return errorMessagePrint(errorDescription);
            }
            else {
                basurl = ("/"+appBasicsEntity.getShortName());
            }
            }
            ApiInfoEntity apiInfoEntity = apiManagementDao.updateApi(apiId, displayName, description, appId,basurl);
            LOGGER.debug("ApiInfoEntity:  "+apiInfoEntity);
            response = new Response<ApiInfoVersionEntity>();
            response.setContent(apiInfoEntity);
            response.setCode(ErrorCode.NO_ERROR.code());
            return response;
        } catch (Exception e) {
            return postResourseError(e);
        }
    }


    @Override
    public Response updateApiVersion(String body) {
        bodyIsEmpty(body);
        long id;
        String name;
        String status;
        String serviceId;
        String mehtod;
        String path;
        StringBuilder finalpath = new StringBuilder("");
        ApiVersionUpdateParam apiVersionUpdateParam ;
        try {
            apiVersionUpdateParam = JsonUtil.fromJson(ApiVersionUpdateParam.class, body);
            assert apiVersionUpdateParam != null;
            id = apiVersionUpdateParam.getId();
            name = apiVersionUpdateParam.getName();
            status = apiVersionUpdateParam.getStatus();
            serviceId = apiVersionUpdateParam.getServiceId();
            mehtod = apiVersionUpdateParam.getMethod();
            path = apiVersionUpdateParam.getPath();

        } catch (Exception e) {
            errorDescription = "格式转换错误！";
            return errorMessagePrint(errorDescription);
        }



        LOGGER.debug("id:  " + id);
        LOGGER.debug("name:  " + name);
        LOGGER.debug("status:  " + status);
        LOGGER.debug("serviceId:  " + serviceId);

        try {
            ApiInfoVersionEntity apiInfoVersionEntity = apiManagementDao.queryApiVersionById(id);
            if(apiInfoVersionEntity ==null){
                response = new Response<ApiInfoVersionEntity>();
                response.setCode(ErrorCode.RESOURCE_NO_EXIST_ERROR.code());
                response.setContent("该api版本不存在");
            }else {
                if(isNull(name)){
                    name = apiInfoVersionEntity.getName();
                }
                if(isNull(path)){
                    path = apiInfoVersionEntity.getPath();
                }
                finalpath.append(apiManagementDao.queryApiById(apiInfoVersionEntity.getApiId()).getBaseUrl());
                finalpath.append("/"+name);
                finalpath.append(path);
                }

            apiInfoVersionEntity = apiManagementDao.updateApiVersion(id, name, status, serviceId,mehtod,finalpath.toString());
            if(isNotNull(mehtod)) {
                //删除 put / post 求body
                ApiInfoRequestBodyEntity apiInfoRequestBodyEntity = apiManagementDao.queryApiInfoRequestBodyByApiVersionId(id);
                if (apiInfoRequestBodyEntity != null) {
//                    ApiInfoTypeEntity apiInfoTypeEntity = apiManagementDao.queryApiInfoTypeByapiRequestBodyId(id);
//                    if (apiInfoTypeEntity != null) {
//                        List<ApiInfoTypePropEntity> apiInfoTypePropEntityList = apiManagementDao.queryApiInfoTypePropByApiTypeId(apiInfoTypeEntity.getId());
//                        if (apiInfoTypePropEntityList != null && apiInfoTypePropEntityList.size() > 0){
//                            apiManagementDao.deleteApiInfoTypePropByApiTypeId(apiInfoTypeEntity.getId());}
//                        apiManagementDao.deleteApiInfoTypeByRequestBodyId(id);
//                    }
                    apiManagementDao.deleteApiInfoRequestBody(id);
                }

                //删除g et/delete 请求hender列表
                apiManagementDao.deleteApiInfoMethodParamByApiVersionId(id);


                List<ApiInfoMethodParamEntity> m = new ArrayList<>();
                if (apiVersionUpdateParam.getParametersList() != null && apiVersionUpdateParam.getParametersList().size() > 0) {
                    apiVersionUpdateParam.getParametersList().forEach(z -> {
                        if (z != null) {
                            ApiInfoMethodParamEntity ap;
                            ap = new ApiInfoMethodParamEntity();
                            ap.setApiVersionId(id);
                            ap.setName(z.getName());
                            ap.setDescription(z.getDescription());
                            ap.setType(z.getType());
                            ap.setRequired(z.isRequired());
                            ap.setExample(z.getExample());
                            ap = apiManagementDao.insertApiInfoMethodParamEntity(ap);
                            m.add(ap);
                            LOGGER.debug("ApiInfoMethodParamEntity  ap:[{}]", ap.asJson());
                        }
                    });
                }
                apiInfoVersionEntity.setParametersList(m);

                    if (apiVersionUpdateParam.getRequestBody() != null) {
                        ApiInfoRequestBodyEntity rb;
                        rb = new ApiInfoRequestBodyEntity();
                        rb.setApiVersionId(id);
                        rb.setContentType(apiVersionUpdateParam.getRequestBody().getContentType());
                        rb.setDescription(apiVersionUpdateParam.getRequestBody().getDescription());
                        rb = apiManagementDao.insertApiInfoRequestBodyEntity(rb);
                        apiInfoVersionEntity.setRequestBody(rb);
                        LOGGER.debug("ApiInfoRequestBodyEntity  rb:[{}]", rb.asJson());
                    }
                    List<ApiInfoResponseEntity> apiInfoResponseEntities = new ArrayList<>();
                    if(apiVersionUpdateParam.getResponseList()!=null&&apiVersionUpdateParam.getResponseList().size()>0){
                       apiManagementDao.deleteApiInfoResponseByApiVersionId(id);
                        apiVersionUpdateParam.getResponseList().forEach(r -> {
                            ApiInfoResponseEntity resp;
                            resp = new ApiInfoResponseEntity();
                            resp.setApiVersionId(id);
                            resp.setContentType(r.getContentType());
                            resp.setCode(r.getCode());
                            resp.setDescription(r.getDescription());
                            resp = apiManagementDao.insertApiInfoResponseEntity(resp);
                            LOGGER.debug("ApiInfoResponseEntity newResp:[{}]", resp.asJson());
                            apiInfoResponseEntities.add(resp);
                        });
                    }

                apiInfoVersionEntity.setResponseList(apiInfoResponseEntities);
            }
            response = new Response<ApiInfoVersionEntity>();
            response.setCode(ErrorCode.NO_ERROR.code());
            response.setContent(apiInfoVersionEntity);
            return response;
        } catch (Exception e) {
            return postResourseError(e);
        }
    }


    /**
     * @param
     * @param path
     * @return
     */
    @Override
    public Response existApiVersion(String baseUrl,String appId,String apiVersionName,String path) {
        try {
            if(baseUrl==null||baseUrl.length()==0)
            {
                if(appId!=null&&appId.length()!=0){
                    baseUrl = "/"+appBasicsDao.selectAppBasicsEntityById(Long.parseLong(appId)).getShortName();
                }
            }
            response = new Response<String>();
            String finalPath = "" + baseUrl +"/"+apiVersionName+ path;
            LOGGER.debug(""+finalPath);
            ApiInfoVersionEntity apiInfoVersionEntity = apiManagementDao.queryApiVersionByFinalPath(finalPath);

            if(apiInfoVersionEntity==null){
                response.setCode(ErrorCode.NO_ERROR.code());
                response.setContent("该api版本不存在");
            }
            else {
                LOGGER.debug("apiVersion:"+apiInfoVersionEntity.asJson());
                response.setCode(1000);
                response.setContent("该api版本已存在");
            }

        } catch (Exception e) {
            response = new Response<String>();
            response.setCode(ErrorCode.QUERY_RESOURCE_ERROR.code());
            response.setContent(ErrorCode.QUERY_RESOURCE_ERROR.name() + e.getMessage());
        }
        return response;
    }

    @Override
    public Response existApi(long appId,String displayname) {
        try {
            response = new Response<String>();
            String baseUrl = "/"+appBasicsDao.selectAppBasicsEntityById(appId).getShortName();
            ApiInfoEntity apiInfoEntity = apiManagementDao.queryApiByBasurlAndDisplayname(baseUrl,displayname);
            if(apiInfoEntity==null) {
                response.setCode(ErrorCode.NO_ERROR.code());
                response.setContent("该api不存在");
            }
            else {
                    LOGGER.debug("apiVersion:"+apiInfoEntity.asJson());
                    response.setCode(1000);
                    response.setContent("该api已存在");
                }


        } catch (Exception e) {
            response = new Response<String>();
            response.setCode(ErrorCode.QUERY_RESOURCE_ERROR.code());
            response.setContent(ErrorCode.QUERY_RESOURCE_ERROR.name() + e.getMessage());
        }
        return response;
    }




    //查询
    /**
     * 查询api版本（非接口）
     * @return ApiInfoVersionEntity
     * @parm ｉｄ
     *
     */
    private ApiInfoVersionEntity queryApiVersion(ApiInfoVersionEntity apiInfoVersionEntity){


        LOGGER.debug(apiInfoVersionEntity.asJson());
        String serviceId = apiInfoVersionEntity.getServiceId();
        ServiceEntity serviceEntity = apiManagementDao.queryServiceById(serviceId);
        if (serviceEntity != null)
        { apiInfoVersionEntity.setServiceName(serviceEntity.getName());}

        Long apiVersionId = apiInfoVersionEntity.getId();

        //Documentation
        List<ApiInfoDocumentationEntity> apiInfoDocumentationEntities = apiManagementDao.queryApiInfoDocumentByApiVersionId(apiVersionId);
        if (apiInfoDocumentationEntities != null && apiInfoDocumentationEntities.size() > 0)
        {
            apiInfoVersionEntity.setDocList(apiInfoDocumentationEntities);
            LOGGER.debug("apiInfoDocumentationEntities:  " + apiInfoVersionEntity.getDocList().toString());
        }
        else {
            apiInfoVersionEntity.setDocList(new ArrayList<>());
        }



        //ApiInfoMediaTypeEntity
        List<ApiInfoMediaTypeEntity> apiInfoMediaTypeEntities = apiManagementDao.queryapiInfoMediaTypeByApiVersionId(apiVersionId);
        if (apiInfoMediaTypeEntities != null && apiInfoMediaTypeEntities.size() > 0)
        {  apiInfoVersionEntity.setMediaTypeList(apiInfoMediaTypeEntities);
            LOGGER.debug("apiInfoMediaTypeEntities" + apiInfoVersionEntity.getMediaTypeList().toString());}
        else {
            apiInfoVersionEntity.setMediaTypeList(new ArrayList<>());
        }


        //ApiInfoResponseEntity
        List<ApiInfoResponseEntity> apiInfoResponseEntities = apiManagementDao.queryApiInfoResponseByApiVersionId(apiVersionId);
        if (apiInfoResponseEntities != null && apiInfoResponseEntities.size() > 0) {
//            apiInfoResponseEntities.forEach(apiInfoResponseEntity -> {
//                long apiResponseId = apiInfoResponseEntity.getId();
//                ApiInfoTypeEntity apiInfoTypeEntity = apiManagementDao.queryApiInfoTypeByResponseId(apiResponseId);
//                if (apiInfoTypeEntity != null) {
//                    apiInfoResponseEntity.setType(queryApiInfoTypeEntity(apiInfoTypeEntity));
//                }
//            });
            apiInfoVersionEntity.setResponseList(apiInfoResponseEntities);
            LOGGER.debug("apiInfoResponseEntity:"+apiInfoVersionEntity.getResponseList().toString());
        }
        else {
            apiInfoVersionEntity.setResponseList(new ArrayList<>());
        }


        //ApiInfoMethodParamEntity
        List<ApiInfoMethodParamEntity> apiInfoMethodParamEntities = apiManagementDao.queryApiInfoMethodParam(apiVersionId);
        if (apiInfoMethodParamEntities != null && apiInfoMethodParamEntities.size() > 0) {
            apiInfoVersionEntity.setParametersList(apiInfoMethodParamEntities);
            LOGGER.debug("ApiInfoMethodParamEntity:" + apiInfoVersionEntity.getMediaTypeList().toString());
        }




        //ApiInfoRequestBodyEntity
        ApiInfoRequestBodyEntity apiInfoRequestBodyEntity = apiManagementDao.queryApiInfoRequestBodyByApiVersionId(apiVersionId);
        LOGGER.debug("apiInfoRequestBodyEntity:"+apiInfoRequestBodyEntity);
        if (apiInfoRequestBodyEntity != null) {
//            ApiInfoTypeEntity apiInfoTypeEntity = apiManagementDao.queryApiInfoTypeByapiRequestBodyId(apiVersionId);
//            if (apiInfoTypeEntity != null) {
//                apiInfoRequestBodyEntity.setType(queryApiInfoTypeEntity(apiInfoTypeEntity));
//            }
            apiInfoVersionEntity.setRequestBody(apiInfoRequestBodyEntity);
            LOGGER.debug("apiInfoRequestBodyEntity:"+apiInfoVersionEntity.getRequestBody().asJson());
        }
        return apiInfoVersionEntity;
    }

    @Override
    public Response queryApi(long id) {
        LOGGER.debug("id:[{}]", id);
        try {
            response = new Response<ApiInfoEntity>();
            ApiInfoEntity apiInfoEntity = apiManagementDao.queryApiById(id);
            LOGGER.debug("apiInfoEntity:" + apiInfoEntity.asJson());
            if (apiInfoEntity != null) {
                Long appId = apiInfoEntity.getAppId();
                if (appId!=null) {
                    LOGGER.debug("appId:" + appId);
                    AppBasicsEntity appBasicsEntity = apiManagementDao.queryAppByAppId(appId);
                    LOGGER.debug("appBasicsEntity:" + appBasicsEntity);
                    if (appBasicsEntity != null) {
                        String name = appBasicsEntity.getName();
                        apiInfoEntity.setAppName(name);
                        LOGGER.debug("name:" + name);
                    }
                }

                //ApiInfoVersionEntity
                List<ApiInfoVersionEntity> apiInfoVersionEntities = apiManagementDao.queryApiVersionByApiId(id);
                if (apiInfoVersionEntities != null && apiInfoVersionEntities.size() > 0) {
                    LOGGER.debug("apiVersion:"+apiInfoVersionEntities.toString());
                    apiInfoVersionEntities.forEach(this::queryApiVersion);
                    apiInfoEntity.setVersionList(apiInfoVersionEntities);
                    LOGGER.debug("apiInfoVersionEntities:" + apiInfoEntity.getVersionList().toString());
                }
            }
            response.setCode(ErrorCode.NO_ERROR.code());
            response.setContent(apiInfoEntity);
            return response;
        } catch (Exception e) {
            return getResourseError(e);
        }
           }




    @Override
    public Response queryApiVersionById(long id) {
        LOGGER.debug("id:[{}]", id);
        try {
            response = new Response<ApiInfoVersionEntity>();
            ApiInfoVersionEntity apiInfoVersionEntity = apiManagementDao.queryApiVersionById(id);
            if (apiInfoVersionEntity == null) {
                response = new Response<String>();
                response.setCode(ErrorCode.PARAMETER_ERROR.code());
                response.setContent(ErrorCode.PARAMETER_ERROR.name() + ":api版本不存在");
                return response;
            }else {
                apiInfoVersionEntity = queryApiVersion(apiInfoVersionEntity);
            }

            response.setCode(ErrorCode.NO_ERROR.code());
            response.setContent(apiInfoVersionEntity);
            return response;
        } catch (Exception e) {
            return getResourseError(e);
        }
    }


    @Override
    public Response queryApiListByCondition(int index, int limit, String sortBy, String sortDirection, String displayName, String baseUrl, String description, String method ,String appName) {
        try {
            response = new Response<PageDto<ApiInfoEntity>>();
            response.setCode(ErrorCode.NO_ERROR.code());
            LOGGER.debug("传入参数为：[{}]",displayName,baseUrl,description,method,appName);
            PageDto<ApiInfoEntity> apiInfoEntityPageDto = apiManagementDao.queryApiByCondition(index, limit, sortBy, sortDirection, displayName, baseUrl, description, method,appName);
            List<ApiInfoEntity> apiInfoEntities = apiInfoEntityPageDto.getData();
            apiInfoEntities.forEach(apiInfoEntity -> {
                Long appId = apiInfoEntity.getAppId();
                LOGGER.debug("appId:" + appId);
                if (appId!=null) {
                    AppBasicsEntity appBasicsEntity = apiManagementDao.queryAppByAppId(appId);
                    LOGGER.debug("appBasicsEntity : " + appBasicsEntity);
                    if (appBasicsEntity != null)
                    {  apiInfoEntity.setAppName(appBasicsEntity.getName());}
                    else {apiInfoEntity.setAppName(null);}
                }
            });
            apiInfoEntityPageDto.setData(apiInfoEntities);
            response.setContent(apiInfoEntityPageDto);
            return response;
        } catch (Exception e) {
            return getResourseError(e);
        }
    }



    @Override
    public Response queryServiceNameAndId() {
        try {
            response = new Response<List>();
            List<IdAndName> list = new ArrayList<>();
            List<ServiceEntity> serviceEntities = apiManagementDao.queryServiceList();
            if (serviceEntities != null && serviceEntities.size() > 0)
            {       serviceEntities.forEach(serviceEntity -> {
                    IdAndName idAndName = new IdAndName();
                    idAndName.setValue(serviceEntity.getId());

                    idAndName.setText(serviceEntity.getName());
                    list.add(idAndName);

                });}
            response.setCode(ErrorCode.NO_ERROR.code());
            response.setContent(list);
            return response;
        } catch (Exception e) {
            return getResourseError(e);
        }


    }

    @Override
    public Response queryApiNameAndId() {
        try {
            response = new Response<List>();
            List<LongAndName> list = new ArrayList<>();
            List<ApiInfoEntity> apiInfoEntities = apiManagementDao.queryApis();
            if (apiInfoEntities != null && apiInfoEntities.size() > 0)
            {    apiInfoEntities.forEach(apiInfoEntity -> {
                    LongAndName longAndName = new LongAndName();
                    longAndName.setValue(apiInfoEntity.getId());
                    longAndName.setText(apiInfoEntity.getDisplayName());
                    list.add(longAndName);
                });}
            response.setCode(ErrorCode.NO_ERROR.code());
            response.setContent(list);
            return response;
        } catch (Exception e) {
            return getResourseError(e);
        }
    }

    @Override
    public Response queryApiList(int index, int limit, String sortBy, String sortDirection) {
        LOGGER.debug("index:：" + index);
        LOGGER.debug("limit:：" + limit);
        LOGGER.debug("sortBy:：" + sortBy);
        LOGGER.debug("sortDirection:：" + sortDirection);
        try {
            response = new Response<PageDto<ApiInfoEntity>>();
            response.setCode(ErrorCode.NO_ERROR.code());
            PageDto<ApiInfoEntity> pageDto = apiManagementDao.queryApiList(index, limit, sortBy, sortDirection);
            List<ApiInfoEntity> apiInfoEntities = pageDto.getData();
            if (apiInfoEntities != null && apiInfoEntities.size() > 0)
            {apiInfoEntities.forEach(apiInfoEntity -> {
                    LOGGER.debug("id" + apiInfoEntity.getId());
                    LOGGER.debug("appid:[{}]", apiInfoEntity.getAppId());
                    String a = "";
                    if (apiInfoEntity.getAppId() != null && !apiInfoEntity.getAppId().equals(a) && apiInfoEntity.getAppId() != 0) {

                        long id = apiInfoEntity.getAppId();
                        AppBasicsEntity appBasicsEntity = apiManagementDao.queryAppByAppId(id);
                        LOGGER.debug("appBasicsEntity:" + appBasicsEntity);
                        if (appBasicsEntity != null) {
                            String name = appBasicsEntity.getName();
                            apiInfoEntity.setAppName(name);
                            LOGGER.debug("name:" + name);
                        }
                        LOGGER.debug("ApiInfoEntity:" + apiInfoEntity);
                    }
                });}
            pageDto.setData(apiInfoEntities);
            response.setContent(pageDto);
            return response;
        } catch (Exception e) {
            return getResourseError(e);
        }
    }


    @Override
    public Response queryFinalpath(String appId) {


        try{
            List<ApiInfoEntity> list;
            List<ApiInfoEntity> al =new ArrayList<>();
            LOGGER.debug("appId:"+appId);
            if(appId!=null&&appId.length()!=0){
                list = apiManagementDao.queryApiByAppId(Long.parseLong(appId));
               }
            else {
                list =    apiManagementDao.queryApiByAppIdIsNull();
            }

            if (list != null && list.size() > 0) {
                LOGGER.debug("ApiInfoEntity" + list.toString());
                list.forEach(apiInfoEntity -> {
                    List<ApiInfoVersionEntity> v = new ArrayList<>();
                    ApiInfoEntity a = new ApiInfoEntity();
                    a.setDisplayName(apiInfoEntity.getDisplayName());
                    List<ApiInfoVersionEntity> apiInfoVersionEntity = apiManagementDao.queryApiVersionByApiId(apiInfoEntity.getId());
                    if (apiInfoVersionEntity != null && apiInfoVersionEntity.size() > 0) {
                        apiInfoVersionEntity.forEach(version -> {
                            ApiInfoVersionEntity versionEntity = new ApiInfoVersionEntity();
                            versionEntity.setFinalPath(version.getFinalPath());
                            v.add(versionEntity);
                        });
                        a.setVersionList(v);
                        al.add(a);
                    } else {
                        a.setVersionList(new ArrayList<>());
                        al.add(a);
                    }
                });
                response = new Response<List>();
                response.setCode(ErrorCode.NO_ERROR.code());
                response.setContent(al);
                return response;
            } else {
                response = new Response<List>();
                response.setCode(ErrorCode.NO_ERROR.code());
                response.setContent(al);
                return response;
            }
        }catch (Exception e){
            return getResourseError(e);
        }
    }

    @Override
    public Response queryApiVersionListByCondition(int index, int limit, String sortBy, String sortDirection, String displayName, String appName) {
        try {
            response = new Response<PageDto<ApiInfoEntity>>();
            response.setCode(ErrorCode.NO_ERROR.code());
            LOGGER.debug("传入参数为：[{}]",displayName,appName);
            PageDto<ApiVersionDto> apiInfoEntityPageDto = apiManagementDao. queryApiVersionByCondition(index, limit, sortBy, sortDirection, displayName,appName);
            List<ApiVersionDto> apiInfoEntities = apiInfoEntityPageDto.getData();
            LOGGER.debug("apiversionlist:"+apiInfoEntities);
            apiInfoEntities.forEach(apiInfoEntity -> {
                Long appId = apiInfoEntity.getAppId();
                if (appId!=null) {
                    LOGGER.debug("appId:" + appId);
                    AppBasicsEntity appBasicsEntity = apiManagementDao.queryAppByAppId(appId);
                    if (appBasicsEntity != null)
                    {
                        LOGGER.debug("appBasicsEntity : " + appBasicsEntity);
                        apiInfoEntity.setAppName(appBasicsEntity.getName());}
                    else {apiInfoEntity.setAppName(null);}
                }
                String serviceId = apiInfoEntity.getServiceId();
                if(serviceId!=null){
                    LOGGER.debug("serviceId : "+serviceId);
                    ServiceEntity serviceEntity = apiManagementDao.queryServiceById(serviceId);
                    if(serviceEntity!=null){apiInfoEntity.setServiceName(serviceEntity.getName());}
                    else {apiInfoEntity.setServiceName(null);}
                }
            });
            apiInfoEntityPageDto.setData(apiInfoEntities);
            response.setContent(apiInfoEntityPageDto);
            return response;
        } catch (Exception e) {
            return getResourseError(e);
        }
    }

}
