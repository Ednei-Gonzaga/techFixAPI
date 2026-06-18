package com.dev.ednei.techFixApi.DTOS.serviceRequest;

import com.dev.ednei.techFixApi.model.ServiceRequests;

public record ServiceRequestUpdateDTO(
        String device,
        String category,
        String problemDescription,
        Long client
) {
    public ServiceRequestUpdateDTO(ServiceRequests serviceRequests) {
        this(serviceRequests.getDevice(), serviceRequests.getCategory().name(), serviceRequests.getProblemDescription(), serviceRequests.getClient().getId());
    }
}
