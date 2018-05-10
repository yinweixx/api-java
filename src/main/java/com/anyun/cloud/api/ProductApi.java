package com.anyun.cloud.api;

import com.anyun.cloud.common.context.ControllerIOC;
import com.anyun.cloud.common.json.JsonUtil;
import com.anyun.cloud.common.sys.Response;
import com.anyun.cloud.service.ProductService;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/products")
public class ProductApi {
    @Context
    private HttpServletRequest request;

    private ProductService service;

    public ProductApi() {
        service = ControllerIOC.getIOC().getInstance(ProductService.class);
    }

    /**
     * 根据id 查询产品详情
     *
     * @param id
     * @return
     */
    @Path("/details/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getDetails(@PathParam("id") int id) {
        Response response = service.getDetails(id);
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
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes(MediaType.APPLICATION_JSON)
    public String getPageList(@QueryParam("index") @DefaultValue("1") String index,
                              @QueryParam("limit") @DefaultValue("10") String limit,
                              @QueryParam("sortBy") @DefaultValue("") String sortBy,
                              @QueryParam("sortDirection") @DefaultValue("asc") String sortDirection) {
        Response response = service.getPageList((int) Double.parseDouble(index), (int) Double.parseDouble(limit), sortBy, sortDirection);
        return JsonUtil.toJson(response);
    }


    /**
     * 根据id 删除产品
     *
     * @param id
     * @return
     */
    @Path("/delete/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String delete(@PathParam("id") int id) {
        Response response = service.delete(id);
        return JsonUtil.toJson(response);
    }

    /**
     * 添加产品
     *
     * @param body
     * @return
     */
    @Path("/create/single")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String singleCreate(String body) {
        Response response = service.create(body);
        return JsonUtil.toJson(response);
    }


    /**
     * 批量添加产品
     *
     * @param body
     * @return
     */
    @Path("/create/batch")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String batchCreate(String body) {
        Response response = service.batchCreate(body);
        return JsonUtil.toJson(response);
    }

    /**
     * 更新单条
     *
     * @param body
     * @return
     */
    @Path("/update/single")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String singleUpdate(String body) {
        Response response = service.update(body);
        return JsonUtil.toJson(response);
    }

    /**
     * 批量更新
     *
     * @param body
     * @return
     */
    @Path("/update/batch")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String batchUpdate(String body) {
        Response response = service.batchUpdate(body);
        return JsonUtil.toJson(response);
    }

    /**
     * zip 文件上传
     */
    @Path("/upload/zip")
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String upload() {
        Response response = service.upload( request);
        return JsonUtil.toJson(response);
    }
}
