package com.dev.ednei.techFixApi.controller;

import com.dev.ednei.techFixApi.DTOS.payments.PaymentsFullDTO;
import com.dev.ednei.techFixApi.DTOS.payments.PaymentsRequestStatus;
import com.dev.ednei.techFixApi.DTOS.payments.PaymentsUpdateDTO;
import com.dev.ednei.techFixApi.model.User;
import com.dev.ednei.techFixApi.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PutMapping("/service-orders/{id}/payments")
    public ResponseEntity<PaymentsFullDTO> updatePayment(@PathVariable Long id, @RequestBody PaymentsUpdateDTO paymentsdto, @AuthenticationPrincipal User user) {
        var payment = paymentService.updatePayment(id, paymentsdto, user);
        return ResponseEntity.ok().body(payment);
    }

    @PatchMapping("/service-orders/{id}/payments/status")
    public ResponseEntity<PaymentsFullDTO> updatePaymentStatus(@PathVariable(name = "id") Long id, @RequestBody @Valid PaymentsRequestStatus paymentsRequestStatus, @AuthenticationPrincipal User user){
        var payment = paymentService.updatePaymentStatus(id, paymentsRequestStatus, user);
        return ResponseEntity.ok().body(payment);
    }

    @GetMapping("/service-orders/{id}/payments")
    public  ResponseEntity<PaymentsFullDTO> findByIdServiceOrder(@PathVariable(name = "id") Long id){
        var payment = paymentService.findByIdServiceOrder(id);
        return ResponseEntity.ok().body(payment);
    }
}
