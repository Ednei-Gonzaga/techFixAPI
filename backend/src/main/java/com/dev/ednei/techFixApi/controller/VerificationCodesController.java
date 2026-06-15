package com.dev.ednei.techFixApi.controller;

import com.dev.ednei.techFixApi.DTOS.verificationCodes.UserEmailResetPassword;
import com.dev.ednei.techFixApi.service.VerificationCodesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v2")
public class VerificationCodesController {
    @Autowired
    private VerificationCodesService verificationCodesService;

    @PostMapping("/password-reset/request-verification-codes")
    public ResponseEntity generateCode(@RequestBody @Valid  UserEmailResetPassword userEmail){
        var codeResume = verificationCodesService.saveVerificationCodes(userEmail);
        return ResponseEntity.status(HttpStatus.CREATED).body(codeResume);
    }

}
