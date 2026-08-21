package com.dev.ednei.techFixApi.DTOS.client;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

public record ClientCreateDTO(
        @NotBlank
        String name,

        @NotBlank
        @CPF
        String cpf,

        String phone,

        @NotBlank
        String whatsapp
) {
}
