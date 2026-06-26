package com.dev.ednei.techFixApi.model;

import com.dev.ednei.techFixApi.DTOS.payments.PaymentsUpdateDTO;
import com.dev.ednei.techFixApi.model.enums.PaymentMethod;
import com.dev.ednei.techFixApi.model.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "payments")
@NoArgsConstructor
@AllArgsConstructor
@Getter
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
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Column(name = "payment_status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Column(name = "paid_at")
    private LocalDateTime paidAt;

    @OneToOne
    @JoinColumn(name = "id_service_order")
    private ServiceOrder serviceOrder;

    @OneToMany(mappedBy = "payment")
    private List<PaymentsHistory> paymentsHistory;

    public Payment(Double laborAmount, Double partsAmount, ServiceOrder serviceOrder) {
        this.laborAmount = laborAmount;
        this.partsAmount = partsAmount;
        this.totalAmount = laborAmount + partsAmount;
        this.discount = 0.0;
        this.paymentStatus = PaymentStatus.PENDING;
        this.serviceOrder = serviceOrder;
    }

    public void autoAdjustPayments(Double laborAmount, Double partsAmount) {
        this.laborAmount = laborAmount;
        this.partsAmount = partsAmount;

        if(this.discount != null) {
            this.totalAmount = (laborAmount + partsAmount) - discount;
        }else{
            this.totalAmount = (laborAmount + partsAmount);
        }

        this.paymentStatus = PaymentStatus.PENDING;
    }

    public void updateStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;

        if(paymentStatus == PaymentStatus.PAID) {
            this.paidAt = LocalDateTime.now();
        }
    }

    public void updateMethodAndDiscount(PaymentsUpdateDTO paymentDto) {
        if(StringUtils.hasText(paymentDto.paymentMethod())){
            this.paymentMethod = PaymentMethod.forValue(paymentDto.paymentMethod());
        }

        if(paymentDto.discount() != null){
            this.discount = paymentDto.discount();
            this.totalAmount = (this.laborAmount + this.partsAmount) - this.discount;
        }
    }
}
