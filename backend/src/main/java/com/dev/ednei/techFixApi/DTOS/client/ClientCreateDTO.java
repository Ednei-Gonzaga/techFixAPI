package com.dev.ednei.techFixApi.DTOS.client;

import com.dev.ednei.techFixApi.model.Client;
import jakarta.validation.constraints.NotBlank;

public record ClientCreateDTO(
        @NotBlank
        String name,

        @NotBlank
        String cpf,

        @NotBlank
        String phone,

        String whatsapp
) {
        public ClientCreateDTO(Client client) {
                this(client.getName(), client.getCpf(), client.getPhone(), client.getWhatsapp());
        }


}
