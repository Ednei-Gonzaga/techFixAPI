package com.dev.ednei.techFixApi.repository;

import com.dev.ednei.techFixApi.model.ServiceRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Long> {

    @Query("""
            SELECT s FROM ServiceRequest s 
            JOIN s.client c 
            WHERE c.cpf = :cpf
            """)
    Page<ServiceRequest> findAllByCpfClient(@Param("cpf") String cpf, Pageable pageable);
}
