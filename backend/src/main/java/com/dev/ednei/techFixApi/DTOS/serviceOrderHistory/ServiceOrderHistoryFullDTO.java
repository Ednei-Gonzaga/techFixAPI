package com.dev.ednei.techFixApi.DTOS.serviceOrderHistory;

import com.dev.ednei.techFixApi.model.ServiceOrderHistory;

import java.time.LocalDateTime;

public record ServiceOrderHistoryFullDTO(
        Long id,
        Long serviceOrderId,
        Long userId,
        String oldStatus,
        String newStatus,
        String notes,
        LocalDateTime createdAt
) {
    public ServiceOrderHistoryFullDTO(ServiceOrderHistory history) {
        this(
                history.getId(),
                history.getServiceOrder() != null ? history.getServiceOrder().getId() : null,
                history.getUser() != null ? history.getUser().getId() : null,
                history.getOldStatus().name(),
                history.getNewStatus().name(),
                history.getNotes(),
                history.getCreatedAt()
        );
    }
}
