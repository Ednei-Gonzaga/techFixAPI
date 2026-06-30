package com.dev.ednei.techFixApi.DTOS.employees;

import com.dev.ednei.techFixApi.model.Employee;

public record EmployeeFullDTO(
        Long id,
        String name,
        String cpf,
        String phone,
        String whatsapp,
        String email,
        Boolean status,
        Long user

) {
    public EmployeeFullDTO(Employee employee) {
        this(employee.getId(), employee.getName(), employee.getCpf(), employee.getPhone(), employee.getWhatsapp(), employee.getEmail(),employee.getUser().isStatus() , employee.getUser().getId());
    }
}
