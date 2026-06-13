package com.dev.ednei.techFixApi.service;

import com.dev.ednei.techFixApi.DTOS.employees.EmployeeFullDTO;
import com.dev.ednei.techFixApi.infra.exceptions.errors.EntityNotFoundException;
import com.dev.ednei.techFixApi.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository repository;

    public EmployeeFullDTO findByEmail(String email) {
        var employee = repository.findByEmail(email);

        if (employee.isEmpty()) {
            return null;
        }
        return new EmployeeFullDTO(employee.get());
    }
}
