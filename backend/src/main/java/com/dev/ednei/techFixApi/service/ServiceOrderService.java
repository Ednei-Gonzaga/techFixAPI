package com.dev.ednei.techFixApi.service;

import com.dev.ednei.techFixApi.DTOS.serviceOrder.ServiceOrderDetailDTO;
import com.dev.ednei.techFixApi.DTOS.serviceOrder.ServiceOrderFullDTO;
import com.dev.ednei.techFixApi.DTOS.serviceOrder.ServiceOrderUpdateDTO;
import com.dev.ednei.techFixApi.infra.exceptions.errors.AccessForbiddenException;
import com.dev.ednei.techFixApi.infra.exceptions.errors.EntityNotFoundException;
import com.dev.ednei.techFixApi.infra.exceptions.errors.UnprocessableEntityException;
import com.dev.ednei.techFixApi.model.ServiceOrder;
import com.dev.ednei.techFixApi.model.User;
import com.dev.ednei.techFixApi.model.enums.RoleUser;
import com.dev.ednei.techFixApi.model.enums.ServiceOrderStatus;
import com.dev.ednei.techFixApi.repository.ServiceOrderRepository;
import com.dev.ednei.techFixApi.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Random;

@Service
public class ServiceOrderService {
    @Autowired
    private ServiceOrderRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void saveServiceOrder(Long serviceRequestId) {
        var code = generateCode();

        while (repository.existsByIdentificationCode(code)) {
            code = generateCode();
        }

        ServiceOrder serviceOrder = new ServiceOrder(serviceRequestId, code);
        repository.save(serviceOrder);
    }

    @Transactional
    public ServiceOrderFullDTO updateServiceOrder(Long serviceOrderId, ServiceOrderUpdateDTO orderDto, User user) {
        var serviceOrder = repository.findById(serviceOrderId);

        if (serviceOrder.isEmpty()) {
            throw new EntityNotFoundException("Não foi possivel encontrar Ordem de Serviço com ID " + serviceOrderId);
        }


        if (orderDto.userTechnical() == null && serviceOrder.get().getUserTechnical() == null
                && StringUtils.hasText(orderDto.status()) && serviceOrder.get().getStatus() == ServiceOrderStatus.PENDING
                && (ServiceOrderStatus.forValue(orderDto.status()) != ServiceOrderStatus.PENDING && ServiceOrderStatus.forValue(orderDto.status()) != ServiceOrderStatus.CANCELED)) {
            throw new UnprocessableEntityException("Para atualizar status é necessario atribuir o serviço a um técnico primeiro");
        }

        if(orderDto.userTechnical() != null){
            var userTechnical = userRepository.findById(orderDto.userTechnical());

            if (userTechnical.isEmpty()) {
                throw new EntityNotFoundException("Não foi possivel encontrar Tecnico com ID " + orderDto.userTechnical());
            }

            if(userTechnical.get().getRole() != RoleUser.TECHNICAL){
                throw new UnprocessableEntityException("A ordem de serviço deve ser atribuida somente ao usuario do tipo Tecnico");
            }
        }

        if (orderDto.userTechnical() != null && serviceOrder.get().getUserTechnical() != null && user.getRole() != RoleUser.MANAGER
                && !orderDto.userTechnical().equals(serviceOrder.get().getUserTechnical().getId())) {
            throw new AccessForbiddenException("Soemente o usuario do tipo Gerente pode atualizar ID do tecnico depois que já foi atribuido a Ordem de Serviço");
        }

        // Enum

        if (user.getRole() == RoleUser.TECHNICAL && ServiceOrderStatus.forValue(orderDto.status()) == (ServiceOrderStatus.DELIVERED)) {
            throw new AccessForbiddenException("Somente Gerente ou Atendente pode atualizar status para entregue");
        }

        if (user.getRole() == RoleUser.ATTENDANT) {
            if (StringUtils.hasText(orderDto.status()) && (ServiceOrderStatus.forValue(orderDto.status()) != ServiceOrderStatus.DELIVERED && ServiceOrderStatus.forValue(orderDto.status()) != ServiceOrderStatus.CANCELED)) {
                throw new AccessForbiddenException("Usuario do tipo Atendente só pode atualizar Status para Entregue ou Cancelado");
            }

            if (StringUtils.hasText(orderDto.status()) && ServiceOrderStatus.forValue(orderDto.status()) == ServiceOrderStatus.CANCELED
                    && serviceOrder.get().getStatus() != ServiceOrderStatus.PENDING && serviceOrder.get().getStatus() != ServiceOrderStatus.CANCELED) {
                throw new AccessForbiddenException("Atendente só pode Cancelar Ordem de Serviço se status for Pedente");
            }

            if (StringUtils.hasText(orderDto.status()) && ServiceOrderStatus.forValue(orderDto.status()) == ServiceOrderStatus.DELIVERED
                    && serviceOrder.get().getStatus() != ServiceOrderStatus.COMPLETED ) {
                throw new AccessForbiddenException("Atendente só pode atualizar para Entregue a Ordem de Serviço se status for Completado");
            }
        }

        serviceOrder.get().updateServiceOrder( orderDto);
        repository.save(serviceOrder.get());
        return new ServiceOrderFullDTO(serviceOrder.get());
    }

    public ServiceOrderDetailDTO findDetailsById(Long serviceOrderId) {
        var serviceOrderDetails = repository.getDetailsById(serviceOrderId);

        if (serviceOrderDetails.isEmpty()) {
            throw new EntityNotFoundException("Não foi possivel encontrar Ordem de Serviço com ID " + serviceOrderId);
        }

        return serviceOrderDetails.get();
    }

    public ServiceOrderDetailDTO findDetailsByCode(String code) {


        var serviceOrderDetails = repository.getDetailsByCode(code);

        if (serviceOrderDetails.isEmpty()) {
            throw new EntityNotFoundException("Não foi  encontrado Ordem de Serviço com Codigo " + code);
        }

        return serviceOrderDetails.get();
    }

   public ServiceOrderDetailDTO findDetailsByUserTechnical(String code){

   }

    private String generateCode() {
        var random = new Random();
        String code = "";

        for (int i = 0; i < 9; i++) {
            code += random.nextInt(10);
        }
        return code;
    }


}
