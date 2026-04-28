package com.dev.ednei.techFixApi.repository;

import com.dev.ednei.techFixApi.DTOS.client.ClientFullDTO;
import com.dev.ednei.techFixApi.model.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<ClientFullDTO> findByCpf(String cpf);

    Page<Client> findAll(Pageable pageable);
}
