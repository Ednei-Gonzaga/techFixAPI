package com.dev.ednei.techFixApi.DTOS.analytics.dataDashboardAlertsFormat;

public record StockAlertsDTO(
        Long id,
        String name,
        Integer stockQuantity,
        Long currentMonthConsumption,
        Long PreviousMonthConsumption
) {
}
