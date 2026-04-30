package com.dev.ednei.techFixApi.controller;

import com.dev.ednei.techFixApi.DTOS.serviceOrder.ServiceOrderFullDTO;
import com.dev.ednei.techFixApi.model.enums.StatusServiceOrder;
import com.dev.ednei.techFixApi.service.ServiceOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

}
