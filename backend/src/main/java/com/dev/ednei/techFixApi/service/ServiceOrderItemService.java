package com.dev.ednei.techFixApi.service;

import com.dev.ednei.techFixApi.DTOS.serviceOrderItem.RequestQuantityServiceOrderItem;
import com.dev.ednei.techFixApi.DTOS.serviceOrderItem.ServiceOrderItemCreatedDTO;
import com.dev.ednei.techFixApi.DTOS.serviceOrderItem.ServiceOrderItemFullDTO;
import com.dev.ednei.techFixApi.infra.exceptions.errors.AccessForbiddenException;
import com.dev.ednei.techFixApi.infra.exceptions.errors.ConflictDataException;
import com.dev.ednei.techFixApi.infra.exceptions.errors.EntityNotFoundException;
import com.dev.ednei.techFixApi.infra.exceptions.errors.UnprocessableEntityException;
import com.dev.ednei.techFixApi.model.Parts;
import com.dev.ednei.techFixApi.model.ServiceOrder;
import com.dev.ednei.techFixApi.model.ServiceOrderItem;
import com.dev.ednei.techFixApi.model.User;
import com.dev.ednei.techFixApi.model.enums.RoleUser;
import com.dev.ednei.techFixApi.repository.PartsRepository;
import com.dev.ednei.techFixApi.repository.ServiceOrderItemRepository;
import com.dev.ednei.techFixApi.repository.ServiceOrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceOrderItemService {
    @Autowired
    private ServiceOrderItemRepository repository;

    @Autowired
    private ServiceOrderRepository serviceOrderRepository;

    @Autowired
    private PartsRepository partsRepository;

    @Transactional
    public ServiceOrderItemFullDTO saveItem(ServiceOrderItemCreatedDTO itemDto, User user) {
        var part = checkExistsPartById(itemDto.part());
        var serviceOrder = checkExistsServiceOrderById(itemDto.serviceOrder());

        if (user.getRole() != RoleUser.MANAGER && !user.getId().equals(serviceOrder.getUserTechnical().getId())) {
            throw new AccessForbiddenException("A Ordem de Serviço com ID " + itemDto.serviceOrder() + " não pertence ao Usuario com ID " + user.getId() + " que realizou a requizição");
        }

        if (repository.existsByServiceOrderIdAndPartId(itemDto.serviceOrder(), itemDto.part())) {
            throw new ConflictDataException("Já existe peça com ID " + itemDto.part() + " para Ordem de Serviço de ID " + itemDto.serviceOrder() + ". Por favor altere somente a quantidade");
        }

        if (!part.isStatus()) {
            throw new UnprocessableEntityException("A peça adicionada com ID " + itemDto.part() + " esta desativada e não pode ser mais ultilizada");
        }

        if (itemDto.quantity() <= 0) {
            throw new UnprocessableEntityException("Quantidade não pode ser igual ou menor que zero");
        }

        if (itemDto.quantity() > part.getStockQuantity()) {
            throw new UnprocessableEntityException("Quantidade maior do que a disponivel. Tem apenas " + part.getStockQuantity() + " peças no estoque");
        }

        ServiceOrderItem serviceOrderItem = new ServiceOrderItem(itemDto, part, serviceOrder);
        part.dropInStock(itemDto.quantity());

        partsRepository.save(part);

        repository.save(serviceOrderItem);

        return new ServiceOrderItemFullDTO(serviceOrderItem);
    }

    @Transactional
    public ServiceOrderItemFullDTO updateItem(Long id, RequestQuantityServiceOrderItem requestQuantity, User user) {
        var serviceOrderItem = checkExistsServiceOrderItemById(id);
        var part = partsRepository.findById(serviceOrderItem.getPart().getId());
        var serviceOrder = checkExistsServiceOrderById(serviceOrderItem.getServiceOrder().getId());

        if (user.getRole() != RoleUser.MANAGER && !user.getId().equals(serviceOrder.getUserTechnical().getId())) {
            throw new AccessForbiddenException("A Ordem de Serviço com ID " + serviceOrder.getId() + " não pertence ao Usuario com ID " + user.getId() + " que realizou a requizição");
        }

        if (requestQuantity.quantity() <= 0) {
            throw new UnprocessableEntityException("Quantidade não pode ser igual ou menor que zero");
        }

        if (requestQuantity.quantity() < serviceOrderItem.getQuantity()) {
            part.get().returnToStock(serviceOrderItem.getQuantity() - requestQuantity.quantity());
        } else if (requestQuantity.quantity() > serviceOrderItem.getQuantity()) {

            if ((requestQuantity.quantity() - serviceOrderItem.getQuantity()) > part.get().getStockQuantity()) {
                throw new UnprocessableEntityException("Quantidade maior do que a disponivel. Tem apenas " + part.get().getStockQuantity() + " peças no estoque");
            }

            part.get().dropInStock(requestQuantity.quantity() - serviceOrderItem.getQuantity());
        }

        serviceOrderItem.updateQuantity(requestQuantity.quantity());
        partsRepository.save(part.get());

        repository.save(serviceOrderItem);
        return new ServiceOrderItemFullDTO(serviceOrderItem);
    }

    @Transactional
    public void deleteItem(Long id, User user) {
        var serviceOrderItem = checkExistsServiceOrderItemById(id);
        var part = partsRepository.findById(serviceOrderItem.getPart().getId());
        var serviceOrder = checkExistsServiceOrderById(serviceOrderItem.getServiceOrder().getId());

        if (user.getRole() != RoleUser.MANAGER && !user.getId().equals(serviceOrder.getUserTechnical().getId())) {
            throw new AccessForbiddenException("A Ordem de Serviço com ID " + serviceOrder.getId() + " não pertence ao Usuario com ID " + user.getId() + " que realizou a requizição");
        }

        if(serviceOrderItem.getQuantity() > 0){
            part.get().returnToStock(serviceOrderItem.getQuantity());
        }

        partsRepository.save(part.get());
        repository.delete(serviceOrderItem);
    }

    public List<ServiceOrderItemFullDTO> getByServiceOrder(Long id) {
        checkExistsServiceOrderById(id);

        var serviceOrderItems = repository.findAllByServiceOrderId(id);

        return serviceOrderItems .stream().map(ServiceOrderItemFullDTO::new).toList();
    }

    //Metodos privados para uso no proprio Service
    private ServiceOrder checkExistsServiceOrderById(Long serviceOrderId) {
        var serviceOrder = serviceOrderRepository.findById(serviceOrderId);

        if (serviceOrder.isEmpty()) {
            throw new EntityNotFoundException("Não foi possivel encontrar  Ordem de Serviço com ID " + serviceOrderId);
        }

        return serviceOrder.get();
    }

    private Parts checkExistsPartById(Long partId) {
        var part = partsRepository.findById(partId);

        if (part.isEmpty()) {
            throw new EntityNotFoundException("Não foi possivel encontrar Peça com ID " + partId);
        }

        return part.get();
    }

    private ServiceOrderItem checkExistsServiceOrderItemById(Long serviceOrderItemId) {
        var serviceOrderItem = repository.findById(serviceOrderItemId);

        if (serviceOrderItem.isEmpty()) {
            throw new EntityNotFoundException("Não foi possivel encontrar Item de Ordem de Serviço com ID " + serviceOrderItemId);
        }

        return serviceOrderItem.get();
    }
}
