package com.dev.ednei.techFixApi.controller;

import com.dev.ednei.techFixApi.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
}
