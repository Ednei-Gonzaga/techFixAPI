package com.dev.ednei.techFixApi.repository;

import com.dev.ednei.techFixApi.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
