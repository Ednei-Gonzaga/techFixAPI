package com.dev.ednei.techFixApi.DTOS.analytics;

import com.dev.ednei.techFixApi.DTOS.analytics.dataDashboardAlertsFormat.ServiceDemand;
import com.dev.ednei.techFixApi.DTOS.analytics.dataDashboardAlertsFormat.StockAlertsDTO;

import java.util.List;

public record DashboardAlertsDTO(
        List<ServiceDemand>  technicianQueues,
        List<StockAlertsDTO> stockInAlertRed,
        List<StockAlertsDTO> stockInAlertYellow
) {
}
