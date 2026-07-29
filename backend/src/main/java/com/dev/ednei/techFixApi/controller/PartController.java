package com.dev.ednei.techFixApi.controller;

import com.dev.ednei.techFixApi.DTOS.part.PartCreateDTO;
import com.dev.ednei.techFixApi.DTOS.part.PartFullDTO;
import com.dev.ednei.techFixApi.DTOS.part.PartUpdateDTO;
import com.dev.ednei.techFixApi.DTOS.part.RequestRecordQuantityStock;
import com.dev.ednei.techFixApi.service.PartService;
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
public class PartController {
    @Autowired
    private PartService partService;

    @PostMapping(value = "/parts")
    public ResponseEntity<PartFullDTO> savePart(@RequestBody @Valid PartCreateDTO partCreateDto){
        var part = partService.saveParte(partCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(part);
    }

    @PutMapping("/parts/{id}")
    public ResponseEntity<PartFullDTO> updateFullPart(@PathVariable Long id, @RequestBody PartUpdateDTO partUpdateDto){
        var part = partService.updatePart(id, partUpdateDto);
        return ResponseEntity.ok(part);
    }

    @DeleteMapping("/parts/{id}")
    public ResponseEntity disablePart(@PathVariable Long id){
        this.partService.disablePart(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/parts/{id}")
    public ResponseEntity<PartFullDTO> findPartById(@PathVariable Long id){
        var part = partService.findPartById(id);
        return ResponseEntity.ok(part);
    }

    @GetMapping("/parts")
    public ResponseEntity<Page<PartFullDTO>> findAllPartsOrNameOrCode(
            @RequestParam(required = false, name = "nameOrCode") String nameOrCode,
            @RequestParam(required = false, name = "status") Boolean status,
            Pageable pageable){

        Page<PartFullDTO> parts = partService.logicFindAll(status, nameOrCode, pageable);
        return ResponseEntity.ok(parts);
    }

    @PatchMapping("/parts/{id}/quantity")
    public ResponseEntity<PartFullDTO> recordQuantityUsed(@PathVariable Long id, @RequestBody @Valid RequestRecordQuantityStock quantityStockUsed){
        var part = partService.updateStockQuantity(id, quantityStockUsed.quantityUsed());
        return ResponseEntity.ok(part);
    }

    @PatchMapping("/parts/{id}/enable")
    public ResponseEntity<PartFullDTO> enablePart(@PathVariable Long id){
        var part = partService.enablePart(id);
        return ResponseEntity.ok(part);
    }
}


