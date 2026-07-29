package com.dev.ednei.techFixApi.controller.forExternalAPI.EvolutionAPI;

import com.dev.ednei.techFixApi.DTOS.evolutionApi.InstanceDetailResponse;
import com.dev.ednei.techFixApi.DTOS.evolutionApi.InstanceQrcodeResponse;
import com.dev.ednei.techFixApi.service.externalApis.evolutionApi.EvolutionApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("api/v2/whatsapp")
public class WhatsappConnectionController {

    @Autowired
    private EvolutionApiService evolutionApiService;

    @GetMapping("instance/connect")
    public ResponseEntity<InstanceQrcodeResponse> generateConnection() throws IOException, InterruptedException {
        return ResponseEntity.ok(evolutionApiService.generateConnectionQrCode());
    }

    @DeleteMapping("/instance")
    public ResponseEntity deleteConnection() throws IOException, InterruptedException {
        evolutionApiService.deleteConnectionInstance();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("instance/detail")
    public ResponseEntity<InstanceDetailResponse> getConnectionDetail() throws IOException, InterruptedException {
        return ResponseEntity.ok(evolutionApiService.findDetailInstance());
    }

}
