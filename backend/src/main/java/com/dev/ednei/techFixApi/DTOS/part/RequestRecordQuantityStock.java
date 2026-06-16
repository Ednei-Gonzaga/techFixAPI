package com.dev.ednei.techFixApi.DTOS.part;

import jakarta.validation.constraints.NotNull;

public record RequestRecordQuantityStock(
        @NotNull
        Integer quantityUsed
) {
}
