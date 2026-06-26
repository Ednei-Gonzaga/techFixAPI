package com.dev.ednei.techFixApi.DTOS.payments;

import jakarta.validation.constraints.NotBlank;

public record PaymentsRequestStatus(
        @NotBlank
        String paymentStatus
) {
}
