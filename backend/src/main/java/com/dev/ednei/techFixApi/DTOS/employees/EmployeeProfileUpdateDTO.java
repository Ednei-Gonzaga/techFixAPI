package com.dev.ednei.techFixApi.DTOS.employees;

import jakarta.validation.constraints.Email;

public record EmployeeProfileUpdateDTO(
        String phone,

        String whatsapp,

        @Email
        String email
) {
}
