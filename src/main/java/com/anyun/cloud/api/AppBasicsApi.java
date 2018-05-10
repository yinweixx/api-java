package com.anyun.cloud.api;


import com.anyun.cloud.common.context.ControllerIOC;
import com.anyun.cloud.common.json.JsonUtil;
import com.anyun.cloud.common.sys.Response;
import com.anyun.cloud.service.AppBasicsService;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/appbasics")
public class AppBasicsApi {
    @Context
    private HttpServletRequest request;

    private AppBasicsService appBasicsService;

    public AppBasicsApi(){
        appBasicsService = ControllerIOC.getIOC().getInstance(AppBasicsService.class);
    }


    /**
     * 创建应用
     * @param body
     * @return
     */
    @Path("/create")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String createAppBasics(String body){
        Response response = appBasicsService.create(body);
        return JsonUtil.toJson(response);
    }

    /**
     * 更新应用
     * @param body
     * @return
     */
    @Path("/update")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String updateAppBasics(String body){
        Response response = appBasicsService.update(body);
        return JsonUtil.toJson(response);
    }

    /**
     * 删除应用
     * @param id
     * @return
     */
    @Path("/delete/{id}")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteAppbasics(@PathParam("id") long id){
        Response response = appBasicsService.delete(id);
        return JsonUtil.toJson(response);
    }

    /**
     * 查询应用列表-(分页)
     * @param index
     * @param limit
     * @param sortBy
     * @param sortDirection
     * @return
     */
    @Path("/page")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String getPageList(@QueryParam("index") @DefaultValue("1") String index,
                              @QueryParam("limit") @DefaultValue("10") String limit,
                              @QueryParam("sortBy") @DefaultValue("") String sortBy,
                              @QueryParam("sortDirection") @DefaultValue("asc") String sortDirection) {
        Response response = appBasicsService.getPageList((int) Double.parseDouble(index), (int) Double.parseDouble(limit), sortBy, sortDirection);
        return JsonUtil.toJson(response);
    }

    /**
     * 根据名称查询应用--模糊查询
     * @param condition
     * @return
     */
    @Path("/vaguelist")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String getVagueList(@QueryParam("condition") String condition){
        Response response =appBasicsService.getVagueList(condition);
        return JsonUtil.toJson(response);
    }

    /**
     * 根据条件查询应用列表
     * @param name
     * @param shortName
     * @param startTime
     * @param endTime
     * @return
     */
    @Path("/queryAppByCondition")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String getAppListByCondition(@QueryParam("index") @DefaultValue("1") String index,
                                        @QueryParam("limit") @DefaultValue("10") String limit,
                                        @QueryParam("sortBy") @DefaultValue("") String sortBy,
                                        @QueryParam("sortDirection") @DefaultValue("asc") String sortDirection,
                                        @QueryParam("name") String name,
                                        @QueryParam("shortName") String shortName,
                                        @QueryParam("startTime") String  startTime,
                                        @QueryParam("endTime") String  endTime){
        Response response =appBasicsService.getAppListByCondition(
                (int) Double.parseDouble(index), (int) Double.parseDouble(limit), sortBy,
                sortDirection,name,shortName,startTime,endTime);
        return JsonUtil.toJson(response);
    }
}
