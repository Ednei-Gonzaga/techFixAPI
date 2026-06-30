package com.dev.ednei.techFixApi.repository;

import com.dev.ednei.techFixApi.model.Parts;
import jakarta.mail.Part;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
}
