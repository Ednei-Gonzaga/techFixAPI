package com.dev.ednei.techFixApi.service;

import com.dev.ednei.techFixApi.repository.ServiceCatalogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceCatalogService {
    @Autowired
    private ServiceCatalogRepository repository;
}
