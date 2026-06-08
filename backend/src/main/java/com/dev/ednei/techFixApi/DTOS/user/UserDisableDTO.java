package com.dev.ednei.techFixApi.DTOS.user;

import jakarta.validation.constraints.NotNull;

public record UserDisableDTO(
        @NotNull
        Long id
) {
}
