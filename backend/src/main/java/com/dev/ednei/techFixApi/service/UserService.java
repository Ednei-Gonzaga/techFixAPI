package com.dev.ednei.techFixApi.service;

import com.dev.ednei.techFixApi.DTOS.user.RequestResetPasswordUser;
import com.dev.ednei.techFixApi.DTOS.user.UserCreateDTO;
import com.dev.ednei.techFixApi.DTOS.user.UserResumeDTO;
import com.dev.ednei.techFixApi.infra.exceptions.errors.AccessForbiddenException;
import com.dev.ednei.techFixApi.infra.exceptions.errors.ConflictDataException;
import com.dev.ednei.techFixApi.infra.exceptions.errors.EntityNotFoundException;
import com.dev.ednei.techFixApi.infra.exceptions.errors.InvalidParameterException;
import com.dev.ednei.techFixApi.model.Employee;
import com.dev.ednei.techFixApi.model.User;
import com.dev.ednei.techFixApi.model.enums.StatusVerificationCode;
import com.dev.ednei.techFixApi.repository.EmployeeRepository;
import com.dev.ednei.techFixApi.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Random;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private VerificationCodesService verificationCodesService;

    @Autowired
    private EmailService emailService;

    @Transactional
    public UserResumeDTO saveUser(UserCreateDTO userCreateDTO) {
        var passwordDefault = generatePasswordDefault();
        var textForEmail = "Olá " + userCreateDTO.name().split(" ")[0].toUpperCase() + "!\n" + """
                Parabéns por ter entrado para nossa empresa! Estamos Felizes e anciosos para te receber no nosso time TechFix.
                """ + "\n" + "Seu login acabou de ser Criado! Você já pode acessar o sistema ultilizando Login abaixo:\n\nLOGIN: seu CPF \nSENHA: " + passwordDefault + "\n\n" + "Essa SENHA e temporaria e DEVE SER TROCADA no primeiro acesso.";

        var user = new User(userCreateDTO, bCryptPasswordEncoder.encode(passwordDefault));
        var employee = new Employee(userCreateDTO, user);

        if(employeeRepository.existsByEmail(employee.getEmail())) {
            throw new ConflictDataException("Já existe um usuario cadastrado com esse email");
        }

        if (employeeRepository.existsByCpf(employee.getCpf())) {
            throw new ConflictDataException("Já existe  um usuario cadastrado com esse CPF");
        }

        repository.save(user);
        employeeRepository.save(employee);

        emailService.sentEmail(userCreateDTO.email(), "Bem-vindo ao TechFix", textForEmail);

        return new UserResumeDTO(employee);
    }

    public void registerLastLogin(User user) {
        user.registerLastLogin();
        repository.save(user);
    }

    public void updatePassword(RequestResetPasswordUser requestResetPasswordUser, User userLogged) {
        var anyMethodChosen = false;

        if (StringUtils.hasText(requestResetPasswordUser.codeVerification()) && StringUtils.hasText(requestResetPasswordUser.email()) && !StringUtils.hasText(requestResetPasswordUser.currentPassword())) {
            updateNotLoggedInUserPassword(requestResetPasswordUser);
            anyMethodChosen = true;

            var user = repository.findByEmailOfEmployee(requestResetPasswordUser.email());

            user.get().registerUpdatedAt();

            repository.save(user.get());
        }

        if (!StringUtils.hasText(requestResetPasswordUser.codeVerification()) && !StringUtils.hasText(requestResetPasswordUser.email()) && StringUtils.hasText(requestResetPasswordUser.currentPassword())) {
            updateLoggedInUserPassword(requestResetPasswordUser, userLogged);
            anyMethodChosen = true;
            userLogged.registerUpdatedAt();
        }

        if (!anyMethodChosen) {
            throw new InvalidParameterException("É necessário preencher somente os campos do metodo atualização de SENHA escolhido");
        }
    }

    @Transactional
    public void disableUser(Long idUser) {
        var user =  repository.findById(idUser);

        if(user.isEmpty()){
            throw new EntityNotFoundException("Não foi encontrado nenhum usuario com ID "+idUser);
        }

        user.get().disableUser();
        repository.save(user.get());
    }

    //Methods private
    @Transactional
    private void updateNotLoggedInUserPassword(RequestResetPasswordUser requestResetPasswordUser) {
        var user = repository.findByEmailOfEmployee(requestResetPasswordUser.email());

        if (user.isEmpty()) {
            throw new EntityNotFoundException("Nao foi encontrado nenhum CADASTRO para o email informado");
        }

        var verificationCodes = verificationCodesService.findByCode(requestResetPasswordUser.codeVerification(), user.get().getId());

        if (verificationCodes == null) {
            throw new InvalidParameterException("O codigo informado está incorreto");
        }

        var isExpiredCode = LocalDateTime.now().isAfter(verificationCodes.getExpiredAt());


        if (isExpiredCode || (verificationCodes.getStatus() != StatusVerificationCode.ACTIVE)) {
            throw new InvalidParameterException("O codigo informado já foi usado ou está expirado");
        }

        if (verificationCodes.getUser().getId() != user.get().getId()) {
            throw new AccessForbiddenException("Esse codigo não pertence ao CADASTRO do Email Enviado");
        }

        user.get().updatePassword(bCryptPasswordEncoder.encode(requestResetPasswordUser.newPassword()));
        verificationCodes.updateStatusUsed();

        repository.save(user.get());

        sendMessageSuccessUpdatePassword(requestResetPasswordUser.email());
    }

    @Transactional
    private void updateLoggedInUserPassword(RequestResetPasswordUser requestResetPasswordUser, User user) {

        System.out.println(user.getId());
        if (!bCryptPasswordEncoder.matches(requestResetPasswordUser.currentPassword(), user.getPassword())) {
            throw new AccessForbiddenException("A senha atual informada está incorreta");
        }

        user.updatePassword(bCryptPasswordEncoder.encode(requestResetPasswordUser.newPassword()));
        repository.save(user);

        var employeeUser = employeeRepository.findByUserId(user.getId());

        sendMessageSuccessUpdatePassword(employeeUser.get().getEmail());

    }

    private String generatePasswordDefault() {
        Random random = new Random();
        String numberInclementPassword = "";

        for (var i = 0; i < 6; i++) {
            var number = random.nextInt(10);
            numberInclementPassword += String.valueOf(number);
        }

        return "Tech@" + numberInclementPassword;

    }

    private void sendMessageSuccessUpdatePassword(String email) {
        String messagePasswordAltered = "Sua senha foi alterada, como você pediu.\nVocê ja pode acessar o TechFix com as novas informações de LOGIN.";
        emailService.sentEmail(email, "Senha Alterada", messagePasswordAltered);
    }
}
