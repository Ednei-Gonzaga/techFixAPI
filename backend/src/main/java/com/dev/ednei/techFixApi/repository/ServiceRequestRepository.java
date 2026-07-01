package com.dev.ednei.techFixApi.repository;

import com.dev.ednei.techFixApi.model.ServiceRequests;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRequestRepository extends JpaRepository<ServiceRequests, Long> {

    Page<ServiceRequests> findAllByClientId(Long id, Pageable pageable);

}
