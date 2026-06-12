package com.dev.ednei.techFixApi.DTOS.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserCreateDTO(
        @NotBlank
        String role,

        @NotBlank
        String name,

        @NotBlank
        String cpf,

        @NotBlank
        String phone,

        @NotBlank
        String whatsapp,

        @NotBlank
        @Email(message = "Email deve ter um formato valido")
        String email
) {
}
