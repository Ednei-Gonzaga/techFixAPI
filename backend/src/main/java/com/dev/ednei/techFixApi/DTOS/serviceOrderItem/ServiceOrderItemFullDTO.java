package com.dev.ednei.techFixApi.DTOS.serviceOrderItem;

import com.dev.ednei.techFixApi.model.ServiceOrderItem;

public record ServiceOrderItemFullDTO(
        Long id,
        Long serviceOrder,
        Long part,
        String namePart,
        Integer quantity,
        Double unitPrice,
        Double subTotal
) {
    public ServiceOrderItemFullDTO(ServiceOrderItem serviceOrderItem) {
        this(serviceOrderItem.getId(), serviceOrderItem.getServiceOrder().getId(), serviceOrderItem.getPart().getId(), serviceOrderItem.getNamePart(), serviceOrderItem.getQuantity(), serviceOrderItem.getUnitPrice(), serviceOrderItem.getSubTotal());
    }
}
