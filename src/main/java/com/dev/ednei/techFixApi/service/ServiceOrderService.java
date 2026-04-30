package com.dev.ednei.techFixApi.service;

import com.dev.ednei.techFixApi.DTOS.serviceOrder.ServiceOrderCreateDTO;
import com.dev.ednei.techFixApi.DTOS.serviceOrder.ServiceOrderFullDTO;
import com.dev.ednei.techFixApi.infra.exception.errors.EntityNotFoundException;
import com.dev.ednei.techFixApi.infra.exception.errors.InvalidParameterException;
import com.dev.ednei.techFixApi.model.ServiceOrder;
import com.dev.ednei.techFixApi.model.enums.StatusServiceOrder;
import com.dev.ednei.techFixApi.repository.ServiceOrderRepository;
import com.dev.ednei.techFixApi.repository.ServiceRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class ServiceOrderService {
    @Autowired
    private ServiceOrderRepository repository;

    @Autowired
    private ServiceRequestRepository serviceRequestRepository;

    public Page<ServiceOrderFullDTO>  findAllServiceOrders(Pageable pageable){
        var listServiceOrder =  repository.findAll(pageable);

        return listServiceOrder.map(ServiceOrderFullDTO::new);
    }

   public Page<ServiceOrderFullDTO> findAllByStatus(String status, Pageable pageable){
        StatusServiceOrder statusFormated;

        if(StatusServiceOrder.fromString(status) == null){
          throw new InvalidParameterException("O status " + status + " não existe");
        }

        statusFormated = StatusServiceOrder.fromString(status);

        var listServiceOrder = repository.findAllByStatus(statusFormated, pageable);

        return listServiceOrder.map(ServiceOrderFullDTO::new);
   }

    @Transactional
    public void saveServiceOrder(ServiceOrderCreateDTO serviceDTO){
        var code = generateIdentificationCode();

        if(!serviceRequestRepository.existsById(serviceDTO.serviceRequest())){
            throw new EntityNotFoundException("Solicitação de serviço com ID " +serviceDTO.serviceRequest() + " não encontrado");
        }

        var serviceOrder = repository.save(new ServiceOrder(serviceDTO, code));

    }




    private String generateIdentificationCode(){
        Random random = new Random();
        long code = ThreadLocalRandom.current().nextLong(10_000_000_000L);
        String formattedCode = String.format("%010d", code);

        while (repository.existsByIdentificationCode(String.valueOf(code))){
            code = ThreadLocalRandom.current().nextLong(10_000_000_000L);
            formattedCode = String.format("%010d", code);
        }

        return  formattedCode;
    }



}
