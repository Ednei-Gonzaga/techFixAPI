package com.dev.ednei.techFixApi.DTOS.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserCreateDTO(
        @NotBlank
        String name,

        @NotBlank
        @Email
        String login,

        @NotBlank
        String password,

        @NotBlank
        String role
) {
}
