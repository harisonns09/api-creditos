# 📄 API de Consulta de Créditos - ISSQN

API REST desenvolvida em Java com Spring Boot para consulta de créditos tributários vinculados a notas fiscais de serviços eletrônicas (NFS-e), especialmente voltada para o imposto ISSQN.

---

## 🚀 Funcionalidades

- 🔎 Buscar crédito por número do crédito
- 🔍 Consultar todos os créditos vinculados a uma NFS-e
- 📑 Estrutura de dados clara e extensível
- ✅ Pronta para integração com frontend Angular - [Frontend-creditos](https://github.com/harisonns09/front-api-creditos)

---

## 🛠️ Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.x**
- **Spring Web**
- **Spring Data JPA**
- **H2 Database (memória)**
- **Maven**
- **Lombok**
- **Swagger OpenAPI (documentação)**

---

## 📦 Instalação e Execução

### Pré-requisitos

- [Java 17+](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Maven 3.8+](https://maven.apache.org/download.cgi)
- (Opcional) IDE como IntelliJ ou VSCode

### Executando localmente

```bash
# Clone o repositório
git clone https://github.com/harisonns09/api-creditos.git
cd api-creditos

# Compile e rode a aplicação
./mvnw spring-boot:run

A aplicação estará disponível em:
📍 http://localhost:8080

🔗 Endpoints Principais

Método	Endpoint	Descrição
GET	/api/creditos/credito/{id}	Retorna um crédito com base no número
GET	/api/creditos/{numeroNfse}	Lista os créditos vinculados a uma NFS-e

Exemplo de Resposta JSON

{
  "numeroCredito": "123456",
  "numeroNfse": "7891011",
  "dataConstituicao": "2024-02-25",
  "valorIssqn": 1500.75,
  "tipoCredito": "ISSQN",
  "simplesNacional": "Sim",
  "aliquota": 5,
  "valorFaturado": 30000,
  "valorDeducao": 5000,
  "baseCalculo": 25000
}
