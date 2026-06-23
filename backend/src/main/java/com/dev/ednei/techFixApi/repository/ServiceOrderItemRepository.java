package com.dev.ednei.techFixApi.repository;

import com.dev.ednei.techFixApi.DTOS.serviceOrderItem.ServiceOrderItemFullDTO;
import com.dev.ednei.techFixApi.model.ServiceOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ServiceOrderItemRepository extends JpaRepository<ServiceOrderItem, Long> {

    boolean existsByServiceOrderIdAndPartId(Long serviceOrderId, Long partId);

    List<ServiceOrderItem> findAllByServiceOrderId(Long serviceOrderId);
}
