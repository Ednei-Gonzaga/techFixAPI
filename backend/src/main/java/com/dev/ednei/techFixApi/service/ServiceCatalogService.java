package com.dev.ednei.techFixApi.service;

import com.dev.ednei.techFixApi.DTOS.serviceCatolog.ServiceCatalogCreatedDTO;
import com.dev.ednei.techFixApi.DTOS.serviceCatolog.ServiceCatalogFullDTO;
import com.dev.ednei.techFixApi.DTOS.serviceCatolog.ServiceCatalogUpdateDTO;
import com.dev.ednei.techFixApi.infra.exceptions.errors.EntityNotFoundException;
import com.dev.ednei.techFixApi.infra.exceptions.errors.UnprocessableEntityException;
import com.dev.ednei.techFixApi.model.ServiceCatalog;
import com.dev.ednei.techFixApi.repository.ServiceCatalogRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class ServiceCatalogService {
    @Autowired
    private ServiceCatalogRepository repository;

    @Transactional
    public ServiceCatalogFullDTO saveServiceCatalog(ServiceCatalogCreatedDTO catalogDto) {
        ServiceCatalog serviceCatalog = new ServiceCatalog(catalogDto);

        if (catalogDto.costPrice() < 0) {
            throw new UnprocessableEntityException("O valor não pode ser menor que zero");
        }

        repository.save(serviceCatalog);

        return new ServiceCatalogFullDTO(serviceCatalog);
    }

    @Transactional
    public ServiceCatalogFullDTO updateServiceCatalog(Long id, ServiceCatalogUpdateDTO catalogDto) {
        var serviceCatalog = repository.findById(id);

        if (serviceCatalog.isEmpty()) {
            throw new EntityNotFoundException("Não foi possivel encontrar Catalogo de Serviço com ID " + id);
        }

        if (catalogDto != null && catalogDto.costPrice() != null && catalogDto.costPrice() < 0) {
            throw new UnprocessableEntityException("O valor não pode ser menor que zero");
        }

        serviceCatalog.get().updateCatalog(catalogDto);

        repository.save(serviceCatalog.get());

        return new ServiceCatalogFullDTO(serviceCatalog.get());
    }

    @Transactional
    public void disableServiceCatalog(Long id) {
        var serviceCatalog = repository.findById(id);

        if (serviceCatalog.isEmpty()) {
            throw new EntityNotFoundException("Não foi possivel encontrar Catalogo de Serviço com ID " + id);
        }

        serviceCatalog.get().disableCatalog();
        repository.save(serviceCatalog.get());

    }

    @Transactional
    public ServiceCatalogFullDTO activeServiceCatalog(Long id) {
        var serviceCatalog = repository.findById(id);

        if (serviceCatalog.isEmpty()) {
            throw new EntityNotFoundException("Não foi possivel encontrar Catalogo de Serviço com ID " + id);
        }

        serviceCatalog.get().activeCatalog();
        repository.save(serviceCatalog.get());

        return new ServiceCatalogFullDTO(serviceCatalog.get());
    }


    public Page<ServiceCatalogFullDTO> getAllOrAllByNameServiceCatalogs(String name, Pageable pageable){
        Page<ServiceCatalog> serviceCatalogs;

        if(StringUtils.hasText(name)){
            serviceCatalogs = repository.findAllByName(true ,name, pageable);
        }else{
           serviceCatalogs = repository.findAllByStatus(true ,pageable);
        }

        return serviceCatalogs.map(ServiceCatalogFullDTO::new);
    }
}
