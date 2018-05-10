package com.anyun.cloud.service;

import com.anyun.cloud.common.sys.Response;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by jt on 18-1-17.
 */
public interface ApiManagementService {
    Response createApi(String body);
    Response  deleteApiById(String id) ;
    Response queryApiList(int index, int limit, String sortBy, String sortDirection) ;
    Response queryApi(long id) ;
    Response updateApi(String body);
    Response createApiVersion(String body)   ;
    Response updateApiVersion(String body) ;
    Response queryApiVersionById(long Id) ;
    Response queryApiListByCondition(int index, int limit, String sortBy, String sortDirection,String displayName , String baseUrl,String description,String method ,String appName);
    Response deleteApiVersion(String id) ;
    Response upload(HttpServletRequest request);
    Response queryServiceNameAndId();
    Response queryApiNameAndId();
    Response existApiVersion (String baseUrl,String appId,String apiVersionName,String path);
    Response existApi(long appId,String diaplayname);
    Response queryFinalpath(String appId);
    Response queryApiVersionListByCondition(int index,int limit,String sortBy,
    String sortDirection, String displayName,String appName);
}
