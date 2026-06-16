package com.dev.ednei.techFixApi.DTOS.part;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PartUpdateDTO(
        String name,

        Double costPrice,

        Integer stockQuantity
) {
}
