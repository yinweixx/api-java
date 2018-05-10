package com.anyun.cloud.api;


import com.anyun.cloud.common.context.ControllerIOC;
import com.anyun.cloud.common.json.JsonUtil;
import com.anyun.cloud.common.sys.Response;
import com.anyun.cloud.service.VideoSourceService;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;


@Path("/videosource")
public class VideoSourceApi {
    @Context
    private HttpServletRequest request;

    private VideoSourceService videoSourceService;

    public VideoSourceApi(){
        videoSourceService = ControllerIOC.getIOC().getInstance(VideoSourceService.class);
    }

    /**
     * excel表格上传读取
     * @return
     */
    @Path("/upload/excel")
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String upload(){
        Response response = videoSourceService.upload(request);
        return JsonUtil.toJson(response);
    }

    /**
     * 查询视频源信息--分页
     * @param index
     * @param limit
     * @param sortBy
     * @param sortDirection
     * @return
     */
    @Path("/pagelist")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getPageList(@QueryParam("index") @DefaultValue("1") String index,
                              @QueryParam("limit") @DefaultValue("10") String limit,
                              @QueryParam("sortBy") @DefaultValue("") String sortBy,
                              @QueryParam("sortDirection") @DefaultValue("asc") String sortDirection) {
        Response response = videoSourceService.getPageList((int) Double.parseDouble(index), (int) Double.parseDouble(limit), sortBy, sortDirection);
        return JsonUtil.toJson(response);
    }

    /**
     * 一键删除所有视频数据
     * @return
     */
    @Path("/deleteall")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteAllVideo(){
        Response response = videoSourceService.deleteAllVideo();
        return JsonUtil.toJson(response);
    }

    /**
     * 根据条件查询视频--（带分页）
     * @param index
     * @param limit
     * @param sortBy
     * @param sortDirection
     * @param intersection
     * @param orientation
     * @param description
     * @param product
     * @param vender
     * @param dataCenter
     * @return
     */
    @Path("/queryVideoByCondition")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String queryVideoByCondition(@QueryParam("index") @DefaultValue("1") String index,
                                        @QueryParam("limit") @DefaultValue("10") String limit,
                                        @QueryParam("sortBy") @DefaultValue("") String sortBy,
                                        @QueryParam("sortDirection") @DefaultValue("asc") String sortDirection,
                                        @QueryParam("intersection") String intersection,
                                        @QueryParam("orientation") String orientation,
                                        @QueryParam("description") String description,
                                        @QueryParam("product") String product,
                                        @QueryParam("vender") String vender,
                                        @QueryParam("dataCenter") String dataCenter
                                        ){
        Response response = videoSourceService.queryVideoByCondition(
                (int) Double.parseDouble(index), (int) Double.parseDouble(limit), sortBy,
                sortDirection,intersection,orientation,description,product,vender,dataCenter);
        return JsonUtil.toJson(response);

    }

    /**
     * 插入单条视频记录
     * @param body
     * @return
     */
    @Path("/insertVideo")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String insertVideoSource(String body){
        Response response = videoSourceService.insertVideo(body);
        return JsonUtil.toJson(response);
    }

    /**
     * 更新视频数据
     * @param body
     * @return
     */
    @Path("/updateVideo")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String updateVideoSource(String body){
        Response response = videoSourceService.updateVideo(body);
        return JsonUtil.toJson(response);
    }
}
