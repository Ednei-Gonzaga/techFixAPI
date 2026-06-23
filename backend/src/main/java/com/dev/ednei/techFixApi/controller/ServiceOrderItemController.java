package com.dev.ednei.techFixApi.controller;

import com.dev.ednei.techFixApi.DTOS.serviceOrderItem.RequestQuantityServiceOrderItem;
import com.dev.ednei.techFixApi.DTOS.serviceOrderItem.ServiceOrderItemCreatedDTO;
import com.dev.ednei.techFixApi.DTOS.serviceOrderItem.ServiceOrderItemFullDTO;
import com.dev.ednei.techFixApi.model.User;
import com.dev.ednei.techFixApi.service.ServiceOrderItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/v2")
public class ServiceOrderItemController {
    @Autowired
    private ServiceOrderItemService serviceOrderItemService;

    @GetMapping("/service-order-items/service-order/{id}")
    public ResponseEntity<List<ServiceOrderItemFullDTO>> findAllByServiceOrderId(@PathVariable Long id){
        var serviceItem = serviceOrderItemService.getByServiceOrder(id);
        return ResponseEntity.status(HttpStatus.OK).body(serviceItem);
    }

    @PostMapping("/service-order-items")
    public ResponseEntity<ServiceOrderItemFullDTO> saveItem(@RequestBody  @Valid ServiceOrderItemCreatedDTO itemDto, @AuthenticationPrincipal User user) {
        var serviceOrderItem = serviceOrderItemService.saveItem(itemDto, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(serviceOrderItem);
    }

    @PutMapping("/service-order-items/{id}/quantity")
    public ResponseEntity<ServiceOrderItemFullDTO> updateQuantityItem(@PathVariable(name = "id") Long id, @RequestBody @Valid RequestQuantityServiceOrderItem itemQuantityDto, @AuthenticationPrincipal User user){
        var serviceOrderItem = serviceOrderItemService.updateItem(id, itemQuantityDto, user);
        return ResponseEntity.status(HttpStatus.OK).body(serviceOrderItem);
    }

    @DeleteMapping("/service-order-items/{id}")
    public ResponseEntity deleteItem(@PathVariable(name = "id") Long id, @AuthenticationPrincipal User user){
        serviceOrderItemService.deleteItem(id, user);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
