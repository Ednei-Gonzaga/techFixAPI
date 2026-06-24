package com.dev.ednei.techFixApi.DTOS.serviceOrderTask;

import com.dev.ednei.techFixApi.model.ServiceOrderTask;

public record ServiceOrderTaskFullDTO(
        Long id,
        Long serviceOrder,
        Long serviceCatalog,
        String nameCatalog,
        Double priceApplied
) {
    public ServiceOrderTaskFullDTO(ServiceOrderTask serviceOrderTask, String name) {
        this(serviceOrderTask.getId(), serviceOrderTask.getServiceOrder().getId(), serviceOrderTask.getServiceCatalog().getId(), name, serviceOrderTask.getPriceApplied());
    }

}
