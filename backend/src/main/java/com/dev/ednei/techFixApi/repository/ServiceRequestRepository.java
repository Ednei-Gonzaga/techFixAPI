package com.dev.ednei.techFixApi.repository;

import com.dev.ednei.techFixApi.model.ServiceRequests;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ServiceRequestRepository extends JpaRepository<ServiceRequests, Long> {

    Page<ServiceRequests> findAllByClientId(Long id, Pageable pageable);

    @Query("""
            SELECT sr.category 
            FROM  ServiceRequests sr
            JOIN sr.serviceOrder so
            WHERE so.status IN ('COMPLETED', 'DELIVERED')
            AND so.dateTimeStart BETWEEN :dateStart AND :dateEnd
            GROUP BY sr.category
            ORDER BY  COUNT(so.id) DESC LIMIT 3     
            """)
    List<String> topCategories(
            @Param("dateStart") LocalDateTime dateStart,
            @Param("dateEnd") LocalDateTime dateEnd
    );
}
