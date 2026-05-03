package com.dev.ednei.techFixApi.controller;

import com.dev.ednei.techFixApi.DTOS.serviceOrder.ServiceOrderFullDTO;
import com.dev.ednei.techFixApi.DTOS.serviceOrder.ServiceOrderUpdateDTO;
import com.dev.ednei.techFixApi.model.User;
import com.dev.ednei.techFixApi.model.enums.StatusServiceOrder;
import com.dev.ednei.techFixApi.service.ServiceOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/service-orders")
public class ServiceOrderController {

    @Autowired
    private ServiceOrderService service;

    @GetMapping()//Rota para ADMIN ver todas tarefas e filtra por Status
    public ResponseEntity<Page<ServiceOrderFullDTO>> findAllServiceOrders(@RequestParam(required = false) String status, Pageable pageable) {
        if (StringUtils.hasText(status)) {
            var listServiceOrder = service.findAllByStatus(status, pageable);
            return ResponseEntity.status(HttpStatus.OK).body(listServiceOrder);
        }
        var listServiceOrder = service.findAllServiceOrders(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(listServiceOrder);
    }

    @GetMapping("/pending")//Rota para técnico ver tarefas pedentes, sem ninguema atribuido
    public ResponseEntity<Page<ServiceOrderFullDTO>> findAllServiceOrdersPending(Pageable pageable) {
        var listServiceOrder = service.findAllByStatus(StatusServiceOrder.PENDING.name(), pageable);
        return ResponseEntity.status(HttpStatus.OK).body(listServiceOrder);
    }

    @GetMapping("/{id}")// busca por id
    public ResponseEntity<ServiceOrderFullDTO> findById(@PathVariable Long id) {
        var serviceOrder = service.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(serviceOrder);
    }

    @GetMapping("/my-tasks")//busca tarefa especifica de cada Tecnico e por Status
    public ResponseEntity<Page<ServiceOrderFullDTO>> findTechnicianTasks(@AuthenticationPrincipal User user, @RequestParam(required = false) String status, Pageable pageable) {

        if (StringUtils.hasText(status)) {
            var serviceOrder = service.findTasksTechnicalByStatus(user, status, pageable);
            return ResponseEntity.status(HttpStatus.OK).body(serviceOrder);
        }

        var serviceOrder = service.findTasksTechnical(user, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(serviceOrder);
    }

    @PatchMapping("/{idServiceOrder}")//atualiza pelo ID
    public ResponseEntity updateServiceOrder(@PathVariable Long idServiceOrder, @RequestBody ServiceOrderUpdateDTO serviceDTO) {
        service.updateServiceOrder(idServiceOrder, serviceDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
