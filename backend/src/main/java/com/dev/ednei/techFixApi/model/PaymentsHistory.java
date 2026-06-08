package com.dev.ednei.techFixApi.model;

import com.dev.ednei.techFixApi.model.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "payments_history")
@NoArgsConstructor
@AllArgsConstructor
public class PaymentsHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_payment")
    private Payment idPayment;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User idUser;

    @Column(name = "old_status")
    private PaymentStatus oldStatus;

    @Column(name = "new_status")
    private PaymentStatus newStatus;

    @Column(name = "transaction_amount")
    private Double transactionAmount;

    private String notes;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
