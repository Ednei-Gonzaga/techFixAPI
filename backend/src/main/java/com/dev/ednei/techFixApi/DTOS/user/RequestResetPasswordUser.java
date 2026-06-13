package com.dev.ednei.techFixApi.DTOS.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RequestResetPasswordUser(
        @NotBlank
        String newPassword,

        String currentPassword,

        String codeVerification,

        @Email
        String email
) {
}
