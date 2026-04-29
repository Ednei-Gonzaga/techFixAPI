package com.dev.ednei.techFixApi.DTOS.serviceRequest;

import com.dev.ednei.techFixApi.model.ServiceRequest;

public record ServiceRequestFullDTO(
        Long id,

        String device,

        String category,

        String problemDescription,

        Long client
) {
    public ServiceRequestFullDTO(ServiceRequest serviceRequest) {
        this(serviceRequest.getId(), serviceRequest.getDevice(), serviceRequest.getCategory().name() , serviceRequest.getProblemDescription(), serviceRequest.getClient().getId() );
    }
}
