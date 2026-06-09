package com.dev.ednei.techFixApi.service;

import com.dev.ednei.techFixApi.repository.ServiceOrderHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceOrderHistoryService {
    @Autowired
    private ServiceOrderHistoryRepository repository;
}
