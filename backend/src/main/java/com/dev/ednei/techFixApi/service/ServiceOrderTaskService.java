package com.dev.ednei.techFixApi.service;

import com.dev.ednei.techFixApi.DTOS.serviceOrderTask.ServiceOrderTaskCreatedDTO;
import com.dev.ednei.techFixApi.DTOS.serviceOrderTask.ServiceOrderTaskFullDTO;
import com.dev.ednei.techFixApi.infra.exceptions.errors.EntityNotFoundException;
import com.dev.ednei.techFixApi.infra.exceptions.errors.UnprocessableEntityException;
import com.dev.ednei.techFixApi.model.ServiceCatalog;
import com.dev.ednei.techFixApi.model.ServiceOrder;
import com.dev.ednei.techFixApi.model.ServiceOrderTask;
import com.dev.ednei.techFixApi.model.User;
import com.dev.ednei.techFixApi.model.enums.RoleUser;
import com.dev.ednei.techFixApi.repository.ServiceCatalogRepository;
import com.dev.ednei.techFixApi.repository.ServiceOrderRepository;
import com.dev.ednei.techFixApi.repository.ServiceOrderTaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceOrderTaskService {
    @Autowired
    private ServiceOrderTaskRepository repository;

    @Autowired
    private ServiceOrderRepository serviceOrderRepository;

    @Autowired
    private ServiceCatalogRepository serviceCatalogRepository;

    @Transactional
    public ServiceOrderTaskFullDTO saveServiceOrderTask(ServiceOrderTaskCreatedDTO taskDto, User user) {
        var serviceOrder = checkExistsServiceOrder(taskDto.serviceOrder());
        var serviceCatalog = checkExistsServiceCatalog(taskDto.serviceCatalog());
        var serviceOrderTask = new ServiceOrderTask(serviceOrder, serviceCatalog);

        if (serviceOrder.getUserTechnical() == null) {
            throw new UnprocessableEntityException("A Ordem de serviço com ID " + serviceOrder.getId() + " não possui um Tecnico responsavel. Adicione um tecnico a Ordem de Serviço primeiro");
        }

       if (user.getRole() == RoleUser.TECHNICAL) {
            if (!serviceOrder.getUserTechnical().getId().equals(user.getId())) {
                throw new UnprocessableEntityException("A Ordem de Serviço com ID " + serviceOrder.getId() + " não pertence ao tecnico que fez a solicitação");
            }
        }

        repository.save(serviceOrderTask);

        return new ServiceOrderTaskFullDTO(serviceOrderTask, serviceCatalog.getName());
    }

    public List<ServiceOrderTaskFullDTO> getAllByServiceOrderId(Long serviceOrderId) {
        checkExistsServiceOrder(serviceOrderId);

        var serviceOrderTasks = repository.findAllByServiceOrderId(serviceOrderId);

        return serviceOrderTasks.stream().map(tasks -> {
                    var catalog = serviceCatalogRepository.findById(tasks.getServiceCatalog().getId());
                    return new ServiceOrderTaskFullDTO(tasks, catalog.get().getName());
                }
        ).toList();

    }

    @Transactional
    public void  deleteServiceOrderTaskById(Long serviceOrderTaskId, User user) {
        var task =  repository.findById(serviceOrderTaskId);

        if(task.isEmpty()){
            throw new EntityNotFoundException("Não foi encontrado Tarefa de Ordem de Serviço com ID " + serviceOrderTaskId);
        }

        var serviceOrder = checkExistsServiceOrder(task.get().getServiceOrder().getId());

        if (user.getRole() == RoleUser.TECHNICAL) {
            if (!serviceOrder.getUserTechnical().getId().equals(user.getId())) {
                throw new UnprocessableEntityException("A Ordem de Serviço com ID " + serviceOrder.getId() + " não pertence ao tecnico que fez a solicitação");
            }
        }

        repository.deleteById(serviceOrderTaskId);
    }

    //METODOS PRIVADOS

    private ServiceOrder checkExistsServiceOrder(Long id) {
        var serviceOrder = serviceOrderRepository.findById(id);

        if (serviceOrder.isEmpty()) {
            throw new EntityNotFoundException("Ordem de Serviço com ID " + id + " não encontrado");
        }

        return serviceOrder.get();
    }

    private ServiceCatalog checkExistsServiceCatalog(Long id) {
        var serviceCatalog = serviceCatalogRepository.findById(id);

        if (serviceCatalog.isEmpty()) {
            throw new EntityNotFoundException("Catalogo de Serviço com ID " + id + " não encontrado");
        }

        return serviceCatalog.get();
    }
}
