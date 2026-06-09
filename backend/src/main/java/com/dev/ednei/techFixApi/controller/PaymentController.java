package com.dev.ednei.techFixApi.controller;

import com.dev.ednei.techFixApi.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {
    @Autowired
    private PaymentService paymentService;
}
