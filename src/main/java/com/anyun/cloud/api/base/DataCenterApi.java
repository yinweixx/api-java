package com.anyun.cloud.api.base;

import com.anyun.cloud.common.context.ControllerIOC;
import com.anyun.cloud.common.json.JsonUtil;
import com.anyun.cloud.common.sys.Response;
import com.anyun.cloud.service.DataCenterService;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 * @author myb  mayanbin@proxzone.com
 * @version 1.0
 * @date 2018/1/18 13:41
 */
@Path("/dataCenter")
public class DataCenterApi {
    @Context
    private HttpServletRequest request;
    private DataCenterService dataCenterService;

    public DataCenterApi() {
        dataCenterService = ControllerIOC.getIOC().getInstance(DataCenterService.class);
    }

    /**
     * 根据Id查询数据中心详情
     * @param id
     * @return
     */
    @Path("/details/{id}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getDetails(@PathParam("id") int id) {
        Response response = dataCenterService.getDetails(id);
        return JsonUtil.toJson(response);
    }

    /**
     * 分页查询
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
        Response response = dataCenterService.getPageList((int) Double.parseDouble(index), (int) Double.parseDouble(limit), sortBy, sortDirection);
        return JsonUtil.toJson(response);
    }

    /**
     * 根据Id删除数据中心
     * @param id
     * @return
     */
    @Path("/delete/{id}")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String delete(@PathParam("id") int id) {
        Response response = dataCenterService.delete(id);
        return JsonUtil.toJson(response);
    }

    /**
     * 添加数据中心
     *
     * @param body
     * @return
     */
    @Path("/create/single")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String singleCreate(String body) {
        Response response = dataCenterService.create(body);
        return JsonUtil.toJson(response);
    }

    /**
     * 更新数据中心
     *
     * @param body
     * @return
     */
    @Path("/update/single")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String singleUpdate(String body) {
        Response response = dataCenterService.update(body);
        return JsonUtil.toJson(response);
    }
    /**
     * 根据Id统计数据中心宿主机信息
     * @param id
     * @return
     */
    @Path("/hosts/details/{id}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getHostsDetails(@PathParam("id") int id) {
        Response response = dataCenterService.getHostsDetails(id);
        return JsonUtil.toJson(response);
    }

}

