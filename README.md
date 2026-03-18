# 📅 Agendador de Horários

> Uma API REST robusta e bem estruturada para gerenciar agendamentos de serviços, desenvolvida com **Spring Boot 4.0.3** e **Java 21**.

<div align="center">

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.3-green?style=for-the-badge&logo=springboot)
![Java](https://img.shields.io/badge/Java-21-red?style=for-the-badge&logo=java)
![H2 Database](https://img.shields.io/badge/H2%20Database-In%20Memory-blue?style=for-the-badge)
![Maven](https://img.shields.io/badge/Maven-Build%20Tool-orange?style=for-the-badge&logo=apache-maven)

</div>

---

## 📋 Sobre o Projeto

O **Agendador de Horários** é uma API REST completa para gerenciar agendamentos de serviços profissionais. O sistema permite criar, consultar, atualizar e deletar agendamentos, com validação automática de conflitos de horários e um design arquitetural robusto baseado em boas práticas de desenvolvimento.

### 🎯 Funcionalidades Principais

- ✅ **Criar agendamentos** com validação de disponibilidade
- ✅ **Consultar agendamentos** por data específica
- ✅ **Atualizar agendamentos** existentes com dados do cliente
- ✅ **Deletar agendamentos** de forma segura
- ✅ **Prevenção de conflitos** de horários para o mesmo serviço
- ✅ **Registro automático** de data/hora de inserção
- ✅ **Console H2** para inspeção de dados em tempo real

---

## 🛠️ Stack Tecnológico

### Backend
- **Framework**: Spring Boot 4.0.3
- **Linguagem**: Java 21
- **Build**: Maven
- **ORM**: JPA/Hibernate
- **Banco de Dados**: H2 Database (em memória)
- **Dependency Injection**: Lombok (@RequiredArgsConstructor)

### Bibliotecas Principais
- `spring-boot-starter-data-jpa` - Camada de persistência
- `spring-boot-starter-webmvc` - Endpoints REST
- `h2` - Banco de dados em memória (ideal para testes)
- `lombok` - Redução de boilerplate code
- `spring-boot-h2console` - Interface web para consultar H2

### Testes
- `spring-boot-starter-data-jpa-test`
- `spring-boot-starter-webmvc-test`

---

## 📁 Estrutura do Projeto

```
agendador-horarios/
├── src/
│   ├── main/
│   │   ├── java/com/devKiq/agendador_horarios/
│   │   │   ├── AgendadorHorariosApplication.java    # Classe principal (Spring Boot)
│   │   │   ├── controller/
│   │   │   │   └── AgendamentoController.java       # Endpoints REST
│   │   │   ├── service/
│   │   │   │   └── AgendamentoService.java          # Lógica de negócio
│   │   │   └── infrastructure/
│   │   │       ├── entity/
│   │   │       │   └── AgendamentoEntity.java       # Model/Entidade JPA
│   │   │       └── repository/
│   │   │           └── AgendamentoRepository.java   # Camada de dados
│   │   └── resources/
│   │       ├── application.properties               # Configurações
│   │       ├── static/                              # Arquivos estáticos (CSS, JS)
│   │       └── templates/                           # Templates (se necessário)
│   └── test/
│       └── java/                                    # Testes unitários
├── pom.xml                                          # Dependências Maven
└── README.md                                        # Este arquivo
```

### 🏗️ Padrão Arquitetural: Camadas

O projeto segue a arquitetura de **camadas em 3 níveis**:

1. **Controller (Apresentação)**
   - Responsável por receber requisições HTTP
   - Mapeia endpoints REST
   - Retorna respostas ao cliente

2. **Service (Lógica de Negócio)**
   - Implementa as regras de negócio
   - Valida conflitos de agendamento
   - Orquestra operações complexas

3. **Infrastructure (Persistência)**
   - **Entity**: Modela os dados da aplicação
   - **Repository**: Abstração para acesso ao banco de dados

---

## 🚀 Como Começar

### Pré-requisitos

- **Java 21** ou superior instalado
- **Maven 3.6+** instalado (ou use o `mvnw` incluído)
- IDE como IntelliJ IDEA ou VS Code

### Instalação e Execução

1. **Clone ou extraia o repositório**
   ```bash
   cd agendador-horarios
   ```

2. **Instale as dependências**
   ```bash
   mvn clean install
   ```

3. **Execute a aplicação**
   ```bash
   mvn spring-boot:run
   ```

   Ou, usando o wrapper Maven incluído:
   ```bash
   ./mvnw spring-boot:run
   ```

4. **Acesse a aplicação**
   - API REST: `http://localhost:8080`
   - Console H2: `http://localhost:8080/h2-console`

---

## 📡 Endpoints da API

### 1. Salvar Agendamento
**POST** `/agendamentos/salvar`

Cria um novo agendamento com validação de conflitos.

**Request Body:**
```json
{
  "produto": "Corte de Cabelo",
  "profissional": "João Silva",
  "servico": "Corte Premium",
  "dataHoraAgendamento": "2024-03-25T14:30:00",
  "cliente": "Maria Santos",
  "telefone": "(11) 98765-4321"
}
```

**Response (201 Created):**
```json
{
  "id": 1,
  "produto": "Corte de Cabelo",
  "profissional": "João Silva",
  "servico": "Corte Premium",
  "dataHoraAgendamento": "2024-03-25T14:30:00",
  "cliente": "Maria Santos",
  "telefone": "(11) 98765-4321",
  "dataInsercao": "2024-03-18T10:15:30"
}
```

**Validações:**
- ❌ Retorna erro se houver outro agendamento para o mesmo serviço no mesmo horário

---

### 2. Buscar Agendamentos por Data
**GET** `/agendamentos/buscar?data=2024-03-25`

Retorna todos os agendamentos de um dia específico.

**Response (200 OK):**
```json
[
  {
    "id": 1,
    "produto": "Corte de Cabelo",
    "profissional": "João Silva",
    "servico": "Corte Premium",
    "dataHoraAgendamento": "2024-03-25T14:30:00",
    "cliente": "Maria Santos",
    "telefone": "(11) 98765-4321",
    "dataInsercao": "2024-03-18T10:15:30"
  }
]
```

---

### 3. Alterar Agendamento
**PUT** `/agendamentos/alterar?cliente=Maria Santos&dataHoraAgendamento=2024-03-25T14:30:00`

Atualiza os dados de um agendamento existente.

**Request Body:**
```json
{
  "produto": "Corte de Cabelo Premium",
  "profissional": "João Silva",
  "servico": "Corte Premium",
  "dataHoraAgendamento": "2024-03-25T15:00:00",
  "cliente": "Maria Santos",
  "telefone": "(11) 98765-4322"
}
```

**Response (200 OK):**
```json
{
  "id": 1,
  "produto": "Corte de Cabelo Premium",
  "profissional": "João Silva",
  "servico": "Corte Premium",
  "dataHoraAgendamento": "2024-03-25T15:00:00",
  "cliente": "Maria Santos",
  "telefone": "(11) 98765-4322",
  "dataInsercao": "2024-03-18T10:15:30"
}
```

---

### 4. Deletar Agendamento
**DELETE** `/agendamentos/deletar?cliente=Maria Santos&dataHoraAgendamento=2024-03-25T14:30:00`

Remove um agendamento específico pelo cliente e data/hora.

**Response (204 No Content)**

---

## 🔍 Detalhes Técnicos

### Entidade: AgendamentoEntity

| Campo | Tipo | Descrição |
|-------|------|-----------|
| `id` | Long | Chave primária (auto-gerada) |
| `produto` | String | Produto/serviço a ser realizado |
| `profissional` | String | Nome do profissional responsável |
| `servico` | String | Tipo de serviço (validado para conflitos) |
| `dataHoraAgendamento` | LocalDateTime | Data e hora do atendimento |
| `cliente` | String | Nome do cliente |
| `telefone` | String | Contato do cliente |
| `dataInsercao` | LocalDateTime | Data/hora de criação (automática) |

### Banco de Dados H2

**Configuração (`application.properties`):**
```properties
spring.datasource.url=jdbc:h2:mem:agendamentos-db
spring.datasource.username=sa
spring.jpa.show-sql=true
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

**Características:**
- Banco de dados em **memória** (ideal para testes e prototipagem)
- Console web acessível em `/h2-console`
- Auto-criação de tabelas com `ddl-auto=update`

---

## 🧪 Testes

Execute os testes com:

```bash
mvn test
```

Os testes cobrem:
- Validação de conflitos de agendamento
- Operações CRUD (Create, Read, Update, Delete)
- Busca por data
- Comportamento da service layer

---

## 💡 Boas Práticas Implementadas

✅ **Separação de Responsabilidades**
- Controllers lidam com HTTP
- Services contêm lógica de negócio
- Repositories gerenciam persistência

✅ **Injeção de Dependência**
- Uso de `@RequiredArgsConstructor` do Lombok
- Construtor automático com campos final

✅ **Tratamento de Exceções**
- Validações com `Objects.nonNull()` e `Objects.isNull()`
- Mensagens de erro claras

✅ **Transações Seguras**
- Anotação `@Transactional` no método de delete

✅ **Queries Customizadas**
- Métodos no Repository otimizados para caso de uso
- Busca por intervalo de tempo (between)

✅ **Código Limpo**
- Lombok reduz boilerplate (getters, setters, constructores)
- Nomenclatura clara e consistente
- Comentários explicativos onde necessário

---

## 🔐 Segurança e Validações

### Validação de Conflitos
O sistema **previne agendamentos duplicados** para o mesmo serviço no mesmo horário:

```java
// Busca agendamentos existentes em um intervalo de 1 hora
AgendamentoEntity agendados = agendamentoRepository
    .findByServicoAndDataHoraAgendamentoBetween(
        agendamento.getServico(), 
        horaAgendamento, 
        horaFim
    );

if (Objects.nonNull(agendados)) {
    throw new RuntimeException("Já existe um agendamento...");
}
```

---

## 📊 Diagrama de Fluxo

```
Cliente HTTP
    ↓
[Controller] - Recebe requisição
    ↓
[Service] - Aplica lógica de negócio
    ↓
[Repository] - Acessa banco de dados
    ↓
[H2 Database] - Armazena/recupera dados
    ↓
[Service] - Retorna resultado
    ↓
[Controller] - Formata resposta HTTP
    ↓
Cliente HTTP - Recebe resposta
```

---

## 🚀 Próximas Melhorias Sugeridas

Para elevar este projeto a produção, considere adicionar:

1. **Spring Security** - Autenticação e autorização
2. **Validation** - `@Valid` e `@NotBlank` nas entidades
3. **Exception Handler Global** - `@ControllerAdvice` para melhor tratamento de erros
4. **Logging** - SLF4J e Logback para rastreabilidade
5. **Documentação Swagger** - `springdoc-openapi` para documentação automática
6. **PostgreSQL** - Migrar de H2 para banco de dados em produção
7. **Docker** - Containerização da aplicação
8. **CI/CD** - Pipeline automatizado (GitHub Actions, Jenkins)
9. **Testes Integrados** - Testes E2E com Testcontainers
10. **Notificações** - Email/SMS para confirmação de agendamentos

---

## 📈 Métricas do Projeto

- **Linhas de Código (Java)**: ~150
- **Classes**: 5
- **Endpoints**: 4
- **Dependências**: 8 principais
- **Cobertura de Testes**: A definir

---

## 🤝 Contribuindo

Este é um projeto de portfólio. Feedbacks são bem-vindos! Sinta-se livre para:
- Abrir issues com sugestões
- Enviar pull requests com melhorias
- Reportar bugs

---

## 📄 Licença

Este projeto é fornecido como está, sem licença específica.

---

## 👨‍💻 Autor

**Desenvolvido por:** devKiq  
**Versão:** 0.0.1-SNAPSHOT  
**Data:** 2024

---

## 📞 Suporte

Para dúvidas ou sugestões sobre este projeto, entre em contato através do repositório.

---

<div align="center">

**Feito com ❤️ e ☕ por um desenvolvedor apaixonado por código limpo e boas práticas.**

[⬆ Voltar ao topo](#-agendador-de-horários)

</div>
