package com.dev.ednei.techFixApi.DTOS.user;

import jakarta.validation.constraints.NotNull;

public record UserUpdateDTO(
        @NotNull
        Long id,

        String name,

        String login,

        String password
) {
}
