package com.dev.ednei.techFixApi.controller;

import com.dev.ednei.techFixApi.service.VerificationCodesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VerificationCodesController {
    @Autowired
    private VerificationCodesService verificationCodesService;

}
