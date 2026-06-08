package com.dev.ednei.techFixApi.DTOS.serviceRequest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ServiceRequestCreateDTO(
        @NotBlank
        String device,

        @NotBlank
        String category,

        @NotBlank
        String problemDescription,

        @NotNull
        Long client

) {
}
