🎲 Console Casino - A Spring Boot Microservices Project
Welcome to Console Casino! 🎰 This is a fully functional, text-based casino application built from the ground up to demonstrate a modern microservices architecture using Java and the Spring ecosystem.

This project was created as a learning exercise to refactor a monolithic application into a resilient, decoupled, and scalable system.

✨ Key Features
Classic Games: Play fully animated versions of Mines 💣 and Plinko 🪙 right in your console.

Full User Accounts: 👤 Secure user registration and login with password hashing.

Persistent Data: 💾 User balances, game history, and scores are all saved to a MySQL database.

Live Leaderboard: 🏆 Compete for the top spot! View a leaderboard of the top 10 richest players.

Game History: 📜 Track your wins and losses with a detailed history of every game you've played.

Resilient Architecture: 🛡️ Built with a Circuit Breaker, so if a game service goes down, the main application won't crash.

Developer Friendly: ⚙️ Includes a convenient shutdown feature that terminates all running services when you exit the main application.

🏗️ Architecture Overview
This project is not a single application, but a collection of three independent microservices that communicate via REST APIs. This design separates concerns and makes the system robust and scalable.

1. userservice (The Banker & Record Keeper) 🗄️

Purpose: Manages all user-related data.

Responsibilities:

User registration and login (with password hashing).

Tracking player balances.

Storing and retrieving game history.

Calculating and providing the leaderboard.

Connects to: MySQL Database.

2. gameservice (The Game Logic Expert) 🎲

Purpose: Contains the pure, stateless logic for the games.

Responsibilities:

Starting new games of Mines and Plinko.

Calculating the step-by-step state of animations.

Determining game outcomes and multipliers.

Connects to: Nothing! It's completely independent.

3. consolecasino (The Main Console & Manager) 🖥️

Purpose: The client application that the user interacts with.

Responsibilities:

Displaying menus and handling user input.

Orchestrating API calls between the userservice and gameservice.

Rendering the game animations in the console.

Implementing the Circuit Breaker for resilience.

🛠️ Technologies Used
This project utilizes a modern Java and Spring technology stack.

Backend: Java 17, Spring Boot 3

Architecture: Microservices

Database: MySQL with Spring Data JPA & Hibernate

Communication: REST APIs with Spring Cloud OpenFeign

Resilience: Spring Cloud Circuit Breaker (Resilience4j)

Build Tool: Maven

🚀 Getting Started
Follow these steps to get the full casino experience running on your local machine.

Prerequisites

Java JDK 17 or higher installed.

Apache Maven installed.

A running MySQL server instance.

An IDE like Eclipse or IntelliJ IDEA.

1. Database Setup

Before running the services, you need to set up the database.

Connect to your local MySQL server.

Create a new, empty schema named apcproj:

SQL
CREATE DATABASE apcproj;
Open the application.properties file in the userservice project and ensure the spring.datasource.username and spring.datasource.password are correct for your MySQL installation.

2. Running the Services

You must start the applications in the correct order. Open a new terminal for each service.

Start the User Service:
Navigate to the userservice folder and run:

Bash
mvn spring-boot:run
Wait for it to start successfully. You should see "Tomcat started on port(s): 8081".

Start the Game Service:
Navigate to the gameservice folder and run:

Bash
mvn spring-boot:run
Wait for it to start successfully. You should see "Tomcat started on port(s): 8082".

Start the Console Casino:
Navigate to the consolecasino folder and run:

Bash
mvn spring-boot:run
The casino menu will now appear in this terminal. You're ready to play!

3. Playing the Game

Interact with the consolecasino terminal to register, log in, and play games. When you are finished, selecting "Exit" from the main menu will automatically shut down all three services. Enjoy! 🎉
