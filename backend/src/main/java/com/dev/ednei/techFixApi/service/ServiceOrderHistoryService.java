package com.dev.ednei.techFixApi.service;

import com.dev.ednei.techFixApi.DTOS.serviceOrderHistory.ServiceOrderHistoryCreate;
import com.dev.ednei.techFixApi.DTOS.serviceOrderHistory.ServiceOrderHistoryFullDTO;
import com.dev.ednei.techFixApi.infra.exceptions.errors.EntityNotFoundException;
import com.dev.ednei.techFixApi.model.ServiceOrderHistory;
import com.dev.ednei.techFixApi.repository.ServiceOrderHistoryRepository;
import com.dev.ednei.techFixApi.repository.ServiceOrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ServiceOrderHistoryService {
    @Autowired
    private ServiceOrderHistoryRepository repository;

    @Autowired
    private ServiceOrderRepository serviceOrderRepository;

    //Metodos para controller
    public Page<ServiceOrderHistoryFullDTO> findAllByOrderService(Long osId, Pageable pageable) {
        checkExistServiceOrder(osId);
        var pageHistory = repository.findAllByServiceOrderId(osId, pageable);

        return pageHistory.map(ServiceOrderHistoryFullDTO::new);
    }


    //Metodos Usados em outras classes
    @Transactional
    public void saveHistoryOrder(ServiceOrderHistoryCreate serviceOrderHistory) {
        var historyOrder = new ServiceOrderHistory(serviceOrderHistory);
        repository.save(historyOrder);
    }

    //Metodos privados
    private void checkExistServiceOrder(Long serviceOrderId) {
        var existServiceOrder = repository.existsById(serviceOrderId);

        if(!existServiceOrder) {
            throw new EntityNotFoundException("Não foi encontrado nenhuma Ordem de Serviço com ID: " + serviceOrderId);
        } else {
            if(!repository.existsByServiceOrderId(serviceOrderId)) {
                throw new EntityNotFoundException("Não foi encontrado histórico para a Ordem de Serviço com ID: " + serviceOrderId);
            }
        }
    }
}
