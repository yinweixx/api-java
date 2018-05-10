package com.anyun.cloud.common.elasticsearch;


import com.anyun.cloud.common.exception.ElasticsearchException;

import java.sql.Connection;

/**
 * @author Shen Xutao
 * @project api-management
 * @email 314255358@qq.com
 * @date 2018/3/26 14:36 星期一
 */
public interface Elasticsearch {
    /**
     *
     * @return
     */
    Connection getConnection() throws ElasticsearchException;





}
