package com.anyun.cloud.api;


import com.anyun.cloud.common.context.ControllerIOC;
import com.anyun.cloud.common.json.JsonUtil;
import com.anyun.cloud.common.sys.Response;
import com.anyun.cloud.service.ApiGatewayService;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/gateway")
public class ApiGatewayApi {
    @Context
    private HttpServletRequest request;

    private ApiGatewayService apiGatewayService;


    public ApiGatewayApi(){
        apiGatewayService = ControllerIOC.getIOC().getInstance(ApiGatewayService.class);
    }


    /**
     * 查询网关运行列表
     * @return
     * @parm index          String    1      页码 （默认第一页）
     * @parm limit          String    10     每页记录数目（默认每页限制10条）
     * @parm sortBy         String          排序字段
     * @parm sortDirection  String    asc     排序方向asc:正序（默认）desc ：倒序
     */
    @Path("/pagelist")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getPageList(@QueryParam("index") @DefaultValue("1") String index,
                              @QueryParam("limit") @DefaultValue("10") String limit,
                              @QueryParam("sortBy") @DefaultValue("") String sortBy,
                              @QueryParam("sortDirection") @DefaultValue("asc") String sortDirection) {
        Response response = apiGatewayService.getPageList((int) Double.parseDouble(index),
                (int) Double.parseDouble(limit), sortBy, sortDirection);
        return JsonUtil.toJson(response);
    }

    /**
     * 根据网关名称查询网关运行详情
     *
     * @param name
     * @return
     */
    @Path("/details/{name}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getDetails(@PathParam("name")  String name) {
        Response response = apiGatewayService.getDetails(name);
        return JsonUtil.toJson(response);
    }

    /**
     * 查询弹性设置
     * @return
     */
    @Path("/elasticsetting/query")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String CreateElasticSetting(){
        Response response = apiGatewayService.queryElasticSetting();
        return JsonUtil.toJson(response);
    }

    /**
     * 更新弹性设置
     * @param body
     * @return
     */
    @Path("/elasticsetting/update")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String UpdateElasticSetting(String body){
        Response response = apiGatewayService.updateElasticSetting(body);
        return JsonUtil.toJson(response);
    }
}
