package com.dev.ednei.techFixApi.service;

import com.dev.ednei.techFixApi.DTOS.serviceOrderHistory.ServiceOrderHistoryCreate;
import com.dev.ednei.techFixApi.model.ServiceOrderHistory;
import com.dev.ednei.techFixApi.repository.ServiceOrderHistoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceOrderHistoryService {
    @Autowired
    private ServiceOrderHistoryRepository repository;

    @Transactional
    public void saveHistoryOrder(ServiceOrderHistoryCreate serviceOrderHistory) {
        var historyOrder = new ServiceOrderHistory(serviceOrderHistory);
        repository.save(historyOrder);
    }
}
