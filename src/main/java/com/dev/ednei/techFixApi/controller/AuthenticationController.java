package com.dev.ednei.techFixApi.controller;

import com.dev.ednei.techFixApi.DTOS.user.UserLoginDTO;
import com.dev.ednei.techFixApi.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth/")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private AuthenticationManager manager;

    @PostMapping("login")
    public ResponseEntity login(@RequestBody @Valid UserLoginDTO user){
        var authentication = manager.authenticate(new UsernamePasswordAuthenticationToken(user.login(), user.password()));
        return ResponseEntity.ok().build();
    }
}
