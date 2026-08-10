package com.dev.ednei.techFixApi.repository;

import com.dev.ednei.techFixApi.model.ServiceOrderHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceOrderHistoryRepository extends JpaRepository<ServiceOrderHistory, Long> {

    Boolean existsByServiceOrderId(Long serviceOrder);

    Page<ServiceOrderHistory> findAllByServiceOrderId(Long osId, Pageable pageable);
}
