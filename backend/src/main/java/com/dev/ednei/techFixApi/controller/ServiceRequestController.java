package com.dev.ednei.techFixApi.controller;

import com.dev.ednei.techFixApi.DTOS.serviceRequest.ServiceRequestCreateDTO;
import com.dev.ednei.techFixApi.DTOS.serviceRequest.ServiceRequestFullDTO;
import com.dev.ednei.techFixApi.DTOS.serviceRequest.ServiceRequestUpdateDTO;
import com.dev.ednei.techFixApi.DTOS.serviceRequest.ServiceRequestWithNotificationAndCodeDTO;
import com.dev.ednei.techFixApi.model.User;
import com.dev.ednei.techFixApi.service.ServiceRequestService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequestMapping("/api/v2")
public class ServiceRequestController {
    @Autowired
    private ServiceRequestService serviceRequestService;

    @PostMapping("/service-requests")
    public ResponseEntity<ServiceRequestWithNotificationAndCodeDTO> saveRequest(@RequestBody @Valid ServiceRequestCreateDTO requestDto, @AuthenticationPrincipal User user) throws IOException, InterruptedException {
        var serviceRequest = serviceRequestService.saveRequest(requestDto, user);
        return new ResponseEntity<>(serviceRequest, HttpStatus.CREATED);
    }

    @PutMapping("/service-requests/{id}")
    public ResponseEntity<ServiceRequestFullDTO> updateRequest(@PathVariable Long id, @RequestBody ServiceRequestUpdateDTO requestDto) {
        var serviceRequest = serviceRequestService.updateRequest(id, requestDto);
        return new ResponseEntity<>(serviceRequest, HttpStatus.OK);
    }

    @GetMapping("/service-requests/{id}")
    public ResponseEntity<ServiceRequestFullDTO> getByIdRequest(@PathVariable Long id) {
        var request = serviceRequestService.findByIdRequest(id);
        return new ResponseEntity<>(request, HttpStatus.OK);
    }

    @GetMapping("/service-requests")
    public ResponseEntity<Page<ServiceRequestFullDTO>> getAllRequest(Pageable pageable) {
        var request = serviceRequestService.findAllRequests(pageable);
        return new ResponseEntity<>(request, HttpStatus.OK);
    }

    @GetMapping("/service-requests/clients/{id}")
    public ResponseEntity<Page<ServiceRequestFullDTO>> getAllByClientRequest(@PathVariable Long id, Pageable pageable) {
         var serviceRequest = serviceRequestService.findAllByClient(id, pageable);
         return new ResponseEntity<>(serviceRequest, HttpStatus.OK);
    }

}
