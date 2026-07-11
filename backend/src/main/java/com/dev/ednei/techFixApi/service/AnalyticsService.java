package com.dev.ednei.techFixApi.service;

import com.dev.ednei.techFixApi.DTOS.analytics.DashboardAlertsDTO;
import com.dev.ednei.techFixApi.DTOS.analytics.DashboardMetricsDTO;
import com.dev.ednei.techFixApi.DTOS.analytics.dataDashboardAlertsFormat.ServiceDemand;
import com.dev.ednei.techFixApi.DTOS.analytics.dataDashboardAlertsFormat.StockAlertsDTO;
import com.dev.ednei.techFixApi.infra.exceptions.errors.UnprocessableEntityException;
import com.dev.ednei.techFixApi.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
public class AnalyticsService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ServiceOrderItemRepository serviceOrderItemRepository;

    @Autowired
    private ServiceRequestRepository serviceRequestRepository;

    @Autowired
    private PartsRepository partsRepository;

    @Autowired
    private EmployeeRepository employeeRepository;


    public DashboardMetricsDTO findDashboardMetrics(LocalDate start, LocalDate end) {
        LocalDateTime dateStart;
        LocalDateTime dateEnd;

        if (start != null && end != null && start.isAfter(end)) {
            throw new UnprocessableEntityException("A data de início não pode ser posterior à data de fim.");
        }

        if (start == null && end == null) {

            dateStart = LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0, 0)).with(TemporalAdjusters.firstDayOfMonth());
            dateEnd = LocalDateTime.now();

        } else if (start != null && end == null) {

            dateStart = LocalDateTime.of(start, LocalTime.of(0, 0, 0));
            if (dateStart.isAfter(LocalDateTime.now())) {
                dateEnd = start.plusDays(30).atTime(LocalTime.MAX);
            } else {
                dateEnd = LocalDateTime.now();
            }

        } else if (end != null && start == null) {

            dateEnd = LocalDateTime.of(end, LocalTime.of(23, 59, 59, 999999999));
            dateStart = LocalDateTime.of(dateEnd.toLocalDate(), LocalTime.of(0, 0, 0)).minusDays(60);

        } else {

            dateStart = LocalDateTime.of(start, LocalTime.of(0, 0, 0));
            dateEnd = LocalDateTime.of(end, LocalTime.of(23, 59, 59, 999999999));

        }

        var dashboardSummary = paymentRepository.findDashboardSummaryRecord(dateStart, dateEnd);
        var categoryRevenues = paymentRepository.findDashboardCategoryRevenue(dateStart, dateEnd);
        var topFiveClients = clientRepository.topFiveClients(dateStart, dateEnd);
        var topCategories = serviceRequestRepository.topCategories(dateStart, dateEnd);
        var mostUsedPart = serviceOrderItemRepository.findMostUsedPart(dateStart, dateEnd);
        var leastUsedPart = serviceOrderItemRepository.findLeastUsedPart(dateStart, dateEnd);

        return new DashboardMetricsDTO(dashboardSummary, categoryRevenues, topCategories, topFiveClients, mostUsedPart, leastUsedPart);
    }

    public DashboardAlertsDTO findStockAlerts() {
        LocalDateTime previousStartDateTime = LocalDateTime.of(LocalDate.now().minusMonths(1).with(TemporalAdjusters.firstDayOfMonth()), LocalTime.MIN);
        LocalDateTime previousEndDateTime = LocalDateTime.of(LocalDate.now().minusMonths(1).with(TemporalAdjusters.lastDayOfMonth()), LocalTime.MAX);
        LocalDateTime dateTimeStartCurrent = LocalDateTime.of(LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()), LocalTime.MIN);
        LocalDateTime dateTimeEndCurrent = LocalDateTime.now();

        var stockInYellow = partsRepository.findLowStockAlertsYellow(previousStartDateTime, previousEndDateTime, dateTimeStartCurrent, dateTimeEndCurrent);
        var stockInRed = partsRepository.findLowStockAlertsRed(previousStartDateTime, previousEndDateTime, dateTimeStartCurrent, dateTimeEndCurrent);
        var technicianDemand  =employeeRepository.findServiceDemand();


        return new DashboardAlertsDTO(technicianDemand, stockInRed, stockInYellow);
    }
}
