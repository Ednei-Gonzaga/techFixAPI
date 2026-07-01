package com.dev.ednei.techFixApi.service;

import com.dev.ednei.techFixApi.DTOS.serviceRequest.ServiceRequestCreateDTO;
import com.dev.ednei.techFixApi.DTOS.serviceRequest.ServiceRequestFullDTO;
import com.dev.ednei.techFixApi.DTOS.serviceRequest.ServiceRequestUpdateDTO;
import com.dev.ednei.techFixApi.infra.exceptions.errors.EntityNotFoundException;
import com.dev.ednei.techFixApi.infra.exceptions.errors.InvalidParameterException;
import com.dev.ednei.techFixApi.model.ServiceRequests;
import com.dev.ednei.techFixApi.model.User;
import com.dev.ednei.techFixApi.model.enums.CategoryDevice;
import com.dev.ednei.techFixApi.repository.ClientRepository;
import com.dev.ednei.techFixApi.repository.ServiceOrderRepository;
import com.dev.ednei.techFixApi.repository.ServiceRequestRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ServiceRequestService {
    @Autowired
    private ServiceRequestRepository repository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ServiceOrderService serviceOrderService;

    //Metodos para usados em Controller

    @Transactional
    public ServiceRequestFullDTO saveRequest(ServiceRequestCreateDTO requestDto, User user) {
        if (!clientRepository.existsById(requestDto.client())) {
            throw new EntityNotFoundException("Cliente com ID  " + requestDto.client() + " não encontrado");
        }

        if (!checkCategoryDeviceIsCorrect(requestDto.category())) {
            throw new InvalidParameterException("Categoria de dispositivo '" + requestDto.category() + "' não disponivel");
        }

        var serviceRequest = new ServiceRequests(requestDto);
        repository.save(serviceRequest);

        serviceOrderService.saveServiceOrder(serviceRequest.getId(), user);

        return new ServiceRequestFullDTO(serviceRequest);
    }

    @Transactional
    public ServiceRequestFullDTO updateRequest(Long id, ServiceRequestUpdateDTO requestDto) {
        var serviceRequest = repository.findById(id);

        if(serviceRequest.isEmpty()){
            throw new EntityNotFoundException("Não foi encontrado Solicitação de Serviço com ID " + id);
        }

        if(StringUtils.hasText(requestDto.category())){
            if (!checkCategoryDeviceIsCorrect(requestDto.category())) {
                throw new InvalidParameterException("Categoria de dispositivo '" + requestDto.category() + "' não disponivel");
            }
        }

        if (!clientRepository.existsById(requestDto.client()) && requestDto.client() != null) {
            throw new EntityNotFoundException("Cliente com ID  " + requestDto.client() + " não encontrado");
        }

        serviceRequest.get().updateServiceRequest(requestDto);

        repository.save(serviceRequest.get());
        return new ServiceRequestFullDTO(serviceRequest.get());
    }

    public ServiceRequestFullDTO findByIdRequest(Long id){
        var serviceRequest = repository.findById(id);

        if(serviceRequest.isEmpty()){
            throw new EntityNotFoundException("Não foi encontrado Solicitação de Serviço com ID " + id);
        }

        return new ServiceRequestFullDTO(serviceRequest.get());
    }

    public Page<ServiceRequestFullDTO> findAllRequests(Pageable pageable){
        var serviceRequests = repository.findAll(pageable);

        return serviceRequests.map(ServiceRequestFullDTO::new);

    }

    public Page<ServiceRequestFullDTO> findAllByClient(Long id, Pageable pageable){
        if(!clientRepository.existsById(id)){
            throw new EntityNotFoundException("Cliente com ID  " + id + " não encontrado");
        }
        var serviceRequest = repository.findAllByClientId(id, pageable);
        return serviceRequest.map(ServiceRequestFullDTO::new);
    }


    //Metodos usados em outras classes

    //Metodos privados
    private boolean checkCategoryDeviceIsCorrect(String categoryCode) {
        var isCorrect = false;
        for (CategoryDevice category : CategoryDevice.values()) {
            if (category == CategoryDevice.toString(categoryCode)) {
                isCorrect = true;
            }
        }
        return isCorrect;
    }

}


