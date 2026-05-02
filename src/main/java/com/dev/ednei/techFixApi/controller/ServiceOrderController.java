package com.dev.ednei.techFixApi.controller;

import com.dev.ednei.techFixApi.DTOS.serviceOrder.ServiceOrderFullDTO;
import com.dev.ednei.techFixApi.DTOS.serviceOrder.ServiceOrderUpdateDTO;
import com.dev.ednei.techFixApi.model.enums.StatusServiceOrder;
import com.dev.ednei.techFixApi.service.ServiceOrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/service-orders")
public class ServiceOrderController {

    @Autowired
    private ServiceOrderService service;

    @GetMapping()
    public ResponseEntity<Page<ServiceOrderFullDTO>> findAllServiceOrders(@RequestParam(required = false) String status, Pageable pageable){
        if(StringUtils.hasText(status)){
            var listServiceOrder = service.findAllByStatus(status, pageable);
            return ResponseEntity.status(HttpStatus.OK).body(listServiceOrder);
        }
        var listServiceOrder = service.findAllServiceOrders(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(listServiceOrder);
    }

    @GetMapping("/pending") //Rota para tecnico ver tarefas pedentes
    public ResponseEntity<Page<ServiceOrderFullDTO>> findAllServiceOrdersPending(Pageable pageable){
        var listServiceOrder = service.findAllByStatus(StatusServiceOrder.PENDING.name() , pageable);
        return ResponseEntity.status(HttpStatus.OK).body(listServiceOrder);
    }

    /*@GetMapping("/my-tasks")
    public ResponseEntity<Page<ServiceOrderFullDTO>> findTechnicianTasks(){

    }*/

    @PatchMapping()
    public ResponseEntity updateServiceOrder(@RequestBody @Valid ServiceOrderUpdateDTO serviceDTO){
        service.updateServiceOrder(serviceDTO);
        return  ResponseEntity.status(HttpStatus.OK).build();
    }
}
