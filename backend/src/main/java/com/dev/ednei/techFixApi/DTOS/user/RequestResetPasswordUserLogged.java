package com.dev.ednei.techFixApi.DTOS.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record RequestResetPasswordUserLogged(
        @NotBlank
        @Pattern(regexp = "^(?=.*[A-Z])(?=.*[0-9]).{8,16}$",
                message = "A senha deve ter entre 8 e 16 caracteres, contendo pelo menos uma letra maiúscula e um número.")
        String newPassword,

        @NotBlank
        String currentPassword
) {
}
