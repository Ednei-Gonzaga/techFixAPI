package com.dev.ednei.techFixApi.DTOS.client;

import jakarta.validation.constraints.NotBlank;

public record ClientCpfRequest(
        @NotBlank
        String cpf
) {
}
