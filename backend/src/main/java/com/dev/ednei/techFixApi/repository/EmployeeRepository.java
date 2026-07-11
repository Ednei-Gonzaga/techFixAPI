package com.dev.ednei.techFixApi.repository;

import com.dev.ednei.techFixApi.DTOS.analytics.dataDashboardAlertsFormat.ServiceDemand;
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


    @Query("""
        SELECT us.id, e.name,
                COUNT(CASE WHEN so.userTechnical.id IS NOT NULL AND so.status NOT IN ('CANCELED', 'COMPLETED', 'DELIVERED') THEN so.userTechnical.id END)
        FROM Employee e
        LEFT JOIN User us ON us.id = e.user.id
        LEFT JOIN ServiceOrder so ON so.userTechnical.id = us.id
        WHERE us.role = 'TECHNICAL'
        GROUP BY us.id, e.name 
        ORDER BY COUNT(CASE WHEN so.userTechnical.id IS NOT NULL AND so.status NOT IN ('CANCELED', 'COMPLETED', 'DELIVERED') THEN so.userTechnical.id END) DESC
        """)
    List<ServiceDemand> findServiceDemand();
}
