package com.dev.ednei.techFixApi.service;

import com.dev.ednei.techFixApi.repository.PaymentsHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentsHistoryService {
    @Autowired
    private PaymentsHistoryRepository repository;
}
