package com.dev.ednei.techFixApi.DTOS.supportContacts;

import jakarta.validation.constraints.NotBlank;

public record SupportContactCreateDTO(
        @NotBlank
        String type,

        @NotBlank
        String contact,

        @NotBlank
        String description
) {
}
