package com.dev.ednei.techFixApi.service.externalApis.evolutionApi;

import com.dev.ednei.techFixApi.DTOS.supportContacts.SupportContactFullDTO;
import com.dev.ednei.techFixApi.repository.SupportContactRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Random;

public class WhatsAppMessagesUtil {
    @Autowired
    private static SupportContactRepository supportContactRepository;

    public static String createMessageUpdateOs(String nameClient, String statusOrderService) {
        var wordsOfGreeting = List.of("Olá", "Oi", "Opa", "Saudações", "Olá novamente");

        var introductoryRemarks = List.of(
                "Passando para avisar sobre o andamento",
                "Só pra te atualizar sobre",
                "Viemos te informar o status",
                "Mensagem rápida pra atualizar",
                "Trazendo atualizações sobre",
                "Passando aqui para deixar você a par",
                "Entrando em contato para te atualizar sobre",
                "Viemos trazer novidades da",
                "Te mandando essa mensagem para informar sobre",
                "Passando para deixar um aviso rápido da"
        );

        var serviceTypes = List.of("sua manutenção", "sua revisão", "o seu reparo");

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
                "vamos continuar te mantendo informado por aqui mesmo",
                "sempre que houver mudança nós te avisamos direto por aqui",
                "nosso sistema te mandará mensagem a cada avanço"
        );

        var farewells = List.of(
                "Um abraço e até a próxima atualização!",
                "Até logo!",
                "Seguimos trabalhando no seu aparelho!",
                "Em breve traremos mais novidades."
        );

        var botWarning = List.of(
                "🤖 *OBS:* Este número é apenas um bot automático do nosso sistema. Ele não recebe respostas, áudios ou ligações.",
                "⚠️ *Aviso:* Esta é uma mensagem automática do sistema. Por favor, não responda a este WhatsApp.",
                "🤖 *Atenção:* Este WhatsApp é usado exclusivamente para envio automático de status e não recebe mensagens.",
                "⚠️ *OBS:* Mensagem gerada automaticamente. Para falar conosco, utilize nosso número de atendimento oficial ou venha até a loja."
        );

