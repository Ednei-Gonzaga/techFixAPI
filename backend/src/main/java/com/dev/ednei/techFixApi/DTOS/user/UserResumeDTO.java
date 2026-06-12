package com.dev.ednei.techFixApi.DTOS.user;

import com.dev.ednei.techFixApi.model.Employee;
import jakarta.validation.constraints.NotBlank;

public record UserResumeDTO(
        String role,

        String name,

        String cpf,

        String phone,

        String whatsapp,

        String email
) {
    public UserResumeDTO(Employee employee) {
        this(employee.getIdUser().getRole().name(), employee.getName(), employee.getCpf(), employee.getPhone(), employee.getWhatsapp(), employee.getEmail());
    }
}
