package com.anyun.cloud.api;

import com.anyun.cloud.common.context.ControllerIOC;
import com.anyun.cloud.common.json.JsonUtil;
import com.anyun.cloud.common.sys.Response;
import com.anyun.cloud.service.TaskManagementService;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/task")
public class TaskManagementApi {
    @Context
    private HttpServletRequest request;

    private TaskManagementService service;

    public TaskManagementApi() {
        service = ControllerIOC.getIOC().getInstance(TaskManagementService.class);
    }

    /**
     * 1,更新任务
     *
     * @param body
     * @return
     */
    @Path("/update/task")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String updateService(String body) {
        Response response = service.update(body);
        return JsonUtil.toJson(response);
    }

    /**
     * 2,任务列表，条件查询
     * @return
     */
    @Path("/queryTaskByCondition")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String taskListByCondition(@QueryParam("index") @DefaultValue("1") String index,
                                      @QueryParam("limit") @DefaultValue("10") String limit,
                                      @QueryParam("sortBy") @DefaultValue("") String sortBy,
                                      @QueryParam("sortDirection") @DefaultValue("asc") String sortDirection,
                                      @QueryParam("name") String name,
                                      @QueryParam("shortUrl") String shortUrl,
                                      @QueryParam("branchName") String branchName,
                                      @QueryParam("projectName") String projectName){
        Response response = service.queryTaskByCondition((int) Double.parseDouble(index), (int) Double.parseDouble(limit), sortBy,
                sortDirection,name,shortUrl,branchName,projectName);
        return JsonUtil.toJson(response);

    }

    /**
     * 3.查询任务详情
     * @param id
     * @return
     */
    @Path("/detail")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String ServiceDetail(@QueryParam("id") String id) {
        Response response = service.getDetail(id);
        return JsonUtil.toJson(response);
    }

    @Path("/cronAnalysis")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String TaskCronAnalysis(String body) {
        Response response = service.TaskCronAnalysis(body);
        return JsonUtil.toJson(response);
    }



}
