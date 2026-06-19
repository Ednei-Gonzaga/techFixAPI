package com.dev.ednei.techFixApi.DTOS.serviceOrder;

import com.dev.ednei.techFixApi.model.ServiceOrder;

import java.time.LocalDateTime;

public record ServiceOrderFullDTO(
        Long id,
        String identificationCode,
        String status,
        LocalDateTime dateTimeStart,
        LocalDateTime dateTimeCompleted,
        LocalDateTime dateTimeUpdateStatus,
        Long serviceRequest,
        Long userTechnical
) {
    public ServiceOrderFullDTO(ServiceOrder order) {
        this(
                order.getId(),
                order.getIdentificationCode(),
                order.getStatus() != null ? order.getStatus().name() : null,
                order.getDateTimeStart(),
                order.getDateTimeCompleted(),
                order.getDateTimeUpdateStatus(),
                order.getServiceRequest() != null ? order.getServiceRequest().getId() : null,
                order.getUserTechnical() != null ? order.getUserTechnical().getId() : null
        );
}
}
