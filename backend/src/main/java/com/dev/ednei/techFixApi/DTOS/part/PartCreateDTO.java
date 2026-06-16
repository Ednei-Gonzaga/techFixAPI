package com.dev.ednei.techFixApi.DTOS.part;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PartCreateDTO (
        @NotBlank
        String name,

        @NotNull
        Double costPrice,

        @NotNull
        Integer stockQuantity
){
}
