# Tech Challenge — Grupo 35 | FIAP

Backend de gestão de usuários para um sistema compartilhado de restaurantes, desenvolvido com Java, Spring Boot e PostgreSQL.

## Tecnologias

- Java 17
- Spring Boot 4.0.5
- PostgreSQL 16
- Docker / Docker Compose
- Swagger / OpenAPI 3
- JWT (autenticação)

## Arquitetura

O projeto segue os princípios de **DDD (Domain-Driven Design)** e **SOLID**, organizado por domínio:

```
src/main/java/com/tech_challange/grupo35/
├── config/          # Configurações (OpenAPI, Security)
├── exception/       # Exceções customizadas e GlobalExceptionHandler (RFC 7807)
├── security/        # JWT service e DTOs de autenticação
└── user/
    ├── controller/  # UserController, UserTypeController
    ├── dto/         # Records de request e response
    ├── entity/      # UserEntity, UserTypeEntity
    ├── mapper/      # Conversão entre entidade e DTO
    ├── repository/  # Interfaces e implementações de repositório
    └── service/     # UserService com toda a lógica de negócio
```

### Modelo de entidades

Não há herança entre usuários: `UserEntity` é uma entidade única. A distinção
entre "Cliente" e "Dono de Restaurante" é feita pela associação a um `UserType`
(entidade gerenciada por CRUD próprio). O `cpf` pertence a todos os usuários.

```
UserEntity (tabela: users) — campos: name, email, login, password, address, cpf
   └── userType → UserTypeEntity (tabela: user_types) — campo: name
```

Todas as tabelas ficam no schema `challenge` do PostgreSQL.

## Pré-requisitos

- Docker e Docker Compose instalados
- Java 17 (apenas para rodar localmente sem Docker)

## Variáveis de ambiente

Crie um arquivo `.env` na raiz do projeto com base no `.env.example`:

```bash
cp .env.example .env
```

Conteúdo do `.env`:

```env
DB_NAME=
DB_USERNAME=
DB_PASSWORD=
DB_PORT=
SERVER_PORT=
```

## Executando com Docker Compose

Sobe a aplicação completa (banco + app):

```bash
docker compose up --build
```

A aplicação estará disponível em `http://localhost:8080`.

Para parar:

```bash
docker compose down
```

## Executando localmente (sem Docker para o app)

1. Suba apenas o banco de dados:

```bash
docker compose up -d db
```

2. Execute a aplicação com Maven:

```bash
export $(cat .env | grep -v '^#' | xargs) && \
DB_URL="jdbc:postgresql://localhost:5432/${DB_NAME}?currentSchema=challenge" \
./mvnw spring-boot:run
```

## Documentação da API

Após subir a aplicação, acesse:

- **Swagger UI:** `http://localhost:8080/swagger-ui.html`
- **OpenAPI JSON:** `http://localhost:8080/api-docs`

## Endpoints

Todos os endpoints seguem o versionamento `/api/v1/`.

### Usuários (`/api/v1/users`)

| Método   | Endpoint                        | Descrição                          |
| -------- | ------------------------------- | ---------------------------------- |
| `POST`   | `/api/v1/users`                 | Cadastrar novo usuário             |
| `PATCH`  | `/api/v1/users/{id}`            | Atualizar dados do usuário         |
| `GET`    | `/api/v1/users?name={nome}`     | Buscar usuários por nome           |
| `PATCH`  | `/api/v1/users/{id}/user-type`  | Associar tipo de usuário existente |
| `PATCH`  | `/api/v1/users/{id}/password`   | Alterar senha                      |
| `DELETE` | `/api/v1/users/{id}`            | Deletar usuário                    |
| `POST`   | `/api/v1/users/login`           | Validar login (retorna JWT)        |

## Exemplos de uso

### Cadastrar usuário

```bash
curl -X POST http://localhost:8080/api/v1/users \
  -H "Content-Type: application/json" \
  -d '{
    "name": "João Silva",
    "email": "joao@email.com",
    "login": "joaosilva",
    "password": "senha123",
    "address": "Rua das Flores, 100, São Paulo - SP",
    "cpf": "123.456.789-00"
  }'
```

### Login

```bash
curl -X POST http://localhost:8080/api/v1/users/login \
  -H "Content-Type: application/json" \
  -d '{
    "login": "joaosilva",
    "password": "senha123"
  }'
```

### Alterar senha

```bash
curl -X PATCH http://localhost:8080/api/v1/users/{id}/password \
  -H "Content-Type: application/json" \
  -d '{
    "currentPassword": "senha123",
    "newPassword": "novaSenha456"
  }'
```

### Buscar por nome

```bash
curl "http://localhost:8080/api/v1/users?name=João"
```

## Tratamento de erros

A API utiliza o padrão **ProblemDetail (RFC 7807)** para todas as respostas de erro:

```json
{
  "type": "about:blank",
  "title": "Email Já Cadastrado",
  "status": 409,
  "detail": "O email joao@email.com já está em uso."
}
```

Erros de validação retornam os campos com problemas:

```json
{
  "type": "about:blank",
  "title": "Dados de Entrada Inválidos",
  "status": 400,
  "detail": "Um ou mais campos possuem valores inválidos.",
  "erros": [{ "campo": "email", "mensagem": "must not be blank" }]
}
```

## Banco de dados

O banco PostgreSQL sobe automaticamente via Docker Compose. As tabelas são criadas automaticamente pelo Hibernate no schema `challenge`.

```
challenge.users
challenge.customers
challenge.restaurant_owners
```

## Coleção Postman

O arquivo `collection.json` na raiz do projeto contém todos os cenários de teste prontos para importar no Postman.

## Autores

Conrado Rennó(RM370819) • Matheus Martins(RM373838) • Lucas Nogueira Pissuto(RM371783) • Pedro Braz(RM371592) • Alexandre Sato(RM372572)
