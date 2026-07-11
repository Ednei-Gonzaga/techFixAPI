package com.dev.ednei.techFixApi.controller;

import com.dev.ednei.techFixApi.repository.ServiceOrderItemRepository;
import com.dev.ednei.techFixApi.repository.ServiceOrderRepository;
import com.dev.ednei.techFixApi.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v2")
public class AnalyticsController {
    @Autowired
    private AnalyticsService analyticsService;

    @GetMapping("/dashboard/metrics")
    public ResponseEntity findDashboardSummaryRecord(@RequestParam(required = false) LocalDate start, @RequestParam(required = false) LocalDate end) {
        return ResponseEntity.ok(analyticsService.findDashboardMetrics(start, end));
    }

    @GetMapping("/real-time-alerts")
    public ResponseEntity findAlerts() {
        return ResponseEntity.ok(analyticsService.findStockAlerts());
    }
}
