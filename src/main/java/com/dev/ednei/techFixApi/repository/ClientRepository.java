package com.dev.ednei.techFixApi.repository;

import com.dev.ednei.techFixApi.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
