package com.dev.ednei.techFixApi.model;


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
    private String oldStatus;

    @Column(name = "new_status")
    private String newStatus;

    private String notes;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

}
