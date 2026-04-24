package com.dev.ednei.techFixApi.repository;

import com.dev.ednei.techFixApi.model.ServiceRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Long> {

}
