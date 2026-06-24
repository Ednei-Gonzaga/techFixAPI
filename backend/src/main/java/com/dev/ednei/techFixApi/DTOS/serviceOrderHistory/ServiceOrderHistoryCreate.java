package com.dev.ednei.techFixApi.DTOS.serviceOrderHistory;

import com.dev.ednei.techFixApi.model.enums.ServiceOrderStatus;

public record ServiceOrderHistoryCreate(
        Long serviceOrder,
        Long user,
        String notes,
        ServiceOrderStatus oldStatus,
        ServiceOrderStatus newStatus
        ) {
}
