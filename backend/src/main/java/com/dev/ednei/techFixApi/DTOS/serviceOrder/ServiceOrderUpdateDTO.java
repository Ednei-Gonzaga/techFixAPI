package com.dev.ednei.techFixApi.DTOS.serviceOrder;

import com.dev.ednei.techFixApi.model.ServiceOrder;

public record ServiceOrderUpdateDTO(
        Long userTechnical,
        String status
) {
}
