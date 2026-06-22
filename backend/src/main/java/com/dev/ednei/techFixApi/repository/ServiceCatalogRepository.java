package com.dev.ednei.techFixApi.repository;

import com.dev.ednei.techFixApi.DTOS.serviceCatolog.ServiceCatalogFullDTO;
import com.dev.ednei.techFixApi.model.ServiceCatalog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ServiceCatalogRepository extends JpaRepository<ServiceCatalog, Long> {

    @Query("""
      SELECT s 
      FROM ServiceCatalog s
      WHERE s.name ILIKE %:name%
          """)
    Page<ServiceCatalog> findAllByName(@Param("name") String name, Pageable pageable);
}
