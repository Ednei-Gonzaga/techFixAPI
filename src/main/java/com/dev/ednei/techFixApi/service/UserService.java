package com.dev.ednei.techFixApi.service;

import com.dev.ednei.techFixApi.DTOS.user.UserCreateDTO;
import com.dev.ednei.techFixApi.DTOS.user.UserResumeDTO;
import com.dev.ednei.techFixApi.model.User;
import com.dev.ednei.techFixApi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public UserResumeDTO crateUser(UserCreateDTO userDTO){
        User user = new User(userDTO);
        user.alterPasswordHashCode(bCryptPasswordEncoder.encode(user.getPassword()));

        return new UserResumeDTO(repository.save(user));
    }
}
