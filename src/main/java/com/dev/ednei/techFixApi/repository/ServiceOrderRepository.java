package com.dev.ednei.techFixApi.repository;

import com.dev.ednei.techFixApi.model.ServiceOrder;
import com.dev.ednei.techFixApi.model.enums.StatusServiceOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceOrderRepository extends JpaRepository<ServiceOrder, Long> {


    boolean existsByIdentificationCode(String code);

    Page<ServiceOrder> findAllByStatus(StatusServiceOrder status, Pageable pageable);

    boolean existsByStatus(StatusServiceOrder status);
}
