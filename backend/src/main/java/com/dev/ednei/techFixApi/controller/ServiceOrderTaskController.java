package com.dev.ednei.techFixApi.controller;

import com.dev.ednei.techFixApi.DTOS.serviceOrderTask.ServiceOrderTaskCreatedDTO;
import com.dev.ednei.techFixApi.DTOS.serviceOrderTask.ServiceOrderTaskFullDTO;
import com.dev.ednei.techFixApi.model.User;
import com.dev.ednei.techFixApi.service.ServiceOrderTaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2")
public class ServiceOrderTaskController {
    @Autowired
    private ServiceOrderTaskService serviceOrderTaskService;

    @PostMapping("/service-order-tasks")
    public ResponseEntity<ServiceOrderTaskFullDTO>  saveServiceOrderTask(@RequestBody @Valid ServiceOrderTaskCreatedDTO taskDto, @AuthenticationPrincipal User user) {
        var serviceOrderTask = serviceOrderTaskService.saveServiceOrderTask(taskDto, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(serviceOrderTask);
    }

    @GetMapping("/service-orders/{id}/task")
    public ResponseEntity<List<ServiceOrderTaskFullDTO>> getAllByServiceOrderId(@PathVariable(name = "id") Long id) {
        var serviceOrderTasks = serviceOrderTaskService.getAllByServiceOrderId(id);
        return ResponseEntity.status(HttpStatus.OK).body(serviceOrderTasks);
    }

    @DeleteMapping("/service-order-tasks/{id}")
    public ResponseEntity deleteServiceOrderTask(@PathVariable(name = "id") Long id, @AuthenticationPrincipal User user) {
        serviceOrderTaskService.deleteServiceOrderTaskById(id, user);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
