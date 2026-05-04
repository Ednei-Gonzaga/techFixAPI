package com.dev.ednei.techFixApi.DTOS.serviceOrder;

import com.dev.ednei.techFixApi.model.ServiceOrder;

import java.time.LocalDateTime;

public record ServiceOrderForClientDTO(
        String status,

        String identificationCode,

        LocalDateTime dateTimeStart,

        LocalDateTime dateTimeCompleted,

        LocalDateTime dateTimeUpdateStatus
) {
    public ServiceOrderForClientDTO(ServiceOrder serviceOrder){
        this(serviceOrder.getStatus().name(), serviceOrder.getIdentificationCode(), serviceOrder.getDateTimeStart(), serviceOrder.getDateTimeCompleted(), serviceOrder.getDateTimeUpdateStatus());
    }
}
