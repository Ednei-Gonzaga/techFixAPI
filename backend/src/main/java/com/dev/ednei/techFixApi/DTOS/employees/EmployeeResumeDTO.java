package com.dev.ednei.techFixApi.DTOS.employees;

import com.dev.ednei.techFixApi.model.Employee;
import jakarta.validation.constraints.Email;

import java.util.Optional;

public record EmployeeResumeDTO(
        Long id,

        String name,

        String cpf,

        String phone,

        String whatsapp,

        @Email
        String email
) {
    public EmployeeResumeDTO(Employee employee) {
        this(employee.getId(), employee.getName(), employee.getCpf(), employee.getPhone(), employee.getWhatsapp(), employee.getEmail());
    }
}
