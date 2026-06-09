package com.dev.ednei.techFixApi.service;

import com.dev.ednei.techFixApi.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository repository;
}
