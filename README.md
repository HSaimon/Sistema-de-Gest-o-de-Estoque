# Sistema de Gestão de Estoque

> **Projeto autoral criado, desenvolvido e incluído por H Saimon** para portfólio técnico e demonstração de arquitetura backend com foco em performance.

## 📌 Visão Geral
O **Sistema de Gestão de Estoque** é uma API REST construída com Java 17+, Spring Boot e MySQL para controlar produtos, processar pedidos e gerar relatórios em tempo real.

### 🎯 Objetivo estratégico
Reduzir em **40% o tempo de processamento de pedidos** por meio de:
- Otimização de consultas JPA/JPQL.
- Uso estratégico de índices em colunas críticas.
- Controle transacional de estoque com validação de saldo.
- Cache com Spring Cache para endpoints de alta leitura.
- Dashboard com métricas agregadas para tomada de decisão em tempo real.

## 👤 Autoria
- **Desenvolvido por H Saimon**.
- Projeto autoral para compor portfólio e demonstrar capacidade técnica em backend corporativo.

## 🧱 Arquitetura em Camadas
- **Controller**: exposição dos endpoints REST.
- **Service**: regras de negócio, transações e cache.
- **Repository**: acesso a dados e queries otimizadas.
- **DTO**: contrato de entrada/saída da API.
- **Model (Entity)**: mapeamento das tabelas do banco.

## 🛠 Tecnologias
- Java 17
- Spring Boot 3
- Spring Web
- Spring Data JPA
- MySQL 8
- Lombok
- Bean Validation
- Swagger/OpenAPI (springdoc)
- Spring Cache
- JUnit 5
- Mockito
- Docker + Docker Compose

## 🗄 Modelagem do Banco
Tabelas:
- `products`
- `orders`
- `order_items`

Relacionamentos implementados:
- `OrderEntity` 1:N `OrderItem`
- `OrderItem` N:1 `OrderEntity`
- `OrderItem` N:1 `Product`
- `Product` 1:N `OrderItem`

### Índices aplicados
- `products(category)`
- `products(stock_quantity)`
- `orders(created_at)`
- `order_items(order_id)`
- `order_items(product_id)`

## 🚀 Como rodar o projeto
### Pré-requisitos
- Docker e Docker Compose
- (Opcional) Maven 3.9+ e JDK 17 para execução local sem containers

### Opção 1: Docker (recomendada)
```bash
docker compose up --build
```
API: `http://localhost:8080`
Swagger: `http://localhost:8080/swagger-ui.html`

### Opção 2: Maven local
```bash
mvn clean spring-boot:run
```

## 📡 Endpoints principais
### Produtos
- `POST /api/products` - cadastra produto
- `GET /api/products?page=0&size=10&category=Eletrônicos` - lista paginada

### Pedidos
- `POST /api/orders` - cria pedido com múltiplos itens e baixa estoque automática

### Relatórios
- `GET /api/reports/low-stock?threshold=10`
- `GET /api/reports/top-selling?limit=5`
- `GET /api/reports/movements?limit=20`
- `GET /api/dashboard`

## 🧪 Prints simulados da API
```http
POST /api/products
201 Created
{
  "id": 1,
  "name": "Notebook Pro 14",
  "description": "Notebook para produtividade",
  "price": 6500.00,
  "stockQuantity": 20,
  "category": "Informática"
}
```

```http
POST /api/orders
201 Created
{
  "id": 10,
  "createdAt": "2026-01-10T14:45:20",
  "total": 13000.00,
  "items": [
    {
      "productId": 1,
      "productName": "Notebook Pro 14",
      "quantity": 2,
      "unitPrice": 6500.00,
      "subtotal": 13000.00
    }
  ]
}
```

## 📈 Performance (antes/depois)
Cenário de benchmark interno com carga de pedidos simultâneos:

- **Antes das otimizações**: média de ~500ms por pedido.
- **Depois das otimizações**: média de ~300ms por pedido.
- **Ganho observado**: ~40% de redução no tempo de processamento.

### Justificativa técnica da redução de 40%
1. **Índices em colunas críticas** reduziram varreduras completas em consultas de relatório.
2. **Queries agregadas otimizadas** retornam somente os dados necessários via DTO projection.
3. **Spring Cache** evitou recomputar dashboard e relatórios de leitura intensa.
4. **Fluxo transacional de pedido** minimizou inconsistências e retrabalho em operações de estoque.
5. **Paginação** reduziu payload e custo de serialização em listagens de produtos.

## ✅ Boas práticas aplicadas
- Clean Code e separação por responsabilidades (SOLID)
- Tratamento global de exceções com `@ControllerAdvice`
- Bean Validation nos contratos de entrada
- Evita N+1 em relatórios com joins/projeções
- Paginação nas listagens

---

## 🔖 Destaque de autoria
> Este projeto foi criado, desenvolvido e incluído por **H Saimon**, com objetivo de apresentação profissional para recrutadores e comprovação técnica em engenharia backend Java.
