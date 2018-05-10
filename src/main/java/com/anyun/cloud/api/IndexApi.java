package com.anyun.cloud.api;

import com.anyun.cloud.common.context.ControllerIOC;
import com.anyun.cloud.common.json.JsonUtil;
import com.anyun.cloud.common.sys.Response;
import com.anyun.cloud.model.dto.IndexFunctionDto;
import com.anyun.cloud.service.IndexFunctionService;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.List;


@Path("/index")
public class IndexApi {
    @Context
    private HttpServletRequest request;

    private IndexFunctionService indexFunctionService;

    public IndexApi(){
        indexFunctionService = ControllerIOC.getIOC().getInstance(IndexFunctionService.class);
    }

    /**
     * 查询运行情况
     *
     * @return
     */
    @Path("/function")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getFunctionSituation(@QueryParam("id") int id){
        Response response = indexFunctionService.getFunctionSituation(id);
        return JsonUtil.toJson(response);
    }

}
