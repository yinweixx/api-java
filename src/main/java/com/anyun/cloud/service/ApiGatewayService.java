package com.anyun.cloud.service;

import com.anyun.cloud.common.sys.Response;

public interface ApiGatewayService {

    Response getPageList(int index, int limit,String sortBy,String sortDirection);

    Response getDetails(String name);

    Response updateElasticSetting(String body);

    Response queryElasticSetting();
}
