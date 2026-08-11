package com.dev.ednei.techFixApi.DTOS.paymentHistory;

import com.dev.ednei.techFixApi.model.PaymentsHistory;

import java.time.LocalDateTime;

public record PaymentsHistoryFullDTO(
        Long id,
        Long paymentId,
        Long userId,
        String oldStatus,
        String newStatus,
        Double transactionAmount,
        String notes,
        LocalDateTime createdAt
) {
    public PaymentsHistoryFullDTO(PaymentsHistory history) {
        this(
                history.getId(),
                history.getPayment() != null ? history.getPayment().getId() : null,
                history.getUser() != null ? history.getUser().getId() : null,
                history.getOldStatus().name(),
                history.getNewStatus().name(),
                history.getTransactionAmount(),
                history.getNotes(),
                history.getCreatedAt()
        );
    }
}
