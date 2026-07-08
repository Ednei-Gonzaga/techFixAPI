package com.dev.ednei.techFixApi.repository;

import com.dev.ednei.techFixApi.DTOS.analytics.DashboardMetricsDTO;
import com.dev.ednei.techFixApi.model.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {
    boolean existsByCpf(String cpf);

    @Query(
            """
                    SELECT c 
                    FROM Client c
                    WHERE c.cpf ILIKE %:cpf%
                    """
    )
    Page<Client> findByCpfClient(String cpf, Pageable pageable);

    @Query("""
                SELECT c
                FROM Client c
                WHERE c.name ILIKE %:nameClient%
            """)
    Page<Client> findByNameClient(@Param("nameClient") String nameClient, Pageable pageable);

    Page<Client> findAll(Pageable pageable);

    @Query("""
              SELECT cl.id, cl.name, SUM(p.totalAmount)
              FROM Payment p
              JOIN p.serviceOrder so
              JOIN so.serviceRequest sr
              JOIN sr.client cl
              WHERE p.paymentStatus ILIKE 'PAID'
              AND p.createdAt BETWEEN :dateStart AND :dateEnd
              GROUP BY cl.id 
              ORDER BY SUM(p.totalAmount) DESC LIMIT 5  
            """)
    List<DashboardMetricsDTO.TopCustomerDTO>  topFiveClients(
            @Param("dateStart") LocalDateTime dateStart,
            @Param("dateEnd") LocalDateTime dateEnd
    );
}
