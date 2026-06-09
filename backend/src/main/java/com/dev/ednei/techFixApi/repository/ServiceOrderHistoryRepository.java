package com.dev.ednei.techFixApi.repository;

import com.dev.ednei.techFixApi.model.ServiceOrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceOrderHistoryRepository extends JpaRepository<ServiceOrderHistory, Long> {
}
