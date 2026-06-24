package com.dev.ednei.techFixApi.DTOS.serviceOrderTask;

import jakarta.validation.constraints.NotNull;

public record ServiceOrderTaskCreatedDTO(
        @NotNull
        Long serviceOrder,

        @NotNull
        Long serviceCatalog
) {
}
