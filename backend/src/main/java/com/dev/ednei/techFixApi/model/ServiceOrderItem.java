package com.dev.ednei.techFixApi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "service_order_item")
@NoArgsConstructor
@AllArgsConstructor
public class ServiceOrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_service_order")
    private ServiceOrder serviceOrder;

    @ManyToOne
    @JoinColumn(name = "id_part")
    private Parts part;

    @Column(name = "name_part")
    private String namePart;

    private Integer quantity;

    @Column(name = "unit_price")
    private Double unitPrice;

    @Column(name = "sub_total")
    private Double subTotal;

}
