package com.dev.ednei.techFixApi.DTOS.serviceCatolog;


import com.dev.ednei.techFixApi.model.ServiceCatalog;

public record ServiceCatalogFullDTO(
        Long id,
        String name,
        Double costPrice,
        boolean status
        ) {
    public ServiceCatalogFullDTO(ServiceCatalog serviceCatalog) {
        this(serviceCatalog.getId(), serviceCatalog.getName(), serviceCatalog.getCostPrice(), serviceCatalog.isStatus());
    }
}
