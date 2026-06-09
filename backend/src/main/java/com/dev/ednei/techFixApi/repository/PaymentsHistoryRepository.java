package com.dev.ednei.techFixApi.repository;

import com.dev.ednei.techFixApi.model.PaymentsHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentsHistoryRepository extends JpaRepository<PaymentsHistory, Long> {
}
