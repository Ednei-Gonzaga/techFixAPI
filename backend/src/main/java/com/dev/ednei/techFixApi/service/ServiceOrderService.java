package com.dev.ednei.techFixApi.service;

import com.dev.ednei.techFixApi.model.ServiceOrder;
import com.dev.ednei.techFixApi.repository.ServiceOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class ServiceOrderService {
    @Autowired
    private ServiceOrderRepository repository;

    public void saveServiceOrder(Long serviceRequestId){
        var code = generateCode();

        while (repository.existsByIdentificationCode(code)){
            code = generateCode();
        }

        ServiceOrder serviceOrder = new ServiceOrder(serviceRequestId, code);
        repository.save(serviceOrder);
    }


    private String generateCode(){
        var random = new Random();
        String code = "";

        for (int i = 0; i < 9; i++){
            code += random.nextInt(10);
        }
        return code;
    }
}
