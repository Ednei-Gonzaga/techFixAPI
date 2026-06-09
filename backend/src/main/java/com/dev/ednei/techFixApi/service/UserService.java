package com.dev.ednei.techFixApi.service;

import com.dev.ednei.techFixApi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;
}
