package com.dev.ednei.techFixApi.DTOS.analytics;

import com.dev.ednei.techFixApi.DTOS.analytics.dataInDashboardFormat.DashboardSummaryRecord;
import com.dev.ednei.techFixApi.DTOS.analytics.dataInDashboardFormat.MostUsedPartFormat;
import com.dev.ednei.techFixApi.model.enums.CategoryDevice;

import java.util.List;

public record DashboardMetricsDTO(
    Double totalRevenue,
    Double realRevenue,
    Double futureTotalRevenue,
    Double futureRealRevenue,
    Double partsExpense,
    Double averageTicket,
    List<CategoryRevenueDTO> revenueByCategory,
    List<String> topCategories,
    List<TopCustomerDTO> topCustomers,
    List<MostUsedPartFormat> mostUsedParts,
    List<MostUsedPartFormat> leastUsedParts
) {
    public DashboardMetricsDTO(DashboardSummaryRecord dashboardSummary, List<CategoryRevenueDTO> categoryRevenues, List<String> topCategories, List<TopCustomerDTO> topFiveClients, List<MostUsedPartFormat> mostUsedPart, List<MostUsedPartFormat> leastUsedPart) {
        this(dashboardSummary.totalRevenue(), dashboardSummary.realRevenue(),dashboardSummary.futureTotalRevenue(),dashboardSummary.futureRealRevenue(),dashboardSummary.partsExpense()
        , dashboardSummary.averageTicket(), categoryRevenues, topCategories, topFiveClients, mostUsedPart,leastUsedPart);
    }

    public record CategoryRevenueDTO(
            CategoryDevice category,
            Double totalRevenue
    ) {
    }

    public record TopCustomerDTO(
            Long client,
            String name,
            Double totalSpent
    ) {}
}
