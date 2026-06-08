package com.dev.ednei.techFixApi.DTOS.serviceOrder;

import com.dev.ednei.techFixApi.model.ServiceOrder;

import java.time.LocalDateTime;

public record ServiceOrderFullDTO(
        Long id,

        Long serviceRequest,

        Long user,

        String status,

        String identificationCode,

        LocalDateTime dateTimeStart,

        LocalDateTime dateTimeCompleted,

        LocalDateTime dateTimeUpdateStatus
) {
    public ServiceOrderFullDTO(ServiceOrder serviceOrder) {
        this(
                serviceOrder.getId(),
                serviceOrder.getServiceRequest().getId(),
                serviceOrder.getUser() != null ? serviceOrder.getUser().getId() : null,
                serviceOrder.getStatus().portugueseOption,
                serviceOrder.getIdentificationCode(),
                serviceOrder.getDateTimeStart() ,
                serviceOrder.getDateTimeCompleted(),
                serviceOrder.getDateTimeUpdateStatus()
        );
    }
}
