package com.dev.ednei.techFixApi.repository;

import com.dev.ednei.techFixApi.model.ServiceOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceOrderItemRepository extends JpaRepository<ServiceOrderItem, Long> {
}
