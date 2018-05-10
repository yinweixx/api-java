package com.anyun.cloud.api;


import com.anyun.cloud.common.context.ControllerIOC;
import com.anyun.cloud.common.json.JsonUtil;
import com.anyun.cloud.service.ApiManagementService;
import com.anyun.cloud.service.Status;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import com.anyun.cloud.common.sys.Response;

/**
 * Created by jt on 18-1-16.
 */
@Path("/apimanagement")
public class ApiManagementApi {
    @Context
    private HttpServletRequest request;

    private ApiManagementService service;

    public ApiManagementApi() {
        service = ControllerIOC.getIOC().getInstance(ApiManagementService.class);
    }



    /**
     * 创建
     */

    /**
     * 1.添加API接口
     * @param body
     * @return
     */
    @Path("/create/api")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String createApi(String body){
        Response response= service.createApi(body);
        return JsonUtil.toJson(response);
    }

    /**
     * 2.zip 文件上传
     */
    @Path("/upload/zip")
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String upload() {
        Response response = service.upload( request);
        return JsonUtil.toJson(response);
    }


    /**
     * 3.添加版本
     * @param
     * @return
     */
    @Path("/create/apiversion")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String createApiVersion(String body) throws Exception {
        Response response = service.createApiVersion(body);
        return JsonUtil.toJson(response);
    }


    /**
     * 查询
     */


    /**
     * 4.按条件查询api(有ｓｔａｔｕｓ参数)
     * @return
     */
    @Path("/get/apilistbycondition")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String queryApiListByCondition(@QueryParam("index") @DefaultValue("1") String index,
                                          @QueryParam("limit") @DefaultValue("10") String limit,
                                          @QueryParam("sortBy") @DefaultValue("") String sortBy,
                                          @QueryParam("sortDirection") @DefaultValue("asc") String sortDirection,
                                          @QueryParam("displayName")  String displayName,
                                          @QueryParam("baseUrl")  String baseUrl,
                                          @QueryParam("description") String description,
                                          @QueryParam("method") String method,
                                          @QueryParam("appName") String appName) throws Exception {
        Response response=service.queryApiListByCondition( (int) Double.parseDouble(index), (int) Double.parseDouble(limit), sortBy, sortDirection
                ,displayName ,  baseUrl, description, method,appName);

        return JsonUtil.toJson(response);
    }


    /**
     *16. API目录
     * @parm index          String    1      页码 （默认第一页）
     * @parm limit          String    10     每页记录数目（默认每页限制10条）
     * @parm sortBy         String          排序字段
     * @parm sortDirection  String    asc     排序方向asc:正序（默认）desc ：倒序
     * @return
     */
    @Path("/get/apiversioncatalog")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String queryApiVersionList(@QueryParam("index") @DefaultValue("1") String index,
                                      @QueryParam("limit") @DefaultValue("10") String limit,
                                      @QueryParam("sortBy") String sortBy,
                                      @QueryParam("sortDirection") @DefaultValue("asc") String sortDirection,
                                      @QueryParam("displayName")  String displayName,
                                      @QueryParam("appName") String appName) throws Exception {

        Response response=service.queryApiVersionListByCondition(Integer.parseInt(index), Integer.parseInt(limit),sortBy,
                sortDirection, displayName, appName);

        return JsonUtil.toJson(response);
    }



    /**
     * 5.查询ａｐｉ详情
     * @param id
     * @return
     */
    @Path("/get/api/{id}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String queryApi(@PathParam("id")long id) throws Exception {
        Response response = service.queryApi(id);
        return JsonUtil.toJson(response);
    }


    /**
     * 6.查询版本
     * @param
     * @return
     */
    @Path("/get/apiversion/{id}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String queryApiVersionById(@PathParam("id")long id) throws Exception {

        Response response = service.queryApiVersionById(id);
        return JsonUtil.toJson(response);
    }


    /**
     * 7.检测api版本是否存在
     * @param
     * @return
     */
    @Path("/test/apiversionisexist")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String existApiVersion(@QueryParam("baseUrl")  String baseUrl,
                                  @QueryParam("appId")  String appId,
                                  @QueryParam("apiVersionName")  String apiVersionName,
                                  @QueryParam("path") String path) throws Exception {
        Response response = service.existApiVersion(baseUrl,appId,apiVersionName,path);
        return JsonUtil.toJson(response);
    }


    /**
     * 8.检测api版本是否存在
     * @param
     * @return
     */
    @Path("/test/apiisexist")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String existApi(@QueryParam("appId")  long appId,
                           @QueryParam("displayname")  String displayname) throws Exception {
        Response response = service.existApi(appId,displayname);
        return JsonUtil.toJson(response);
    }





    /**9.  　查询服务ｉｄ和名称
     * @param
     * @return
     **/
    @Path("/query/servicenameandid")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String queryServiceNameAndId() throws Exception {

        Response response = service.queryServiceNameAndId();
        return JsonUtil.toJson(response);
    }

