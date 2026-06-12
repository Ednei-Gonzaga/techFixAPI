package com.dev.ednei.techFixApi.service;

import com.dev.ednei.techFixApi.DTOS.user.UserCreateDTO;
import com.dev.ednei.techFixApi.DTOS.user.UserResumeDTO;
import com.dev.ednei.techFixApi.model.Employee;
import com.dev.ednei.techFixApi.model.User;
import com.dev.ednei.techFixApi.repository.EmployeeRepository;
import com.dev.ednei.techFixApi.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public UserResumeDTO saveUser(UserCreateDTO userCreateDTO){
        var user = new User(userCreateDTO, generatePasswordDefault());
        var employee = new Employee(userCreateDTO, user);

        repository.save(user);
        employeeRepository.save(employee);

        ///Enviar email para o email do funcionario cadastrado com LOGIN E SENHA GERADA

        return new UserResumeDTO(employee);
    }

    private String generatePasswordDefault(){
        Random  random = new Random();
        String numberInclementPassword = "";

        for (var i = 0; i < 6; i++){
            var number = random.nextInt( 10);
            numberInclementPassword += String.valueOf(number);
        }

        return bCryptPasswordEncoder.encode("Tech@" +numberInclementPassword);

    }
}
