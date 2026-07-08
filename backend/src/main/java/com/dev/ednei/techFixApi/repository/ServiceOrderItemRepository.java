package com.dev.ednei.techFixApi.repository;

import com.dev.ednei.techFixApi.DTOS.analytics.dataInDashboardFormat.MostUsedPartFormat;
import com.dev.ednei.techFixApi.DTOS.serviceOrderItem.ServiceOrderItemFullDTO;
import com.dev.ednei.techFixApi.model.ServiceOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ServiceOrderItemRepository extends JpaRepository<ServiceOrderItem, Long> {

    boolean existsByServiceOrderIdAndPartId(Long serviceOrderId, Long partId);

    List<ServiceOrderItem> findAllByServiceOrderId(Long serviceOrderId);

    @Query("""
        SELECT item.namePart, SUM(item.quantity)
        FROM ServiceOrderItem item
        JOIN item.serviceOrder so
        WHERE so.dateTimeStart BETWEEN :dateStart AND :dateEnd
        AND so.status IN ('COMPLETED', 'DELIVERED')
        GROUP BY item.part, item.namePart
        ORDER BY SUM(item.quantity) DESC LIMIT 3
        """)
    List<MostUsedPartFormat> findMostUsedPart(
            @Param("dateStart") LocalDateTime dateStart,
            @Param("dateEnd") LocalDateTime dateEnd
    );

    @Query("""
        SELECT item.namePart, SUM(item.quantity)
        FROM ServiceOrderItem item
        JOIN item.serviceOrder so
        WHERE so.dateTimeStart BETWEEN :dateStart AND :dateEnd
        AND so.status IN ('COMPLETED', 'DELIVERED')
        GROUP BY item.part, item.namePart
        ORDER BY SUM(item.quantity) ASC LIMIT 3
        """)
    List<MostUsedPartFormat> findLeastUsedPart(
            @Param("dateStart") LocalDateTime dateStart,
            @Param("dateEnd") LocalDateTime dateEnd
    );
}
