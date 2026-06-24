package com.dev.ednei.techFixApi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "service_order_task")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ServiceOrderTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_service_order")
    private ServiceOrder serviceOrder;

    @ManyToOne
    @JoinColumn(name = "id_service_catalog")
    private ServiceCatalog serviceCatalog;

    @Column(name = "price_applied")
    private Double priceApplied;

    public ServiceOrderTask(ServiceOrder serviceOrder, ServiceCatalog serviceCatalog) {
        this.serviceOrder = serviceOrder;
        this.serviceCatalog = serviceCatalog;
        this.priceApplied = serviceCatalog.getCostPrice();
    }
}
