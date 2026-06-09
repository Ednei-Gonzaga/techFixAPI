package com.dev.ednei.techFixApi.service;

import com.dev.ednei.techFixApi.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository repository;
}
