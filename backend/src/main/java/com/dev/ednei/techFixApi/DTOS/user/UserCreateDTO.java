package com.dev.ednei.techFixApi.DTOS.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

public record UserCreateDTO(
        @NotBlank
        String role,

        @NotBlank
        String name,

        @NotBlank
        @CPF(message = "CPF inválido ou formato incorreto.")
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
