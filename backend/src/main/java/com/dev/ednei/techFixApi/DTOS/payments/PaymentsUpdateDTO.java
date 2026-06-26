package com.dev.ednei.techFixApi.DTOS.payments;

public record PaymentsUpdateDTO(
        Double discount,
        String paymentMethod
) {
}
