package com.dev.ednei.techFixApi.DTOS.serviceOrderItem;

import jakarta.validation.constraints.NotNull;

public record ServiceOrderItemCreatedDTO(
        @NotNull
        Long serviceOrder,

        @NotNull
        Long part,

        @NotNull
        Integer quantity
) {
}
