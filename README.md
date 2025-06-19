# 📦 Order & Inventory Management App

This project is a **web application** designed to manage customer orders and inventory operations within a distribution center workflow.

It includes:
- A **Java Spring Boot 3** backend that handles order creation, inventory updates, and asynchronous messaging via Kafka.
- A **React + TypeScript** frontend that provides an intuitive user interface for interacting with the system.
- API documentation with **Swagger (OpenAPI)**.
- Security integrated via **Spring Security**.

---

## 🚀 Tech Stack

| Layer     | Technology                                               |
|-----------|----------------------------------------------------------|
| Backend   | Java 17, Spring Boot 3, Spring Security, Kafka, H2, Rate-Limitting |
| Frontend  | React, TypeScript                                 |
| API Docs  | Swagger / OpenAPI                                        |
| Tooling   | Maven, Kafka + Docker                       |

---

## 🛠️ Getting Started

### ▶️ Backend Setup

### Prerequisites:
- Java 17+
- Maven
- Docker & Docker Compose (for Kafka/Zookeeper setup)

How to run the backend locally:

1. Open a terminal and navigate to the backend directory:  
   ```cd inventory-api```

2. Build the project:  
   ```./mvnw clean install```

3. Start the Spring Boot application:  
   ```./mvnw spring-boot:run```

4. The backend will be available at:  
   ```http://localhost:8080```

API documentation (Swagger UI):

  ```http://localhost:8080/swagger-ui.html```

Alternatively, API documentation can be reviewed by opening ```openapi.yaml``` file.

### Initial data:
- On startup, the application loads initial test data using the `data.sql` file.
- This includes sample products and inventory records.

### Testing the API:
You can use the `.http` files found in the `/.http` directory to test the API locally
via IntelliJ HTTP Client. Open a `.http` file, select a request, and click "Run".

Kafka & Zookeeper (optional, if running with Docker Compose):

To start Kafka and Zookeeper with 1 replica each:
   ```docker-compose up -d```

Note:
Ensure that required services (like Kafka) are running locally 
and the application configuration matches your environment.

### ▶️ Frontend Setup

### Prerequisites
- Node.js (>= 18.x)
- npm (>= 9.x) or yarn or pnpm

Setup
1. Install dependencies:  ```npm install```
2. Start the development server:  ```npm run dev```

The app should now be running at ```http://localhost:5173```

Notes
	•	The app connects to the backend using the URL: ```http://localhost:8080```
	•	Responsive design is handled via Tailwind.

## TODO

- Finalize API (create controllers for the rest of the business scenarios)
- Improve error handling and global notifications in the UI
- Add pagination and filters to orders list
- Expand test coverage (frontend unit + integration, backend controller tests)
- Add CI workflow (GitHub Actions) for testing and linting
