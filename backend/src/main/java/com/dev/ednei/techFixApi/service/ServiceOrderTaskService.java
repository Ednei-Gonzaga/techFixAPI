package com.dev.ednei.techFixApi.service;

import com.dev.ednei.techFixApi.repository.ServiceOrderTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceOrderTaskService {
    @Autowired
    private ServiceOrderTaskRepository repository;
}
