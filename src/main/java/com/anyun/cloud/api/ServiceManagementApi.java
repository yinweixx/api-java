package com.anyun.cloud.api;


import com.anyun.cloud.common.context.ControllerIOC;
import com.anyun.cloud.common.json.JsonUtil;
import com.anyun.cloud.common.sys.Response;
import com.anyun.cloud.service.ServiceManagementService;
import com.google.inject.Inject;
import org.apache.ibatis.annotations.Update;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Context;
import java.io.File;
import java.net.URL;

@Path("/service")
public class ServiceManagementApi {

    @Context
    private HttpServletRequest request;

    private ServiceManagementService service;

    public ServiceManagementApi() {
        service = ControllerIOC.getIOC().getInstance(ServiceManagementService.class);
    }

    /**
     * 1,更新单条服务
     *
     * @param body
     * @return
     */
    @Path("/update/service")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String updateService(String body) {
        Response response = service.update(body);
        return JsonUtil.toJson(response);
    }


    /**
     * 2.查询服务详情
     * @param id
     * @param user
     * @return
     */
    @Path("/details")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String ServiceDetail(@QueryParam("id") String id,
                                @QueryParam("user") String user) {
        Response response = service.getDetails(id,user);
        return JsonUtil.toJson(response);
    }

    /**
     * 3,根据id删除服务（无此功能）
     *
     * @param id
     * @return
     */
    @Path("/delete/{id}")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String delete(@PathParam("id") String id) {
        Response response = service.delete(id);
        return JsonUtil.toJson(response);
    }


    /**
     * 4,服务目录
     *
     * @param index
     * @param limit
     * @param sortBy
     * @param sortDirection
     * @return
     * @parm index          String    1      页码 （默认第一页）
     * @parm limit          String    10     每页记录数目（默认每页限制10条）
     * @parm sortBy         String          排序字段
     * @parm sortDirection  String    asc     排序方向asc:正序（默认）desc ：倒序
     */
    @Path("/page")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getPageList(@QueryParam("index") @DefaultValue("1") String index,
                              @QueryParam("limit") @DefaultValue("10") String limit,
                              @QueryParam("sortBy") @DefaultValue("") String sortBy,
                              @QueryParam("sortDirection") @DefaultValue("asc") String sortDirection) {
        Response response = service.getPageList((int) Double.parseDouble(index), (int) Double.parseDouble(limit), sortBy, sortDirection);
        return JsonUtil.toJson(response);
    }

    /**
     * 5,服务列表
     *
     * @param index
     * @param limit
     * @param sortBy
     * @param sortDirection
     * @param user          String           用户
     * @return
     * @parm index          String    1      页码 （默认第一页）
     * @parm limit          String    10     每页记录数目（默认每页限制10条）
     * @parm sortBy         String          排序字段
     * @parm sortDirection  String    asc     排序方向asc:正序（默认）desc ：倒序
     */
    @Path("/page/user")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getPageListByUser(@QueryParam("index") @DefaultValue("1") String index,
                                    @QueryParam("limit") @DefaultValue("10") String limit,
                                    @QueryParam("sortBy") @DefaultValue("") String sortBy,
                                    @QueryParam("sortDirection") @DefaultValue("asc") String sortDirection,
                                    @QueryParam("user") String user) {
        Response response = service.getPageListByUser((int) Double.parseDouble(index), (int) Double.parseDouble(limit), sortBy, sortDirection, user);
        return JsonUtil.toJson(response);
    }

    /**
     * 6.检查出项目分支
     * @return
     */
    @Path("/getbranch")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getBranch(@QueryParam("remote") URL remote){
        Response response = service.getBranch(remote);
        return JsonUtil.toJson(response);
    }

    /**
     * 7.检查服务
     * @param
     * @return
     */
    @Path("/getservice")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getService(@QueryParam("remote") URL remote,
                                @QueryParam("branch") String branch){
        Response response = service.getService(remote,branch);
        return JsonUtil.toJson(response);
    }

    /**
     * 8.创建服务
     * @param body
     * @return
     */
    @Path("/create/project")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String createProject(String body){
        Response response = service.createProject(body);
        return JsonUtil.toJson(response);
    }


    /**
     * 9.服务回滚（更改状态）
     * @param body
     * @return
     */
    @Path("/rollback")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String rollback(String body){
        Response response = service.rollback(body);
        return JsonUtil.toJson(response);
    }

    /**
     * 10,根据条件查询服务
     * @param sname
     * @param bname
     * @param pname
     * @return
     */
    @Path("/queryByCondition")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String queryByCondition(@QueryParam("index") @DefaultValue("1") String index,
                                   @QueryParam("limit") @DefaultValue("10") String limit,
                                   @QueryParam("sortBy") @DefaultValue("") String sortBy,
                                   @QueryParam("sortDirection") @DefaultValue("asc") String sortDirection,
                                   @QueryParam("sname") String sname,
                                   @QueryParam("bname") String bname,
                                   @QueryParam("pname") String pname,
                                   @QueryParam("shortUrl") String shortUrl,
                                   @QueryParam("user") String user){
        Response response = service.queryByCondition((int) Double.parseDouble(index), (int) Double.parseDouble(limit), sortBy,
                sortDirection,sname,bname,pname,shortUrl,user);
        return JsonUtil.toJson(response);
    }

