package com.dev.ednei.techFixApi.service;

import com.dev.ednei.techFixApi.DTOS.serviceOrder.ServiceOrderCreateDTO;
import com.dev.ednei.techFixApi.DTOS.serviceRequest.ServiceRequestCreateDTO;
import com.dev.ednei.techFixApi.DTOS.serviceRequest.ServiceRequestFullDTO;
import com.dev.ednei.techFixApi.infra.exception.errors.EntityNotFoundException;
import com.dev.ednei.techFixApi.model.ServiceRequest;
import com.dev.ednei.techFixApi.repository.ServiceRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ServiceRequestService {
    @Autowired
    private ServiceRequestRepository repository;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ServiceOrderService serviceOrderService;

    public ServiceRequestFullDTO saveService(ServiceRequestCreateDTO serviceDTO) {
        checkExistsClient(serviceDTO.client());

        var serviceRequest = repository.save(new ServiceRequest(serviceDTO));

        serviceOrderService.saveServiceOrder(new ServiceOrderCreateDTO(serviceRequest.getId()));

        return new ServiceRequestFullDTO(serviceRequest);

    }

    public Page<ServiceRequestFullDTO> findAllServiceRequest(Pageable pageable){
        var listServiceRequest = repository.findAll(pageable);

        return listServiceRequest.map(ServiceRequestFullDTO::new);
    }

    public ServiceRequestFullDTO findById(Long id){
        checkExistsServiceRequest(id);
        return new ServiceRequestFullDTO(repository.findById(id).get());
    }

    public Page<ServiceRequestFullDTO> findAllByCpfClient(String cpf, Pageable pageable){
        checkExistClientByCpf(cpf);
        var listServiceRequest = repository.findAllByCpfClient(cpf, pageable);
        return listServiceRequest.map(ServiceRequestFullDTO::new);
    }


    private void checkExistsClient(Long id) {
        var hasClient = clientService.existsClient(id);

        if (!hasClient) {
            throw new EntityNotFoundException("Cliente com ID " + id + " não existe.");
        }
    }

    private void checkExistClientByCpf(String cpf){
        var hasClient = clientService.existsClientByCpf(cpf);

        if (!hasClient) {
            throw new EntityNotFoundException("Cliente com CPF " + cpf + " não encontrado");
        }
    }

    private void checkExistsServiceRequest(Long id){
        var hasServiceRequest = repository.existsById(id);

        if(!hasServiceRequest){
            throw  new EntityNotFoundException("Solicitação de Serviço com ID " +id+" não existe");
        }
    }
}
