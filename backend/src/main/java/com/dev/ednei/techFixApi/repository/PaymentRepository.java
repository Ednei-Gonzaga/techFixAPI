package com.dev.ednei.techFixApi.repository;

import com.dev.ednei.techFixApi.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
