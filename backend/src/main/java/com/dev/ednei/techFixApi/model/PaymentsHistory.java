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
    @JoinColumn(name = "id_payments")
    private Payment payment;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @Column(name = "old_status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus oldStatus;

    @Column(name = "new_status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus newStatus;

    @Column(name = "transaction_amount")
    private Double transactionAmount;

    private String notes;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public PaymentsHistory(Payment payment, User user, PaymentStatus newStatus, PaymentStatus oldStatus, String notes) {
        this.payment = payment;
        this.user = user;
        this.newStatus = newStatus;
        this.oldStatus = oldStatus;
        this.notes = notes;
        this.createdAt = LocalDateTime.now();
        this.transactionAmount = payment.getTotalAmount();
    }
}
