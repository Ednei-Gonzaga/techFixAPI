package com.dev.ednei.techFixApi.service;

import com.dev.ednei.techFixApi.DTOS.part.PartCreateDTO;
import com.dev.ednei.techFixApi.DTOS.part.PartFullDTO;
import com.dev.ednei.techFixApi.DTOS.part.PartUpdateDTO;
import com.dev.ednei.techFixApi.infra.exceptions.errors.EntityNotFoundException;
import com.dev.ednei.techFixApi.infra.exceptions.errors.UnprocessableEntityException;
import com.dev.ednei.techFixApi.model.Parts;
import com.dev.ednei.techFixApi.repository.PartsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class PartService {
    @Autowired
    private PartsRepository repository;

    @Transactional
    public PartFullDTO saveParte(PartCreateDTO partDto) {
        var existsCode = true;
        var codeSku = generateCodeSku();

        while (existsCode) {
            if (!repository.existsByCodeSku(codeSku)) {
                existsCode = false;
            } else {
                codeSku = generateCodeSku();
            }

        }

        var part = new Parts(partDto, codeSku);
        repository.save(part);

        return new PartFullDTO(part);
    }

    @Transactional
    public PartFullDTO updatePart(Long id, PartUpdateDTO partDto) {
        var parts = repository.findById(id);

        if (parts.isEmpty()) {
            throw new EntityNotFoundException("Não foi encontrado Peça com o id: " + id);
        }

        parts.get().updatePart(partDto);
        repository.save(parts.get());

        return new PartFullDTO(parts.get());
    }

    @Transactional
    public void disablePart(Long id) {
        var parts = repository.findById(id);

        if (parts.isEmpty()) {
            throw new EntityNotFoundException("Não foi encontrado Peça com o id: " + id);
        }

        parts.get().disablePart();

        repository.save(parts.get());
    }

    public PartFullDTO findPartById(Long id) {
        var parts = repository.findById(id);

        if (parts.isEmpty()) {
            throw new EntityNotFoundException("Não foi encontrado Peça com o id: " + id);
        }

        return new PartFullDTO(parts.get());
    }

    public Page<PartFullDTO> findAllParts(Pageable pageable) {
        var parts = repository.findAll(pageable);

        return parts.map(PartFullDTO::new);
    }

    public  Page<PartFullDTO> findAllByNameOrCodeSku(String part, Pageable pageable) {
        var parts = repository.findAllByNameOrCodeSku( part, pageable);
        return parts.map(PartFullDTO::new);
    }

    public PartFullDTO updateStockQuantity(Long id, Integer quantityUsed){
        var part = repository.findById(id);

        if (part.isEmpty()) {
            throw new EntityNotFoundException("Não foi encontrado Peça com o id: " + id);
        }

        if(part.get().getStockQuantity() < quantityUsed){
            throw new UnprocessableEntityException("A quantidade usada e maior que a disponivel no estoque");
        }

        part.get().recordQuantityUsed(quantityUsed);

        repository.save(part.get());
        return new PartFullDTO(part.get());
    }


    private String generateCodeSku() {
        var random = new Random();
        String codeSku = "";

        for (int i = 0; i < 4; i++) {
            codeSku += random.nextInt(9);
        }
        return codeSku;
    }


}
