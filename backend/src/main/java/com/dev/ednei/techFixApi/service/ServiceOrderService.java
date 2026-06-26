package com.dev.ednei.techFixApi.service;

import com.dev.ednei.techFixApi.DTOS.serviceOrder.ServiceOrderDetailDTO;
import com.dev.ednei.techFixApi.DTOS.serviceOrder.ServiceOrderFullDTO;
import com.dev.ednei.techFixApi.DTOS.serviceOrder.ServiceOrderUpdateDTO;
import com.dev.ednei.techFixApi.DTOS.serviceOrderHistory.ServiceOrderHistoryCreate;
import com.dev.ednei.techFixApi.infra.exceptions.errors.AccessForbiddenException;
import com.dev.ednei.techFixApi.infra.exceptions.errors.EntityNotFoundException;
import com.dev.ednei.techFixApi.infra.exceptions.errors.InvalidParameterException;
import com.dev.ednei.techFixApi.infra.exceptions.errors.UnprocessableEntityException;
import com.dev.ednei.techFixApi.model.ServiceOrder;
import com.dev.ednei.techFixApi.model.User;
import com.dev.ednei.techFixApi.model.enums.CategoryDevice;
import com.dev.ednei.techFixApi.model.enums.PaymentStatus;
import com.dev.ednei.techFixApi.model.enums.RoleUser;
import com.dev.ednei.techFixApi.model.enums.ServiceOrderStatus;
import com.dev.ednei.techFixApi.repository.ServiceOrderRepository;
import com.dev.ednei.techFixApi.repository.ServiceOrderTaskRepository;
import com.dev.ednei.techFixApi.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class ServiceOrderService {
    @Autowired
    private ServiceOrderRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ServiceOrderHistoryService serviceOrderHistoryService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private ServiceOrderTaskRepository serviceOrderTaskRepository;


    @Transactional
    public void saveServiceOrder(Long serviceRequestId, User user) {
        var code = generateCode();

        while (repository.existsByIdentificationCode(code)) {
            code = generateCode();
        }

        ServiceOrder serviceOrder = new ServiceOrder(serviceRequestId, code);
        repository.save(serviceOrder);

        var orderHistoryCreate = new ServiceOrderHistoryCreate(serviceOrder.getId(), user.getId(),
                "Ordem de Serviço foi criada", ServiceOrderStatus.PENDING, ServiceOrderStatus.PENDING);

        serviceOrderHistoryService.saveHistoryOrder(orderHistoryCreate);
    }

    @Transactional
    public ServiceOrderFullDTO updateServiceOrder(Long serviceOrderId, ServiceOrderUpdateDTO orderDto, User user) {
        var serviceOrder = repository.findById(serviceOrderId);

        if (serviceOrder.isEmpty()) {
            throw new EntityNotFoundException("Não foi possivel encontrar Ordem de Serviço com ID " + serviceOrderId);
        }

        var oldStatus = serviceOrder.get().getStatus();

        if (orderDto.status() != null && ServiceOrderStatus.forValue(orderDto.status()) == null) {
            throw new InvalidParameterException("O status " + orderDto.status() + " não e valido");
        }

        validatePermissionsByStatus(orderDto.status(), serviceOrder.get(), user);

        if (orderDto.userTechnical() == null && serviceOrder.get().getUserTechnical() == null
                && StringUtils.hasText(orderDto.status()) && serviceOrder.get().getStatus() == ServiceOrderStatus.PENDING
                && (ServiceOrderStatus.forValue(orderDto.status()) != ServiceOrderStatus.PENDING && ServiceOrderStatus.forValue(orderDto.status()) != ServiceOrderStatus.CANCELED)) {
            throw new UnprocessableEntityException("Para atualizar status é necessario atribuir o serviço a um técnico primeiro");
        }

        if (orderDto.userTechnical() != null) {
            var userTechnical = userRepository.findById(orderDto.userTechnical());

            if (userTechnical.isEmpty()) {
                throw new EntityNotFoundException("Não foi possivel encontrar Tecnico com ID " + orderDto.userTechnical());
            }

            if (userTechnical.get().getRole() != RoleUser.TECHNICAL) {
                throw new UnprocessableEntityException("A ordem de serviço deve ser atribuida somente ao usuario do tipo Tecnico");
            }
        }

        if (orderDto.userTechnical() != null && serviceOrder.get().getUserTechnical() != null && user.getRole() != RoleUser.MANAGER
                && !orderDto.userTechnical().equals(serviceOrder.get().getUserTechnical().getId())) {
            throw new AccessForbiddenException("Somente o usuario do tipo Gerente pode atualizar ID do tecnico depois que já foi atribuido a Ordem de Serviço");
        }

        if (user.getRole() == RoleUser.TECHNICAL) {
            if (ServiceOrderStatus.forValue(orderDto.status()) == (ServiceOrderStatus.DELIVERED)) {
                throw new AccessForbiddenException("Somente Gerente ou Atendente pode atualizar status para Entregue");
            }
            if (ServiceOrderStatus.forValue(orderDto.status()) == ServiceOrderStatus.CANCELED) {
                throw new AccessForbiddenException("Somente Gerente ou Atendente pode atualizar status para Cancelado");
            }
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
                    && serviceOrder.get().getStatus() != ServiceOrderStatus.COMPLETED) {
                throw new AccessForbiddenException("Atendente só pode atualizar para Entregue a Ordem de Serviço se status for Completado");
            }
        }

        validateExistsTaskForCompleted(orderDto.status(), serviceOrder.get().getId());

        validatePaymentForDelivery(orderDto.status(), serviceOrder.get().getId());

        serviceOrder.get().updateServiceOrder(orderDto);
        repository.save(serviceOrder.get());

        if (StringUtils.hasText(orderDto.status())) {
            var newStatus = ServiceOrderStatus.forValue(orderDto.status());
            if (oldStatus != newStatus) {
                var notes = "Ordem de Serviço atualizada de " + oldStatus.portugueseOption.replace("_", " ") + " para " + newStatus.portugueseOption.replace("_", " ");

                var orderHistoryCreate = new ServiceOrderHistoryCreate(serviceOrder.get().getId(), user.getId(),
                        notes, oldStatus, newStatus);
                serviceOrderHistoryService.saveHistoryOrder(orderHistoryCreate);
            }
        }

        processPayment(serviceOrder.get(), user);

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

    public Page<ServiceOrderDetailDTO> findAllOrAllByFilter(String status, String category, Pageable pageable) {
        Page<ServiceOrderDetailDTO> serviceOrders = null;
        List<ServiceOrderStatus> listStatus;
        List<CategoryDevice> listCategory;

        if (!StringUtils.hasText(status) && !StringUtils.hasText(category)) {
            listStatus = Arrays.stream(ServiceOrderStatus.values())
                    .filter(s -> s != ServiceOrderStatus.CANCELED
                            && s != ServiceOrderStatus.DELIVERED
                            && s != ServiceOrderStatus.COMPLETED)
                    .toList();
            listCategory = List.of(CategoryDevice.values());

            serviceOrders = repository.findAllOrAllByStatusActivesAndCategory(listStatus, listCategory, pageable);
        }

        checkStatusAndCategory(status, category);

        if (!StringUtils.hasText(status) && StringUtils.hasText(category)) {
            listStatus = Arrays.stream(ServiceOrderStatus.values())
                    .filter(s -> s != ServiceOrderStatus.CANCELED
                            && s != ServiceOrderStatus.DELIVERED
                            && s != ServiceOrderStatus.COMPLETED)
                    .toList();
            listCategory = List.of(CategoryDevice.toString(category));

            serviceOrders = repository.findAllOrAllByStatusActivesAndCategory(listStatus, listCategory, pageable);
        }

        if (StringUtils.hasText(status) && !StringUtils.hasText(category)) {
            listStatus = List.of(ServiceOrderStatus.forValue(status));
            listCategory = List.of(CategoryDevice.values());

            if (listStatus.get(0) != ServiceOrderStatus.CANCELED && listStatus.get(0) != ServiceOrderStatus.DELIVERED && listStatus.get(0) != ServiceOrderStatus.COMPLETED) {

                serviceOrders = repository.findAllOrAllByStatusActivesAndCategory(listStatus, listCategory, pageable);

            } else {
                serviceOrders = repository.findAllOrAllByStatusFinishAndCategory(listStatus, listCategory, pageable);
            }
        }

        if (StringUtils.hasText(status) && StringUtils.hasText(category)) {
            listStatus = List.of(ServiceOrderStatus.forValue(status));
            listCategory = List.of(CategoryDevice.toString(category));

            if (listStatus.get(0) != ServiceOrderStatus.CANCELED && listStatus.get(0) != ServiceOrderStatus.DELIVERED && listStatus.get(0) != ServiceOrderStatus.COMPLETED) {

                serviceOrders = repository.findAllOrAllByStatusActivesAndCategory(listStatus, listCategory, pageable);

            } else {
                serviceOrders = repository.findAllOrAllByStatusFinishAndCategory(listStatus, listCategory, pageable);
            }
        }

        return serviceOrders;
    }

    public Page<ServiceOrderDetailDTO> findMyTask(String status, String category, User user, Pageable pageable) {
        Page<ServiceOrderDetailDTO> serviceOrders = null;
        List<ServiceOrderStatus> listStatus;
        List<CategoryDevice> listCategory;

        if (!StringUtils.hasText(status) && !StringUtils.hasText(category)) {
            listStatus = Arrays.stream(ServiceOrderStatus.values())
                    .filter(s -> s != ServiceOrderStatus.CANCELED
                            && s != ServiceOrderStatus.DELIVERED
                            && s != ServiceOrderStatus.COMPLETED)
                    .toList();
            listCategory = List.of(CategoryDevice.values());

            serviceOrders = repository.findAllOrAllByStatusActivesAndCategoryMyTask(listStatus, listCategory, user, pageable);
        }

        checkStatusAndCategory(status, category);

        if (!StringUtils.hasText(status) && StringUtils.hasText(category)) {
            listStatus = Arrays.stream(ServiceOrderStatus.values())
                    .filter(s -> s != ServiceOrderStatus.CANCELED
                            && s != ServiceOrderStatus.DELIVERED
                            && s != ServiceOrderStatus.COMPLETED)
                    .toList();
            listCategory = List.of(CategoryDevice.toString(category));

            serviceOrders = repository.findAllOrAllByStatusActivesAndCategoryMyTask(listStatus, listCategory, user, pageable);
        }

        if (StringUtils.hasText(status) && !StringUtils.hasText(category)) {
            listStatus = List.of(ServiceOrderStatus.forValue(status));
            listCategory = List.of(CategoryDevice.values());

            if (listStatus.get(0) != ServiceOrderStatus.CANCELED && listStatus.get(0) != ServiceOrderStatus.DELIVERED && listStatus.get(0) != ServiceOrderStatus.COMPLETED) {

                serviceOrders = repository.findAllOrAllByStatusActivesAndCategoryMyTask(listStatus, listCategory, user, pageable);

            } else {
                serviceOrders = repository.findAllOrAllByStatusFinishAndCategoryMyTask(listStatus, listCategory, user, pageable);
            }
        }

        if (StringUtils.hasText(status) && StringUtils.hasText(category)) {
            listStatus = List.of(ServiceOrderStatus.forValue(status));
            listCategory = List.of(CategoryDevice.toString(category));

            if (listStatus.get(0) != ServiceOrderStatus.CANCELED && listStatus.get(0) != ServiceOrderStatus.DELIVERED && listStatus.get(0) != ServiceOrderStatus.COMPLETED) {

                serviceOrders = repository.findAllOrAllByStatusActivesAndCategoryMyTask(listStatus, listCategory, user, pageable);

            } else {
                serviceOrders = repository.findAllOrAllByStatusFinishAndCategoryMyTask(listStatus, listCategory, user, pageable);
            }
        }

        return serviceOrders;
    }


    // Metodos privados
    private String generateCode() {
        var random = new Random();
        String code = "";

        for (int i = 0; i < 9; i++) {
            code += random.nextInt(10);
        }
        return code;
    }

    private void checkStatusAndCategory(String status, String category) {
        if (StringUtils.hasText(category)) {
            if (CategoryDevice.toString(category) == null) {
                throw new InvalidParameterException("Não existe a categoria '" + category + "'");
            }
        }

        if (StringUtils.hasText(status)) {
            if (ServiceOrderStatus.forValue(status) == null) {
                throw new InvalidParameterException("Não existe  status com o nome '" + status + "'");
            }
        }
    }

    private void processPayment(ServiceOrder serviceOrder, User user) {
        if (serviceOrder.getStatus() == ServiceOrderStatus.COMPLETED) {
            if (paymentService.existPaymentByServiceOrderId(serviceOrder.getId())) {
                paymentService.autoAdjustPayment(serviceOrder.getId(), user);
            } else {
                paymentService.createPayment(serviceOrder.getId(), user);
            }

        }

        if (serviceOrder.getStatus() == ServiceOrderStatus.CANCELED) {
            if (paymentService.existPaymentByServiceOrderId(serviceOrder.getId())) {
                paymentService.canceledPayments(serviceOrder.getId(), user);
            }
        }
    }

    private void validatePaymentForDelivery(String newStatus, Long serviceOrderId) {
        var payment = paymentService.findByIdServiceOrderWithoutException(serviceOrderId);

        if (StringUtils.hasText(newStatus) && ServiceOrderStatus.forValue(newStatus) == ServiceOrderStatus.DELIVERED) {
            if (payment.isEmpty()) {
                throw new UnprocessableEntityException("Não é possível finalizar a entrega. Nenhum registro de pagamento foi localizado para esta Ordem de Serviço. Por favor, conclua a OS para gerar a cobrança primeiro.");
            }

            if (payment.get().getPaymentStatus() != PaymentStatus.PAID && payment.get().getPaymentStatus() != PaymentStatus.CANCELED) {
                throw new UnprocessableEntityException("Não é possível entregar o aparelho. O faturamento desta Ordem de Serviço ainda consta como " + payment.get().getPaymentStatus().portugueseOption + ". É necessário realizar a baixa do pagamento primeiro.");
            }

            if (payment.get().getPaymentStatus() == PaymentStatus.CANCELED) {
                throw new UnprocessableEntityException("Atenção: O pagamento vinculado a esta Ordem de Serviço foi cancelado. Não é possível entregar um aparelho com faturamento cancelado.");
            }
        }
    }

    private void validateExistsTaskForCompleted(String newStatus, Long serviceOrderId) {
        if (StringUtils.hasText(newStatus) && ServiceOrderStatus.forValue(newStatus) == ServiceOrderStatus.COMPLETED) {
            if (!serviceOrderTaskRepository.existsByServiceOrderId(serviceOrderId)) {
                throw new UnprocessableEntityException("Não foi possível CONCLUIR a Ordem de Serviço. É necessário que tenha pelo menos 1 Catálogo de Serviço (Tipo de Serviço Feito) atribuído à OS");
            }
        }
    }

    private void validatePermissionsByStatus(String newStatus, ServiceOrder serviceOrder, User loggedUser) {
        if (serviceOrder.getStatus() == ServiceOrderStatus.DELIVERED && StringUtils.hasText(newStatus)) {
            throw new UnprocessableEntityException("Esta Ordem de Serviço já foi Entregue ao cliente e o seu ciclo foi encerrado. Nenhuma alteração é permitida.");
        }

        if (serviceOrder.getStatus() == ServiceOrderStatus.CANCELED && StringUtils.hasText(newStatus)) {
            if (loggedUser.getRole() != RoleUser.MANAGER) {
                throw new AccessForbiddenException("Apenas usuários com perfil de Gerente podem alterar uma Ordem de Serviço que encontra-se Cancelada");
            }
        }
    }

}
