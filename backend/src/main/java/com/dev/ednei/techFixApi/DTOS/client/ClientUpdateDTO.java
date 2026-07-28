package com.dev.ednei.techFixApi.DTOS.client;

import com.dev.ednei.techFixApi.model.Client;
import org.hibernate.validator.constraints.br.CPF;

public record ClientUpdateDTO(
        String name,
        @CPF
        String cpf,
        String phone,
        String whatsapp
) {
        public ClientUpdateDTO(Client client) {
                this( client.getName(), client.getCpf(), client.getPhone(), client.getWhatsapp());
        }
}
