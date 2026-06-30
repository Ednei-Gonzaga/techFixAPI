package com.dev.ednei.techFixApi.controller;

import com.dev.ednei.techFixApi.DTOS.employees.EmployeeManagerUpdateDTO;
import com.dev.ednei.techFixApi.DTOS.employees.EmployeeProfileUpdateDTO;
import com.dev.ednei.techFixApi.DTOS.employees.EmployeeRequestCpf;
import com.dev.ednei.techFixApi.model.User;
import com.dev.ednei.techFixApi.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PutMapping("/employees/{id}")
    public ResponseEntity updateEmployeeForId(@PathVariable Long id, @RequestBody @Valid EmployeeManagerUpdateDTO dto) {
        var employee = employeeService.updateEmployeeForId(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(employee);
    }

    @PutMapping("/employees/me")
    public ResponseEntity updateEmployeeForMe(@RequestBody EmployeeProfileUpdateDTO dto, @AuthenticationPrincipal User user) {
        var employee = employeeService.updateEmployeeForProfileUser(user, dto);
        return ResponseEntity.status(HttpStatus.OK).body(employee);
    }

    @GetMapping("/employees")
    public ResponseEntity getAllEmployees(@RequestParam(required = false, name = "status")  Boolean status ,
                                          @RequestParam(required = false, name = "name") String name,
                                          Pageable pageable) {
        var employees = employeeService.logicFindAllUser(status,name, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(employees);
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity getEmployeeById(@PathVariable Long id){
        var employee = employeeService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(employee);
    }

    @GetMapping("/employees/me")
    public ResponseEntity getEmployeeByMeLogged(@AuthenticationPrincipal User user){
        var employee = employeeService.findEmployeeLogged(user);
        return ResponseEntity.status(HttpStatus.OK).body(employee);
    }

    @PostMapping("employees/search/cpf")
    public ResponseEntity getEmployeeForCpf(@RequestBody EmployeeRequestCpf employeeRequestCpf, Pageable pageable){
        var employees = employeeService.findEmployeeByCpf(employeeRequestCpf, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(employees);
    }

}
