# TechFixApi REST
 
A TechFixApi é uma aplicação desenvolvida em **Java, Spring Boot e PostgreSQL** para gerenciar operações de uma assistência técnica. O sistema controla estoque de peças, catálogo de serviços, ordens de serviço, clientes, funcionários, pagamentos, histórico de alterações das OS e notificações via WhatsApp quando a atualização de Status da Ordem de Serviço. O acesso é g
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          erenciado por autenticação via **token JWT** e perfis de permissão (`MANAGER`, `TECHNICAL`, `ATTENDANT`), com respostas de erro padronizadas e dados entregues com paginação.
 
Este é um projeto pessoal, criado para aplicar e testar conhecimentos em desenvolvimento backend.
 
---
 
## ✨ Principais funcionalidades
 
- **Autenticação e autorização** via JWT, com controle de acesso por perfil de usuário
- **Gestão de usuários e funcionários**, incluindo fluxo de primeiro acesso com troca obrigatória de senha e recuperação de senha por e-mail
- **Cadastro de clientes** com busca por CPF
- **Solicitações de serviço** (`service-requests`), que dão origem às ordens de serviço
- **Ordens de serviço** (`service-orders`) completas: itens, tarefas, pagamentos e histórico de alterações de status
- **Acompanhamento do Concerto**, o cliente ao entregar o aparelho e registrar o problema, recebe um codígo que pode ser usado para acompanhar as estapas do concerto.
- **Catálogo de serviços** e **controle de estoque de peças**, com controle de quantidade e habilitação/desabilitação de itens
- **Histórico de pagamentos e de atualizações da ordem de serviço**, com busca paginada por ordem de serviço. A cada alteração de status da OS e Pagamento e registrado a data e hora e qual usuario fez a mudança.
- **Dashboard de analytics**: métricas gerenciais (`/dashboard/metrics`) mostra dados estrategicos para MANAGER e alertas em tempo real de estoque (`/real-time-alerts`) aviso se vai faltar peças no mês. O alerta de estoque tem 2 tipos RED(estoque acabou ou não vai dar para mês todo) e YELLOW(estoque dará para o mês, mas pode faltar se for consumido mas que mês anterior)
- **Contatos de suporte** (`support-contact`), de uso restrito ao perfil `MANAGER`, para cadastro de contatos da assistencia. Tipos de contatos: "WHATSAPP", "PHONE", "EMAIL".
- **Notificações via WhatsApp**, através da integração com a **Evolution API**. Bot automatico que envia atualizações para o cliente toda vez que o status da Ordem de Serviço muda ou finaliza.
- Respostas de erro padronizadas seguindo **RFC 7807** (`ProblemDetail`)
- Documentação completa da API via **Swagger/OpenAPI 3.0**, possivel testar e ver cada rota da API.
- **Docker**, infraestrutura do projeto (Banco de dados, evolutionApi) está containerizada

## 👤 Perfis

| Perfil      | Função                       |
| ----------- | ------------------------------ |
| `ATTENDANT` / `ATENDENTE`  | Realiza cadastro de Ordem de Serviço e Clientes e registra a confirmação de pagamento e entrega dos dispositivos. |
| `TECHNICAL` / `TÉCNICO`  | Consegue ver e aceitar Ordem de Serviço e gerenciar status, peças usadas e tipos de serviços das suas OS aceitas. |
| `MANAGER` / `GERENTE`  | Tem controle de todo o sistema. Consegue gerenciar funcionários, estoque, catálogos de serviço e contatos de suporte. Tem acesso à dashboard e também pode trabalhar individualmente caso seja uma assistência sem nenhum funcionário. |

## 🛠️ Tecnologias
 
| Categoria         | Tecnologia                                       |
|--------------------|---------------------------------------------------|
| Linguagem/Runtime  | Java 21                                            |
| Framework          | Spring Boot (Web MVC, Data JPA, Validation, Mail, Security) |
| Banco de dados     | PostgreSQL 17                                      |
| Migrations         | Flyway                                             |
| Autenticação       | JWT (`java-jwt`) / Auth0                                    |
| Documentação       | Swagger UI / OpenAPI 3.0 (especificação própria, escrita manualmente) |
| Integrações        | Evolution API (WhatsApp)                            |
| Build              | Maven                                              |
| Infraestrutura      | Docker                                             |
 
 
## ✅ Pré-requisitos
 
