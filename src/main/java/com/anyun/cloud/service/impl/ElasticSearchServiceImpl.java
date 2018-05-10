package com.anyun.cloud.service.impl;

import com.anyun.cloud.common.context.Context;
import com.anyun.cloud.common.elasticsearch.Elasticsearch;
import com.anyun.cloud.common.sys.Response;
import com.anyun.cloud.dao.ElasticSearchDao;
import com.anyun.cloud.model.ErrorCode;
import com.anyun.cloud.model.dto.ElasticSearchDto;
import com.anyun.cloud.model.dto.PageDto;
import com.anyun.cloud.service.ElasticSearchService;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Date;

public class ElasticSearchServiceImpl implements ElasticSearchService {
    private final static Logger LOGGER = LoggerFactory.getLogger(ElasticSearchServiceImpl.class);
    private ElasticSearchDao elasticSearchDao;
    private Context context;
    private Response response;
    private Elasticsearch elasticsearch;

    @Inject
    public ElasticSearchServiceImpl(Context context) {
        this.context = context;
        this.elasticSearchDao = context.getBeanByClass(ElasticSearchDao.class);
        this.elasticsearch=context.getElasticsearch();
    }

    @Override
    public Response queryLogByCondition(int index, int limit, String sortBy, String sortDirection, String id, String host, String source, String startTimestamp, String endTimestamp,String message) {
        try {
            response = new Response<PageDto<ElasticSearchDto>>();
            response.setCode(ErrorCode.NO_ERROR.code());
            response.setContent(elasticSearchDao.QueryLogByCondition(index,limit,sortBy,sortDirection,id,host,source,startTimestamp,endTimestamp,message,elasticsearch));
            return response;
        } catch (Exception e) {
            response = new Response<String>();
            response.setCode(ErrorCode.QUERY_RESOURCE_ERROR.code());
            response.setContent(ErrorCode.QUERY_RESOURCE_ERROR.name() + e.getMessage());
            return response;
        }
    }

    @Override
    public Response getDetails(String id) {
        //判断id是否为空
        LOGGER.debug("id:[{}]", id);
        if ("null".equals(String.valueOf(id))) {
            response = new Response<String>();
            response.setCode(ErrorCode.PARAMETER_ERROR.code());
            response.setContent(ErrorCode.PARAMETER_ERROR.name() + "id is empty");
        }
        try {
            response = new Response<ElasticSearchDto>();
            response.setCode(ErrorCode.NO_ERROR.code());
            response.setContent(elasticSearchDao.selectServiceDetail(id,elasticsearch));
            return response;
        } catch (Exception e) {
            response = new Response<String>();
            response.setCode(ErrorCode.QUERY_RESOURCE_ERROR.code());
            response.setContent(ErrorCode.QUERY_RESOURCE_ERROR.name() + e.getMessage());
            return response;
        }
    }
}
