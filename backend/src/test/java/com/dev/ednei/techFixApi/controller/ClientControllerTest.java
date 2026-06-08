package com.dev.ednei.techFixApi.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@AutoConfigureMockMvc
@WebMvcTest(ClientController.class)
class ClientControllerTest {

    @Autowired
    private MockMvc mvc; // Segunda opção, que simula uma requizição sem precisar rodar projeto, etc

    @Test
    @DisplayName("Verificar se esta retornando status 404 quando cpf esta errado")
    @WithMockUser
    void findAllClientOrByCpf() throws Exception {
        var response = mvc.perform(get("/api/clients?cpf=1")).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(404);
    }

    @Test
    @DisplayName("Verificar se esta retornando status 400")
    @WithMockUser
    void saveClient() throws Exception {
        var response = mvc.perform(post("/api/clients")).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(400);

    }
}