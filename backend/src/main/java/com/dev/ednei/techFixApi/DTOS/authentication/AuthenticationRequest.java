package com.dev.ednei.techFixApi.DTOS.authentication;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationRequest(
        @NotBlank
        String login,

        @NotBlank
        String password
) {
}
