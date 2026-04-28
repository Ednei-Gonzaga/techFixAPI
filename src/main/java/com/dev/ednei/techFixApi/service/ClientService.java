package com.dev.ednei.techFixApi.service;

import com.dev.ednei.techFixApi.DTOS.client.ClientCreateDTO;
import com.dev.ednei.techFixApi.DTOS.client.ClientFullDTO;
import com.dev.ednei.techFixApi.DTOS.client.ClientUpdateDTO;
import com.dev.ednei.techFixApi.infra.exception.errors.EntityNotFoundException;
import com.dev.ednei.techFixApi.model.Client;
import com.dev.ednei.techFixApi.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientService {
    @Autowired
    private ClientRepository repository;

    public ClientFullDTO findByCpf(String cpf){
        var client = repository.findByCpf(cpf);

        if(client.isEmpty()){
           throw  new EntityNotFoundException("Cliente com o cpf " + cpf + " não encontrado.");
        }

        return client.get();
    }

    public ClientFullDTO findById(Long id){
        var client = repository.findById(id);

        if(client.isEmpty()){
           throw  new EntityNotFoundException("Cliente com o " + id + " não encontrado.");
        }

        return new ClientFullDTO(client.get());
    }

    public Page<ClientFullDTO> findAllClients(Pageable pageable){
        var listClient = repository.findAll(pageable);

        return listClient.map(ClientFullDTO::new);
    }

    @Transactional
    public ClientCreateDTO saveClient(ClientCreateDTO clientDTO){
        var client = new Client(clientDTO);
        return new ClientCreateDTO(repository.save(client));
    }

    @Transactional
    public void updateDataClient(ClientUpdateDTO clientDTO) {
        var client = repository.findById(clientDTO.id());

        if (client.isEmpty()){
            throw new EntityNotFoundException("Não foi possivel atualizar cliente com ID " + clientDTO.id() + " inexistente.");
        }

        client.get().updateClient(clientDTO);

        repository.save(client.get());

    }



}
