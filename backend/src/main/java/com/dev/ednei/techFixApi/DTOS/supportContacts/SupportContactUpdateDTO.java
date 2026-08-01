package com.dev.ednei.techFixApi.DTOS.supportContacts;

import jakarta.validation.constraints.NotBlank;

public record SupportContactUpdateDTO(
        String type,

        String contact,

        String description
) {
}
