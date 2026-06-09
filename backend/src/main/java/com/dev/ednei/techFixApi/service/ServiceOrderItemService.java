package com.dev.ednei.techFixApi.service;

import com.dev.ednei.techFixApi.repository.ServiceOrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceOrderItemService {
    @Autowired
    private ServiceOrderItemRepository repository;
}