        return wordsOfGreeting.get(randomNumber(wordsOfGreeting.size())) + ", " + nameClient + "! " +
                introductoryRemarks.get(randomNumber(introductoryRemarks.size())) + " " +
                serviceTypes.get(randomNumber(serviceTypes.size())) + ".\n\n" +
                statusTransitions.get(randomNumber(statusTransitions.size())) + " *" + statusOrderService + "*.\n\n" +
                reassurancePhrases.get(randomNumber(reassurancePhrases.size())) + " " +
                trackingAssurances.get(randomNumber(trackingAssurances.size())) + ".\n\n" +
                farewells.get(randomNumber(farewells.size())) + "\n\n" +
                botWarning.get(randomNumber(botWarning.size()));
    }

    public static String createMessageOrderServiceOpened(String nameClient, String linkUrl, String codeOs) {
        var wordsOfGreeting = List.of("Olá", "Oi", "Opa", "Saudações");

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
                "Se quiser acompanhar o andamento do conserto",
                "Para acompanhar o serviço em tempo real",
                "Caso queira checar o status do reparo",
                "Para ver os detalhes do conserto do seu aparelho"
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
                "Agradecemos a preferência e cuidaremos bem do seu aparelho!",
                "Em breve traremos mais atualizações.",
                "Deixe com a gente, o seu aparelho está em boas mãos!",
                "Agradecemos a confiança no nosso trabalho!"
        );

        var botWarning = List.of(
                "🤖 *OBS:* Este número é apenas um bot automático do nosso sistema. Ele não recebe respostas, áudios ou ligações.",
                "⚠️ *Aviso:* Esta é uma mensagem automática do sistema. Por favor, não responda a este WhatsApp.",
                "🤖 *Atenção:* Este WhatsApp é usado exclusivamente para envio automático de status e não recebe mensagens.",
                "⚠️ *OBS:* Mensagem gerada automaticamente. Para falar conosco, utilize nosso número de atendimento oficial ou venha até a loja."
        );

        return wordsOfGreeting.get(randomNumber(wordsOfGreeting.size())) + ", " + nameClient + "! " +
                introductoryRemarks.get(randomNumber(introductoryRemarks.size())) + " que o seu aparelho foi " +
                actionStatus.get(randomNumber(actionStatus.size())) + " com sucesso no nosso sistema.\n\n" +
                trackingMotivations.get(randomNumber(trackingMotivations.size())) + ", " +
                trackingInstructions.get(randomNumber(trackingInstructions.size())) + "\n" +
                linkUrl + "\n\n" +
                codeIntroductions.get(randomNumber(codeIntroductions.size())) + " *" + codeOs + "*.\n\n" +
                farewells.get(randomNumber(farewells.size())) + "\n\n" +
                botWarning.get(randomNumber(botWarning.size()));
    }

    public static String createMessageOrderServiceCompleted(String nameClient) {
        var wordsOfGreeting = List.of("Olá", "Oi", "Opa", "Saudações");

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
                "e você já pode vir buscar na loja quando quiser.",
                "e ele já está liberado para retirada no nosso balcão.",
                "e você pode passar aqui na assistência para retirar.",
                "e estamos com ele separadinho, aguardando a sua visita."
        );

        var appreciation = List.of(
                "Agradecemos muito pela confiança no nosso trabalho!",
                "Muito obrigado por escolher os nossos serviços!",
                "Foi um prazer cuidar do seu aparelho!",
                "Agradecemos a preferência e confiança!"
        );

        var farewells = List.of(
                "Até breve aqui na loja!",
                "Te esperamos aqui no balcão!",
                "Um abraço e até logo!",
                "Nos vemos em breve na loja!"
        );

        var botWarning = List.of(
                "🤖 *OBS:* Este número é apenas um bot automático do nosso sistema. Ele não recebe respostas, áudios ou ligações.",
                "⚠️ *Aviso:* Esta é uma mensagem automática do sistema. Por favor, não responda a este WhatsApp.",
                "🤖 *Atenção:* Este WhatsApp é usado exclusivamente para envio automático de status e não recebe mensagens.",
                "⚠️ *OBS:* Mensagem gerada automaticamente. Para falar conosco, utilize nosso número de atendimento oficial."
        );

        return wordsOfGreeting.get(randomNumber(wordsOfGreeting.size())) + ", " + nameClient + "! " +
                goodNews.get(randomNumber(goodNews.size())) + "\n\n" +
                completionStatus.get(randomNumber(completionStatus.size())) + " " +
                pickUpInstructions.get(randomNumber(pickUpInstructions.size())) + "\n\n" +
                appreciation.get(randomNumber(appreciation.size())) + "\n\n" +
                farewells.get(randomNumber(farewells.size())) + "\n\n" +
                botWarning.get(randomNumber(botWarning.size()));
    }

    public static String createMessageOrderServiceCanceled(String nameClient) {
        var wordsOfGreeting = List.of("Olá", "Oi", "Tudo bem", "Como vai");

        var badNews = List.of(
                "Passando para te dar um retorno sobre o seu aparelho.",
                "Entrando em contato para atualizar o status do seu serviço.",
                "Trazendo uma atualização sobre a sua ordem de serviço.",
                "Infelizmente, temos uma notícia chata sobre o reparo."
        );

        var detailsInStore = List.of(
                "O conserto precisou ser cancelado. Para entender os detalhes técnicos do que aconteceu, pedimos que compareça à nossa loja.",
                "Não foi possível dar andamento ao reparo. Por favor, venha até a loja para que possamos te explicar a situação pessoalmente.",
                "Houve um imprevisto técnico que inviabilizou o conserto. Compareça à loja para conversarmos melhor sobre os detalhes.",
                "Nossos testes mostraram que o serviço não seria viável. Passa aqui na loja para te explicarmos exatamente o que houve."
        );

        var pickUpInstructions = List.of(
                "O seu aparelho já está separado e totalmente liberado para devolução.",
                "O dispositivo já se encontra na bancada aguardando a sua retirada.",
                "Você já pode passar aqui a qualquer momento para retirar o equipamento.",
                "Já deixamos tudo pronto para a devolução assim que você chegar."
        );

        var apology = List.of(
                "Pedimos desculpas pelo imprevisto e agradecemos muito a compreensão.",
                "Agradecemos a confiança e sentimos muito por não conseguir resolver desta vez.",
                "Lamentamos o transtorno e estamos à disposição presencialmente para tirar qualquer dúvida.",
                "Agradecemos a preferência de sempre e pedimos desculpas pelo inconveniente."
        );

        var botWarning = List.of(
                "🤖 *OBS:* Este número é apenas um bot automático do nosso sistema. Ele não recebe respostas, áudios ou ligações.",
                "⚠️ *Aviso:* Esta é uma mensagem automática do sistema. Por favor, não responda a este WhatsApp.",
                "🤖 *Atenção:* Este WhatsApp é usado exclusivamente para envio automático de status e não recebe mensagens.",
                "⚠️ *OBS:* Mensagem gerada automaticamente. Para falar conosco, utilize nosso número de atendimento oficial ou venha até a loja."
        );

        return wordsOfGreeting.get(randomNumber(wordsOfGreeting.size())) + ", " + nameClient + "! " +
                badNews.get(randomNumber(badNews.size())) + "\n\n" +
                detailsInStore.get(randomNumber(detailsInStore.size())) + " " +
                pickUpInstructions.get(randomNumber(pickUpInstructions.size())) + "\n\n" +
                apology.get(randomNumber(apology.size())) + "\n\n" +
                botWarning.get(randomNumber(botWarning.size()));
    }

    public static String messageOfContacts(List<SupportContactFullDTO> contacts) {

        var wordsOfGreeting = List.of("Olá", "Oi", "Opa", "Tudo bem", "Tudo joia");

        var introMessages = List.of(
                "Conforme combinado, seguem os nossos contatos:",
                "Aqui estão os contatos de suporte que temos disponíveis:",
                "Estou te enviando os nossos canais de atendimento direto:",
                "Segue abaixo a lista com os nossos números para contato:"
        );

        var actionMessages = List.of(
                "Qualquer dúvida, é só chamar em um desses números.",
                "Fique à vontade para entrar em contato caso precise de algo.",
                "Estamos à disposição em qualquer um desses canais.",
                "Pode nos acionar por qualquer um desses contatos quando precisar."
        );

        var farewells = List.of(
                "Um abraço!",
                "Até logo!",
                "Ficamos à disposição!",
                "Tenha um excelente dia!"
        );

        String formatTypePhone = "- Telefone: ";
        String formatTypeEmail = "- Email: ";
        String formatTypeWhatsapp = "- Whatsapp: ";


        for (SupportContactFullDTO contactDto : contacts) {
            if (contactDto.type().equalsIgnoreCase("PHONE")) {
                formatTypePhone += contactDto.contact() + ", ";
            }

            if (contactDto.type().equalsIgnoreCase("WHATSAPP")) {
                formatTypeWhatsapp += contactDto.contact() + ", ";
            }

            if (contactDto.type().equalsIgnoreCase("EMAIL")) {
                formatTypeEmail += contactDto.contact() + ", ";
            }
        }

        var messageFinal = wordsOfGreeting.get(randomNumber(wordsOfGreeting.size())) + "!\n" + introMessages.get(randomNumber(introMessages.size())) + "\n\n" +
                formatTypePhone + "\n" +
                formatTypeEmail + "\n" +
                formatTypeWhatsapp + "\n\n" +
                actionMessages.get(randomNumber(actionMessages.size())) + " " + farewells.get(randomNumber(farewells.size())) + "\n";

        return messageFinal;

    }

    private static Integer randomNumber(Integer sizeList) {
        var radom = new Random();
        return radom.nextInt(sizeList - 1);
    }
}
