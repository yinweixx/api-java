package com.anyun.cloud.common.elasticsearch;

import com.alibaba.druid.pool.ElasticSearchDruidDataSourceFactory;
import com.anyun.cloud.common.exception.ElasticsearchException;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author Shen Xutao
 * @project api-management
 * @email 314255358@qq.com
 * @date 2018/3/26 14:37 星期一
 */
public class DefaultElasticsearch implements Elasticsearch {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultElasticsearch.class);
    private Connection connection;
    private ElasticsearchConifg elasticsearchConifg;


    @Inject
    public DefaultElasticsearch(ElasticsearchConifg elasticsearchConifg) {
        this.elasticsearchConifg = elasticsearchConifg;
    }

    @Override
    public Connection getConnection() throws ElasticsearchException {
        String url = elasticsearchConifg.getUrl();
        String username = elasticsearchConifg.getUsername();
        String password = elasticsearchConifg.getPassword();
        Properties properties = new Properties();
        properties.put("url", url);
        properties.put("username", username);
        properties.put("password", password);
        LOGGER.debug("Elasticsearch  url :[{}]  " + "\t" + "username:[{}]" + "\t" + " password:[{}]", url, username, password);
        DataSource dds = null;
        try {
            dds = ElasticSearchDruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            connection = dds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.debug("Elasticsearch Connection fail" + e.getMessage());
        }
        LOGGER.debug("Elasticsearch Connection connection:[{}]", connection);
        return connection;
    }
}
