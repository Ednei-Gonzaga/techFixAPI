package com.dev.ednei.techFixApi.DTOS.verificationCodes;

import jakarta.validation.constraints.NotBlank;

public record UserEmailResetPassword(
        @NotBlank
        String email
) {
}
