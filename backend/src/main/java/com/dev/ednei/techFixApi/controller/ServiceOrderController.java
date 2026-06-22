package com.dev.ednei.techFixApi.controller;

import com.dev.ednei.techFixApi.DTOS.serviceOrder.ServiceOrderDetailDTO;
import com.dev.ednei.techFixApi.DTOS.serviceOrder.ServiceOrderFullDTO;
import com.dev.ednei.techFixApi.DTOS.serviceOrder.ServiceOrderUpdateDTO;
import com.dev.ednei.techFixApi.model.User;
import com.dev.ednei.techFixApi.model.enums.CategoryDevice;
import com.dev.ednei.techFixApi.model.enums.ServiceOrderStatus;
import com.dev.ednei.techFixApi.service.ServiceOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2")
public class ServiceOrderController {
    @Autowired
    private ServiceOrderService serviceOrderService;

    @PutMapping("/service-orders/{id}")
    public ResponseEntity<ServiceOrderFullDTO> updateServiceOrder(@PathVariable Long id, @RequestBody ServiceOrderUpdateDTO orderDto, @AuthenticationPrincipal User user) {
        var serviceOrder = serviceOrderService.updateServiceOrder(id, orderDto, user);
        return ResponseEntity.ok().body(serviceOrder);
    }

    @GetMapping("/service-orders/{id}")
    public ResponseEntity<ServiceOrderDetailDTO> findDetailsById(@PathVariable Long id) {
        var serviceOrderDetails = serviceOrderService.findDetailsById(id);
        return ResponseEntity.ok().body(serviceOrderDetails);
    }

    @GetMapping("/service-orders/identification-code/{code}")
    public ResponseEntity<ServiceOrderDetailDTO> findDetailsById(@PathVariable String code) {
        var serviceOrderDetails = serviceOrderService.findDetailsByCode(code);
        return ResponseEntity.ok().body(serviceOrderDetails);
    }

    @GetMapping("/service-orders")
    public ResponseEntity<Page<ServiceOrderDetailDTO>> findAllOrderDetailsInProgress(@RequestParam(required = false, name = "status") String status, @RequestParam(required = false, name = "category") String category, Pageable pageable) {
         var serviceOrders = serviceOrderService. findAllOrAllByFilter(status, category, pageable);

        return ResponseEntity.ok().body(serviceOrders);
    }

    @GetMapping("/service-orders/my-tasks")
    public ResponseEntity<Page<ServiceOrderDetailDTO>> findAllOrderDetailsInProgressMyTask(@RequestParam(required = false, name = "status") String status, @RequestParam(required = false, name = "category") String category, @AuthenticationPrincipal User user, Pageable pageable) {
        var serviceOrders = serviceOrderService. findMyTask(status, category, user, pageable);

        return ResponseEntity.ok().body(serviceOrders);
    }

}
