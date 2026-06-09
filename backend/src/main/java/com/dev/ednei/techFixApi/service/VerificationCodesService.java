package com.dev.ednei.techFixApi.service;

import com.dev.ednei.techFixApi.repository.VerificationCodesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerificationCodesService {
    @Autowired
    private VerificationCodesRepository repository;
}
