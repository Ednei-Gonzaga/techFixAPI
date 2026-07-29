package com.dev.ednei.techFixApi.service.externalApis.evolutionApi;

import java.util.List;
import java.util.Random;

public class WhatsAppMessagesUtil {

    public static String createMessageUpdateOs(String nameClient, String statusOrderService) {
        var wordsOfGreeting = List.of("Olá", "Oi", "Como vai", "Tudo Certo", "Tudo joia", "Opa", "Tudo Bem");
        var introductoryRemarks = List.of("Passando para avisar", "Só pra te atualizar", "Viemos te informar", "Mensagem rápida pra avisar", "Trazendo atualizações", "Passando aqui para deixar você a par de tudo", "Entrando em contato para te atualizar", "Viemos trazer novidades", "Te mandando essa mensagem para informar", "Passando para deixar um aviso rápido");

        var serviceTypes = List.of("manutenção", "revisão");

        var statusTransitions = List.of(
                "O status acabou de mudar para:",
                "Neste momento o aparelho está na fase de:",
                "A etapa do serviço agora é:",
                "Avançamos para a etapa de:"
        );

        var reassurancePhrases = List.of(
                "E não se preocupe,",
                "Como combinamos,",
                "Pode ficar tranquilo(a),",
                "Só pra lembrar,"
        );

        var trackingAssurances = List.of(
                "não precisa ficar checando o site toda hora",
                "vou continuar te mantendo informado por aqui mesmo",
                "sempre que houver mudança eu te aviso direto por aqui",
                "nós te mandaremos mensagem a cada avanço"
        );

        var farewells = List.of(
                "Um abraço!",
                "Até logo!",
                "Qualquer coisa, manda mensagem!",
                "Qualquer dúvida, é só responder."
        );

        return wordsOfGreeting.get(randomNumber(wordsOfGreeting.size())) + ", " + nameClient + "! " + introductoryRemarks.get(randomNumber(introductoryRemarks.size())) + " da sua " + serviceTypes.get(randomNumber(serviceTypes.size())) + ".\n\n" + statusTransitions.get(randomNumber(statusTransitions.size())) + " " + statusOrderService + ".\n\n" + reassurancePhrases.get(randomNumber(reassurancePhrases.size())) + " " + trackingAssurances.get(randomNumber(trackingAssurances.size())) + ".\n\n" + farewells.get(randomNumber(farewells.size()));
    }

    public static String createMessageOrderServiceOpened(String nameClient, String linkUrl, String codeOs) {
        var wordsOfGreeting = List.of("Olá", "Oi", "Opa", "Tudo bem");

        var introductoryRemarks = List.of(
                "Passando para avisar",
                "Só pra te atualizar",
                "Viemos te informar",
                "Mensagem rápida pra avisar"
        );

        var actionStatus = List.of(
                "registrado",
                "cadastrado",
                "iniciado",
                "aberto"
        );

        var trackingMotivations = List.of(
                "Se quiser acompanhar o andamento do concerto",
                "Para acompanhar o serviço em tempo real",
                "Caso queira checar o status do concerto",
                "Para ver os detalhes do concerto do seu aparelho"
        );

        var trackingInstructions = List.of(
                "basta acessar nosso site:",
                "é só clicar no link abaixo:",
                "acesse nosso portal direto:"
        );

        var codeIntroductions = List.of(
                "Seu código de rastreio é:",
                "O número de rastreio é:",
                "Para buscar, use o código a seguir:",
                "Guarde o seu código de acompanhamento:"
        );

        var farewells = List.of(
                "Qualquer dúvida, estamos por aqui!",
                "Se precisar de algo, é só chamar!",
                "Ficamos à disposição!"
        );

        return wordsOfGreeting.get(randomNumber(wordsOfGreeting.size())) + ", " + nameClient + "! " +
                introductoryRemarks.get(randomNumber(introductoryRemarks.size())) + " que o problema do seu aparelho foi " +
                actionStatus.get(randomNumber(actionStatus.size())) + " com sucesso no nosso sistema.\n\n" +
                trackingMotivations.get(randomNumber(trackingMotivations.size())) + ", " +
                trackingInstructions.get(randomNumber(trackingInstructions.size())) + "\n" +
                linkUrl + "\n\n" +
                codeIntroductions.get(randomNumber(codeIntroductions.size())) + " " + codeOs + ".\n\n" +
                farewells.get(randomNumber(farewells.size()));
    }

    public static String createMessageOrderServiceCompleted(String nameClient) {
        var wordsOfGreeting = List.of("Olá", "Oi", "Opa", "Tudo bem", "Tudo joia");

        var goodNews = List.of(
                "Temos ótimas notícias!",
                "Passando com uma excelente notícia!",
                "Boas novas!",
                "Tudo pronto por aqui!"
        );

        var completionStatus = List.of(
                "O seu aparelho já está pronto",
                "O serviço no seu dispositivo foi totalmente finalizado",
                "A manutenção foi concluída com sucesso",
                "Terminamos todos os testes e o serviço está finalizado"
        );

        var pickUpInstructions = List.of(
                "e você já pode vir buscar quando quiser.",
                "e ele já está liberado para retirada na nossa loja.",
                "e você pode passar aqui para retirar assim que puder.",
                "e estamos com ele separadinho, aguardando a sua retirada."
        );

        var appreciation = List.of(
                "Agradecemos muito pela confiança no nosso trabalho!",
                "Muito obrigado por escolher os nossos serviços!",
                "Foi um prazer cuidar do seu aparelho!",
                "Agradecemos a preferência!"
        );

        var farewells = List.of(
                "Até breve!",
                "Te esperamos aqui!",
                "Um abraço e até logo!",
                "Qualquer dúvida antes de vir, é só mandar mensagem."
        );

        return wordsOfGreeting.get(randomNumber(wordsOfGreeting.size())) + ", " + nameClient + "! " +
                goodNews.get(randomNumber(goodNews.size())) + "\n\n" +
                completionStatus.get(randomNumber(completionStatus.size())) + " " +
                pickUpInstructions.get(randomNumber(pickUpInstructions.size())) + "\n\n" +
                appreciation.get(randomNumber(appreciation.size())) + "\n\n" +
                farewells.get(randomNumber(farewells.size()));
    }

    private static Integer randomNumber(Integer sizeList) {
        var radom = new Random();
        return radom.nextInt(sizeList - 1);
    }
}
