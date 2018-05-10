package com.anyun.cloud.model.dto;

import com.anyun.cloud.common.tools.db.AbstractEntity;

public class ServiceEtedDto extends AbstractEntity{
    public ServiceConditionDto serviceConditionDto;
    public ServiceCatalogDto serviceCatalogDto;

    public ServiceConditionDto getServiceConditionDto() {
        return serviceConditionDto;
    }

    public void setServiceConditionDto(ServiceConditionDto serviceConditionDto) {
        this.serviceConditionDto = serviceConditionDto;
    }

    public ServiceCatalogDto getServiceCatalogDto() {
        return serviceCatalogDto;
    }

    public void setServiceCatalogDto(ServiceCatalogDto serviceCatalogDto) {
        this.serviceCatalogDto = serviceCatalogDto;
    }
}