    /**
     * 10.根据appid查询完整路径
     * @param
     * @return
     */
    @Path("/get/finalpath/{appId}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String queryFinalpath(@PathParam("appId")  String appId) throws Exception {
        Response response = service.queryFinalpath(appId);
        return JsonUtil.toJson(response);
    }


    /** 11.  　查询apid的ｉｄ和名称
     * @param
     * @return
     **/
    @Path("/query/apinameandid")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String queryApiNameAndId() throws Exception {

        Response response = service.queryApiNameAndId();
        return JsonUtil.toJson(response);
    }


    /**
     * 删除
     */


    /**
     * 12.删除版本
     * @param
     * @return
     */
    @Path("/delete/apiversion/{id}")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteApiVersion(@PathParam("id")String id) throws Exception {
        Response response = service.deleteApiVersion(id);
        return JsonUtil.toJson(response);
    }


    /**
     * 13..删除API
     * @param id  long  api的id
     * @return
     */
    @Path("/delete/api/{id}")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public  String deleteApiById(@PathParam("id") String id) throws Exception {
        Response response = service.deleteApiById(id);
        return JsonUtil.toJson(response);
    }


    /**
     * 编辑
     */


    /**
     * 14.修改版本
     * @param body
     * @return
     */
    @Path("/update/apiversion")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String updateApiVersion(String body) throws Exception {
        Response response = service.updateApiVersion(body);
        return JsonUtil.toJson(response);
    }



    /**
     * 15.修改api
     * @param body
     * @return
     */
    @Path("/update/api")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String updateApi(String body) throws Exception {
        Response response = service.updateApi(body);
        return JsonUtil.toJson(response);
    }




    /**
     * 未使用接口
     */

//
//
//    /**
//     * 4.API列表展示
//     * @param index          String    1      页码 （默认第一页）
//     * @param limit          String    10     每页记录数目（默认每页限制10条）
//     * @param sortBy         String          排序字段
//     * @param sortDirection  String    asc     排序方向asc:正序（默认）desc ：倒序
//     * @return
//     */
//    @Path("/get/apilist")
//    @GET
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public String queryApiList(@QueryParam("index") @DefaultValue("1") String index,
//                               @QueryParam("limit") @DefaultValue("10") String limit,
//                               @QueryParam("sortBy") @DefaultValue("") String sortBy,
//                               @QueryParam("sortDirection") @DefaultValue("asc") String sortDirection) throws Exception {
//
//        Response response=service.queryApiList((int) Double.parseDouble(index), (int) Double.parseDouble(limit), sortBy, sortDirection);
//
//        return JsonUtil.toJson(response);
//    }
//
//
//
//
//
//
//
//
//

//
//

//
//    /**
//     * 14.激活/停用版本
//     * @param body
//     * @return
//     */
//    @Path("/activation")
//    @POST
//    @Produces(MediaType.APPLICATION_JSON)
//    public String activationApiVersion(String body) throws Exception {
//        Response response = service.activationApiVersion(body);
//        return JsonUtil.toJson(response);
//    }


//     /* 15.导出版本
//     * @param userid
//     * @return
//     */
//    @Path("/export/{id}")
//    @GET
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public String exportApiDocumentation(@PathParam("id")long id) throws Exception {
//
//        Response response = service.exportApiDocumentation(id);
//        return JsonUtil.toJson(response);
//    }
//
//
//    /**
//     * 17.修改doc
//     * @param body
//     * @return
//     */
//    @Path("/update/doc")
//    @POST
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public String updateApiDoc(String body) throws Exception {
//        Response response = service.updateApiDoc(body);
//        return JsonUtil.toJson(response);
//    }
//    /**
//     * 18.修改media
//     * @param body
//     * @return
//     */
//    @Path("/update/media")
//    @POST
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public String updateApiMedia(String body) throws Exception {
//        Response response = service.updateApiMedia(body);
//        return JsonUtil.toJson(response);
//    }
//
//    /**
//     * 19.修改get/delete 请求hender列表
//     * @param body
//     * @return
//     */
//    @Path("/update/hender")
//    @POST
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public String updateApiHender(String body) throws Exception {
//        Response response = service.updateApiHender(body);
//        return JsonUtil.toJson(response);
//    }
//
//
//    /**
//     * 20.修改get/delete 请求body列表
//     * @param body
//     * @return
//     */
//    @Path("/update/requestbody")
//    @POST
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public String updateApiRequestBody(String body) throws Exception {
//        Response response = service.updateApiRequestBody(body);
//        return JsonUtil.toJson(response);
//    }
//
//    /**
//     * 21.修改get/delete 请求body列表
//     * @param body
//     * @return
//     */
//    @Path("/update/response")
//    @POST
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public String updateApiResponse(String body) throws Exception {
//        Response response = service.updateApiResponse(body);
//        return JsonUtil.toJson(response);
//    }


}
