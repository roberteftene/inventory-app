# 🏬 Inventory Service

This is a sample full-stack backend service that simulates inventory and order management for distribution centers. It exposes REST APIs for:

- Managing products, inventory, and orders
- Querying stock levels per distribution center
- Creating and tracking customer orders
- Sending simulated email notifications using Kafka messaging

Designed for use in scalable systems, it demonstrates:
- Spring Boot (Java 17)
- JPA & H2 database
- RESTful architecture
- Kafka messaging
- DTO mapping with MapStruct
- Validation with Jakarta annotations
- Swagger/OpenAPI documentation

---

## 🛠 Local Setup

### 📦 Requirements

- Java 17+
- Maven
- Docker & Docker Compose
- (Optional) HTTP client (e.g. Postman, IntelliJ HTTP client)

---

### 🚀 Running the App Locally

1. **Clone the repository**
   ```bash
   git clone https://github.com/your-org/inventory-service.git
   cd inventory-service


## ⚙️ Kafka Setup

### 🐳 Start Kafka and Zookeeper with Docker Compose

In the project root (where `docker-compose.yml` is located), run:

```bash
docker compose up -d
```

This starts:

* Kafka broker on `localhost:9092`
* Zookeeper on `localhost:2181`

### 📬 Simulated Email Sending

* When an order is placed, a message is published to the `order-email` topic.
* The `@KafkaListener` (consumer) listens and logs the message to simulate sending an email.

---

## 📖 Swagger API Documentation

Swagger UI is automatically enabled with SpringDoc.

### Access Swagger UI:

After running the app:

```
http://localhost:8080/swagger-ui/index.html
```

### Dependencies (already included in `pom.xml`):

```xml
<dependency>
  <groupId>org.springdoc</groupId>
  <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
  <version>2.5.0</version>
</dependency>
```

### Customize OpenAPI Metadata (optional)

In `application.properties`:

```properties
springdoc.api-docs.enabled=true
springdoc.swagger-ui.enabled=true
springdoc.swagger-ui.path=/swagger-ui.html

springdoc.api-docs.title=Inventory Service API
springdoc.api-docs.description=REST API for managing products, orders, and inventory
springdoc.api-docs.version=v1
```

You can also annotate your controller methods with `@Operation(summary = "...", description = "...")` to enrich the documentation.
