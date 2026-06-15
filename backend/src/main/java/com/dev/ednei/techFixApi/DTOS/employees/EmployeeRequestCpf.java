package com.dev.ednei.techFixApi.DTOS.employees;

import jakarta.validation.constraints.NotBlank;

public record EmployeeRequestCpf(
        @NotBlank
        String cpf
) {
}
