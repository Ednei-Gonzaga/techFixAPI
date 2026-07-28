package com.dev.ednei.techFixApi.DTOS.employees;

import jakarta.validation.constraints.Email;
import org.hibernate.validator.constraints.br.CPF;

public record EmployeeManagerUpdateDTO(
        String name,

        @CPF
        String cpf,

        String phone,

        String whatsapp,

        @Email
        String email
) {
}
