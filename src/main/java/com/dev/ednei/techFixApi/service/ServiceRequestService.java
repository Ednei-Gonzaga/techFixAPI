package com.dev.ednei.techFixApi.service;

import com.dev.ednei.techFixApi.repository.ServiceOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceRequestService {
    @Autowired
    private ServiceOrderRepository repository;

}
