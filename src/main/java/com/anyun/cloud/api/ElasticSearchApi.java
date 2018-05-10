package com.anyun.cloud.api;


import com.anyun.cloud.common.context.ControllerIOC;
import com.anyun.cloud.common.json.JsonUtil;
import com.anyun.cloud.common.sys.Response;
import com.anyun.cloud.service.ElasticSearchService;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.Date;

@Path("/elastic")
public class ElasticSearchApi {
    @Context
    private HttpServletRequest request;

    private ElasticSearchService service;

    public ElasticSearchApi() {
        service = ControllerIOC.getIOC().getInstance(ElasticSearchService.class);
    }


    /**
     * 1,根据条件查询日志
     * @return
     */
    @Path("/queryLogByCondition")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String queryByCondition(@QueryParam("index") @DefaultValue("1") String index,
                                   @QueryParam("limit") @DefaultValue("10") String limit,
                                   @QueryParam("sortBy") @DefaultValue("") String sortBy,
                                   @QueryParam("sortDirection") @DefaultValue("asc") String sortDirection,
                                   @QueryParam("id") String id,
                                   @QueryParam("host") String host,
                                   @QueryParam("source") String source,
                                   @QueryParam("message") String message,
                                   @QueryParam("startTimestamp") String startTimestamp,
                                   @QueryParam("endTimestamp") String endTimestamp){
        Response response = service.queryLogByCondition((int) Double.parseDouble(index), (int) Double.parseDouble(limit), sortBy,
                sortDirection,id,host,source,startTimestamp,endTimestamp,message);
        return JsonUtil.toJson(response);
    }


    @Path("/details")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String ElasticDetail(@QueryParam("id") String id) {
        Response response = service.getDetails(id);
        return JsonUtil.toJson(response);
    }
}
