package com.dev.ednei.techFixApi.model;

import com.dev.ednei.techFixApi.DTOS.client.ClientCreateDTO;
import com.dev.ednei.techFixApi.DTOS.client.ClientUpdateDTO;
import com.dev.ednei.techFixApi.model.dataModeling.PeopleData;
import com.google.i18n.phonenumbers.NumberParseException;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.List;

@Entity
@Table(name = "clients")
@NoArgsConstructor
@AllArgsConstructor
public class Client extends PeopleData {
    @OneToMany(mappedBy = "client")
    private List<ServiceRequests> serviceRequests;

    public Client(ClientCreateDTO clientCreateDto) throws NumberParseException {
        super(clientCreateDto.name(), clientCreateDto.cpf(), clientCreateDto.phone(),  clientCreateDto.whatsapp());
    }

    public Client(Long client) {
        setId(client);
    }

    public void updateClient(ClientUpdateDTO clientDto) {
        if(StringUtils.hasText(clientDto.name())) {
            this.setName(clientDto.name());
        }

        if(StringUtils.hasText(clientDto.cpf())) {
            this.setCpf(clientDto.cpf());
        }

        if(StringUtils.hasText(clientDto.phone())) {
            this.setPhone(clientDto.phone());
        }

        if(StringUtils.hasText(clientDto.whatsapp())) {
            this.setWhatsapp(clientDto.whatsapp());
        }
    }
}
