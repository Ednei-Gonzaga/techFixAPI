package com.dev.ednei.techFixApi.service;

import com.dev.ednei.techFixApi.DTOS.verificationCodes.UserEmailResetPassword;
import com.dev.ednei.techFixApi.DTOS.verificationCodes.VerificationCodesResumeDTO;
import com.dev.ednei.techFixApi.infra.exceptions.errors.EntityNotFoundException;
import com.dev.ednei.techFixApi.model.User;
import com.dev.ednei.techFixApi.model.VerificationCodes;
import com.dev.ednei.techFixApi.repository.VerificationCodesRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class VerificationCodesService {
    @Autowired
    private VerificationCodesRepository repository;

    @Autowired
    private EmployeeService employeeService;


    @Autowired
    private EmailService emailService;


    @Transactional
    public VerificationCodesResumeDTO saveVerificationCodes(UserEmailResetPassword userEmailResetPassword){
        var code = generateCode();
        var userEmployee = employeeService.findByEmail(userEmailResetPassword.email());

        if(userEmployee == null) {
            throw  new EntityNotFoundException("Nâo foi possivel encontrar CADASTRO com esse email " + userEmailResetPassword.email());
        }

        var checkExistsCodeUser = checkExistsCodeForUSer(code, userEmployee.user());

        while (checkExistsCodeUser) {
            code = generateCode();
            checkExistsCodeUser = checkExistsCodeForUSer(code, userEmployee.user());
        }

        String textEmail = "Se você solicitou uma redefinição de senha para " + userEmailResetPassword.email()
                + ", use o código de confirmação abaixo para concluir o processo. Se você não fez essa solicitação, ignore este e-mail.\n\n"
                + "Código de Verificação: " + code + "\n\n";
        var userLogin = new User(userEmployee.user());
        var verificationCode = new VerificationCodes(code, userLogin);

        repository.save(verificationCode);

        //Preciso ter metodo que garante que esse emaiil foi enviado
        emailService.sentEmail(userEmailResetPassword.email(), "Solicitação de redefinição de senha", textEmail);

        return new VerificationCodesResumeDTO(verificationCode);
    }

    @Transactional
    public VerificationCodes findByCode(String code, Long userId){
        var verificationCodes = repository.findByCodeAndUserId(code, userId);

        if(verificationCodes.isEmpty()){
            return null;
        }

        return verificationCodes.get();
    }

    private String generateCode(){
        Random random = new Random();
        String code = "";

        for(var i = 0; i < 6; i++){
            var number = random.nextInt(6);
            code += String.valueOf(number);
        }

        return code;
    }

    private boolean checkExistsCodeForUSer(String code, Long userId){
        var existsCode = repository.findByCodeAndUserId(code, userId);

        if(existsCode.isPresent()){
            return true;
        }
        return false;
    }
}
