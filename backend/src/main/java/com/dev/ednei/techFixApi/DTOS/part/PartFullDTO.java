package com.dev.ednei.techFixApi.DTOS.part;

import com.dev.ednei.techFixApi.model.Parts;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PartFullDTO(
        Long id,

        String name,

        Double costPrice,

        Integer stockQuantity,

        boolean status,

        String codeSku
) {
    public PartFullDTO(Parts part) {
        this(part.getId(), part.getName(), part.getCostPrice(), part.getStockQuantity(), part.isStatus(), part.getCodeSku());
    }
}
