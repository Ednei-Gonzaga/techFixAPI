package com.dev.ednei.techFixApi.controller;

import com.dev.ednei.techFixApi.DTOS.serviceRequest.ServiceRequestCreateDTO;
import com.dev.ednei.techFixApi.DTOS.serviceRequest.ServiceRequestFullDTO;
import com.dev.ednei.techFixApi.service.ServiceRequestService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/service-requests")
public class ServiceRequestController {
    @Autowired
    private ServiceRequestService service;

    @GetMapping()
    public ResponseEntity<Page<ServiceRequestFullDTO>> findAllServiceRequest(@RequestParam(required = false) String cpf, Pageable pageable){
        if(StringUtils.hasText(cpf)){
            var serviceRequest = service.findAllByCpfClient(cpf, pageable);
            return ResponseEntity.status(HttpStatus.OK).body(serviceRequest);
        }
        var serviceRequest = service.findAllServiceRequest(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(serviceRequest);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceRequestFullDTO> findById(@PathVariable Long id){
        var serviceRequest = service.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(serviceRequest);
    }

    @PostMapping()
    public ResponseEntity<ServiceRequestFullDTO> saveServiceRequest(@RequestBody @Valid ServiceRequestCreateDTO serviceDTO){
        var serviceRequest = service.saveService(serviceDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(serviceRequest);
    }
}
