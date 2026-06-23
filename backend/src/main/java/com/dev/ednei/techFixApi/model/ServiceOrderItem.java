package com.dev.ednei.techFixApi.model;

import com.dev.ednei.techFixApi.DTOS.serviceOrderItem.ServiceOrderItemCreatedDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "service_order_item")
@NoArgsConstructor
@AllArgsConstructor
@Getter
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

    @Setter
    private Integer quantity;

    @Column(name = "unit_price")
    private Double unitPrice;

    @Column(name = "sub_total")
    private Double subTotal;

    public ServiceOrderItem(ServiceOrderItemCreatedDTO itemDto, Parts part, ServiceOrder serviceOrder) {
        this.serviceOrder = serviceOrder;
        this.part = part;
        this.namePart = part.getName();
        this.quantity = itemDto.quantity();
        this.unitPrice = part.getCostPrice();
        this.subTotal = quantity * unitPrice;
    }

    public void updateQuantity(Integer newQuantity) {
        this.quantity = newQuantity;
        this.subTotal = quantity * unitPrice;
    }
}
