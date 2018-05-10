package com.anyun.cloud.api.base;

import com.anyun.cloud.common.context.ControllerIOC;
import com.anyun.cloud.common.json.JsonUtil;
import com.anyun.cloud.common.sys.Response;
import com.anyun.cloud.service.IpUseService;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 * @author myb  mayanbin@proxzone.com
 * @version 1.0
 * @date 2018/2/1 18:55
 */
@Path("/ip_use")
public class IpUseApi {

    @Context
    private HttpServletRequest request;

    private IpUseService ipUseService;

    public IpUseApi() {
        ipUseService = ControllerIOC.getIOC().getInstance(IpUseService.class);
    }

    /**
     * 根据id 查询已使用Ip信息
     *
     * @param id
     * @return
     */
    @Path("/details/{id}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getDetails(@PathParam("id") int id) {
        Response response = ipUseService.getDetails(id);
        return JsonUtil.toJson(response);
    }

    /**
     * 获取分配Ip列表
     *
     * @return
     */
    @Path("/get/list")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getIpList(@QueryParam("count")int count, @QueryParam("blockId")int blockId,@QueryParam("des") String des) {
        Response response = ipUseService.getIpList(count, blockId,des);
        return JsonUtil.toJson(response);
    }
    /**
     * 获取Ip已使用信息
     *
     * @return
     * @parm index          String    1      页码 （默认第一页）
     * @parm limit          String    10     每页记录数目（默认每页限制10条）
     * @parm sortBy         String          排序字段
     * @parm sortDirection  String    asc     排序方向asc:正序（默认）desc ：倒序
     */
    @Path("/page/list")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getPageList(@QueryParam("index") @DefaultValue("1") String index,
                              @QueryParam("limit") @DefaultValue("10") String limit,
                              @QueryParam("sortBy") @DefaultValue("") String sortBy,
                              @QueryParam("sortDirection") @DefaultValue("asc") String sortDirection,
                              @QueryParam("blockId") int blockId){
        Response response =ipUseService.getPageList((int) Double.parseDouble(index), (int) Double.parseDouble(limit), sortBy,
                sortDirection,blockId);
        return JsonUtil.toJson(response);
    }
    /**
     * 根据id 解绑已占用IP
     *
     * @param id
     * @return
     */
    @Path("/unbundling/{id}")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String unBundling(@PathParam("id") int id) {
        Response response = ipUseService.delete(id);
        return JsonUtil.toJson(response);
    }


}