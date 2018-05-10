package com.anyun.cloud.dao;

import com.anyun.cloud.common.elasticsearch.Elasticsearch;
import com.anyun.cloud.model.dto.ElasticSearchDto;
import com.anyun.cloud.model.dto.PageDto;

public interface ElasticSearchDao {

    PageDto<ElasticSearchDto> QueryLogByCondition(int index, int limit, String sortBy, String sortDirection, String id, String host, String source, String startTimestamp, String endTimestamp, String message, Elasticsearch elasticsearch);

    ElasticSearchDto selectServiceDetail(String id, Elasticsearch elasticsearch);
}
