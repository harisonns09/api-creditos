# API de Consulta de Créditos

API REST para consulta de créditos fiscais vinculados a notas fiscais de serviços eletrônicas (NFS-e). Desenvolvida em Spring Boot, a aplicação oferece endpoints para busca e listagem de créditos por número de NFS-e e por número de crédito, além de integração com RabbitMQ para envio e consumo de mensagens.

---

## Índice

- [Funcionalidades](#funcionalidades)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Arquitetura](#arquitetura)
- [Configuração do RabbitMQ](#configuração-do-rabbitmq)
- [Endpoints](#endpoints)
- [Tratamento de Erros](#tratamento-de-erros)
- [Como Rodar](#como-rodar)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Contribuições](#contribuições)
- [Licença](#licença)

---

## Funcionalidades

- Consultar créditos por número de NFS-e
- Buscar crédito por número de crédito
- Enviar dados de créditos para fila RabbitMQ
- Consumir mensagens de créditos da fila RabbitMQ
- Retornar mensagens de erro estruturadas em caso de recurso não encontrado

---

## Tecnologias Utilizadas

- Java 17+
- Spring Boot
- Spring Data JPA
- ModelMapper
- RabbitMQ (Spring AMQP)
- PostgreSQL (banco de dados)
- Maven (gerenciamento de dependências)
- Jakarta Persistence API (JPA)

---

## Arquitetura

A aplicação segue arquitetura REST com camadas divididas em:

- **Model**: Entidade `Credito`
- **Repository**: Interface para operações com banco de dados `CreditoRepository`
- **Service**: Lógica de negócio `CreditoService` e integração com RabbitMQ (`CreditoMessageProducer` e `CreditoMessageConsumer`)
- **Controller**: Camada REST API (`CreditoController`)
- **DTOs**: Transferência de dados entre camadas (`CreditoDTO`)
- **Exception Handling**: Tratamento de exceções com `RestExceptionHandler`
- **Mensageria**: Configuração RabbitMQ (`RabbitMQConfig`)

---

## Configuração do RabbitMQ

O projeto utiliza RabbitMQ para comunicação assíncrona. As principais configurações são:

- Fila: `credito.fila`
- Exchange: `creditos.exchange` (Direct Exchange)
- Routing Key: `creditos.routing.key`
- Conversor de mensagem: JSON via `Jackson2JsonMessageConverter`

As configurações podem ser ajustadas no arquivo `application.properties` ou `application.yml` com as propriedades:

```properties
spring.rabbitmq.queue=credito.fila
spring.rabbitmq.exchange=creditos.exchange
spring.rabbitmq.routingkey=creditos.routing.key
```

## Endpoints

Listar créditos por número NFS-e
```
GET /api/creditos/{numeroNfse}
```
Retorna uma lista de créditos vinculados ao número da NFS-e.

Exemplo de resposta:
```
[
  {
    "numeroCredito": "123",
    "numeroNfse": "456",
    "dataConstituicao": "2023-05-10",
    "valorIssqn": 100.00,
    "tipoCredito": "ISSQN",
    "simplesNacional": "Sim",
    "aliquota": 2.5,
    "valorFaturado": 4000.00,
    "valorDeducao": 0.00,
    "baseCalculo": 4000.00
  }
]
```


Buscar crédito por número de crédito

```GET /api/creditos/credito/{numeroCredito}```
Retorna um crédito específico pelo número do crédito. Caso encontrado, o crédito também é enviado para a fila RabbitMQ.

Exemplo de resposta:
```
{
  "numeroCredito": "123",
  "numeroNfse": "456",
  "dataConstituicao": "2023-05-10",
  "valorIssqn": 100.00,
  "tipoCredito": "ISSQN",
  "simplesNacional": "Sim",
  "aliquota": 2.5,
  "valorFaturado": 4000.00,
  "valorDeducao": 0.00,
  "baseCalculo": 4000.00
}
```
Se o crédito não for encontrado, retorna HTTP 404 com mensagem JSON.


## Tratamento de Erros

Quando um recurso não é encontrado (ResourceNotFoundException), a API retorna um JSON padronizado:
```
{
  "titulo": "Not Found",
  "status": 404,
  "mensagem": "Mensagem detalhada do erro"
}
```


## Como Rodar

Pré-requisitos
Java 17+
Maven
Docker (para rodar o PostgreSQL e RabbitMQ facilmente)
Passos

Clone o repositório:
```
git clone https://github.com/seu-usuario/api-creditos.git
cd api-creditos
```


Suba o PostgreSQL e RabbitMQ via Docker (exemplo simples):
```
docker run -d --name postgres-db -e POSTGRES_PASSWORD=senha -p 5433:5432 postgres
docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management
```

Ajuste as configurações do banco e RabbitMQ no application.properties (porta do PostgreSQL, usuário, senha, etc).

Build e execute a aplicação:
```
mvn clean install
mvn spring-boot:run
```
A API estará disponível em http://localhost:8080/api/creditos

## Estrutura do Projeto
```
src/main/java/com/consultacreditos/consulta
│
├── model
│   └── Credito.java
├── repository
│   └── CreditoRepository.java
├── services
│   ├── CreditoService.java
│   ├── CreditoMessageProducer.java
│   └── CreditoMessageConsumer.java
├── shared
│   └── CreditoDTO.java
├── view
│   ├── controller
│   │   └── CreditoController.java
│   └── model
│       ├── CreditoRequest.java
│       └── CreditoResponse.java
├── handler
│   └── RestExceptionHandler.java
├── messages
│   └── RabbitMQConfig.java
├── model
│   ├── error
│   │   └── ErrorMessage.java
│   └── exception
│       └── ResourceNotFoundException.java
└── ConsultaApplication.java
```
## Contribuições

Contribuições são bem-vindas! Para contribuir:

Fork o repositório
Crie uma branch para sua feature (git checkout -b minha-feature)
Commit suas mudanças (git commit -m 'Minha nova feature')
Push para a branch (git push origin minha-feature)
Abra um Pull Request




