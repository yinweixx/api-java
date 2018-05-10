package com.anyun.cloud.common.context;

import com.anyun.cloud.service.*;
import com.anyun.cloud.service.impl.*;
import com.google.inject.AbstractModule;

/**
 *  服务模块接口和实现绑定
 */
public class ServiceModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(RamlApiRamlParser.class).to(DefaultApiRamlParser.class);
        bind(ProductService.class).to(ProductServiceImpl.class);
        bind(ApiGatewayService.class).to(ApiGatewayServiceImpl.class);
        bind(DataCenterService.class).to(DataCenterServiceImpl.class);
        bind(IpBlockService.class).to(IpBlockServiceImpl.class);
        bind(IpPoolService.class).to(IpPoolServiceImpl.class);
        bind(MiddlewareService.class).to(MiddlewareServiceImpl.class);
        bind(ServiceManagementService.class).to(ServiceManagementServiceImpl.class);
        bind(ApiManagementService.class).to(ApiManagementServiceImpl.class);
        bind(HostManagementService.class).to(HostManagementServiceImpl.class);
        bind(AppBasicsService.class).to(AppBasicsServiceImpl.class);
        bind(IpUseService.class).to(IpUseServiceImpl.class);
        bind(TaskManagementService.class).to(TaskManagementServiceImpl.class);
        bind(VideoSourceService.class).to(VideoSourceServiceImpl.class);
        bind(IndexFunctionService.class).to(IndexFunctionServiceImpl.class);
        bind(ElasticSearchService.class).to(ElasticSearchServiceImpl.class);
    }
}
