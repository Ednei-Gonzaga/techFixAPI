package com.dev.ednei.techFixApi.DTOS.employees;

import jakarta.validation.constraints.Email;

public record EmployeeManagerUpdateDTO(
        String name,

        String cpf,

        String phone,

        String whatsapp,

        @Email
        String email
) {
}
