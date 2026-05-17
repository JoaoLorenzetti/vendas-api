# Vendas API

API REST para gerenciamento de vendas, desenvolvida como desafio técnico.

## Tecnologias

- Java 21
- Spring Boot 3.3.5
- Spring Data JPA
- Banco de dados H2 (em memória)
- JUnit 5 + Mockito

## Como executar

### Pré-requisitos
- Java 21+

### Rodando a aplicação

```bash
.\mvnw spring-boot:run
```

A API estará disponível em `http://localhost:8080`.

### Rodando os testes

```bash
.\mvnw test
```

### Console H2

Disponível em `http://localhost:8080/h2-console`

- **JDBC URL:** `jdbc:h2:mem:vendasdb`
- **User:** `sa`
- **Password:** (vazio)

---

## Endpoints

### Criar uma venda

**POST** `/api/v1/vendas`

Body:
```json
{
  "dataVenda": "2024-03-15",
  "valor": 1500.00,
  "vendedorId": 1,
  "vendedorNome": "Carlos Silva"
}
```

Resposta (201 Created):
```json
{
  "id": 1,
  "dataVenda": "2024-03-15",
  "valor": 1500.00,
  "vendedorId": 1,
  "vendedorNome": "Carlos Silva"
}
```

---

### Resumo de vendedores por período

**GET** `/api/v1/vendedores/resumo?dataInicio=YYYY-MM-DD&dataFim=YYYY-MM-DD`

Exemplo:

GET /api/v1/vendedores/resumo?dataInicio=2024-01-01&dataFim=2024-01-31

Resposta (200 OK):
```json
[
  {
    "vendedorId": 1,
    "vendedorNome": "Carlos Silva",
    "totalVendas": 12,
    "mediaDiariaVendas": 0.39
  }
]
```

> A média diária é calculada como `totalVendas / número de dias no período`.