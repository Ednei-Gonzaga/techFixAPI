package com.dev.ednei.techFixApi.repository;

import com.dev.ednei.techFixApi.DTOS.analytics.DashboardMetricsDTO;
import com.dev.ednei.techFixApi.DTOS.analytics.DashboardMetricsDTO.CategoryRevenueDTO;
import com.dev.ednei.techFixApi.DTOS.analytics.dataInDashboardFormat.DashboardSummaryRecord;
import com.dev.ednei.techFixApi.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByServiceOrderId(Long id);

    boolean existsByServiceOrderId(Long serviceOrderId);

    @Query("""
            SELECT SUM(p.totalAmount), 
                   SUM(p.totalAmount) - SUM(p.partsAmount),
                   (SELECT SUM(p.totalAmount) FROM Payment p WHERE p.paymentStatus IN('PAID','PENDING')  AND p.createdAt BETWEEN :dateStart AND :dateEnd),
                   (SELECT SUM(p.totalAmount) - SUM(p.partsAmount) FROM Payment p WHERE p.paymentStatus IN('PAID','PENDING')  AND p.createdAt BETWEEN :dateStart AND :dateEnd),
                   SUM(p.partsAmount),
                   ROUND(AVG(p.totalAmount))
                   FROM Payment p WHERE p.paymentStatus ILIKE 'PAID'
                   AND p.createdAt BETWEEN :dateStart AND :dateEnd
            """)
    DashboardSummaryRecord findDashboardSummaryRecord(
            @Param("dateStart") LocalDateTime dateStart,
            @Param("dateEnd") LocalDateTime dateEnd
    );


    @Query("""
                    SELECT sr.category,
                           SUM(p.totalAmount)
                           FROM Payment p
                           JOIN p.serviceOrder so
                           JOIN so.serviceRequest sr
                           WHERE p.paymentStatus ILIKE 'PAID'
                           AND p.createdAt BETWEEN :dateStart AND :dateEnd
                           GROUP BY sr.category
            """)
    List<CategoryRevenueDTO> findDashboardCategoryRevenue(
            @Param("dateStart") LocalDateTime dateStart,
            @Param("dateEnd") LocalDateTime dateEnd
    );


}
