package com.dev.ednei.techFixApi.repository;

import com.dev.ednei.techFixApi.model.ServiceCatalog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceCatalogRepository extends JpaRepository<ServiceCatalog, Long> {
}
