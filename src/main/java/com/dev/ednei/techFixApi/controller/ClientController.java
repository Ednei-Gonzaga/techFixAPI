package com.dev.ednei.techFixApi.controller;

import com.dev.ednei.techFixApi.DTOS.client.ClientCreateDTO;
import com.dev.ednei.techFixApi.DTOS.client.ClientFullDTO;
import com.dev.ednei.techFixApi.DTOS.client.ClientUpdateDTO;
import com.dev.ednei.techFixApi.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/clients")
public class ClientController {
    @Autowired
    private ClientService service;

    @GetMapping()
    public ResponseEntity findAllClientOrByCpf(@RequestParam(required = false) String cpf, Pageable pageable){

        if(StringUtils.hasText(cpf)){
            var client =  service.findByCpf(cpf);
            return ResponseEntity.status(HttpStatus.OK).body(client);
        }

        Page<ClientFullDTO> listClient = service.findAllClients(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(listClient);

    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Long id){
        var client = service.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(client);
    }

    @PostMapping()
    public ResponseEntity saveClient(@RequestBody @Valid ClientCreateDTO clientDTO){
        var client = service.saveClient(clientDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(client);
    }

    @PutMapping()
    public ResponseEntity updateClient(@RequestBody @Valid ClientUpdateDTO clientDTO){
        service.updateDataClient(clientDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
