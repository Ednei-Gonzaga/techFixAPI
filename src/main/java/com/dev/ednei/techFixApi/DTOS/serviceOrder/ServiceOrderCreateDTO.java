package com.dev.ednei.techFixApi.DTOS.serviceOrder;

import jakarta.validation.constraints.NotNull;


public record ServiceOrderCreateDTO(
        @NotNull
        Long serviceRequest
) {
}
