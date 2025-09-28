# 🎰 SpringBoot Casino Microservices

A microservices-based terminal casino application built with Spring Boot, Feign Clients, and Resilience4J. Users can register, log in, manage their balance, and play games like Mines and Plinko.

## 🌟 Technologies & Stack

| Component | Technology | Role |
| :--- | :--- | :--- |
| **Backend** | ☕ Java 17 | Core language for all services. |
| **Framework** | 🍃 Spring Boot 3.5.6 | Powers the REST APIs and dependency injection. |
| **Build Tool** | 📦 Apache Maven | Project management and building. |
| **Database** | 🐬 MySQL & Spring Data JPA | Persistent storage for user and game history data. |
| **Communication**| 📞 Spring Cloud OpenFeign | Declarative HTTP clients for inter-service calls. |
| **Resilience** | 🛡️ Resilience4J Circuit Breaker | Ensures application stability when services are down. |

## 📐 Architecture Overview (Microservices)

The project is structured into three decoupled services that communicate via REST APIs.

| Service | Port | Description | Core Responsibility |
| :--- | :--- | :--- | :--- |
| `userservice` | `8081` | The backend for all user data. | User accounts, login/register, balance, history, leaderboard. |
| `gameservice` | `8082` | Contains all game logic. | Mines 💣 and Plinko 🔴 game execution. |
| `consolecasino`| `8080` | The command-line interface (Client). | User interaction, calls other services using Feign/Circuit Breaker. |

## ✨ Core Features

* **🔐 User Management:** Secure registration and login (passwords are SHA-256 hashed).
* **💰 Balance & History:** Users have a tracked balance and a history of all games played.
* **🥇 Leaderboard:** View the top 10 users ranked by their current balance.
* **💣 Mines Game:** A thrilling 5x5 grid game where users click spaces to find diamonds 💎 or a mine 💣. Multiplier increases with each safe click.
* **🔴 Plinko Game:** A luck-based game where a ball falls down a 10-row pyramid, and the final landing spot determines the multiplier.
* **♻️ Service Resilience:** Uses Resilience4J to gracefully handle scenarios where the `gameservice` is unavailable, refunding bets and informing the user.

## ⚙️ Setup and Running Locally

To run this project, you will need Java 17+ and a local MySQL database.

### 1. Database Setup

1.  **Start MySQL:** Ensure your local MySQL server is running on port `3306`.
2.  **Create Schema:** The application is configured to use a schema named `apcproj`.
    ```sql
    CREATE DATABASE apcproj;
    ```
3.  **Configure Credentials:** The `userservice` is configured with the following credentials. **⚠️ IMPORTANT:** You should change these in a real environment.
    * **URL:** `jdbc:mysql://localhost:3306/apcproj?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true`
    * **Username:** `root`
    * **Password:** `yourpasswordhere`

### 2. Build the Projects

Navigate to the root directory of each service (`userservice`, `gameservice`, `consolecasino`) and run the Maven build command:

```bash
# In each service directory (userservice, gameservice, consolecasino)
./mvnw clean install
