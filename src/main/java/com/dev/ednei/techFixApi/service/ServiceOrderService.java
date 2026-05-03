package com.dev.ednei.techFixApi.service;

import com.dev.ednei.techFixApi.DTOS.serviceOrder.ServiceOrderCreateDTO;
import com.dev.ednei.techFixApi.DTOS.serviceOrder.ServiceOrderFullDTO;
import com.dev.ednei.techFixApi.DTOS.serviceOrder.ServiceOrderUpdateDTO;
import com.dev.ednei.techFixApi.infra.exception.errors.EntityNotFoundException;
import com.dev.ednei.techFixApi.infra.exception.errors.ForbiddenOperationException;
import com.dev.ednei.techFixApi.infra.exception.errors.InvalidParameterException;
import com.dev.ednei.techFixApi.model.ServiceOrder;
import com.dev.ednei.techFixApi.model.User;
import com.dev.ednei.techFixApi.model.enums.RoleUser;
import com.dev.ednei.techFixApi.model.enums.StatusServiceOrder;
import com.dev.ednei.techFixApi.repository.ServiceOrderRepository;
import com.dev.ednei.techFixApi.repository.ServiceRequestRepository;
import com.dev.ednei.techFixApi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class ServiceOrderService {
    @Autowired
    private ServiceOrderRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ServiceRequestRepository serviceRequestRepository;

    public Page<ServiceOrderFullDTO> findAllServiceOrders(Pageable pageable) {
        var listServiceOrder = repository.findAll(pageable);

        return listServiceOrder.map(ServiceOrderFullDTO::new);
    }

    public Page<ServiceOrderFullDTO> findAllByStatus(String status, Pageable pageable) {
        StatusServiceOrder statusFormated;

        if (StatusServiceOrder.fromString(status) == null) {
            throw new InvalidParameterException("O status " + status + " não existe");
        }

        statusFormated = StatusServiceOrder.fromString(status);

        var listServiceOrder = repository.findAllByStatusOrderByDateTimeStartAsc(statusFormated, pageable);

        return listServiceOrder.map(ServiceOrderFullDTO::new);
    }

    public ServiceOrderFullDTO findById(Long id) {
        var serviceOrder = repository.findById(id);

        if (serviceOrder.isEmpty()) {
            throw new EntityNotFoundException("Ordem de Serviço com ID " + id + " não encontrado");
        }

        return new ServiceOrderFullDTO(serviceOrder.get());
    }

    public Page<ServiceOrderFullDTO> findTasksTechnical(User technical, Pageable pageable) {
        var serviceOrder = repository.findAllByUserId(technical.getId(), pageable);

        return serviceOrder.map(ServiceOrderFullDTO::new);
    }

    public Page<ServiceOrderFullDTO> findTasksTechnicalByStatus(User technical, String status, Pageable pageable) {
        if(StatusServiceOrder.fromString(status) == null){
            throw new InvalidParameterException("O status " + status + " não existe");
        }

        var serviceOrder = repository.findAllByUserIdAndStatus(technical.getId(), StatusServiceOrder.fromString(status), pageable);

        return serviceOrder.map(ServiceOrderFullDTO::new);
    }

    @Transactional
    public void saveServiceOrder(ServiceOrderCreateDTO serviceDTO) {
        var code = generateIdentificationCode();

        if (!serviceRequestRepository.existsById(serviceDTO.serviceRequest())) {
            throw new EntityNotFoundException("Solicitação de serviço com ID " + serviceDTO.serviceRequest() + " não encontrado");
        }

        var serviceOrder = repository.save(new ServiceOrder(serviceDTO, code));

    }


    @Transactional
    public void updateServiceOrder(Long idServiceOrder, ServiceOrderUpdateDTO serviceDTO) {
        var serviceOrder = repository.findById(idServiceOrder);

        if (serviceOrder.isEmpty()) {
            throw new EntityNotFoundException("Ordem de Serviço com ID " + idServiceOrder + " não encontrado");
        }

        if (serviceDTO.user() != null) {
            var user = userRepository.findById(serviceDTO.user());

            if (user.isEmpty()) {
                throw new EntityNotFoundException("Usuario com ID " + serviceDTO.user() + " não encontrado");
            }

            if (user.get().getRole() != RoleUser.TECHNICIAN) {
                throw new ForbiddenOperationException("Ordem de Serviço só pode ser atribuido a um usuario do tipo TECNICO");
            }
        }

        //Verifica se tem usuario atribuido a tarefa antes de atualizar status
        if (serviceOrder.get().getUser() == null && serviceDTO.user() == null && StringUtils.hasText(serviceDTO.status())) {

            if (StatusServiceOrder.fromString(serviceDTO.status()) == null) {
                throw new InvalidParameterException("O status " + serviceDTO.status() + " não existe");
            }

            if (!(StatusServiceOrder.CANCELED.name().equalsIgnoreCase(StatusServiceOrder.fromString(serviceDTO.status()).name()) || StatusServiceOrder.PENDING.name().equalsIgnoreCase(StatusServiceOrder.fromString(serviceDTO.status()).name()))) {
                throw new ForbiddenOperationException("Para atualizar status de PEDENTE/CANCELADO para outros, é preciso adicionar primeiro o tecnico");
            }

        }


        serviceOrder.get().updateServiceOrder(serviceDTO);
        repository.save(serviceOrder.get());
    }


    private String generateIdentificationCode() {
        Random random = new Random();
        long code = ThreadLocalRandom.current().nextLong(10_000_000_000L);
        String formattedCode = String.format("%010d", code);

        while (repository.existsByIdentificationCode(String.valueOf(code))) {
            code = ThreadLocalRandom.current().nextLong(10_000_000_000L);
            formattedCode = String.format("%010d", code);
        }

        return formattedCode;
    }

    private void checkExistsServiceOrder(Long id) {
        var hasServiceOrder = repository.existsById(id);

        if (!hasServiceOrder) {
            throw new EntityNotFoundException("Ordem de Serviço com ID " + id + " não encontrado");
        }

    }

}
