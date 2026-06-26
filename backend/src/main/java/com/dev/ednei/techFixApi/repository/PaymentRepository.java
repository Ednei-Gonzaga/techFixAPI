package com.dev.ednei.techFixApi.repository;

import com.dev.ednei.techFixApi.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByServiceOrderId(Long id);

    boolean existsByServiceOrderId(Long serviceOrderId);
}
