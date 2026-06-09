package com.dev.ednei.techFixApi.controller;

import com.dev.ednei.techFixApi.service.ServiceOrderHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class serviceOrderHistoryController {
    @Autowired
    private ServiceOrderHistoryService serviceOrderHistoryService;
}
