package com.dev.ednei.techFixApi.repository;

import com.dev.ednei.techFixApi.model.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClientRepository extends JpaRepository<Client, Long> {
    boolean existsByCpf(String cpf);

    @Query(
        """
        SELECT c 
        FROM Client c
        WHERE c.cpf ILIKE %:cpf%
        """
        )
    Page<Client> findByCpfClient(String cpf, Pageable pageable);

    @Query("""
        SELECT c
        FROM Client c
        WHERE c.name ILIKE %:nameClient%
    """)
    Page<Client> findByNameClient(@Param("nameClient") String nameClient, Pageable pageable);

    Page<Client> findAll(Pageable pageable);
}
