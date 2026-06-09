package com.dev.ednei.techFixApi.service;

import com.dev.ednei.techFixApi.repository.PartsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PartHistoryService {
    @Autowired
    private PartsRepository repository;

}
