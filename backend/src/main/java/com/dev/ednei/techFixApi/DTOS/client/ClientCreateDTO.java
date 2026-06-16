package com.dev.ednei.techFixApi.DTOS.client;

import jakarta.validation.constraints.NotBlank;

public record ClientCreateDTO(
        @NotBlank
        String name,

        @NotBlank
        String cpf,

        String phone,

        @NotBlank
        String whatsapp
) {
}
