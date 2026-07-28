package com.dev.ednei.techFixApi.service;

import com.dev.ednei.techFixApi.DTOS.client.ClientCpfRequest;
import com.dev.ednei.techFixApi.DTOS.client.ClientCreateDTO;
import com.dev.ednei.techFixApi.DTOS.client.ClientFullDTO;
import com.dev.ednei.techFixApi.DTOS.client.ClientUpdateDTO;
import com.dev.ednei.techFixApi.infra.exceptions.errors.ConflictDataException;
import com.dev.ednei.techFixApi.infra.exceptions.errors.EntityNotFoundException;
import com.dev.ednei.techFixApi.model.Client;
import com.dev.ednei.techFixApi.repository.ClientRepository;
import com.google.i18n.phonenumbers.NumberParseException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
    @Autowired
    private ClientRepository repository;

    @Transactional
    public ClientFullDTO saveClient(ClientCreateDTO clientCreateDto) throws NumberParseException {
        var client = new Client(clientCreateDto);

        if (repository.existsByCpf(clientCreateDto.cpf())) {
            throw new ConflictDataException("Cliente já cadastrado com CPF informado");
        }

        repository.save(client);
        return new ClientFullDTO(client);
    }

    @Transactional
    public ClientFullDTO updateClient(Long id, ClientUpdateDTO clientUpdateDto) {
        var client = repository.findById(id);

        if (client.isEmpty()) {
            throw new EntityNotFoundException("Cliente com ID " + id + " não encontrado");
        }

        if (repository.existsByCpf(clientUpdateDto.cpf()) && !client.get().getCpf().equals(clientUpdateDto.cpf())) {
            throw new ConflictDataException("Já existe cliente com CPF informado");
        }

        client.get().updateClient(clientUpdateDto);
        repository.save(client.get());

        return new ClientFullDTO(client.get());
    }

    public ClientFullDTO getClientById(Long id) {
        var client = repository.findById(id);

        if (client.isEmpty()) {
            throw new EntityNotFoundException("Cliente com ID " + id + " não encontrado");
        }

        return new ClientFullDTO(client.get());

    }

    public Page<ClientFullDTO> getClientByCpf(ClientCpfRequest clientCpfRequest, Pageable pageable) {
        var client = repository.findByCpfClient(clientCpfRequest.cpf(), pageable);
        return client.map(ClientFullDTO::new);
    }

    public Page<ClientFullDTO> getAllClient(Pageable pageable) {
        var client = repository.findAll(pageable);
        return client.map(ClientFullDTO::new);
    }

    public Page<ClientFullDTO> getClientByName(String name, Pageable pageable) {
        var client = repository.findByNameClient(name, pageable);
        return client.map(ClientFullDTO::new);
    }
}
