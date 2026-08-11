package com.dev.ednei.techFixApi.service;

import com.dev.ednei.techFixApi.DTOS.paymentHistory.PaymentsHistoryFullDTO;
import com.dev.ednei.techFixApi.infra.exceptions.errors.EntityNotFoundException;
import com.dev.ednei.techFixApi.model.Payment;
import com.dev.ednei.techFixApi.model.PaymentsHistory;
import com.dev.ednei.techFixApi.model.User;
import com.dev.ednei.techFixApi.model.enums.PaymentStatus;
import com.dev.ednei.techFixApi.repository.PaymentRepository;
import com.dev.ednei.techFixApi.repository.PaymentsHistoryRepository;
import com.dev.ednei.techFixApi.repository.ServiceOrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PaymentsHistoryService {
    @Autowired
    private PaymentsHistoryRepository repository;

    @Autowired
    private ServiceOrderRepository serviceOrderRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    //Metodos para controller
    public Page<PaymentsHistoryFullDTO> findAllByOrderService(Long osId, Pageable pageable) {
        checkExistServiceOrder(osId);
        var pageHistory = repository.findAllByServiceOrder(osId, pageable);

        return pageHistory.map(PaymentsHistoryFullDTO::new);
    }


    // Metodos para Outras classes
    @Transactional
    public void saveHistoryPayment(Payment payment, User user, PaymentStatus newStatus, PaymentStatus oldStatus, String notes) {
        var PaymentsHistory = new PaymentsHistory(payment, user, newStatus, oldStatus, notes);
        repository.save(PaymentsHistory);
    }

    //Metodos privados

    private void checkExistServiceOrder(Long serviceOrderId) {
        var existServiceOrder = serviceOrderRepository.existsById(serviceOrderId);

        if (!existServiceOrder) {
            throw new EntityNotFoundException("Não foi encontrado nenhuma Ordem de Serviço com ID: " + serviceOrderId);
        }
    }
}
