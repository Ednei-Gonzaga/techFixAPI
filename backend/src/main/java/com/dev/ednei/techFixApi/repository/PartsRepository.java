package com.dev.ednei.techFixApi.repository;

import com.dev.ednei.techFixApi.DTOS.analytics.dataDashboardAlertsFormat.StockAlertsDTO;
import com.dev.ednei.techFixApi.model.Parts;
import jakarta.mail.Part;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PartsRepository extends JpaRepository<Parts, Long> {
    boolean existsByCodeSku(String code);


    @Query("""
      SELECT  p
      FROM Parts p
      WHERE p.status IN :status 
    """)
    Page<Parts> findAllOrAllByStatus(@Param("status") List<Boolean> status, Pageable pageable);

    @Query("""
                SELECT  p
                FROM Parts p
                WHERE p.status IN :status 
                AND p.name ILIKE %:part%
                OR p.codeSku ILIKE %:part%  
           """)
    Page<Parts> findAllByNameOrCodeSkuOrStatus(@Param("status") List<Boolean> status, @Param("part") String part, Pageable pageable);

    @Query("""
        SELECT p.id, p.name, p.stockQuantity, 
               SUM(CASE WHEN so.dateTimeStart BETWEEN :dateTimeStartPreviousMonth AND :dateTimeEndPreviousMonth THEN item.quantity ELSE 0 END),
               SUM(CASE WHEN so.dateTimeStart BETWEEN :dateTimeStartCurrentMonth AND :dateTimeEndCurrentMonth THEN item.quantity ELSE 0 END)
        FROM Parts p
        LEFT JOIN ServiceOrderItem item ON item.part.id = p.id
        LEFT JOIN ServiceOrder so ON item.serviceOrder.id = so.id 
        GROUP BY p.name, p.id
        HAVING SUM(CASE WHEN so.dateTimeStart BETWEEN :dateTimeStartPreviousMonth AND :dateTimeEndPreviousMonth THEN item.quantity ELSE 0 END) 
               >= (p.stockQuantity + SUM(CASE WHEN so.dateTimeStart BETWEEN :dateTimeStartCurrentMonth AND :dateTimeEndCurrentMonth THEN item.quantity ELSE 0 END))
        OR p.stockQuantity <= 2
                """)
        List<StockAlertsDTO> findLowStockAlertsRed(
                @Param("dateTimeStartPreviousMonth") LocalDateTime dateTimeStartPreviousMonth,
                @Param("dateTimeEndPreviousMonth") LocalDateTime dateTimeEndPreviousMonth,
                @Param("dateTimeStartCurrentMonth") LocalDateTime  dateTimeStartCurrentMonth,
                @Param("dateTimeEndCurrentMonth") LocalDateTime dateTimeEndCurrentMonth
    );


    @Query("""
            SELECT p.id, p.name, p.stockQuantity, 
                   SUM(CASE WHEN so.dateTimeStart BETWEEN :dateTimeStartPreviousMonth AND :dateTimeEndPreviousMonth THEN item.quantity ELSE 0 END),
                   SUM(CASE WHEN so.dateTimeStart BETWEEN :dateTimeStartCurrentMonth AND :dateTimeEndCurrentMonth THEN item.quantity ELSE 0 END)
            FROM Parts p
            LEFT JOIN ServiceOrderItem item ON item.part.id = p.id
            LEFT JOIN ServiceOrder so ON item.serviceOrder.id = so.id 
            GROUP BY p.name, p.id
            HAVING SUM(CASE WHEN so.dateTimeStart BETWEEN :dateTimeStartPreviousMonth AND :dateTimeEndPreviousMonth THEN item.quantity ELSE 0 END) 
                   < (p.stockQuantity + SUM(CASE WHEN so.dateTimeStart BETWEEN :dateTimeStartCurrentMonth AND :dateTimeEndCurrentMonth THEN item.quantity ELSE 0 END))
            AND ((p.stockQuantity + SUM(CASE WHEN so.dateTimeStart BETWEEN :dateTimeStartCurrentMonth AND :dateTimeEndCurrentMonth THEN item.quantity ELSE 0 END))
            - SUM(CASE WHEN so.dateTimeStart BETWEEN :dateTimeStartPreviousMonth AND :dateTimeEndPreviousMonth THEN item.quantity ELSE 0 END))
            <  SUM(CASE WHEN so.dateTimeStart BETWEEN :dateTimeStartPreviousMonth AND :dateTimeEndPreviousMonth THEN item.quantity ELSE 0 END)
            """)
    List<StockAlertsDTO> findLowStockAlertsYellow(
            @Param("dateTimeStartPreviousMonth") LocalDateTime dateTimeStartPreviousMonth,
            @Param("dateTimeEndPreviousMonth") LocalDateTime dateTimeEndPreviousMonth,
            @Param("dateTimeStartCurrentMonth") LocalDateTime  dateTimeStartCurrentMonth,
            @Param("dateTimeEndCurrentMonth") LocalDateTime dateTimeEndCurrentMonth
    );
}
