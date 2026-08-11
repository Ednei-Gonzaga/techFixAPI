package com.dev.ednei.techFixApi.repository;

import com.dev.ednei.techFixApi.model.PaymentsHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PaymentsHistoryRepository extends JpaRepository<PaymentsHistory, Long> {

    @Query("""
    SELECT h
    FROM PaymentsHistory h
    JOIN h.payment p
    JOIN p.serviceOrder s
    WHERE s.id = :idServiceOrder
    """)
    Page<PaymentsHistory> findAllByServiceOrder(Long idServiceOrder, Pageable pageable);
}
