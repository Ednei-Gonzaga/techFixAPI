package com.dev.ednei.techFixApi.controller;

import com.dev.ednei.techFixApi.DTOS.user.RequestResetPasswordNotLogged;
import com.dev.ednei.techFixApi.DTOS.user.RequestResetPasswordUserLogged;
import com.dev.ednei.techFixApi.DTOS.user.UserCreateDTO;
import com.dev.ednei.techFixApi.model.User;
import com.dev.ednei.techFixApi.service.EmailService;
import com.dev.ednei.techFixApi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/users")
    public ResponseEntity saveUser(@RequestBody @Valid UserCreateDTO userCreateDTO) {
        var user = userService.saveUser(userCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PutMapping("/users/reset-password")
    public ResponseEntity updatePasswordNotLogged(@RequestBody @Valid RequestResetPasswordNotLogged requestResetPasswordUser, @AuthenticationPrincipal User user) {

       userService.updateNotLoggedInUserPassword(requestResetPasswordUser);
       return ResponseEntity.ok().build();
    }

    @PutMapping("/users/me/password")
    public ResponseEntity  updatePasswordLogged(@RequestBody @Valid RequestResetPasswordUserLogged requestResetPasswordUser, @AuthenticationPrincipal User user) {
        userService.updateLoggedInUserPassword(requestResetPasswordUser, user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity disableUser(@PathVariable("id") Long id) {
        userService.disableUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
