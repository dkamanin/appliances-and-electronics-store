# Maconi — E-commerce Backend Showcase

Maconi is a full-stack e-commerce platform designed to demonstrate production-grade backend engineering. The project's primary mission is to build a robust, maintainable server-side architecture using a **Modular Monolith** approach and **Domain-Driven Design (DDD)** principles, with modules implemented via **Clean Architecture**.

While the project includes a functional web frontend, it serves mainly as a visual layer for the backend. To optimize development, the frontend is maintained using an **AI-Driven Development (AIDD)** approach — leveraging LLMs for code generation under strict human supervision.

## Project Structure

* **server/** — Backend: Spring Boot REST Service (The core of the project).
* **client/** — Frontend: React application.

## Tech Stack

* **Backend:** `Java 21`, `Spring Boot 3`, `Gradle` (with custom build-logic).
* **Frontend:** `React`, `Redux Toolkit`, `Vite`.
* **Infrastructure:** `PostgreSQL`, `Docker`.

## CI/CD & Engineering Standards

* **Protected Main Branch:** Direct pushes to `main` are disabled. Verified commits only.
* **[Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0/#summary):** Strict adherence to the specification for clear and automated versioning history.
* **Static Analysis:** Code quality is enforced via linters and formatters (`Spotless`, `Checkstyle`).
* **Containerization:** `Docker` is used as the standard for application packaging and deployment.

## Roadmap

*Detailed task tracking and backlog are available in the [Issues](https://github.com/dkamanin/appliances-and-electronics-store/issues) section.*

- [x] **Build Infrastructure:** Centralized build logic via Gradle Convention Plugins.
- [ ] **Architectural Migration:** Refactoring legacy layered code into a Modular Monolith with DDD and Clean Architecture. *(In Progress)*
- [ ] **Stabilized CI/CD:** Fully automated testing and deployment pipelines.
- [ ] **Database Evolution:** Implementing Liquibase for reliable migrations.
- [ ] **Event-Driven Messaging:** Integration of Apache Kafka for inter-module communication.
- [ ] **Frontend Modernization:** AI-assisted refactoring of the legacy UI using Cursor and high-end LLMs.


### Origins

This project began as a final thesis at Hexlet College. It was successfully defended as a working prototype with a traditional layered architecture.

Recognizing its potential, I decided to use it as a foundation for a high-complexity system. My goal is to evolve this prototype into a production-ready showcase that adheres to the highest industry standards for backend development.
