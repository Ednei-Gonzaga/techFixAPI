package com.dev.ednei.techFixApi.model;


import com.dev.ednei.techFixApi.DTOS.serviceOrderHistory.ServiceOrderHistoryCreate;
import com.dev.ednei.techFixApi.model.enums.ServiceOrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "service_order_history")
@NoArgsConstructor
@AllArgsConstructor
public class ServiceOrderHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_service_order")
    private ServiceOrder serviceOrder;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @Column(name = "old_status")
    @Enumerated(EnumType.STRING)
    private ServiceOrderStatus oldStatus;

    @Column(name = "new_status")
    @Enumerated(EnumType.STRING)
    private ServiceOrderStatus newStatus;

    private String notes;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public ServiceOrderHistory(ServiceOrderHistoryCreate history) {
        this.serviceOrder = new ServiceOrder(history.serviceOrder());
        this.user =  new User(history.user());
        this.notes =  history.notes();
        this.oldStatus =  history.oldStatus();
        this.newStatus =  history.newStatus();
        this.createdAt = LocalDateTime.now();
    }
}
