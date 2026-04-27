package com.dev.ednei.techFixApi.controller;

import com.dev.ednei.techFixApi.DTOS.user.UserCreateDTO;
import com.dev.ednei.techFixApi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth/")
public class UserController {
    @Autowired
    private UserService service;

    @PostMapping("register")
    public ResponseEntity createUser(@RequestBody @Valid UserCreateDTO userDTO){
        var user = service.crateUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

}
