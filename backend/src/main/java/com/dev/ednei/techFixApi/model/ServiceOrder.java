package com.dev.ednei.techFixApi.model;

import com.dev.ednei.techFixApi.DTOS.serviceOrder.ServiceOrderUpdateDTO;
import com.dev.ednei.techFixApi.model.enums.ServiceOrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "service_orders")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ServiceOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "identification_code")
    private String identificationCode;

    @Enumerated(EnumType.STRING)
    private ServiceOrderStatus status;

    @Column(name = "date_time_start")
    private LocalDateTime dateTimeStart;

    @Column(name = "date_time_completed")
    private LocalDateTime dateTimeCompleted;

    @Column(name = "date_time_update_status")
    private LocalDateTime dateTimeUpdateStatus;

    @ManyToOne
    @JoinColumn(name = "service_request")
    private ServiceRequests serviceRequest;

    @ManyToOne
    @JoinColumn(name = "id_user_technical")
    private User userTechnical;

    @OneToOne(mappedBy = "serviceOrder")
    private Payment payment;

    @OneToMany(mappedBy = "serviceOrder")
    private List<ServiceOrderItem> serviceOrderItem;

    @OneToMany(mappedBy = "serviceOrder")
    private List<ServiceOrderTask> serviceOrderTasks;

    @OneToMany(mappedBy = "serviceOrder")
    private List<ServiceOrderHistory> serviceOrderHistory;

    public ServiceOrder(Long serviceRequestId, String code) {
        this.serviceRequest = new ServiceRequests(serviceRequestId);
        this.userTechnical = null;
        this.identificationCode = code;
        this.status = ServiceOrderStatus.PENDING;
        this.dateTimeStart = LocalDateTime.now();
        this.dateTimeCompleted = null;
        this.dateTimeUpdateStatus = null;
    }

    public void updateServiceOrder(@NonNull ServiceOrderUpdateDTO orderDto) {
        if(orderDto.userTechnical() != null) {
            this.userTechnical = new User(orderDto.userTechnical());
            this.dateTimeUpdateStatus = LocalDateTime.now();
        }

        if(StringUtils.hasText(orderDto.status())){
            this.status = ServiceOrderStatus.forValue(orderDto.status());
            this.dateTimeUpdateStatus = LocalDateTime.now();
        }

        if(this.status == ServiceOrderStatus.COMPLETED ) {
            this.dateTimeCompleted = LocalDateTime.now();
        }else if (this.status != ServiceOrderStatus.DELIVERED) {
            this.dateTimeCompleted = null;
        }
    }
}
