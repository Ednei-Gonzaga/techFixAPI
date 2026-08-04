package com.dev.ednei.techFixApi.service;

import com.dev.ednei.techFixApi.DTOS.payments.PaymentsFullDTO;
import com.dev.ednei.techFixApi.DTOS.payments.PaymentsRequestStatus;
import com.dev.ednei.techFixApi.DTOS.payments.PaymentsUpdateDTO;
import com.dev.ednei.techFixApi.infra.exceptions.errors.EntityNotFoundException;
import com.dev.ednei.techFixApi.infra.exceptions.errors.InvalidParameterException;
import com.dev.ednei.techFixApi.infra.exceptions.errors.UnprocessableEntityException;
import com.dev.ednei.techFixApi.model.*;
import com.dev.ednei.techFixApi.model.enums.PaymentMethod;
import com.dev.ednei.techFixApi.model.enums.PaymentStatus;
import com.dev.ednei.techFixApi.model.enums.ServiceOrderStatus;
import com.dev.ednei.techFixApi.repository.PaymentRepository;
import com.dev.ednei.techFixApi.repository.ServiceOrderItemRepository;
import com.dev.ednei.techFixApi.repository.ServiceOrderRepository;
import com.dev.ednei.techFixApi.repository.ServiceOrderTaskRepository;
import jakarta.transaction.Transactional;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository repository;

    @Autowired
    private ServiceOrderRepository serviceOrderRepository;

    @Autowired
    private ServiceOrderTaskRepository serviceOrderTaskRepository;

    @Autowired
    private ServiceOrderItemRepository serviceOrderItemRepository;

    @Autowired
    private PaymentsHistoryService paymentsHistoryService;

    //Metodos para Controller

    @Transactional
    public PaymentsFullDTO updatePayment(Long id, @NonNull PaymentsUpdateDTO paymentDto, User user) {
        var payment = repository.findByServiceOrderId(id);

        if (payment.isEmpty()) {
            throw new EntityNotFoundException("Não existe Dados de Pagamento para serviço com ID " + id);
        }

        var status= payment.get().getPaymentStatus();

        if (StringUtils.hasText(paymentDto.paymentMethod()) && PaymentMethod.forValue(paymentDto.paymentMethod()) == null) {
            throw new InvalidParameterException("Metodo de Pagamento " + paymentDto.paymentMethod() + " nao existe");
        }

        if ( payment.get().getPaymentStatus() == PaymentStatus.PAID) {
            throw new UnprocessableEntityException("Não e possivel alterar pagamento com Status PAGO");
        }

        if ( payment.get().getPaymentStatus() == PaymentStatus.CANCELED) {
            throw new UnprocessableEntityException("Não e possivel alterar pagamento com Status CANCELADO");
        }

        payment.get().updateMethodAndDiscount(paymentDto);
        repository.save( payment.get());

        String notes = "Dados Pagamento ajustados manualmente (Método de Pagamento e/ou Desconto).";
        paymentsHistoryService.saveHistoryPayment( payment.get(), user, status, status, notes);

        return new PaymentsFullDTO( payment.get());
    }

    @Transactional
    public PaymentsFullDTO updatePaymentStatus(Long id, PaymentsRequestStatus requestStatus, User user)   {
        var payment = repository.findByServiceOrderId(id);

        if (payment.isEmpty()) {
            throw new EntityNotFoundException("Não existe Dados de Pagamento para serviço com ID " + id);
        }

        var oldStatus = payment.get().getPaymentStatus();

        if (PaymentStatus.forValue(requestStatus.paymentStatus()) == null) {
            throw new InvalidParameterException("Status de Pagamento " + requestStatus.paymentStatus() + " nao existe");
        }

        if (payment.get().getPaymentStatus() == PaymentStatus.PAID || payment.get().getPaymentStatus() == PaymentStatus.CANCELED) {
            throw new UnprocessableEntityException("Este pagamento já foi encerrado (Pago ou Cancelado) e não pode sofrer novas alterações.");
        }

        if (payment.get().getPaymentMethod() == null && PaymentStatus.forValue(requestStatus.paymentStatus()) == PaymentStatus.PAID) {
            throw new UnprocessableEntityException("Para atualizar status para PAGO e necessario preencher primeiro qual foi o Metodo de Pagamento");
        }

        //Verifica se Service Order foi completado
        if(!checkServiceOrderIsCompleted(id) && PaymentStatus.forValue(requestStatus.paymentStatus()) == PaymentStatus.PAID ){
            throw new UnprocessableEntityException("A Ordem de Serviço atribuido a esse pagamento não foi Completado Ainda. Finalize a OS primeiro antes de concluir pagamento");
        }

        payment.get().updateStatus(PaymentStatus.forValue(requestStatus.paymentStatus()));
        repository.save(payment.get());

        String notes = "Status Pagamento ajustado manualmente para " + payment.get().getPaymentStatus().portugueseOption;
        paymentsHistoryService.saveHistoryPayment(payment.get(), user, oldStatus, payment.get().getPaymentStatus(), notes);

        return new PaymentsFullDTO(payment.get());
    }

    public PaymentsFullDTO findByIdServiceOrder(Long serviceOrderId) {
        var payment = repository.findByServiceOrderId(serviceOrderId);

        if (payment.isEmpty()) {
            throw new EntityNotFoundException("Não existe Dados de Pagamento para serviço com ID " + serviceOrderId);
        }

        return new PaymentsFullDTO(payment.get());
    }


    // Metodos para outras classes dentro do JAVA
    public boolean existPaymentByServiceOrderId(Long serviceOrderId) {
        return repository.existsByServiceOrderId(serviceOrderId);
    }

    @Transactional
    public void createPayment(Long serviceOrderId, User user) {
        var serviceOrder = checkExistsServiceOrder(serviceOrderId);

        Double partsAmount = findPartsAmount(serviceOrderId);
        Double laborAmount = findLaborAmount(serviceOrderId);

        var payment = new Payment(laborAmount, partsAmount, serviceOrder);
        repository.save(payment);

        String notes = "Pagamento gerado automaticamente após a conclusão da Ordem de Serviço";
        paymentsHistoryService.saveHistoryPayment(payment, user, PaymentStatus.PENDING, PaymentStatus.PENDING, notes);
    }

    @Transactional
    public void autoAdjustPayment(Long serviceOrderId, User user) {
        var payment = repository.findByServiceOrderId(serviceOrderId);

        var status = payment.get().getPaymentStatus();

        Double partsAmount = findPartsAmount(serviceOrderId);
        Double laborAmount = findLaborAmount(serviceOrderId);

        payment.get().autoAdjustPayments(laborAmount, partsAmount);
        repository.save(payment.get());

        String notes = "Ajuste automático nos valores da cobrança devido a alterações na Ordem de Serviço";
        paymentsHistoryService.saveHistoryPayment(payment.get(), user, status, status, notes);
    }

    @Transactional
    public void canceledPayments(Long serviceOrderId, User user) {
        var payment = repository.findByServiceOrderId(serviceOrderId);
        var oldStatus = payment.get().getPaymentStatus();

        payment.get().updateStatus(PaymentStatus.CANCELED);

        repository.save(payment.get());

        String notes = "Pagamento CANCELADO automaticamente após o CANCELAMENTO da Ordem de Serviço";
        paymentsHistoryService.saveHistoryPayment(payment.get(), user, oldStatus, payment.get().getPaymentStatus(), notes);
    }

    public Optional<Payment> findByIdServiceOrderWithoutException(Long serviceOrderId) {
        return repository.findByServiceOrderId(serviceOrderId);
    }


    //Metodos privados

    private ServiceOrder checkExistsServiceOrder(Long id) {
        var serviceOrder = serviceOrderRepository.findById(id);

        if (serviceOrder.isEmpty()) {
            throw new EntityNotFoundException("Ordem de Serviço com ID " + id + " não encontrado");
        }

        return serviceOrder.get();
    }

    private Double findPartsAmount(Long serviceOrderId) {
        var serviceOrderItem = serviceOrderItemRepository.findAllByServiceOrderId(serviceOrderId);
        Double partsAmount = 0.0;

        if (serviceOrderItem.size() > 0) {
            for (ServiceOrderItem item : serviceOrderItem) {
                partsAmount += item.getSubTotal();
            }
        }

        return partsAmount;
    }

    private Double findLaborAmount(Long serviceOrderId) {
        Double laborAmount = 0.0;
        var serviceOrderTask = serviceOrderTaskRepository.findAllByServiceOrderId(serviceOrderId);

        for (ServiceOrderTask task : serviceOrderTask) {
            laborAmount += task.getPriceApplied();
        }

        return laborAmount;
    }

    private Payment checkExistsPaymentById(Long id) {
        var payment = repository.findById(id);

        if (payment.isEmpty()) {
            throw new EntityNotFoundException("Pagamento com ID " + id + " nao encontrado");
        }

        return payment.get();
    }

    // Não permitir atualizar Pagamaneto para pago Se a OS não for COMPLETED
    private Boolean checkServiceOrderIsCompleted(Long id){
        var serviceOrder = serviceOrderRepository.findById(id);

        if (serviceOrder.get().getStatus() == ServiceOrderStatus.COMPLETED) {
            return true;
        }

        return false;
    }

}
