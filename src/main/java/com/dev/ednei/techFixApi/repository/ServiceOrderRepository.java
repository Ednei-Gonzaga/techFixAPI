package com.dev.ednei.techFixApi.repository;

import com.dev.ednei.techFixApi.model.ServiceOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceOrderRepository extends JpaRepository<ServiceOrder, Long> {

}
