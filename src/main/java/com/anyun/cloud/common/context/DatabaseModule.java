package com.anyun.cloud.common.context;
import com.anyun.cloud.common.db.Database;
import com.anyun.cloud.common.db.DatabaseConfig;
import com.anyun.cloud.dao.*;
import com.anyun.cloud.dao.impl.*;
import com.google.inject.AbstractModule;

/**
 * 数据库模块接口和实现绑定
 */
public class DatabaseModule extends AbstractModule {
    private SystemEnvironment environment;

    public DatabaseModule(SystemEnvironment environment) {
        this.environment = environment;
    }

    @Override
    protected void configure() {
        DatabaseConfig databaseConfig = new DatabaseConfig(environment);
        Database database = new Database(databaseConfig);
        bind(DatabaseConfig.class).toInstance(databaseConfig);
        bind(Database.class).toInstance(database);
        bind(ProductDao.class).to(ProductDaoImpl.class);
        bind(ServiceManagementDao.class).to(ServiceManagementDaoImpl.class);
        bind(DataCenterDao.class).to(DataCenterDaoImpl.class);
        bind(IpPoolDao.class).to(IpPoolDaoImpl.class);
        bind(IpBlockDao.class).to(IpBlockDaoImpl.class);
        bind(MiddlewareDao.class).to(MiddlewareDaoImpl.class);
        bind(ApiManagementDao.class).to(ApiManagementDaoImpl.class);
        bind(ApiGatewayDao.class).to(ApiGatewayDaoImpl.class);
        bind(HostManagementDao.class).to(HostManagementDaoImpl.class);
        bind(AppBasicsDao.class).to(AppBasicsDaoImpl.class);
        bind(IpUseDao.class).to(IpUseDaoImpl.class);
        bind(TaskManagementDao.class).to(TaskManagementDaoImpl.class);
        bind(VideoSourceDao.class).to(VideoSourceDaoImpl.class);
        bind(IndexFunctionDao.class).to(IndexFunctionDaoImpl.class);
        bind(ElasticSearchDao.class).to(ElasticSearchDaoImpl.class);
    }
}
