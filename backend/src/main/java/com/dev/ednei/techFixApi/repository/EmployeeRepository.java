package com.dev.ednei.techFixApi.repository;

import com.dev.ednei.techFixApi.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
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

    @Query("""
        SELECT e 
        FROM Employee e 
        JOIN e.user u
        WHERE u.status = :status
        """)
    Page<Employee> findByStatus(@Param("status") Boolean status, Pageable pageable);

    @Query("""
        SELECT e 
        FROM Employee e 
        JOIN e.user u
        WHERE u.status IN :status
        AND e.name ILIKE %:name%
        """)
    Page<Employee> findByStatusOrName(
            @Param("status") List<Boolean> status,
            @Param("name") String name,
            Pageable pageable);
}
