package com.anyun.cloud.api.base;


import com.anyun.cloud.common.context.ControllerIOC;
import com.anyun.cloud.common.json.JsonUtil;
import com.anyun.cloud.common.sys.Response;
import com.anyun.cloud.service.IpPoolService;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 * @author myb  mayanbin@proxzone.com
 * @version 1.0
 * @date 2018/1/19 10:33
 */
@Path("/ip_pool")
public class IpPoolApi {
    @Context
    private HttpServletRequest request;

    private IpPoolService ipPoolService;

    public IpPoolApi() { ipPoolService = ControllerIOC.getIOC().getInstance(IpPoolService.class);
    }

    /**
     * 根据id 查询Ip池信息
     *
     * @param id
     * @return
     */
    @Path("/details/{id}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getDetails(@PathParam("id") int id) {
        Response response = ipPoolService.getDetails(id);
        return JsonUtil.toJson(response);
    }
    /**
     * 删除单个Ip池
     *
     * @param
     * @return
     */
    @Path("/delete/{id}")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String delete(@PathParam("id") int id){
        Response response =ipPoolService.delete(id);
        return JsonUtil.toJson(response);
    }
    /**
     * 添加Ip池
     *
     *@param body
     * @return
     */
    @Path("/create/")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String create(String body){
        Response response=ipPoolService.create(body);
        return JsonUtil.toJson(response);

    }
    /**
     * 修改IP池
     * @param body
     * @return
     *
     */
    @Path("/update/")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String update(String body){
        Response response=ipPoolService.update(body);
        return JsonUtil.toJson(response);
    }
    /**
     * 分页查询Ip池
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
                              @QueryParam("sortDirection") @DefaultValue("asc") String sortDirection,
                               @QueryParam("centerId") @DefaultValue("1") int centerId){
         Response response = ipPoolService.getPageList((int) Double.parseDouble(index), (int) Double.parseDouble(limit), sortBy, sortDirection,centerId);
         return JsonUtil.toJson(response);
     }

    /**
     * 根据条件查询Ip池
     * @param index
     * @param limit
     * @param sortBy
     * @param sortDirection
     * @param poolName
     * @param environment
     * @param category
     * @return
     */
    @Path("/query/condition")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String getPoolListByCondition(@QueryParam("index") @DefaultValue("1") String index,
                                        @QueryParam("limit") @DefaultValue("10") String limit,
                                        @QueryParam("sortBy") @DefaultValue("") String sortBy,
                                        @QueryParam("sortDirection") @DefaultValue("asc") String sortDirection,
                                        @QueryParam("poolName") String poolName,
                                        @QueryParam("environment") String  environment,
                                        @QueryParam("category") String  category,
                                         @QueryParam("centerId") @DefaultValue("1") int centerId){
        Response response =ipPoolService.getPoolListByCondition(
                (int) Double.parseDouble(index), (int) Double.parseDouble(limit), sortBy,
                sortDirection,poolName,environment,category,centerId);
        return JsonUtil.toJson(response);
    }


}
