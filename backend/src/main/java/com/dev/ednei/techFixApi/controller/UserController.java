package com.dev.ednei.techFixApi.controller;

import com.dev.ednei.techFixApi.DTOS.user.UserCreateDTO;
import com.dev.ednei.techFixApi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public ResponseEntity saveUser(@RequestBody @Valid UserCreateDTO userCreateDTO) {
        var user = userService.saveUser(userCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
}
