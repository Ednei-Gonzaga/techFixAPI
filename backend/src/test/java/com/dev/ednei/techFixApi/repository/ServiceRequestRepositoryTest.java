package com.dev.ednei.techFixApi.repository;

import com.dev.ednei.techFixApi.DTOS.client.ClientCreateDTO;
import com.dev.ednei.techFixApi.DTOS.serviceRequest.ServiceRequestCreateDTO;
import com.dev.ednei.techFixApi.model.Client;
import com.dev.ednei.techFixApi.model.ServiceRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class ServiceRequestRepositoryTest {

    @Autowired
    private ServiceRequestRepository repository;

    @Autowired
    private TestEntityManager entityManager;


    @Test
    void findAllByCpfClient() {
        var client = createClient(new Client(new ClientCreateDTO("teste1", "1233123812", "123344324", null)));
        var serviceRequestTest = createServiceRequest(new ServiceRequest(new ServiceRequestCreateDTO("Notebook pica", "notebook", "tela quebrada", client.getId())));
        var serviceRequest =  repository.findAllByCpfClient("123312381", null);
        assertThat(serviceRequest).isNull();
    }


    private Client createClient(Client client){
        entityManager.persist(client);
        return client;
    }

    private ServiceRequest createServiceRequest(ServiceRequest serviceRequest){
        entityManager.persist(serviceRequest);
        return serviceRequest;
    }
}