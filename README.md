# 📌 Projeto techFixApi

## 📖 Sobre o Projeto

A TechFix API é uma aplicação RESTful (atualmente em desenvolvimento) pensada para fornecer uma solução completa de organização e gerenciamento no conserto de aparelhos eletrônicos para assistências técnicas. O objetivo é permitir que a loja tenha controle total no registro de clientes, funcionários e serviços. Além disso, o sistema permite que os clientes acompanhem o status de seus dispositivos desde o balcão até a entrega final, e auxilia na organização da ordem em que os técnicos irão realizar as manutenções na bancada.

### 🛠 Tecnologias Utilizadas

O projeto está sendo desenvolvido utilizando as seguintes tecnologias:

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJ%20IDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)
![Insomnia](https://img.shields.io/badge/Insomnia-black?style=for-the-badge&logo=insomnia&logoColor=5849BE)



### 💡 Motivação do Projeto

Este sistema foi desenvolvido como um laboratório prático para consolidar conhecimentos em desenvolvimento Back-End e Engenharia de Software. O foco principal é aplicar soluções do mundo real, como arquitetura RESTful, segurança com JWT, modelagem de banco de dados relacional e tratamento global de exceções, saindo do básico e lidando com regras de negócio complexas.

---


## 🛣️ Mapeamento de Rotas (API Endpoints)

A API foi projetada simulando o fluxo real de uma assistência técnica. Todas as rotas (exceto a de rastreio público e login) são protegidas por **Spring Security** e exigem um Token JWT no cabeçalho da requisição (`Authorization: Bearer {token}`).

### 🔐 1. Autenticação
* **`POST`** `/api/auth/login`
    * **Descrição:** Autentica um usuário (Admin ou Técnico) no sistema.
    * **Retorno:** Token JWT utilizado para acessar as rotas protegidas.

### 👤 2. Gestão de Clientes (Acessivel somente para usuarios Admin)
* **`POST`** `/api/clients`
    * **Descrição:** Cadastra um novo cliente no sistema.
* **`GET`** `/api/clients?cpf={cpf}`
    * **Descrição:** Realiza a busca de um cliente específico utilizando o CPF para.

* **`GET`** `/api/clients`
    * **Descrição:** Lista todos os clientes.

* **`GET`** `/api/clients/{id}`
    * **Descrição:** Busca Cliente por ID.

### 👥 6. Gestão de Equipe (Usuários)
Esta seção é responsável pelo gerenciamento dos funcionários da loja (Admins e Técnicos) acessivel somente para usuarios do tipo Admin.

* **`GET`** `/api/users`
    * **Descrição:** Lista todos os usuários cadastrados no sistema.
* **`GET`** `/api/users/{id}`
    * **Descrição:** Retorna os detalhes de um usuário específico buscando pelo seu ID.
* **`POST`** `/api/users`
    * **Descrição:** Cadastra um novo funcionário no sistema, definindo seu perfil de acesso.
* **`PUT`** `/api/users`
    * **Descrição:** Atualiza as informações de um funcionário existente.
* **`DELETE`** `/api/users`
    * **Descrição:** Desativa (exclusão lógica) um funcionário do sistema. O usuário não é apagado do banco de dados para manter o histórico de serviços.

### 📱 3. Pedidos de Serviço (Service Requests)
Esta seção gerencia a entrada de aparelhos na assistência técnica. O registro feito aqui é o ponto de partida do conserto.

* **`GET`** `/api/service-requests`
    * **Descrição:** Lista todos os registros de entrada de aparelhos (com paginação).
    * **Filtro opcional:** Aceita o parâmetro `?cpf={cpf}` na URL para retornar apenas o histórico de aparelhos de um cliente específico.
* **`GET`** `/api/service-requests/{id}`
    * **Descrição:** Retorna os detalhes completos de um registro de entrada específico utilizando o seu ID.
* **`POST`** `/api/service-requests`
    * **Descrição:** Registra a entrada de um aparelho defeituoso. O sistema vincula o equipamento ao cliente e **gera automaticamente** uma Ordem de Serviço (Service Order) com status `PENDING`, enviando-a para a fila da bancada.

### ⚙️ 4. Ordens de Serviço (Bancada dos Técnicos)
O fluxo da bancada utiliza um sistema *Pull* (semelhante ao Kanban), onde o técnico tem autonomia para assumir as tarefas. **Nota arquitetural:** Não há rota `POST` neste endpoint, pois a Ordem de Serviço é gerada de forma automatizada no momento em que a Recepção cria uma `ServiceRequest`.

* **`GET`** `/api/service-orders`
    * **Descrição:** Visão gerencial (Admin). Lista todas as ordens de serviço da loja (com paginação).
    * **Filtro opcional:** Aceita o parâmetro `?status={status}` para buscar manutenções em estados específicos.
* **`GET`** `/api/service-orders/pending`
    * **Descrição:** Rota da "Piscina de Tarefas". Retorna todas as ordens que acabaram de chegar do balcão e possuem o status `PENDING`, prontas para serem assumidas.
* **`GET`** `/api/service-orders/my-tasks`
    * **Descrição:** Retorna a bancada pessoal do técnico logado (injetado via Token JWT). Exibe apenas as manutenções que ele assumiu.
    * **Filtro opcional:** Aceita o parâmetro `?status={status}` para o técnico filtrar suas próprias tarefas (ex: buscar apenas as que estão aguardando peça).
* **`PATCH`** `/api/service-orders/{idServiceOrder}`
    * **Descrição:** Atualiza o status da Ordem de Serviço. É utilizada para o técnico iniciar a manutenção, momento em que o sistema captura o seu Token e o vincula como responsável oficial pelo conserto.

### 🔍 5. Rastreio Público (Visão do Cliente)
* **`GET`** `/api/service-orders/tracking/{code}`
    * **Descrição:** Rota **pública** onde o cliente consulta o andamento do seu aparelho utilizando o código de rastreio fornecido pela loja. Retorna um DTO limpo e seguro (`ServiceOrderForClientDTO`), sem expor os dados internos dos funcionários ou do banco de dados.
