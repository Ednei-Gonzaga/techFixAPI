package com.dev.ednei.techFixApi.model;

import com.dev.ednei.techFixApi.DTOS.client.ClientCreateDTO;
import com.dev.ednei.techFixApi.DTOS.client.ClientUpdateDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.List;

@Entity
@Table(name = "clients")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String cpf;

    private String phone;

    private String whatsapp;

    @OneToMany(mappedBy = "client")
    private List<ServiceRequest> serviceRequests;

    public Client(ClientCreateDTO clientDTO) {
        this.name = clientDTO.name();
        this.cpf = clientDTO.cpf();
        this.phone = clientDTO.phone();
        this.whatsapp = clientDTO.whatsapp();
    }

    public void updateClient(ClientUpdateDTO clientDTO) {
        if (StringUtils.hasText(clientDTO.name())) {
            this.name = clientDTO.name();
        }

        if (StringUtils.hasText(clientDTO.cpf())) {
            this.cpf = clientDTO.cpf();
        }

        if (StringUtils.hasText(clientDTO.phone())) {
            this.phone = clientDTO.phone();
        }

        if (StringUtils.hasText(clientDTO.whatsapp())) {
            this.whatsapp = clientDTO.whatsapp();
        }
    }
}
