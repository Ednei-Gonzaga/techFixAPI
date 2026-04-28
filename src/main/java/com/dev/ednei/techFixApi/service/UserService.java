package com.dev.ednei.techFixApi.service;

import com.dev.ednei.techFixApi.DTOS.user.UserCreateDTO;
import com.dev.ednei.techFixApi.DTOS.user.UserDisableDTO;
import com.dev.ednei.techFixApi.DTOS.user.UserResumeDTO;
import com.dev.ednei.techFixApi.DTOS.user.UserUpdateDTO;
import com.dev.ednei.techFixApi.infra.exception.errors.EntityNotFoundException;
import com.dev.ednei.techFixApi.model.User;
import com.dev.ednei.techFixApi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public Page<UserResumeDTO> findAllUsers(Pageable pageable){
        var listUsers = repository.findAll(pageable);

        return listUsers.map(UserResumeDTO::new);
    }

    public UserResumeDTO finsById(Long id){
        var user = repository.findById(id);

        if(user.isEmpty()){
            throw new EntityNotFoundException("Usuario com ID " + id + " não encontrado.");
        }

        return new UserResumeDTO(user.get());
    }

    @Transactional
    public void updateUser(UserUpdateDTO userDTO){
        var user = repository.findById(userDTO.id());

        if(user.isEmpty()){
            throw new EntityNotFoundException("Usuario com ID " + userDTO.id() + " não encontrado.");
        }

        user.get().updateUser(userDTO);

        repository.save(user.get());
    }

    @Transactional
    public void disableUser(UserDisableDTO userDTO){
        var user = repository.findById(userDTO.id());
        if(user.isEmpty()){
            throw new EntityNotFoundException("Usuario com ID " + userDTO.id() + " não encontrado.");
        }

        user.get().disableUser();

        repository.save(user.get());
    }

    @Transactional
    public UserResumeDTO crateUser(UserCreateDTO userDTO){
        User user = new User(userDTO);
        user.alterPasswordHashCode(bCryptPasswordEncoder.encode(user.getPassword()));

        return new UserResumeDTO(repository.save(user));
    }
}
