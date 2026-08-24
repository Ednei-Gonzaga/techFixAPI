package com.dev.ednei.techFixApi.service;

import com.dev.ednei.techFixApi.DTOS.employees.*;
import com.dev.ednei.techFixApi.infra.exceptions.errors.ConflictDataException;
import com.dev.ednei.techFixApi.infra.exceptions.errors.EntityNotFoundException;
import com.dev.ednei.techFixApi.infra.exceptions.errors.InvalidParameterException;
import com.dev.ednei.techFixApi.model.Employee;
import com.dev.ednei.techFixApi.model.User;
import com.dev.ednei.techFixApi.model.enums.RoleUser;
import com.dev.ednei.techFixApi.repository.EmployeeRepository;
import com.dev.ednei.techFixApi.repository.UserRepository;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;


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
    public EmployeeResumeDTO updateEmployeeForId(Long id, EmployeeManagerUpdateDTO dto) throws NumberParseException {
        var employee = repository.findById(id);

        if (employee.isEmpty()) {
            throw new EntityNotFoundException("Funcionario com ID " + id + " não encontrado");
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

        verificationNumberPhoneAndWhatsappIsValid(dto.phone(), dto.whatsapp());

        employee.get().updateById(dto);
        repository.save(employee.get());

        user.get().registerUpdatedAt();
        userRepository.save(user.get());

        return new EmployeeResumeDTO(employee.get());
    }

    @Transactional
    public EmployeeResumeDTO updateEmployeeForProfileUser(User user, EmployeeProfileUpdateDTO dto) throws NumberParseException {
        var employee = repository.findByUserId(user.getId());
        if (StringUtils.hasText(dto.email())) {
            if (repository.existsByEmail(dto.email()) && !employee.get().getEmail().equals(dto.email())) {
                throw new ConflictDataException("Já existe um Funcionario cadastrado com esse email");
            }
        }

        verificationNumberPhoneAndWhatsappIsValid(dto.phone(), dto.whatsapp());

        employee.get().updateByEmployeeLogged(dto);
        repository.save(employee.get());

        user.registerUpdatedAt();
        userRepository.save(user);

        return new EmployeeResumeDTO(employee.get());
    }

    public Page<EmployeeFullDTO> logicFindAllUser(Boolean status, String name, Pageable pageable) {

        if(StringUtils.hasText(name)) {
            List<Boolean> listBoolean;
            if(status != null) {
                listBoolean = List.of(Boolean.valueOf(status));
                return findAllByNameOrStatus(listBoolean, name, pageable);
            }else{
                listBoolean = List.of(Boolean.FALSE,  Boolean.TRUE);
                System.out.println(listBoolean);
                return  findAllByNameOrStatus(listBoolean, name, pageable);
            }
        }

        if (status != null) {
            return findAllByStatus(status, pageable);
        } else {
            return findAll(pageable);
        }

    }

    public EmployeeFullDTO findById(Long id) {
        var employee = repository.findById(id);

        if (employee.isEmpty()) {
            throw new EntityNotFoundException("Não encontrado um Funcionario com ID " + id);
        }

        return new EmployeeFullDTO(employee.get());
    }

    public EmployeeResumeDTO findEmployeeLogged(User user) {
        var employee = repository.findByUserId(user.getId());

        if (employee.isEmpty()) {
            throw new EntityNotFoundException("Funcionario com ID " + user.getId());
        }

        return new EmployeeResumeDTO(employee.get());
    }

    public Page<EmployeeResumeDTO> findEmployeeByCpf(EmployeeRequestCpf employeeRequestCpf, Pageable pageable) {
        var employee = repository.findForCpf(employeeRequestCpf.cpf(), pageable);
        return employee.map(EmployeeResumeDTO::new);
    }

    //metodos usados em outras classes



    //Metodos privados

    private Page<EmployeeFullDTO> findAllByStatus(Boolean status, Pageable pageable) {
        var employee = repository.findByStatus(status, pageable);
        return employee.map(EmployeeFullDTO::new);
    }

    private Page<EmployeeFullDTO> findAll(Pageable pageable) {
        var employee = repository.findAll(pageable);
        return employee.map(EmployeeFullDTO::new);
    }

    private  Page<EmployeeFullDTO> findAllByNameOrStatus(List<Boolean> status, String name,  Pageable pageable) {
        var employees = repository.findByStatusOrName(status, name, pageable);
        return employees.map(EmployeeFullDTO::new);
    }

    public static void verificationNumberPhoneAndWhatsappIsValid(String phone, String whatsapp) throws NumberParseException {
        PhoneNumberUtil util = PhoneNumberUtil.getInstance();


        if(StringUtils.hasText(phone)){

            var parsePhone = util.parse(phone.replaceAll("[^0-9]", ""), "BR");
            if(!util.isValidNumber(parsePhone)) {
                throw new InvalidParameterException("Número do campo 'phone' está no formato incorreto. Verifique se contém DDD e é um número válido no Brasil e na região do DDD.");
            }

        }

        if(StringUtils.hasText(whatsapp)){

            var parseWhatsapp = util.parse(whatsapp.replaceAll("[^0-9]", ""), "BR");
            if(!util.isValidNumber(parseWhatsapp) || util.getNumberType(parseWhatsapp) != PhoneNumberUtil.PhoneNumberType.MOBILE){
                throw new InvalidParameterException("Número de Whatsapp está no formato incorreto.  Verifique se contem DDD e '9' no começo do número e se é  um número valilido a região do DDD.");
            }

        }
    }


}

