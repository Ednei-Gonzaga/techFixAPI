package com.dev.ednei.techFixApi.DTOS.serviceOrder;

import jakarta.validation.constraints.NotNull;

public record ServiceOrderUpdateDTO(
        @NotNull
        Long id,

        Long user,

        String status
) {
}
