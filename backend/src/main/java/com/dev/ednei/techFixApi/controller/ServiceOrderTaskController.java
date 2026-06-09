package com.dev.ednei.techFixApi.controller;

import com.dev.ednei.techFixApi.service.ServiceOrderItemService;
import com.dev.ednei.techFixApi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceOrderTaskController {
    @Autowired
    private ServiceOrderItemService serviceOrderItemService;
}
