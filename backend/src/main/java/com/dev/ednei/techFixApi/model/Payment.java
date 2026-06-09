package com.dev.ednei.techFixApi.model;

import com.dev.ednei.techFixApi.model.enums.PaymentMethod;
import com.dev.ednei.techFixApi.model.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "labor_amount")
    private Double laborAmount;

    @Column(name = "parts_amount")
    private Double partsAmount;

    private Double discount;

    @Column(name = "total_amount")
    private Double totalAmount;

    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;

    @Column(name = "payment_status")
    private PaymentStatus paymentStatus;

    @Column(name = "paid_at")
    private LocalDateTime paidAt;

    @OneToOne
    @JoinColumn(name = "id_service_order")
    private ServiceOrder serviceOrder;
}
