package com.dev.ednei.techFixApi.DTOS.analytics.dataInDashboardFormat;

public record DashboardSummaryRecord(
        Double totalRevenue,
        Double realRevenue,
        Double futureTotalRevenue,
        Double futureRealRevenue,
        Double partsExpense,
        Double averageTicket
) {
    public DashboardSummaryRecord {
        totalRevenue = totalRevenue == null ? 0.0 : totalRevenue;
        realRevenue = realRevenue == null ? 0.0 : realRevenue;
        futureTotalRevenue = futureTotalRevenue == null ? 0.0 : futureTotalRevenue;
        futureRealRevenue = futureRealRevenue == null ? 0.0 : futureRealRevenue;
        partsExpense = partsExpense == null ? 0.0 : partsExpense;
        averageTicket = averageTicket == null ? 0.0 : averageTicket;
    }
}
