package com.dev.ednei.techFixApi.repository;

import com.dev.ednei.techFixApi.model.ServiceOrderTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceOrderTaskRepository extends JpaRepository<ServiceOrderTask, Long> {

    List<ServiceOrderTask> findAllByServiceOrderId(Long serviceOrderId);

}
