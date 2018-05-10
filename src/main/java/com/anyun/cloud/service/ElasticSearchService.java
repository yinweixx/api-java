package com.anyun.cloud.service;

import com.anyun.cloud.common.sys.Response;

import java.util.Date;

public interface ElasticSearchService {


    Response queryLogByCondition(int index, int limit, String sortBy, String sortDirection, String id, String host, String source, String startTimestamp, String endTimestamp,String message);

    Response getDetails(String id);
}
