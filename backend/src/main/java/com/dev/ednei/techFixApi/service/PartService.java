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
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Random;

@Service
public class PartService {
    @Autowired
    private PartsRepository repository;

    // Metodos para Controller
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

    @Transactional
    public PartFullDTO enablePart(Long id) {
        var parts = repository.findById(id);

        if (parts.isEmpty()) {
            throw new EntityNotFoundException("Não foi encontrado Peça com o id: " + id);
        }

        parts.get().enablePart();

        repository.save(parts.get());
        return new PartFullDTO(parts.get());
    }

    public PartFullDTO findPartById(Long id) {
        var parts = repository.findById(id);

        if (parts.isEmpty()) {
            throw new EntityNotFoundException("Não foi encontrado Peça com o id: " + id);
        }

        return new PartFullDTO(parts.get());
    }

    public Page<PartFullDTO> logicFindAll(Boolean status, String part, Pageable pageable) {

        if(StringUtils.hasText(part)){
            return findAllByNameOrCodeSkuOrStatus(status, part, pageable);
        }else{
           return  findAllOrAllByStatus(status, pageable);
        }

    }

    public PartFullDTO updateStockQuantity(Long id, Integer quantityUsed) {
        var part = repository.findById(id);

        if (part.isEmpty()) {
            throw new EntityNotFoundException("Não foi encontrado Peça com o id: " + id);
        }

        if (part.get().getStockQuantity() < quantityUsed) {
            throw new UnprocessableEntityException("A quantidade usada e maior que a disponivel no estoque");
        }

        part.get().recordQuantityUsed(quantityUsed);

        repository.save(part.get());
        return new PartFullDTO(part.get());
    }


    //Metodos privados

    private String generateCodeSku() {
        var random = new Random();
        String codeSku = "";

        for (int i = 0; i < 4; i++) {
            codeSku += random.nextInt(9);
        }
        return codeSku;
    }

    private Page<PartFullDTO> findAllByNameOrCodeSkuOrStatus(Boolean status, String part, Pageable pageable) {
        List<Boolean> statusList;
        Page<Parts> parts;

        if (status != null) {
            statusList = List.of(status);
            parts = repository.findAllByNameOrCodeSkuOrStatus(statusList, part, pageable);
        } else {
            statusList = List.of(Boolean.FALSE, Boolean.TRUE);
            parts = repository.findAllByNameOrCodeSkuOrStatus(statusList, part, pageable);
        }


        return parts.map(PartFullDTO::new);
    }

    public Page<PartFullDTO> findAllOrAllByStatus(Boolean status, Pageable pageable) {
        List<Boolean> statusList;
        Page<Parts> parts;

        if(status != null) {
            statusList = List.of(status);
            parts = repository.findAllOrAllByStatus(statusList, pageable);
        }else {
            statusList = List.of(Boolean.FALSE, Boolean.TRUE);
            parts = repository.findAllOrAllByStatus(statusList, pageable);
        }

        return parts.map(PartFullDTO::new);
    }
}
