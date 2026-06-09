package com.dev.ednei.techFixApi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "service_order_task")
@NoArgsConstructor
@AllArgsConstructor
public class ServiceOrderTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_service_order")
    private ServiceOrder serviceOrder;

    @ManyToOne
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_service_catalog")
    private ServiceCatalog serviceCatalog;

    @Column(name = "price_applied")
    private Double priceApplied;
}
