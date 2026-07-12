# Tech Challenge — Grupo 35 | FIAP

Backend de gestão de usuários, restaurantes e cardápios para um sistema compartilhado de restaurantes, desenvolvido com **Java 17**, **Spring Boot** e **PostgreSQL**, estruturado sobre **Clean Architecture**, com práticas de **DDD**, **SOLID** e **Arquitetura Hexagonal (Ports & Adapters)**.

---

## Índice

- [Tecnologias](#tecnologias)
- [Arquitetura](#arquitetura)
  - [Clean Architecture](#clean-architecture)
  - [Arquitetura Hexagonal — Ports & Adapters](#arquitetura-hexagonal--ports--adapters)
  - [DDD — Padrões Táticos](#ddd--padrões-táticos)
  - [SOLID aplicado ao código](#solid-aplicado-ao-código)
  - [Fluxo de uma requisição](#fluxo-de-uma-requisição)
- [Estrutura do projeto](#estrutura-do-projeto)
- [Estratégia de testes](#estratégia-de-testes)
- [Banco de dados e migrações](#banco-de-dados-e-migrações)
- [Como executar](#como-executar)
- [Documentação da API](#documentação-da-api)
- [Endpoints](#endpoints)
- [Tratamento de erros](#tratamento-de-erros)
- [Melhorias futuras](#melhorias-futuras)
- [Autores](#autores)

---

## Tecnologias

- Java 17
- Spring Boot 4.x (WebMVC, Data JPA, Security)
- PostgreSQL 16 + Flyway (migrações versionadas)
- JWT — jjwt 0.12 (autenticação stateless)
- Swagger / OpenAPI 3 (springdoc)
- Lombok
- JUnit 5, Mockito, AssertJ, H2 (testes)
- JaCoCo (cobertura de testes)
- Docker / Docker Compose

---

## Arquitetura

### Clean Architecture

O projeto é organizado em quatro camadas concêntricas. A **regra de dependência** é estrita: as dependências apontam sempre de fora para dentro — o domínio não conhece nenhuma das camadas externas nem qualquer framework.

```
┌──────────────────────────────────────────────────────────────┐
│  infrastructure  (Spring, JPA, JWT, Web, Config)             │
│  ┌────────────────────────────────────────────────────────┐  │
│  │  adapters  (Controllers e Presenters livres de         │  │
│  │             framework)                                 │  │
│  │  ┌──────────────────────────────────────────────────┐  │  │
│  │  │  application  (Use Cases, Ports, DTOs, Mappers)  │  │  │
│  │  │  ┌────────────────────────────────────────────┐  │  │  │
│  │  │  │  domain  (Modelos ricos, invariantes,      │  │  │  │
│  │  │  │           exceções de negócio)             │  │  │  │
│  │  │  └────────────────────────────────────────────┘  │  │  │
│  │  └──────────────────────────────────────────────────┘  │  │
│  └────────────────────────────────────────────────────────┘  │
└──────────────────────────────────────────────────────────────┘
```

| Camada | Pacote | Responsabilidade |
| ------ | ------ | ---------------- |
| **Domain** | `domain/model`, `domain/exception` | Entidades de negócio com invariantes (`User`, `Restaurant`, `MenuItem`, `UserType`), Value Object `Address` e exceções de domínio. Sem dependência de Spring ou JPA. |
| **Application** | `application/usecase`, `application/port`, `application/dto`, `application/mapper` | Casos de uso (um por operação de negócio), portas de entrada e saída, DTOs (records) e mapeadores. Orquestra o domínio sem conhecer detalhes de web ou persistência. |
| **Adapters** | `adapters/controller`, `adapters/presenter` | Controllers internos e presenters **livres de framework**: recebem DTOs, invocam os use cases pelas portas de entrada e formatam a resposta. |
| **Infrastructure** | `infrastructure/web`, `infrastructure/persistence`, `infrastructure/security`, `infrastructure/config` | Detalhes: `@RestController` (delegam para os controllers internos), JPA (entidades, repositórios, mapeadores de persistência), JWT e o **composition root** (`*Config`) que faz a montagem via injeção de dependência. |

Pontos de atenção da implementação:

- **Frameworks são detalhe.** Os use cases e os controllers internos são classes puras instanciadas por fábricas estáticas (`newInstance`/`create`) e expostas como beans apenas no composition root (`ControllerConfig`, `*UseCaseConfig`). Trocar o Spring por outro mecanismo de entrega não tocaria as camadas internas.
- **Testabilidade por construção.** Como as dependências entram por construtor via interfaces, todo caso de uso é testável com dublês de teste, sem subir contexto Spring.

### Arquitetura Hexagonal — Ports & Adapters

A separação entre o *core* e o mundo externo é feita por portas explícitas:

- **Portas de entrada** (`application/port/in`) — interfaces de caso de uso, uma por operação (`CreateUser`, `LoginUser`, `UpdateRestaurant`, `GetMenuItemsByRestaurant`...). São os *driving ports*: quem chama a aplicação (web, testes, futura fila de mensagens) o faz por elas.
- **Portas de saída** (`application/port/out`) — interfaces que a aplicação exige do mundo externo (`UserRepository`, `RestaurantRepository`, `MenuItemRepository`, `UserTypeRepository`, `TokenService`). São os *driven ports*: a infraestrutura as implementa (`UserRepositoryImpl` sobre JPA, `JwtService` para tokens).

O benefício prático: a implementação de persistência pode ser trocada (JPA → JDBC, Postgres → outro banco) sem alterar uma linha de use case, e os testes de unidade substituem as portas de saída por mocks.

### DDD — Padrões Táticos

- **Entidades ricas com invariantes** — `User`, `Restaurant`, `MenuItem` e `UserType` validam seus próprios dados. Não existem setters públicos nem `@Data`: o estado só muda por métodos de negócio.
- **Fábricas** — criação e reconstituição são explícitas e distintas: `User.create(...)` valida campos obrigatórios e carimba o timestamp; `User.reconstitute(...)` rehidrata a partir da persistência sem revalidar. Isso impede a existência de objetos de domínio em estado inválido.
- **Value Object** — `Address` é um objeto de valor imutável embutido em `User` (e mapeado como `@Embeddable` apenas na camada de persistência).
- **Linguagem ubíqua** — regras como "dono de restaurante" estão no domínio (`user.isRestaurantOwner()`), não espalhadas em services.
- **Exceções de domínio** — `EmailAlreadyExistsException`, `InvalidRestaurantOwnerException`, `UserTypeInUseException` etc. expressam violações de regra de negócio; a tradução para HTTP acontece só na borda (`GlobalExceptionHandler`).
- **Separação modelo de domínio × modelo de persistência** — `User` (domínio) e `UserEntity` (JPA) são classes distintas, convertidas por mapeadores dedicados (`infrastructure/persistence/mapper`). O domínio não carrega anotações de ORM.

### SOLID aplicado ao código

| Princípio | Onde se manifesta |
| --------- | ----------------- |
| **S** — Single Responsibility | Um use case por operação (`CreateRestaurantUseCase` só cria restaurante); presenters só formatam resposta; mappers só convertem. |
| **O** — Open/Closed | Novas operações entram como novos pares porta + use case, sem modificar os existentes. |
| **L** — Liskov Substitution | Qualquer implementação de `UserRepository` (JPA real ou mock de teste) é substituível sem quebrar os use cases. |
| **I** — Interface Segregation | Portas de entrada mínimas e específicas — o controller de restaurante depende de `CreateRestaurant`, `GetRestaurantById`..., nunca de uma interface "gorda" com dezenas de métodos. |
| **D** — Dependency Inversion | Use cases dependem de abstrações (`port/out`); as implementações concretas (JPA, JWT) ficam na infraestrutura e são ligadas no composition root. |

### Fluxo de uma requisição

```
HTTP Request
   │
   ▼
UserApiController (@RestController — infrastructure/web)
   │  valida entrada (Bean Validation) e delega
   ▼
UserController (adapters — sem framework)
   │  invoca a porta de entrada
   ▼
CreateUserUseCase (application) ──▶ User.create(...) (domain: invariantes)
   │  persiste pela porta de saída
   ▼
UserRepository (port/out) ◀── UserRepositoryImpl (infrastructure/persistence)
   │                               └── UserEntityMapper ⇄ UserEntity (JPA)
   ▼
UserPresenter (adapters) ──▶ UserResponse (DTO)
   │
   ▼
HTTP Response (ou ProblemDetail RFC 7807 via GlobalExceptionHandler)
```

---

## Estrutura do projeto

```
src/main/java/com/tech_challange/grupo35/
├── domain/
│   ├── model/            # User, Restaurant, MenuItem, UserType, Address (VO)
│   └── exception/        # Exceções de negócio
├── application/
│   ├── port/in/          # Portas de entrada (interfaces de caso de uso)
│   ├── port/out/         # Portas de saída (repositórios, TokenService)
│   ├── usecase/          # Implementações dos casos de uso
│   ├── dto/              # Records de request/response
│   └── mapper/           # DTO ⇄ modelo de domínio
├── adapters/
│   ├── controller/       # Controllers internos (livres de framework)
│   └── presenter/        # Formatação de saída
└── infrastructure/
    ├── web/              # @RestControllers + GlobalExceptionHandler (RFC 7807)
    ├── persistence/      # Entidades JPA, repositórios, mapeadores
    ├── security/         # JwtService, JwtFilter
    └── config/           # Composition root: montagem dos beans

src/main/resources/db/migration/   # Migrações Flyway (V1..V7)
src/test/java/...                  # Testes espelhando as camadas de produção
```

---

## Estratégia de testes

Os testes seguem a **pirâmide de testes**: base larga de testes de unidade rápidos e isolados, complementados por testes de integração que exercitam o sistema de ponta a ponta.

```
        ▲  Integração (@SpringBootTest, fluxo completo via HTTP)
       ▲▲  Componentes de borda (handler de erros, JWT, wiring dos beans)
     ▲▲▲▲  Unidade (domínio, use cases, mappers, presenters,
           controllers e adapters de persistência)
```

A suíte de testes **espelha a estrutura de produção**: cada camada da Clean Architecture tem seus próprios testes, com a estratégia de isolamento adequada ao seu papel — o que também serve de documentação viva da arquitetura.

### Testes de unidade

- **Domínio** (`domain/model/*Test`) — validam invariantes e regras de negócio das entidades sem nenhum mock, pois o domínio não tem dependências. São os testes mais rápidos e estáveis do projeto.
- **Use cases** (`application/usecase/*Test`) — cobertura completa das operações de negócio, incluindo os fluxos de usuário (`CreateUser`, `UpdateUser`, `DeleteUser`, `LoginUser`, `ChangePassword`, `AssignUserType`, `FindUsersByName`). Testam a orquestração com **Mockito** (`@ExtendWith(MockitoExtension.class)`, `@Mock`/`@InjectMocks`), substituindo as portas de saída por mocks — nenhum contexto Spring é carregado. Cobrem caminhos felizes e de exceção (ex.: `LoginUseCaseTest` valida token para credenciais corretas e `InvalidPasswordException`/`UserNotFoundException` para as incorretas).
- **Adapters** (`adapters/controller/*Test`, `adapters/presenter/*Test`) — verificam delegação e formatação de resposta de forma isolada.
- **Mappers nas duas fronteiras** (`application/mapper/*Test`, `infrastructure/persistence/mapper/*Test`) — garantem a fidelidade da conversão DTO ⇄ domínio e domínio ⇄ entidade JPA, protegendo o contrato de cada camada.
- **Adapters de persistência** (`infrastructure/persistence/repository/*ImplTest`) — testam cada `*RepositoryImpl` em isolamento, mockando o repositório JPA e o entity mapper, para validar que a tradução entre a porta de saída e o JPA está correta sem tocar em banco.
- **Composition root / wiring** (`infrastructure/config/*ConfigTest`) — testes leves que instanciam as classes de configuração com portas mockadas e verificam que todos os beans (use cases, controllers, presenters, mappers) são montados corretamente. Detectam quebras de wiring em tempo de teste unitário, sem o custo de subir o contexto Spring.

### Testes de integração

- `integration/UserTypeRestaurantIntegrationTest` — sobe a aplicação completa (`@SpringBootTest(webEnvironment = RANDOM_PORT)`) e exercita o fluxo real de negócio via HTTP: busca o tipo semeado por migração → cria usuário → associa tipo → cria restaurante. Valida a colaboração entre todas as camadas, incluindo Flyway e persistência.
- **H2** é usado como banco em memória nos testes, mantendo a suíte rápida e sem dependências externas.

### Boas práticas adotadas

- **Padrão AAA / Given-When-Then** — cada teste organiza claramente preparação, ação e verificação.
- **Princípios FIRST** — testes rápidos (unidade sem Spring), isolados (mocks nas fronteiras), repetíveis (H2 em memória, dados semeados por migração), autoverificáveis (AssertJ) e escritos junto do código.
- **Testar comportamento, não implementação** — os testes de use case validam o resultado observável pelas portas, permitindo refatorar a implementação interna sem quebrar a suíte.
- **Cobertura com JaCoCo** — relatório gerado no build:

```bash
./mvnw clean verify
# relatório em target/site/jacoco/index.html
```

### Executando os testes

```bash
./mvnw test                 # suíte completa
./mvnw test -Dtest='*UseCaseTest'   # apenas use cases
```

### Coleção Postman

O arquivo `collection.json` na raiz contém os cenários de teste manual/exploratório da API, alinhados aos testes automatizados.

---

## Banco de dados e migrações

O schema é **versionado com Flyway** — nenhuma tabela é criada pelo Hibernate (`ddl-auto=validate`, que apenas confere se o schema corresponde ao mapeamento):

| Migração | Descrição |
| -------- | --------- |
| `V1__create_tables.sql` | Tabelas iniciais de usuários |
| `V2__add_user_types.sql` | Tipos de usuário |
| `V3__flatten_user_hierarchy.sql` | Remove herança: usuário único + associação a tipo |
| `V4__seed_user_types.sql` | Seed de `CUSTOMER` e `RESTAURANT_OWNER` |
| `V5__create_restaurants.sql` | Restaurantes |
| `V6__user_address_embedded.sql` | Endereço como campos embutidos (VO `Address`) |
| `V7__create_menu_items.sql` | Itens de cardápio |

Todas as tabelas residem no schema `challenge` do PostgreSQL. Migrações versionadas garantem que qualquer ambiente (dev, teste, produção) reproduza exatamente o mesmo schema, com histórico auditável.

---

## Como executar

### Pré-requisitos

- Docker e Docker Compose
- Java 17 (apenas para execução local sem Docker)

### Variáveis de ambiente

```bash
cp .env.example .env
# preencha DB_NAME, DB_USERNAME, DB_PASSWORD, DB_PORT, SERVER_PORT
```

### Com Docker Compose (app + banco)

```bash
docker compose up --build
# aplicação em http://localhost:8080
docker compose down   # para parar
```

### Localmente (apenas o banco no Docker)

```bash
docker compose up -d db
export $(cat .env | grep -v '^#' | xargs) && \
DB_URL="jdbc:postgresql://localhost:5432/${DB_NAME}?currentSchema=challenge" \
./mvnw spring-boot:run
```

---

## Documentação da API

- **Swagger UI:** `http://localhost:8080/swagger-ui.html`
- **OpenAPI JSON:** `http://localhost:8080/api-docs`

---

## Endpoints

Todos os endpoints seguem o versionamento `/api/v1/`.

### Usuários — `/api/v1/users`

| Método | Endpoint | Descrição |
| ------ | -------- | --------- |
| `POST` | `/api/v1/users` | Cadastrar usuário |
| `POST` | `/api/v1/users/login` | Login (retorna JWT) |
| `GET` | `/api/v1/users?name={nome}` | Buscar usuários por nome |
| `PATCH` | `/api/v1/users/{id}` | Atualizar dados |
| `PATCH` | `/api/v1/users/{id}/password` | Alterar senha |
| `PATCH` | `/api/v1/users/{id}/user-type` | Associar tipo de usuário |
| `DELETE` | `/api/v1/users/{id}` | Remover usuário |

### Tipos de usuário — `/api/v1/user-types`

| Método | Endpoint | Descrição |
| ------ | -------- | --------- |
| `POST` | `/api/v1/user-types` | Criar tipo |
| `GET` | `/api/v1/user-types` | Listar tipos |
| `GET` | `/api/v1/user-types?name={nome}` | Buscar por nome |
| `GET` | `/api/v1/user-types/{id}` | Buscar por id |
| `PUT` | `/api/v1/user-types/{id}` | Atualizar tipo |
| `DELETE` | `/api/v1/user-types/{id}` | Remover tipo (bloqueado se em uso) |

### Restaurantes — `/api/v1/restaurants`

| Método | Endpoint | Descrição |
| ------ | -------- | --------- |
| `POST` | `/api/v1/restaurants` | Criar restaurante (owner deve ser `RESTAURANT_OWNER`) |
| `GET` | `/api/v1/restaurants` | Listar restaurantes |
| `GET` | `/api/v1/restaurants/{id}` | Buscar por id |
| `PUT` | `/api/v1/restaurants/{id}` | Atualizar restaurante |
| `DELETE` | `/api/v1/restaurants/{id}` | Remover restaurante |

### Itens de cardápio — `/api/v1/restaurants/{restaurantId}/menu-items`

| Método | Endpoint | Descrição |
| ------ | -------- | --------- |
| `POST` | `.../menu-items` | Criar item |
| `GET` | `.../menu-items` | Listar itens do restaurante |
| `GET` | `.../menu-items/{menuItemId}` | Buscar item |
| `PUT` | `.../menu-items/{menuItemId}` | Atualizar item |
| `DELETE` | `.../menu-items/{menuItemId}` | Remover item |

### Exemplo — cadastrar usuário

```bash
curl -X POST http://localhost:8080/api/v1/users \
  -H "Content-Type: application/json" \
  -d '{
    "name": "João Silva",
    "email": "joao@email.com",
    "login": "joaosilva",
    "password": "senha123",
    "cpf": "123.456.789-00",
    "address": {
      "street": "Rua das Flores",
      "number": "100",
      "neighborhood": "Centro",
      "city": "São Paulo",
      "state": "SP",
      "zipCode": "01000-000"
    }
  }'
```

---

## Tratamento de erros

A API adota **ProblemDetail (RFC 7807)** para todas as respostas de erro, centralizado no `GlobalExceptionHandler` — as exceções de domínio são traduzidas para HTTP apenas nesta borda:

```json
{
  "type": "about:blank",
  "title": "Email Já Cadastrado",
  "status": 409,
  "detail": "O email joao@email.com já está em uso."
}
```

Erros de validação detalham os campos:

```json
{
  "type": "about:blank",
  "title": "Dados de Entrada Inválidos",
  "status": 400,
  "detail": "Um ou mais campos possuem valores inválidos.",
  "erros": [{ "campo": "email", "mensagem": "must not be blank" }]
}
```

---

## Melhorias futuras

- **Testcontainers** — substituir H2 por PostgreSQL real nos testes de integração, eliminando divergências de dialeto entre teste e produção.
- **ArchUnit** — testes automatizados de arquitetura que garantem a regra de dependência (ex.: `domain` não pode importar `infrastructure`).
- **Externalizar o segredo JWT** — mover `jwt.secret` de `application.properties` para variável de ambiente/secret manager.
- **Pipeline de CI** — build + testes + cobertura mínima como gate de merge (GitHub Actions).
- **Testes de mutação (PIT)** — medir a efetividade real da suíte além da cobertura de linhas.
- **Testes de contrato** — validar o contrato OpenAPI contra as respostas reais da API.

---

## Autores

Conrado Rennó (RM370819) • Matheus Martins (RM373838) • Lucas Nogueira Pissuto (RM371783) • Pedro Braz (RM371592) • Alexandre Sato (RM372572)
