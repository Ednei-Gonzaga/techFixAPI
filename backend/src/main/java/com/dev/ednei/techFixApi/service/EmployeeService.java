package com.dev.ednei.techFixApi.service;

import com.dev.ednei.techFixApi.DTOS.employees.*;
import com.dev.ednei.techFixApi.infra.exceptions.errors.ConflictDataException;
import com.dev.ednei.techFixApi.infra.exceptions.errors.EntityNotFoundException;
import com.dev.ednei.techFixApi.model.User;
import com.dev.ednei.techFixApi.repository.EmployeeRepository;
import com.dev.ednei.techFixApi.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository repository;

    @Autowired
    private UserRepository userRepository;

    public EmployeeFullDTO findByEmail(String email) {
        var employee = repository.findByEmail(email);

        if (employee.isEmpty()) {
            return null;
        }
        return new EmployeeFullDTO(employee.get());
    }

    @Transactional
    public EmployeeResumeDTO updateEmployeeForId(Long id, EmployeeManagerUpdateDTO dto) {
        var employee = repository.findById(id);

        if (employee.isEmpty()) {
           throw  new EntityNotFoundException("Funcionario com ID " + id + " não encontrado");
        }

        if (StringUtils.hasText(dto.email())) {
            if (repository.existsByEmail(dto.email()) && !employee.get().getEmail().equals(dto.email())) {
                throw new ConflictDataException("Já existe um Funcionario cadastrado com esse email");
            }
        }

        var user = userRepository.findById(employee.get().getUser().getId());

        if (user.isPresent()) {
            if (StringUtils.hasText(dto.cpf())) {

                if (repository.existsByCpf(dto.cpf()) && !dto.cpf().equals(employee.get().getCpf())) {
                    throw new ConflictDataException("Já existe um funcionario com CPF informado");
                }

                user.get().setLogin(dto.cpf());
                userRepository.save(user.get());
            }
        }

        employee.get().updateById(dto);
        repository.save(employee.get());

        user.get().registerUpdatedAt();
        userRepository.save(user.get());

        return new EmployeeResumeDTO(employee.get());
    }

    @Transactional
    public EmployeeResumeDTO updateEmployeeForProfileUser(User user, EmployeeProfileUpdateDTO dto) {
        var employee = repository.findByUserId(user.getId());
        if (StringUtils.hasText(dto.email())) {
            if (repository.existsByEmail(dto.email()) && !employee.get().getEmail().equals(dto.email())) {
                throw new ConflictDataException("Já existe um Funcionario cadastrado com esse email");
            }
        }

        employee.get().updateByEmployeeLogged(dto);
        repository.save(employee.get());

        user.registerUpdatedAt();
        userRepository.save(user);

        return new EmployeeResumeDTO(employee.get());
    }

    public Page<EmployeeFullDTO> findAllUser(Pageable pageable){
        var employee = repository.findAll(pageable);
        return employee.map(EmployeeFullDTO::new);
    }

    public EmployeeFullDTO findById(Long id){
        var employee = repository.findById(id);

        if(employee.isEmpty()){
            throw  new EntityNotFoundException("Não encontrado um Funcionario com ID " + id);
        }

        return new EmployeeFullDTO(employee.get());
    }

    public EmployeeResumeDTO findEmployeeLogged(User user) {
        var employee = repository.findByUserId(user.getId());

        if(employee.isEmpty()){
            throw new  EntityNotFoundException("Funcionario com ID " + user.getId());
        }

        return new EmployeeResumeDTO(employee.get());
    }

    public Page<EmployeeResumeDTO> findEmployeeByCpf(EmployeeRequestCpf employeeRequestCpf, Pageable pageable){
        var employee = repository.findForCpf(employeeRequestCpf.cpf(),  pageable);
        return employee.map(EmployeeResumeDTO::new);
    }
}
