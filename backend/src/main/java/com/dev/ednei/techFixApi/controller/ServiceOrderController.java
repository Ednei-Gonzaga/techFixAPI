package com.dev.ednei.techFixApi.controller;

import com.dev.ednei.techFixApi.DTOS.serviceOrder.ServiceOrderDetailDTO;
import com.dev.ednei.techFixApi.DTOS.serviceOrder.ServiceOrderFullDTO;
import com.dev.ednei.techFixApi.DTOS.serviceOrder.ServiceOrderUpdateDTO;
import com.dev.ednei.techFixApi.model.User;
import com.dev.ednei.techFixApi.service.ServiceOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2")
public class ServiceOrderController {
    @Autowired
    private ServiceOrderService serviceOrderService;

    @PutMapping("/service-orders/{id}")
    public ResponseEntity<ServiceOrderFullDTO>  updateServiceOrder(@PathVariable Long id, @RequestBody ServiceOrderUpdateDTO orderDto, @AuthenticationPrincipal User user) {
        var serviceOrder = serviceOrderService.updateServiceOrder(id, orderDto, user);
        return ResponseEntity.ok().body(serviceOrder);
    }

    @GetMapping("/service-orders/{id}")
    public ResponseEntity<ServiceOrderDetailDTO> findDetailsById(@PathVariable Long id){
        var serviceOrderDetails = serviceOrderService.findDetailsById(id);
        return ResponseEntity.ok().body(serviceOrderDetails);
    }

    @GetMapping("/service-orders/identification-code/{code}")
    public ResponseEntity<ServiceOrderDetailDTO> findDetailsById(@PathVariable String code){
        var serviceOrderDetails = serviceOrderService.findDetailsByCode(code);
        return ResponseEntity.ok().body(serviceOrderDetails);
    }
}
