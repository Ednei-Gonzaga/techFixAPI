package com.dev.ednei.techFixApi.DTOS.serviceOrder;

import com.dev.ednei.techFixApi.model.enums.CategoryDevice;
import com.dev.ednei.techFixApi.model.enums.ServiceOrderStatus;

import java.time.LocalDateTime;

public record ServiceOrderDetailDTO(
        Long id,
        String nameClient,
        String cpfClient,
        String phone,
        String whatsapp,
        String nameEmployee,
        String device,
        CategoryDevice category,
        String problemDescription,
        String identificationCode,
        ServiceOrderStatus status,
        LocalDateTime dateTimeStart,
        LocalDateTime dateTimeCompleted,
        LocalDateTime dateTimeUpdateStatus,
        Long userTechnical,
        Long serviceRequest
) {

}
