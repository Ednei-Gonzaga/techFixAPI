package com.dev.ednei.techFixApi.DTOS.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserLoginDTO(
        @NotBlank
        @Email(message = "Email esta escrito incorretamente.")
        String login,

        @NotBlank
        String password
) {
}
