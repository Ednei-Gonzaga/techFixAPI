package com.dev.ednei.techFixApi.model;

import com.dev.ednei.techFixApi.DTOS.serviceOrder.ServiceOrderCreateDTO;
import com.dev.ednei.techFixApi.DTOS.serviceOrder.ServiceOrderUpdateDTO;
import com.dev.ednei.techFixApi.model.enums.StatusServiceOrder;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Entity
@Table(name = "service_order")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_service_requests")
    private ServiceRequest serviceRequest;

    @ManyToOne
    @JoinColumn(name = "id_user_technical")
    private User user;

    @Column(name = "identification_code")
    private String identificationCode;

    @Enumerated(value = EnumType.STRING)
    private StatusServiceOrder status;

    @Column(name = "date_time_start")
    private LocalDateTime dateTimeStart;

    @Column(name = "date_time_completed")
    private LocalDateTime dateTimeCompleted;

    public ServiceOrder(ServiceOrderCreateDTO serviceDTO, String code) {
        this.serviceRequest = new ServiceRequest(serviceDTO.serviceRequest());
        this.identificationCode = code;
        this.status = StatusServiceOrder.PENDING;
        this.dateTimeStart = LocalDateTime.now();

    }

    public void updateServiceOrder(ServiceOrderUpdateDTO serviceDTO) {

        if (serviceDTO.user() != null) {
            this.user = new User(serviceDTO.user());
        }

        if (StringUtils.hasText(serviceDTO.status())) {
            this.status = StatusServiceOrder.fromString(serviceDTO.status());
        }

        if ((StatusServiceOrder.fromString(serviceDTO.status()) == StatusServiceOrder.COMPLETED) || (StatusServiceOrder.fromString(serviceDTO.status()) == StatusServiceOrder.CANCELED)) {
            this.dateTimeCompleted = LocalDateTime.now();
        }
    }
}
