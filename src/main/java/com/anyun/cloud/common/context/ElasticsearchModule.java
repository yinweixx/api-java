package com.anyun.cloud.common.context;

import com.anyun.cloud.common.elasticsearch.DefaultElasticsearch;
import com.anyun.cloud.common.elasticsearch.Elasticsearch;
import com.anyun.cloud.common.elasticsearch.ElasticsearchConifg;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;

/**
 * @author Shen Xutao
 * @project api-management
 * @email 314255358@qq.com
 * @date 2018/3/26 14:23 星期一
 */
public class ElasticsearchModule extends AbstractModule {
    private SystemEnvironment environment;

    @Inject
    public ElasticsearchModule(SystemEnvironment environment) {
        this.environment = environment;
    }

    @Override
    protected void configure() {
        ElasticsearchConifg elasticsearchConifg = new ElasticsearchConifg(environment);
        bind(ElasticsearchConifg.class).toInstance(elasticsearchConifg);
        bind(Elasticsearch.class).to(DefaultElasticsearch.class);
    }


}
