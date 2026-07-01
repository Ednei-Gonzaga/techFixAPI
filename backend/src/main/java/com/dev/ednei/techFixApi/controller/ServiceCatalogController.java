package com.dev.ednei.techFixApi.controller;

import com.dev.ednei.techFixApi.DTOS.serviceCatolog.ServiceCatalogCreatedDTO;
import com.dev.ednei.techFixApi.DTOS.serviceCatolog.ServiceCatalogFullDTO;
import com.dev.ednei.techFixApi.DTOS.serviceCatolog.ServiceCatalogUpdateDTO;
import com.dev.ednei.techFixApi.service.ServiceCatalogService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2")
public class ServiceCatalogController {
    @Autowired
    private ServiceCatalogService serviceCatalogService;

    @PostMapping("/service-catalogs")
    public ResponseEntity<ServiceCatalogFullDTO> saveServiceCatalog(@RequestBody @Valid ServiceCatalogCreatedDTO catalogDto) {
        var serviceCatalog = serviceCatalogService.saveServiceCatalog(catalogDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(serviceCatalog);
    }

    @PutMapping("/service-catalogs/{id}")
    public ResponseEntity<ServiceCatalogFullDTO> saveServiceCatalog(@PathVariable(name = "id") Long id, @RequestBody ServiceCatalogUpdateDTO catalogDto) {
        var serviceCatalog = serviceCatalogService.updateServiceCatalog(id, catalogDto);
        return ResponseEntity.ok().body(serviceCatalog);
    }

    @DeleteMapping("/service-catalogs/{id}")
    public ResponseEntity disableServiceCatalog(@PathVariable(name = "id") Long id) {
        serviceCatalogService.disableServiceCatalog(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/service-catalogs/{id}/enable")
    public ResponseEntity activeServiceCatalog(@PathVariable(name = "id") Long id) {
        var serviceCatalog = serviceCatalogService.activeServiceCatalog(id);
        return ResponseEntity.ok().body(serviceCatalog);
    }

    @GetMapping("/service-catalogs")
    public ResponseEntity<Page<ServiceCatalogFullDTO>> getAllServiceCatalogs(@RequestParam(required = false, name = "name") String name, Pageable pageable) {
        var serviceCatalogs = serviceCatalogService.getAllOrAllByNameServiceCatalogs(name, pageable);
        return ResponseEntity.ok().body(serviceCatalogs);
    }

    @GetMapping("/admin/service-catalogs")
    public ResponseEntity<Page<ServiceCatalogFullDTO>> getAllServiceCatalogs(
            @RequestParam(required = false, name = "name") String name,
            @RequestParam(required = false, name = "status") Boolean status,
            Pageable pageable) {

        var serviceCatalogs = serviceCatalogService.getAllOrAllByNameOrStatus(status, name, pageable);
        return ResponseEntity.ok().body(serviceCatalogs);
    }
}