Antes de começar, tenha instalado em sua máquina:
 
- [JDK 21+](https://adoptium.net/)
- [Maven 3.9+](https://maven.apache.org/) 
- **PostgreSQL 17** instalado e rodando localmente
- Uma instância da **Evolution API** configurada local ou docker (veja o passo 4 abaixo)
- Uma conta de e-mail do Gmail com **senha de aplicativo** habilitada (usada para envio de e-mails via SMTP, sobre autualização de senhas)
- [Docker](https://www.docker.com/get-started/) e **Docker Compose** **não é obrigatorio**, somente se for implementar via docker que é o **recomendado**
 
## 🚀 Como executar o projeto
 
## 1. Baixar e configurar tudo manualmente

### 1.1 Clonar o repósitorio
 
```bash
git clone https://github.com/Ednei-Gonzaga/techFixAPI.git
```
 
### 1.2 Criar o banco de dados no PostgreSQL 17
 
Tenha instalado o [Postegres 17](https://www.postgresql.org/about/news/postgresql-17-released-2936/), e crie um banco vazio para a aplicação (o Flyway cuida de criar todas as tabelas automaticamente na primeira execução):
 
```sql
CREATE DATABASE techfix_db;
```
 
### 1.3 Configurar as variáveis de ambiente
 
As configurações sensíveis da aplicação ficam centralizadas em:
 
```
backend/src/main/resources/application.properties
```
 
Esse arquivo referencia variáveis de ambiente (não vêm com valores fixos no repositório). Configure as seguintes variáveis antes de subir a aplicação:
 
| Variável                  | Descrição                                                              | Exemplo                                                |
|----------------------------|---------------------------------------------------------------------------|-----------------------------------------------------------|
| `URL_DB`                  | URL de conexão JDBC do PostgreSQL 17                                      | `jdbc:postgresql://localhost:5432/techfix_db`              |
| `USERNAME_DB`              | Usuário do banco de dados                                                 | `postgres`                                                 |
| `PASSWORD_DB`              | Senha do banco de dados                                                   | `postgres`                                                 |
| `SHIPPING_EMAIL`           | E-mail (Gmail) usado para envio de notificações via SMTP                  | `seuprojeto@gmail.com`                                     |
| `PASSWORD_EMAIL`           | Senha de aplicativo do e-mail acima                                       | `xxxx xxxx xxxx xxxx`                                       |
| `PASSWORD_TOKEN`           | Chave secreta usada para assinar e validar os tokens JWT                  | `uma-string-longa-e-aleatoria`                              |
| `INSTANCE_NOTIFICATION_OS` | Nome da instância criada na Evolution API, usada para enviar mensagens    | `techfix-instance`                                          |
| `EVOLUTION_API_KEY`        | API Key configurada na sua instância da Evolution API, usada para ter permissão de acesso aos endpoints                    | `sua-api-key`                                               |
|`EVOLUTION_API_SERVER`      | Servidor onde vai ficar a sua Evolution API                 | `http://localhost:8081`  |
 
O projeto faz envio de **EMAIL pela plataforma do google** para atualização de senha, é necessario que você escolha um EMAIL e gere uma [Senha de Aplicativo](https://support.google.com/accounts/answer/185833?hl=pt-BR) e crie as variaveis de ambientes: **SHIPPING_EMAIL**, **PASSWORD_EMAIL**.
 
### 1.4 Configurar e subir a Evolution API
 
A aplicação se comunicará com a Evolution API através do endereço definido na variavel`EVOLUTION_API_SERVER`(usada internamente no `EvolutionApiService`). Você precisa ter uma instância dela rodando no server definido e na mesma versão usada no desenvolvimento deste projeto, que foi a v2.3.6, caso ultilize versão mais atual ou anterior, pode ser que não funcione corretamente.
 
Passos gerais:
 
1. Baixe e execute a [Evolution API](https://docs.evolutionfoundation.com.br/evolution-api/installation) definindo o server na variável informada anteriomente. Consulte a [documentação oficial da Evolution API](https://doc.evolution-api.com/) para o método de instalação de sua preferência (Docker, instalação manual via Node.js, etc.)

2. A [Evolution API](https://docs.evolutionfoundation.com.br/evolution-api/installation) usa uma chave para identificação e acesso aos endpoints. Defina essa chave que foi criada na hora da instalação na variável `EVOLUTION_API_KEY` do passo 1.3.

3. Após configurar a [Evolution API](https://docs.evolutionfoundation.com.br/evolution-api/installation), defina um nome da instacia que deseja criar na variavel de ambiente `INSTANCE_NOTIFICATION_OS`, pode ser qualquer nome (isso e sá para conseguimos identificar o nome da conexão criada na Evolution API e realizar as requizições apartir dele).

4. Use o endpoint `GET /api/v2/whatsapp/instance/connect` da TechFixApi (acessível apenas ao perfil `MANAGER`) para gerar o QR Code de conexão da instância com o WhatsApp.

> OBS: A Evolution API funciona perfeitamente, só que é um projeto desenvolvido pela comunidade, ou seja, não é oficial do WhatsApp. Em atualizações das políticas de segurança do WhatsApp, o número pode vir a ser bloqueado ou desativado. Portanto, sempre use um número que seja destinado somente a essa função; dessa forma, não correrá o risco de perda de dados importantes.

### 1.5 Executar a aplicação
 
Com o PostgreSQL e a Evolution API no ar e as variáveis de ambiente configuradas, rode a aplicação.

 
A API sobe por padrão em `http://localhost:8080`. As tabelas são criadas automaticamente pelo Flyway na primeira execução (migrations em `src/main/resources/db/migration`).

# 2. Instalar via Docker

### 2.1 Clonar o repósitorio

Com o [Docker](https://www.docker.com/get-started/) já instalado, baixe o projeto ou clone ele com: 
 
```bash
git clone https://github.com/Ednei-Gonzaga/techFixAPI.git
```
e acesse a pasta principal `backend`

### 2.2 Configure as variáveis de ambiente
Acesse o arquivo `docker-compose.yml` na raiz do projeto e altere as variáveis essenciais na seção `environment` do serviço `techFix-api-java:` com os seus dados reais. É obrigatório configurar pelo menos as variáveis de e-mail para que todas as funcionalidades operem corretamente: 
```yaml
## E-MAIL QUE FARÁ ENVIo DAS MENSAGENS
SHIPPING_EMAIL: seu-email-real@gmail.com

## SENHA DE APLICATIVO DO GMAIL
PASSWORD_EMAIL: sua_senha_de_app_gerada_aqui

```

Como esse projeto é apenas para testes e portfólio, escolhi deixar as outras variáveis(banco de dados, Redis e Evolution API) já **pré-configuradas** para facilitar a execução de quem for testar. Caso queira alterar todas elas estão comentadas informando o que colocar, nesse mesmo arquivo `docker-compose.yml`.

### 2.3 Subir o projeto
Depois de configurar o arquivo `docker-compose.yml`, basta rodar esse comando na mesma pasta que estiver **dockfile, docker-compose.yml**:

```
docker compose up --build -d
```

Quando o terminal liberar, os contêineres estarão rodando em segundo plano. Você poderá acessar aplicação em `http://localhost:8080` ee realizar testes utilizando ferramentas como **Insominia/Postman** ou acessar o **SWAGGER UI** disponivel na rota 
[localhost:8080/swagger.yaml](http://localhost:8080/swagger-ui/index.html).
 
# 🧪 Como testar
 
Ao iniciar a aplicação, o Flyway **criará automaticamente um usuário padrão** `MANAGER` com `login` 'UserAdmin' e `password`'TechFix@Api', para que quem for testar consiga receber o token, cadastrar funcionários e acessar as demais rotas. Esse usuário precisará alterar `login` e `password` no primeiro acesso. Para testar aplicação use:

- **Swagger UI:** `http://localhost:8080/swagger-ui/index.html`, onde posssui todos endpoints para testes e informações de retornos e erros
- **Ferramentas como Insomnia/Postman**.

**Passo a passo básico:**
 
1. Autentique-se em `POST /api/v2/auth/login` com um usuário válido para obter o token JWT.
2. Use o token retornado no header `Authorization: Bearer <token>` nas demais requisições.
3. Explore os recursos de acordo com o perfil do usuário autenticado (`MANAGER`, `TECHNICAL` ou `ATTENDANT`) — os endpoints têm regras de autorização específicas por perfil e por método HTTP, então usuários com perfis diferentes enxergam conjuntos de rotas diferentes.
## 📚 Principais grupos de endpoints
 
Todos os endpoints ficam sob o prefixo `/api/v2`. Alguns destaques:
 
| Grupo                     | Endpoints principais                                             | Descrição                                                |
|-----------------------------|----------------------------------------------------------------------|-----------------------------------------------------------|
| Autenticação                | `POST /auth/login`                                                   | Login e emissão de token JWT                              |
| Usuários                    | `/users`, `/users/reset-password`, `/users/me/password`              | Criação, recuperação e troca de senha                     |
| Funcionários                | `/employees`, `/employees/me`, `/employees/search/cpf`               | Gestão de funcionários (restrito a `MANAGER`)              |
| Clientes                    | `/clients`, `/clients/cpf/search`                                    | Cadastro e busca de clientes                               |
| Peças                       | `/parts`, `/parts/{id}/quantity`, `/parts/{id}/enable`                | Estoque e Gerenciamento peças                                            |
| Catálogo de serviços        | `/service-catalogs`, `/admin/service-catalogs`                       | Gerenciamento dos serviços oferecidos pela assistência técnica                |
| Solicitações de serviço     | `/service-requests`                                                   | Registro inicial de uma demanda do cliente                  |
| Ordens de serviço           | `/service-orders`, `/service-orders/{id}/payments`, `/service-order-items`, `/service-order-tasks` | Ciclo completo da ordem de serviço                           |
| Histórico                   | `/service-order/{id}/history/updates`, `/service-order/{id}/payment/history` | Histórico paginado de status da OS e pagamentos (restrito a `MANAGER`) |
| Contato de suporte          | `/support-contact`                                                    | Cadastro dos contatos da assistencia. Restrito a `MANAGER`                                         |
| Analytics                   | `/dashboard/metrics`, `/real-time-alerts`                            | Métricas e alertas gerenciais (restrito a `MANAGER`)         |
| WhatsApp                    | `/whatsapp/instance/connect`, `/whatsapp/instance/detail`, `/whatsapp/instance` | Gestão da conexão com a Evolution API (restrito a `MANAGER`) |
 
A descrição detalhada de cada endpoint (parâmetros, corpo de requisição, respostas e códigos de erro) está disponível no Swagger UI da aplicação. E a explicação de funcionamento de cada endpoint fica na [Documentação Detalhada](https://github.com/Ednei-Gonzaga/techFixAPI/blob/main/backend/src/main/resources/static/TechFixApi-Docs.pdf) na pasta `resources/static`.
 
## 📁 Estrutura do projeto
 
```
backend/
├── src/main/java/com/dev/ednei/techFixApi/
│   ├── controller/       # Endpoints REST
│   ├── service/          # Regras de negócio
│   ├── repository/       # Acesso a dados (Spring Data JPA)
│   ├── model/             # Entidades e enums
│   ├── DTOS/               # Objetos de transferência de dados
│   └── infra/              # Segurança e tratamento de exceções
└── src/main/resources/
    ├── application.properties
    ├── static/swagger.yaml     # Especificação OpenAPI escrita manualmente
    └── db/migration/            # Scripts de versionamento do banco (Flyway)
```
 

## 📄 Licença
 
Este projeto está sob a licença MIT.
 
## 👤 Autor
 
Desenvolvido por **Ednei** — projeto pessoal para estudo e aplicação prática de conceitos de backend com Java e Spring Boot.