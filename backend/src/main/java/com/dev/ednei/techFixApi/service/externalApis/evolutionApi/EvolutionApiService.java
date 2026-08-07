package com.dev.ednei.techFixApi.service.externalApis.evolutionApi;

import com.dev.ednei.techFixApi.DTOS.evolutionApi.*;
import com.dev.ednei.techFixApi.DTOS.evolutionApi.apiResponseRepresentations.ConnectionStateResponse;
import com.dev.ednei.techFixApi.infra.exceptions.errors.EntityNotFoundException;
import com.dev.ednei.techFixApi.infra.exceptions.errors.UnprocessableEntityException;
import com.dev.ednei.techFixApi.model.enums.evolutionAPI.StatusSendWhatsapp;
import com.dev.ednei.techFixApi.service.externalApis.HttpClientService;
import jakarta.mail.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class EvolutionApiService {
    @Autowired
    private HttpClientService httpService;

    @Autowired
    private ObjectMapper objectMapper;

    private final String server = "http://localhost:8081";

    @Value("${INSTANCE_NOTIFICATION_OS}")
    private String instanceStandard;

    @Value("${EVOLUTION_API_KEY}")
    private String apiKey;

    //Metodos para controller
    public InstanceQrcodeResponse generateConnectionQrCode() throws IOException, InterruptedException {
        var headers = new ArrayList<Header>();
        headers.add(new Header("apiKey", apiKey));

        var findQrcode = httpService.get(server + "/instance/connect/" + instanceStandard, headers);

        if (findQrcode.statusCode() != 200) {
            headers.add(new Header("Content-Type", "application/json"));
            var instanceCreate = objectMapper.writeValueAsString(new InstanceCreateRequest(instanceStandard, true, "WHATSAPP-BAILEYS"));
            httpService.post(server + "/instance/create", instanceCreate, headers);
            findQrcode = httpService.get(server + "/instance/connect/" + instanceStandard, headers);
        }

        var instanceAlreadyConnected = objectMapper.readValue(findQrcode.body().toString(), ConnectionStateResponse.class);

        if (instanceAlreadyConnected.instance() != null) {
            throw new UnprocessableEntityException("A instância já está conectada e não necessita de um novo QR Code. Caso queira alterar o número, DELETE a instância ou desconecte o número que está conectado no momento e faça uma requisição para essa rota novamente.");
        }

        return objectMapper.readValue(findQrcode.body().toString(), InstanceQrcodeResponse.class);
    }

    public void deleteConnectionInstance() throws IOException, InterruptedException {
        var arrayList = new ArrayList<Header>();
        arrayList.add(new Header("apiKey", apiKey));

        var response = httpService.delete(server + "/instance/delete/" + instanceStandard, arrayList);

        if (response.statusCode() != 200) {
            throw new EntityNotFoundException("Instâcia já foi deletada. Acesse a rota de geração do qrCode e se conecte novamente.");
        }
    }

    public InstanceDetailResponse findDetailInstance() throws IOException, InterruptedException {
        var arrayList = new ArrayList<Header>();
        arrayList.add(new Header("apiKey", apiKey));

        var response = httpService.get(server + "/instance/fetchInstances?instanceName=" + instanceStandard, arrayList);

        if (response.statusCode() != 200) {
            throw new EntityNotFoundException("Não foi encontrado nenhuma instância para Bot de Notificações. Acesse a rota de conexão e depois tente novamente.");
        }

        InstanceDetailResponse[] details = objectMapper.readValue(response.body().toString(), InstanceDetailResponse[].class);

        return details[0];
    }


    //Metodos para Outras classes
    public NotificationSituationMessageWhatsapp sendMessageWhatsapp(String message, String number) throws IOException, InterruptedException {
        var arrayList = new ArrayList<Header>();
        arrayList.add(new Header("apiKey", apiKey));
        arrayList.add(new Header("Content-Type", "application/json"));

        StatusSendWhatsapp status = null;
        String detail = "";

        //Verificação Instacia whatsapp já foi configurada e numero e valido
        var checkNumberExist = httpService.post(server + "/chat/whatsappNumbers/" + instanceStandard, objectMapper.writeValueAsString(Map.of("numbers", List.of(number))), arrayList);


        if (!checkStateInstance(server + "/instance/connectionState/" + instanceStandard, arrayList)) {

            status = StatusSendWhatsapp.FAILED;
            detail = "Menssagem não enviada. Bot automático não configurado ou conexão caiu";

        } else {
            ResponseCheckNumber[] responseCheckNumberObject = objectMapper.readValue(checkNumberExist.body().toString(), ResponseCheckNumber[].class);

            if (!responseCheckNumberObject[0].exists()) {
                status = StatusSendWhatsapp.FAILED;
                detail = "Numero cadastrado do cliente não é válido ou não está registrado no WhatsApp";
            } else {
                var body = Map.of("number", number, "text", message, "delay", 20000);
                var sendMessage = httpService.post(server + "/message/sendText/" + instanceStandard, objectMapper.writeValueAsString(body), arrayList);

                System.out.println(responseCheckNumberObject[0].exists());

                if (sendMessage.statusCode() == 201) {
                    status = StatusSendWhatsapp.DELIVERED;
                    detail = "Bot automatico está funcionando e menssagem foi enviada com sucesso";
                } else {
                    status = StatusSendWhatsapp.FAILED;
                    detail = "Messagem não enviada. Houve um erro com servidor responsável do envio da mensagem";
                }

            }
        }

        return new NotificationSituationMessageWhatsapp(number, status.name(), detail, LocalDateTime.now());
    }


    private Boolean checkStateInstance(String url, ArrayList<Header> headers) throws IOException, InterruptedException {
        var response = httpService.get(url, headers);
        var instance = objectMapper.readValue(response.body().toString(), ConnectionStateResponse.class);

        if (response.statusCode() != 200) {
            return false;
        }

        if (response.statusCode() == 200 && !instance.instance().state().equalsIgnoreCase("open")) {
            return false;
        }

        return true;
    }


}