    /**
     * 查询服务目录
     * @param index
     * @param limit
     * @param sortBy
     * @param sortDirection
     * @param sname
     * @param pname
     * @param environmental
     * @return
     */
    @Path("/queryServiceCatalog")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String queryServiceCatalogByCondition(@QueryParam("index") @DefaultValue("1") String index,
                                                 @QueryParam("limit") @DefaultValue("10") String limit,
                                                 @QueryParam("sortBy") @DefaultValue("") String sortBy,
                                                 @QueryParam("sortDirection") @DefaultValue("asc") String sortDirection,
                                                 @QueryParam("sname") String sname,
                                                 @QueryParam("pname") String pname,
                                                 @QueryParam("environmental") String environmental){
        Response response = service.queryServiceCatalogByCondition((int) Double.parseDouble(index), (int) Double.parseDouble(limit), sortBy,
                sortDirection,sname,pname,environmental);
        return JsonUtil.toJson(response);

    }







    /**
     *11. 删除项目
     * @return
     */
    @Path("/delete/project")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteProject(@QueryParam("id") int id,
                                @QueryParam("type") String type,
                                @QueryParam("user") String user){
        Response response = service.deleteProject(id,type,user);
        return JsonUtil.toJson(response);
    }


    /**
     * 12,项目列表
     *
     * @param index
     * @param limit
     * @param sortBy
     * @param sortDirection
     * @param
     * @return
     * @parm index          String    1      页码 （默认第一页）
     * @parm limit          String    10     每页记录数目（默认每页限制10条）
     * @parm sortBy         String          排序字段
     * @parm sortDirection  String    asc     排序方向asc:正序（默认）desc ：倒序
     */
    @Path("/page/project")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getPageListProjectByUser(@QueryParam("index") @DefaultValue("1") String index,
                                           @QueryParam("limit") @DefaultValue("10") String limit,
                                           @QueryParam("sortBy") @DefaultValue("") String sortBy,
                                           @QueryParam("sortDirection") @DefaultValue("asc") String sortDirection){
        Response response = service.getPageListProject((int) Double.parseDouble(index), (int) Double.parseDouble(limit), sortBy, sortDirection);
        return JsonUtil.toJson(response);
    }


    /**
     * 13 服务回滚
     * @param body
     * @return
     */
    @Path("/rollbackService")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String rollbackService(String body){
        Response response = service.rollbackService(body);
        return JsonUtil.toJson(response);
    }


    /**
     * 14 更改状态（没用）
     * @return
     */
    @Path("/update/Private")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String updatePrivate(String body){
        Response response = service.updatePrivate(body);
        return JsonUtil.toJson(response);
    }


    /**
     * 15 根据项目id查询对应版本
     * @param id
     * @return
     */
    @Path("/queryBranch")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String queryBranchByProjectId(@QueryParam("id") int id){ //项目id
        Response response = service.queryBranchByProjectId(id);
        return JsonUtil.toJson(response);
    }

    /**
     * 16 根据项目id查询所有服务
     * @param id
     * @return
     */
    @Path("/queryService")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String queryServiceByProjectId(@QueryParam("id") int id,
                                           @QueryParam("type") String type ,
                                            @QueryParam("user") String user){ //项目id)
        Response response = service.queryServiceByProjectId(id,type,user);
        return JsonUtil.toJson(response);
    }


    /**
     * 17 按条件查询项目
     * @param index
     * @param limit
     * @param sortBy
     * @param sortDirection
     * @param pname
     * @param shortUrl
     * @param
     * @param
     * @return
     */
    @Path("/queryProjectByCondition")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String queryProjectByCondition(@QueryParam("index") @DefaultValue("1") String index,
                                   @QueryParam("limit") @DefaultValue("10") String limit,
                                   @QueryParam("sortBy") @DefaultValue("") String sortBy,
                                   @QueryParam("sortDirection") @DefaultValue("asc") String sortDirection,
                                   @QueryParam("pname") String pname,
                                   @QueryParam("shortUrl") String shortUrl,
                                   @QueryParam("type") String type
                                   ){
        Response response = service.queryProjectByCondition((int) Double.parseDouble(index), (int) Double.parseDouble(limit), sortBy,
                sortDirection,pname,shortUrl,type);
        return JsonUtil.toJson(response);
    }


    /**
     * 18 回滚中根据git地址和版本查询服务
     * @param gitUrl
     * @param branch
     * @return
     */
    @Path("/rollback/getservice")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String rollbackGetService(@QueryParam("gitUrl") String gitUrl,
                             @QueryParam("branch") String branch){
        Response response = service.rollbackGetService(gitUrl,branch);
        return JsonUtil.toJson(response);
    }

    /**
     * 更新项目
     * @param body
     * @return
     */
    @Path("/update/project")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String updateProject(String body) {
        Response response = service.updateProject(body);
        return JsonUtil.toJson(response);
    }


}
