package com.anyun.cloud.api.base;

import com.anyun.cloud.common.context.ControllerIOC;
import com.anyun.cloud.common.json.JsonUtil;
import com.anyun.cloud.common.sys.Response;
import com.anyun.cloud.service.MiddlewareService;
import com.anyun.cloud.service.ProductService;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 * @author myb  mayanbin@proxzone.com
 * @version 1.0
 * @date 2018/1/23 15:08
 */
@Path("/middleware" )
public class MiddlewareApi {
    @Context
    private HttpServletRequest request;

    private MiddlewareService middlewareService;

    public MiddlewareApi() {
        middlewareService= ControllerIOC.getIOC().getInstance(MiddlewareService.class);
    }

    /**
     * 根据id 查询中间件详情
     *
     * @param id
     * @return
     */
    @Path("/details/{id}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getDetails(@PathParam("id") int id) {
        Response response = middlewareService.getDetails(id);
        return JsonUtil.toJson(response);
    }
    /**
     * 分页查询中间件服务列表
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
        Response response = middlewareService.getPageList((int) Double.parseDouble(index), (int) Double.parseDouble(limit), sortBy, sortDirection);
        return JsonUtil.toJson(response);
    }

    /**
     * 根据条件查询中间件信息
     * @param index
     * @param limit
     * @param sortBy
     * @param sortDirection
     * @param middlewareIp
     * @param middlewareName
     * @param middlewareState
     * @param middlewareType
     * @return
     */
    @Path("/query/condition")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String getBlockListByCondition(@QueryParam("index") @DefaultValue("1") String index,
                                          @QueryParam("limit") @DefaultValue("10") String limit,
                                          @QueryParam("sortBy") @DefaultValue("") String sortBy,
                                          @QueryParam("sortDirection") @DefaultValue("asc") String sortDirection,
                                          @QueryParam("middlewareIp")String  middlewareIp,
                                          @QueryParam("middlewareName") String middlewareName,
                                          @QueryParam("middlewareState") String  middlewareState,
                                          @QueryParam("middlewareType") String  middlewareType){
        Response response =middlewareService.getMiddlewareListByCondition(
                (int) Double.parseDouble(index), (int) Double.parseDouble(limit), sortBy,
                sortDirection,middlewareIp ,middlewareName,middlewareState,middlewareType);
        return JsonUtil.toJson(response);
    }

}
