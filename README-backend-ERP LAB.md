# ERP-LAB — Backend

> API REST desenvolvida em Spring Boot para gerenciamento de clientes, endereços, produtos e vendas.

---

## 📋 Sobre o projeto

O **ERP-LAB** é um projeto de estudo desenvolvido para praticar conceitos de desenvolvimento backend com Java e Spring Boot, cobrindo desde modelagem de entidades JPA até exposição de endpoints RESTful com paginação e boas práticas de arquitetura em camadas.

---

## 🚀 Tecnologias

| Tecnologia | Versão |
|---|---|
| Java | 17+ |
| Spring Boot | 3.x |
| Spring Data JPA |
| PostgreSQL | 15+ |
| Docker / Docker Compose |
| Maven |
| Springdoc OpenAPI | 2.8.16 |

---

## 🗂️ Estrutura do projeto

```
erp-lab/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/erplab/
│   │   │       ├── controller/
│   │   │       ├── service/
│   │   │       ├── repository/
│   │   │       ├── domain/
│   │   │       └── dto/
│   │   │       └── infra/
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── docker-compose.yml
└── pom.xml
```

---

## ⚙️ Como executar

### Pré-requisitos

- Java 17+
- Docker e Docker Compose instalados

### 1. Clone o repositório

```bash
git clone https://github.com/seu-usuario/erp-lab.git
cd erp-lab
```

### 2. Suba o banco de dados com Docker

```bash
docker-compose up -d
```

### 3. Execute a aplicação

```bash
./mvnw spring-boot:run
```

A API estará disponível em `http://localhost:8080`.

---

## 📖 Documentação

A API possui documentação interativa gerada automaticamente pelo **Springdoc OpenAPI (Swagger UI)**.

| Interface | URL |
|---|---|
| Swagger UI | `http://localhost:8080/swagger-ui/index.html` |
| Especificação OpenAPI (JSON) | `http://localhost:8080/api-docs` |

> A documentação lista todos os endpoints com descrições, parâmetros e exemplos de requisição/resposta.

---

## 🔌 Endpoints

### Produtos

| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/api/v1/produtos` | Lista todos os produtos |
| `GET` | `/api/v1/produtos/{cod}` | Busca produto por código |
| `POST` | `/api/v1/produtos` | Cadastra produto |
| `PUT` | `/api/v1/produtos/{cod}` | Edita produto por código |
| `PUT` | `/api/v1/produtos/add/{cod}/{qnt}` | Adiciona quantidade ao estoque |

### Clientes

| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/api/v1/clientes` | Lista clientes |
| `GET` | `/api/v1/clientes/{cpf}` | Busca cliente por CPF |
| `GET` | `/api/v1/clientes/{cpf}/enderecos` | Busca todos os endereços do cliente |
| `GET` | `/api/v1/clientes/{cpf}/endereco` | Busca endereço do cliente |
| `POST` | `/api/v1/clientes` | Cadastra cliente |
| `POST` | `/api/v1/clientes/{cpf}/enderecos` | Adiciona endereço ao cliente |
| `PUT` | `/api/v1/clientes/{cpf}` | Edita cliente por CPF |
| `PUT` | `/api/v1/clientes/{cpf}/enderecos` | Edita endereço do cliente |
| `DELETE` | `/api/v1/clientes/{cpf}` | Deleta cliente por CPF |
| `DELETE` | `/api/v1/clientes/{cpf}/enderecos` | Deleta endereço do cliente |

### Vendas

| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/api/v1/vendas/pendentes` | Lista vendas pendentes |
| `GET` | `/api/v1/vendas/finalizadas` | Lista vendas finalizadas |
| `GET` | `/api/v1/vendas/canceladas` | Lista vendas canceladas |
| `GET` | `/api/v1/vendas/{cpf}` | Busca venda pendente do cliente |
| `GET` | `/api/v1/vendas/all/{cpf}` | Lista todas as vendas do cliente |
| `POST` | `/api/v1/vendas/{cpf}` | Cria venda para o cliente |
| `PUT` | `/api/v1/vendas/add/{cpf}/{cod}` | Adiciona produto na venda |
| `PUT` | `/api/v1/vendas/remove/{cpf}/{cod}` | Remove produto da venda |
| `PUT` | `/api/v1/vendas/close/{cpf}` | Finaliza venda |
| `PUT` | `/api/v1/vendas/cancel/{cpf}` | Cancela venda |

---

## 🐳 Docker Compose

O arquivo `docker-compose.yml` sobe um container PostgreSQL pronto para uso:

```yaml
services:
  postgres:
    image: postgres:15
    container_name: erplab-db
    environment:
      POSTGRES_DB: erplab
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgres
    ports:
      - "5432:5432"
```

---

## 🧪 Testes

```bash
./mvnw test
```

Os testes unitários utilizam **Mockito** para isolar as camadas de serviço sem necessidade de contexto Spring completo.

---

## 📚 Conceitos praticados

- Modelagem de entidades com JPA e relacionamentos (`@OneToMany`, `@ManyToOne`)
- DTOs para entrada e saída de dados
- Paginação com `Pageable` e `Page<T>`
- JPQL com `countQuery` para queries com `JOIN FETCH`
- Lombok com `@Builder` e `@RequiredArgsConstructor`
- Injeção de dependência via construtor
- Testes unitários com Mockito
- Documentação automática com Springdoc OpenAPI / Swagger UI

---

## 🔗 Links
- [Repositório Frontend](https://github.com/seu-usuario/erp-lab-frontend)

---

## 👤 Autor

**Isac**  
Desenvolvedor Java Backend | Estudante de Ciência da Computação  
[GitHub](https://github.com/fantestiQ) · [LinkedIn](https://www.linkedin.com/in/isac-santos-939329343/)
