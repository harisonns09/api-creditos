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
