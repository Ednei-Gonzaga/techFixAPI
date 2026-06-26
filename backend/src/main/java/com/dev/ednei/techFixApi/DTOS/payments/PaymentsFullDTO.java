package com.dev.ednei.techFixApi.DTOS.payments;

import com.dev.ednei.techFixApi.model.Payment;
import com.dev.ednei.techFixApi.model.enums.PaymentMethod;

import java.time.LocalDateTime;

public record PaymentsFullDTO(
        Long id,
        Double laborAmount,
        Double partAmount,
        Double discount,
        Double totalAmounts,
        String paymentsMethod,
        String paymentStatus,
        LocalDateTime paidAt,
        Long ServiceOrder
) {
    public PaymentsFullDTO(Payment payment) {
        this(payment.getId(),payment.getLaborAmount(),payment.getPartsAmount(), payment.getDiscount(), payment.getTotalAmount(), payment.getPaymentMethod() == null ? null: payment.getPaymentMethod().name(),
                payment.getPaymentStatus().name(), payment.getPaidAt(), payment.getServiceOrder().getId());
    }
}
