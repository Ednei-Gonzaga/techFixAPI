package com.dev.ednei.techFixApi.controller;

import com.dev.ednei.techFixApi.DTOS.serviceOrderHistory.ServiceOrderHistoryFullDTO;
import com.dev.ednei.techFixApi.service.ServiceOrderHistoryService;
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
@RequestMapping("api/v2")
public class serviceOrderHistoryController {
    @Autowired
    private ServiceOrderHistoryService serviceOrderHistoryService;

    @GetMapping("/service-order/{id}/history/updates")
    public ResponseEntity<Page<ServiceOrderHistoryFullDTO>> findAllByOs(@PathVariable(name = "id") Long id, Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(serviceOrderHistoryService.findAllByOrderService(id, pageable));
    }
}
