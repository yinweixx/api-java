package com.anyun.cloud.api.base;

import com.anyun.cloud.common.context.ControllerIOC;
import com.anyun.cloud.common.json.JsonUtil;
import com.anyun.cloud.common.sys.Response;
import com.anyun.cloud.service.HostManagementService;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 * @author myb  mayanbin@proxzone.com
 * @version 1.0
 * @date 2018/1/29 18:37
 */

@Path("hosts")
public class HostApi  {

    @Context
    private HttpServletRequest request;

    private HostManagementService service ;

    public HostApi (){
        service = ControllerIOC.getIOC().getInstance(HostManagementService.class);
    }

    /**
     * 查询未注册的宿主机
     * @return
     */
    @Path("/unregistered")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getUnregisteredHosts() {
        Response response = service.getUnregisteredHosts();
        return JsonUtil.toJson(response);
    }

    /**
     * 注册宿主机
     * @return
     */
    @Path("/register/{ip}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String registeredHosts(@PathParam("ip") String ip) {
        Response response = service.registeredHosts(ip);
        return JsonUtil.toJson(response);
    }

    /**
     * 根据id 查询已注册宿主机基础信息
     *
     * @param id
     * @return
     */
    @Path("/details/{id}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String get(@PathParam("id") int id) {
        Response response = service.getRegisteredHosts(id);
        return JsonUtil.toJson(response);
    }
    /**
     * 查询宿主机lxd信息
     * @param  id
     * @return
     */
    @Path("/lxd/details/{id}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getHostLxdDetails(@PathParam("id") int id) {
        Response response = service.getHostLxdDetails(id);
        return JsonUtil.toJson(response);
    }
    /**
     * 查询宿主机docker信息
     * @param  id
     * @return
     */
    @Path("/docker/details/{id}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getHostDockerDetails(@PathParam("id") int id) {
        Response response = service.getHostDockerDetails(id);
        return JsonUtil.toJson(response);
    }

    /**
     * 查询宿主机软件信息
     * @param  id
     * @return
     */
    @Path("/software/details/{id}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getHostSoftwareDetails(@PathParam("id") int id) {
        Response response = service.getHostSoftwareDetails(id);
        return JsonUtil.toJson(response);
    }

    /**
     * 查询宿主机硬件信息
     * @param  id
     * @return
     */

    @Path("/hardware/details/{id}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getHostHardwareDetails(@PathParam("id") int id) {
        Response response = service.getHostHardwareDetails(id);
       return JsonUtil.toJson(response);
    }

    /**
     * 分页查询注册宿主机列表
     *
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
     * 根据id注销宿主机
     *
     * @param id
     * @return
     */
    @Path("/delete/{id}")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String delete(@PathParam("id") int id) {
        Response response = service.cancel(id);
        return JsonUtil.toJson(response);
    }

    /**
     * 根据条件模糊查询宿主机
     * @param index
     * @param limit
     * @param sortBy
     * @param sortDirection
     * @param ip
     * @return
     */
    @Path("/list/condition")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getHostsList(@QueryParam("index") @DefaultValue("1") String index,
                              @QueryParam("limit") @DefaultValue("10") String limit,
                              @QueryParam("sortBy") @DefaultValue("") String sortBy,
                              @QueryParam("sortDirection") @DefaultValue("asc") String sortDirection,
                               @QueryParam("ip") String ip ,
                               @QueryParam("environment") String environment,
                               @QueryParam("category")String category,
                               @QueryParam("centerName")String centerName) {
        Response response = service.getHostsListByCondition((int) Double.parseDouble(index), (int) Double.parseDouble(limit), sortBy, sortDirection,ip,environment,category,centerName);
        return JsonUtil.toJson(response);
    }


    @Path("/query/center")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getHostsByCenterId(@QueryParam("centerId") @DefaultValue("0")int centerId) {
        Response response = service.getHostsByCenterName(centerId);
        return JsonUtil.toJson(response);
    }
    /**
     *
     * @param index
     * @param limit
     * @return
     */
    @Path("/unregistered/paging")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getUnregisteredHostsList(@QueryParam("index") @DefaultValue("1") int  index,
                                           @QueryParam("limit") @DefaultValue("10")int  limit) {
        Response response = service.getUnregisteredHostsList(index,limit);
        return JsonUtil.toJson(response);
    }

}
