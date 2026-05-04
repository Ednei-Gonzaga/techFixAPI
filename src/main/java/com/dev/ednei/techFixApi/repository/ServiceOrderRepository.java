package com.dev.ednei.techFixApi.repository;

import com.dev.ednei.techFixApi.model.ServiceOrder;
import com.dev.ednei.techFixApi.model.enums.StatusServiceOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServiceOrderRepository extends JpaRepository<ServiceOrder, Long> {


    boolean existsByIdentificationCode(String code);

    Page<ServiceOrder> findAllByStatusOrderByDateTimeStartAsc(StatusServiceOrder status, Pageable pageable);

    boolean existsByStatus(StatusServiceOrder status);

     Page<ServiceOrder> findAllByUserId(Long id, Pageable pageable);

    Page<ServiceOrder> findAllByUserIdAndStatus(Long id, StatusServiceOrder status, Pageable pageable);

    Optional<ServiceOrder> findByIdentificationCode(String code);
}
