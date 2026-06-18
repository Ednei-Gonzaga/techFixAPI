package com.dev.ednei.techFixApi.DTOS.serviceRequest;

import com.dev.ednei.techFixApi.model.ServiceRequests;

import java.time.LocalDateTime;

public record ServiceRequestFullDTO(
        Long id,
        String device,
        String category,
        String problemDescription,
        LocalDateTime createdAt,
        Long client
) {
    public ServiceRequestFullDTO(ServiceRequests serviceRequests) {
        this(serviceRequests.getId(), serviceRequests.getDevice(), serviceRequests.getCategory().name(), serviceRequests.getProblemDescription(), serviceRequests.getCreatedAt() ,serviceRequests.getClient().getId());
    }
}
