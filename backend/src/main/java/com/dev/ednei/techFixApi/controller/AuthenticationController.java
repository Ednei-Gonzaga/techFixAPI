package com.dev.ednei.techFixApi.controller;

import com.dev.ednei.techFixApi.DTOS.authentication.AuthenticationRequest;
import com.dev.ednei.techFixApi.infra.exceptions.errors.FirstAccessException;
import com.dev.ednei.techFixApi.model.User;
import com.dev.ednei.techFixApi.service.TokenService;
import com.dev.ednei.techFixApi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v2")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserService userService;

    @PostMapping("/auth/login")
    public ResponseEntity LoginToken(@RequestBody AuthenticationRequest authenticationRequest){
       var authentication = manager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.login(), authenticationRequest.password()));
       var token = tokenService.createTokenJwt((User) authentication.getPrincipal());
       var user  = (User) authentication.getPrincipal();

       if(!user.isForcePasswordChanger()){
           throw new FirstAccessException(tokenService.tokenJwtForAlterPassword(user));
       }

       userService.registerLastLogin(user);

       return ResponseEntity.status(HttpStatus.OK).body(Map.of("Token", token));
    }
}
