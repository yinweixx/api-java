package com.anyun.cloud.api.base;

import com.anyun.cloud.common.context.ControllerIOC;
import com.anyun.cloud.common.json.JsonUtil;
import com.anyun.cloud.common.sys.Response;
import com.anyun.cloud.service.IpBlockService;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 * @author myb  mayanbin@proxzone.com
 * @version 1.0
 * @date 2018/1/22 10:40
 */
@Path("ip_block")
public class IpBlockApi {
    @Context
    private HttpServletRequest request;

    private IpBlockService ipBlockService;

    public IpBlockApi (){
        ipBlockService = ControllerIOC.getIOC().getInstance(IpBlockService.class);
    }

    /**
     * 根据id 查询ip段详情
     *
     * @param id
     * @return
     */
    @Path("/details/{id}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getDetails(@PathParam("id") int id) {
        Response response = ipBlockService.getDetails(id);
        return JsonUtil.toJson(response);
    }

    /**
     * 根据id 删除ip段
     *
     * @param id
     * @return
     */
    @Path("/delete/{id}")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String delete(@PathParam("id") int id) {
        Response response = ipBlockService.delete(id);
        return JsonUtil.toJson(response);
    }
    /**
     * 添加IP段
     * @param body
     * @return
     */
    @Path("/create/single")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String singleCreate(String body) {
        Response response = ipBlockService.create(body);
        return JsonUtil.toJson(response);
    }

    /**
     * 更新Ip段信息
     * @param body
     * @return
     */
    @Path("/update/single")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String singleUpdate(String body) {
        Response response = ipBlockService.update(body);
        return JsonUtil.toJson(response);
    }

    /**
     *分页列表
     * @param index
     * @param limit
     * @param sortBy
     * @param sortDirection
     * @return
     */
    @Path("/page")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getPageList(@QueryParam("index") @DefaultValue("1") String index,
                              @QueryParam("limit") @DefaultValue("10") String limit,
                              @QueryParam("sortBy") @DefaultValue("") String sortBy,
                              @QueryParam("sortDirection") @DefaultValue("asc") String sortDirection) {
        Response response = ipBlockService.getPageList((int) Double.parseDouble(index), (int) Double.parseDouble(limit), sortBy, sortDirection);
        return JsonUtil.toJson(response);
    }

    /**
     *根据I池Id查询IP段
     * @param index
     * @param limit
     * @param sortBy
     * @param sortDirection
     * @param poolId
     * @returnP
     */
     @Path("/query")
     @GET
     @Produces(MediaType.APPLICATION_JSON)
     @Consumes(MediaType.APPLICATION_JSON)
     public String getAppListByCondition(@QueryParam("index") @DefaultValue("1") String index,
                                         @QueryParam("limit") @DefaultValue("10") String limit,
                                         @QueryParam("sortBy") @DefaultValue("") String sortBy,
                                         @QueryParam("sortDirection") @DefaultValue("asc") String sortDirection,
                                         @QueryParam("poolId") int poolId){
         Response response =ipBlockService.queryByPoolId(
                 (int) Double.parseDouble(index), (int) Double.parseDouble(limit), sortBy,
                 sortDirection,poolId);
         return JsonUtil.toJson(response);
     }

    /**
     *根据条件查询Ip段
     * @param index
     * @param limit
     * @param sortBy
     * @param sortDirection
     * @param poolId
     * @param blockStartIp
     * @param blockEndIp
     * @param blockGateway
     * @param blockNetMask
     * @param blockCategory
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
                                         @QueryParam("poolId")int poolId,
                                         @QueryParam("startIp") String blockStartIp,
                                         @QueryParam("endIp") String  blockEndIp,
                                         @QueryParam("gateway") String  blockGateway,
                                         @QueryParam("netMask") String blockNetMask,
                                         @QueryParam("category")String blockCategory){
        Response response =ipBlockService.getBlockListByCondition(
                (int) Double.parseDouble(index), (int) Double.parseDouble(limit), sortBy,
                sortDirection,poolId ,blockStartIp,blockEndIp,blockGateway,blockNetMask,blockCategory);
        return JsonUtil.toJson(response);
    }


}
