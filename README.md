# Sistema de Gestão de Estoque

Backend desenvolvido com **Java 17 + Spring Boot + MySQL** para gestão de estoque e pedidos com foco em performance operacional.

## 🎯 Objetivo do Projeto
Reduzir em **40% o tempo de processamento de pedidos** através de:
- Otimização de consultas com índices em tabelas críticas.
- Atualização transacional de estoque no momento da venda.
- Cache para leituras frequentes de produtos.
- Endpoints de relatórios em tempo real e dashboard agregado.

## 🧰 Tecnologias Utilizadas
- Java 17
- Spring Boot 3
- Spring Web
- Spring Data JPA
- Bean Validation
- Spring Cache
- MySQL 8
- Lombok
- OpenAPI/Swagger
- JUnit 5 + Mockito
- Docker e Docker Compose

## 🏗️ Arquitetura
Estrutura em camadas:
- **Controller**: exposição dos endpoints REST
- **Service**: regras de negócio (estoque, pedidos, relatórios)
- **Repository**: acesso ao banco de dados
- **DTO**: contratos de entrada e saída
- **Model (Entity)**: modelagem relacional

## 🗃️ Modelagem de Banco
Tabelas principais:
- `products`
- `orders`
- `order_items`
- `stock_movements`

Relacionamentos:
- `Order` → `OneToMany` com `OrderItem`
- `OrderItem` → `ManyToOne` com `Order`
- `OrderItem` → `ManyToOne` com `Product`
- `Product` → `OneToMany` com `OrderItem`

## ⚡ Estratégias de Performance (meta de -40%)
1. **Índices JPA** nas colunas de pesquisa (`category`, `stock_quantity`, `order_id`, `product_id`, `created_at`).
2. **Consultas agregadas otimizadas** para ranking de produtos e métricas de receita.
3. **Cache de produtos** (`@Cacheable`) para reduzir leitura repetitiva.
4. **Atualização transacional do estoque** evitando retrabalho e inconsistência.
5. **Paginação** em listagens para limitar carga por requisição.

## 🚀 Como rodar
### 1) Requisitos
- Java 17+
- Maven 3.9+
- Docker + Docker Compose

### 2) Subir com Docker Compose
```bash
docker compose up --build
```

### 3) Rodar local sem Docker
```bash
docker compose up -d mysql
mvn spring-boot:run
```

## 📘 Documentação da API
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

## 🔌 Exemplos de Endpoints
### Criar produto
`POST /api/products`
```json
{
  "name": "Notebook Pro 16",
  "description": "Notebook para uso corporativo",
  "price": 7999.90,
  "stockQuantity": 20,
  "category": "Informática"
}
```

### Criar pedido com múltiplos itens
`POST /api/orders`
```json
{
  "customerName": "Empresa XPTO",
  "items": [
    { "productId": 1, "quantity": 2 },
    { "productId": 2, "quantity": 1 }
  ]
}
```

### Relatórios em tempo real
- `GET /api/reports/low-stock?threshold=10`
- `GET /api/reports/top-selling?limit=5`
- `GET /api/reports/movements?page=0&size=20`
- `GET /api/reports/dashboard`

## 🧪 Testes
```bash
mvn test
```

## 🖼️ Prints simulados da API
### Swagger UI (simulado)
```text
GET /api/products
POST /api/orders
GET /api/reports/dashboard
```

### Resposta do dashboard (simulada)
```json
{
  "totalOrders": 128,
  "totalProducts": 340,
  "lowStockProducts": 17,
  "totalRevenue": 982340.50,
  "averageTicket": 7674.53
}
```

## 📈 Resultados de Performance
Cenário comparativo (simulado):
- Antes: **500 ms** por processamento médio de pedido.
- Depois: **300 ms** por processamento médio de pedido.
- Ganho: **40% de redução no tempo médio**.

Justificativa técnica: redução de IO via cache + consultas com índices + menos consultas redundantes durante fechamento de pedido.

## ✅ Boas Práticas Aplicadas
- Clean Code (nomes claros, métodos coesos, baixo acoplamento)
- SOLID (camadas com responsabilidades definidas)
- Tratamento global de exceções com `@ControllerAdvice`
- Validações de entrada com Bean Validation
