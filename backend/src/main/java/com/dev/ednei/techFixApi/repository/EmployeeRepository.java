package com.dev.ednei.techFixApi.repository;

import com.dev.ednei.techFixApi.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmail(String email);

    Optional<Employee> findByUserId(Long idUser);

    boolean existsByCpf(String cpf);

    boolean existsByEmail(String email);

    @Query(
            """
            SELECT e
            FROM Employee e
            WHERE e.cpf ILIKE  %:cpf%  
            """
    )
    Page<Employee> findForCpf(String cpf, Pageable pageable);
}
