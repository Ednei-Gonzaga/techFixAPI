package com.dev.ednei.techFixApi.repository;

import com.dev.ednei.techFixApi.model.ServiceRequests;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRequestRepository extends JpaRepository<ServiceRequests, Long> {
}
