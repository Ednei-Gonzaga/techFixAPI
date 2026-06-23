package com.dev.ednei.techFixApi.DTOS.serviceOrderItem;

import jakarta.validation.constraints.NotNull;

public record RequestQuantityServiceOrderItem(
        @NotNull
        Integer quantity
) {
}
