package com.dev.ednei.techFixApi.service;

import com.dev.ednei.techFixApi.model.Payment;
import com.dev.ednei.techFixApi.model.PaymentsHistory;
import com.dev.ednei.techFixApi.model.User;
import com.dev.ednei.techFixApi.model.enums.PaymentStatus;
import com.dev.ednei.techFixApi.repository.PaymentsHistoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentsHistoryService {
    @Autowired
    private PaymentsHistoryRepository repository;

    @Transactional
    public void saveHistoryPayment(Payment payment, User user, PaymentStatus newStatus, PaymentStatus oldStatus, String notes) {
        var PaymentsHistory = new PaymentsHistory(payment, user, newStatus, oldStatus, notes);
        repository.save(PaymentsHistory);
    }
}
