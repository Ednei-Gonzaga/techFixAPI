package com.dev.ednei.techFixApi.controller;

import com.dev.ednei.techFixApi.DTOS.user.UserLoginDTO;
import com.dev.ednei.techFixApi.model.User;
import com.dev.ednei.techFixApi.service.AuthenticationService;
import com.dev.ednei.techFixApi.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("api/auth/")
public class AuthenticationController {
    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager manager;

    @PostMapping("login")
    public ResponseEntity login(@RequestBody @Valid UserLoginDTO user){
        var authentication = manager.authenticate(new UsernamePasswordAuthenticationToken(user.login(), user.password()));
        String token = tokenService.authenticationToken((User) authentication.getPrincipal());
        return ResponseEntity.ok().body(Map.of("token", token));
    }
}
