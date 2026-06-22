package com.dev.ednei.techFixApi.DTOS.serviceCatolog;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ServiceCatalogCreatedDTO(
        @NotBlank
        String name,

        @NotNull
        Double costPrice
) {
}
