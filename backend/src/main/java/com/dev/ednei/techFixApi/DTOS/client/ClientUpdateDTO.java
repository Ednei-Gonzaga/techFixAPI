package com.dev.ednei.techFixApi.DTOS.client;

import com.dev.ednei.techFixApi.model.Client;

public record ClientUpdateDTO(
        String name,
        String cpf,
        String phone,
        String whatsapp
) {
        public ClientUpdateDTO(Client client) {
                this( client.getName(), client.getCpf(), client.getPhone(), client.getWhatsapp());
        }
}
