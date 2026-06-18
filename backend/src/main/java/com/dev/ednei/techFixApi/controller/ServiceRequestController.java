package com.dev.ednei.techFixApi.controller;

import com.dev.ednei.techFixApi.DTOS.serviceRequest.ServiceRequestCreateDTO;
import com.dev.ednei.techFixApi.DTOS.serviceRequest.ServiceRequestFullDTO;
import com.dev.ednei.techFixApi.DTOS.serviceRequest.ServiceRequestUpdateDTO;
import com.dev.ednei.techFixApi.service.ServiceRequestService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v2")
public class ServiceRequestController {
    @Autowired
    private ServiceRequestService serviceRequestService;

    @PostMapping("/service-requests")
    public ResponseEntity<ServiceRequestFullDTO> saveRequest(@RequestBody @Valid ServiceRequestCreateDTO requestDto){
        var serviceRequest = serviceRequestService.saveRequest(requestDto);
        return new ResponseEntity<>(serviceRequest, HttpStatus.OK);
    }

    @PutMapping("/service-request/{id}")
    public ResponseEntity<ServiceRequestFullDTO> updateRequest(@PathVariable Long id, @RequestBody  ServiceRequestUpdateDTO requestDto){
        var serviceRequest = serviceRequestService.updateRequest(id, requestDto);
        return new ResponseEntity<>(serviceRequest, HttpStatus.OK);
    }

    @GetMapping("/service-request/{id}")
    public ResponseEntity<ServiceRequestFullDTO> getByIdRequest(@PathVariable Long id){
        var request = serviceRequestService.findByIdRequest(id);
        return new ResponseEntity<>(request,HttpStatus.OK);
    }

    @GetMapping("/service-request")
    public ResponseEntity<Page<ServiceRequestFullDTO>> getAllRequest(Pageable pageable){
        var request = serviceRequestService.findAllRequests(pageable);
        return new ResponseEntity<>(request, HttpStatus.OK);
    }
}
