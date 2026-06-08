package com.dev.ednei.techFixApi.model;

import com.dev.ednei.techFixApi.model.enums.ServiceOrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "service_orders")
@NoArgsConstructor
@AllArgsConstructor
public class ServiceOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "indentification_code")
    private String indentificationCode;

    private ServiceOrderStatus status;

    @Column(name = "date_time_start")
    private LocalDateTime dateTimeStart;

    @Column(name = "date_time_completed")
    private LocalDateTime dateTimeCompleted;

    @Column(name = "date_time_update_status")
    private LocalDateTime dateTimeUpdateStatus;

    @ManyToOne
    @JoinColumn(name = "id_service_request")
    private ServiceRequests idServiceRequest;

    @ManyToOne
    @JoinColumn(name = "id_user_technical")
    private User idUserTechnical;

    @OneToOne(mappedBy = "idServiceOrder")
    private Payment payment;
}
