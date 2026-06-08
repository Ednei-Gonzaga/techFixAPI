package com.dev.ednei.techFixApi.controller;

import com.dev.ednei.techFixApi.DTOS.user.UserCreateDTO;
import com.dev.ednei.techFixApi.DTOS.user.UserDisableDTO;
import com.dev.ednei.techFixApi.DTOS.user.UserResumeDTO;
import com.dev.ednei.techFixApi.DTOS.user.UserUpdateDTO;
import com.dev.ednei.techFixApi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users")
public class UserController {
    @Autowired
    private UserService service;

    @GetMapping()
    public ResponseEntity<Page<UserResumeDTO>> findAllUsers(Pageable pageable){
        var listUsers = service.findAllUsers(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(listUsers);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserResumeDTO> findById(@PathVariable Long id){
        var user = service.finsById(id);

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PostMapping("")
    public ResponseEntity createUser(@RequestBody @Valid UserCreateDTO userDTO){
        var user = service.crateUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PutMapping()
    public ResponseEntity updateUser(@RequestBody @Valid UserUpdateDTO userDTO ){
        service.updateUser(userDTO);
        return  ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping
    public ResponseEntity disableUser(@RequestBody @Valid UserDisableDTO userDTO){
        service.disableUser(userDTO);

        return ResponseEntity.status(HttpStatus.OK).build();
    }



}
