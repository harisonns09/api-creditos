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
