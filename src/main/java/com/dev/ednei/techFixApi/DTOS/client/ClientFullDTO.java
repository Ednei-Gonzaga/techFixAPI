package com.dev.ednei.techFixApi.DTOS.client;

import com.dev.ednei.techFixApi.model.Client;
import jakarta.validation.constraints.NotBlank;

public record ClientFullDTO(

        Long id,

        String name,

        String cpf,

        String phone,

        String whatsapp
) {
    public ClientFullDTO(Client client) {
        this(client.getId(), client.getName(), client.getCpf(), client.getPhone(), client.getWhatsapp());
    }
}
