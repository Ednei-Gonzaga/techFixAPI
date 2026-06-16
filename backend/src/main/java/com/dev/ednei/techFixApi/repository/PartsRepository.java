package com.dev.ednei.techFixApi.repository;

import com.dev.ednei.techFixApi.model.Parts;
import jakarta.mail.Part;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PartsRepository extends JpaRepository<Parts, Long> {
    boolean existsByCodeSku(String code);


    Page<Parts> findAll(Pageable pageable);

    @Query("""
                SELECT  p
                FROM Parts p
                WHERE p.name ILIKE %:part%
                OR p.codeSku ILIKE %:part%  
           """)
    Page<Parts> findAllByNameOrCodeSku(@Param("part") String part, Pageable pageable);
}
