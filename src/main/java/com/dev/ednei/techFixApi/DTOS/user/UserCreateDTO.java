package com.dev.ednei.techFixApi.DTOS.user;

import jakarta.validation.constraints.NotBlank;

public record UserCreateDTO(
        @NotBlank
        String name,

        @NotBlank
        String login,

        @NotBlank
        String password,

        @NotBlank
        String role
) {
}
