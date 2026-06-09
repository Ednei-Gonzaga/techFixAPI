package com.dev.ednei.techFixApi.controller;

import com.dev.ednei.techFixApi.service.ServiceOrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ServiceOrderItemController {
    @Autowired
    private ServiceOrderItemService serviceOrderItemService;
}
