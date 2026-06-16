package com.dev.ednei.techFixApi.controller;

import com.dev.ednei.techFixApi.DTOS.client.ClientCpfRequest;
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
@RequestMapping("/api/v2")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @PostMapping("/clients")
    public ResponseEntity<ClientFullDTO> createClient(@RequestBody @Valid  ClientCreateDTO clientDto) {
        var client = clientService.saveClient(clientDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(client);
    }

    @PutMapping("/clients/{id}")
    public ResponseEntity<ClientFullDTO> updateClient(@PathVariable("id") Long id, @RequestBody @Valid ClientUpdateDTO clientUpdateDto) {
        var client = clientService.updateClient(id, clientUpdateDto);
        return ResponseEntity.ok(client);
    }

    @GetMapping("/clients/{id}")
    public ResponseEntity<ClientFullDTO> finByIdClient(@PathVariable("id") Long id){
        var client = clientService.getClientById(id);
        return ResponseEntity.ok(client);
    }

    @PostMapping("/clients/cpf/search")
    public ResponseEntity<Page<ClientFullDTO>> findByCpfClient(@RequestBody @Valid ClientCpfRequest clientCpfRequest, Pageable pageable) {
        var client = clientService.getClientByCpf(clientCpfRequest, pageable);
        return ResponseEntity.ok(client);
    }

   @GetMapping("/clients")
   public ResponseEntity<Page<ClientFullDTO>> findAllNameOrCpf(@RequestParam(required = false, name = "name") String name, Pageable pageable){
       Page<ClientFullDTO> client = null;

       if(StringUtils.hasText(name)){
           client = clientService.getClientByName(name, pageable);
       }else{
           client = clientService.getAllClient(pageable);
       }
       return ResponseEntity.ok(client);
    }
}
