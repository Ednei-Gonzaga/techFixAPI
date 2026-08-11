package com.dev.ednei.techFixApi.controller;

import com.dev.ednei.techFixApi.DTOS.paymentHistory.PaymentsHistoryFullDTO;
import com.dev.ednei.techFixApi.DTOS.serviceOrderHistory.ServiceOrderHistoryFullDTO;
import com.dev.ednei.techFixApi.service.PaymentsHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2")
public class PaymentHistoryController {
    @Autowired
    private PaymentsHistoryService paymentsHistoryService;

    @GetMapping("/service-order/{id}/payment/history")
    public ResponseEntity<Page<PaymentsHistoryFullDTO>> findAllByOs(@PathVariable(name = "id") Long id, Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(paymentsHistoryService.findAllByOrderService(id, pageable));
    }

}
