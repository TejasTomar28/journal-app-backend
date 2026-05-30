# Journal App 📖

A production-oriented backend application built using **Spring Boot** that enables users to maintain personal journals, analyze emotional sentiment from journal entries, and receive automated weekly sentiment reports via email.

The project demonstrates modern backend engineering concepts including **JWT Authentication, Kafka Messaging, Redis Caching, MongoDB Atlas, Swagger Documentation, Scheduled Jobs, and Role-Based Authorization**.

---

## Project Highlights

✅ JWT Authentication & Authorization

✅ Role-Based Access Control (USER / ADMIN)

✅ MongoDB Atlas Cloud Database

✅ Apache Kafka Producer-Consumer Architecture

✅ Redis Caching

✅ Scheduled Background Jobs

✅ Email Notification Service

✅ Weather API Integration

✅ Swagger/OpenAPI Documentation

✅ SonarQube Code Quality Analysis

---

## Tech Stack

### Backend
- Java 17
- Spring Boot 2.7.16
- Maven

### Security
- Spring Security
- JWT Authentication
- BCrypt Password Encoding

### Database
- MongoDB Atlas

### Messaging
- Apache Kafka

### Caching
- Redis

### Documentation
- Swagger / OpenAPI 3

### Other Tools
- SonarQube
- Git & GitHub
- GitHub Actions
- Spring Scheduler
- Spring Mail

---

# System Architecture

```text
                    ┌─────────────────┐
                    │      Client     │
                    └────────┬────────┘
                             │
                             ▼
                  JWT Authentication
                             │
                             ▼
                    Spring Security
                             │
                             ▼
                       Controllers
                             │
                             ▼
                        Services
                   ┌─────────┴─────────┐
                   ▼                   ▼
           MongoDB Atlas          Redis Cache

                             ▲
                             │
                     Scheduled Jobs
                             │
                             ▼
                      Kafka Producer
                             │
                             ▼
                        Kafka Topic
                             │
                             ▼
                      Kafka Consumer
                             │
                             ▼
                        Email Service
```

---

# Features

## Authentication & Authorization

- User Registration
- Secure Login
- JWT Token Generation
- Role-Based Authorization
- BCrypt Password Encryption

### Roles

#### USER

- Create Journal Entries
- Update Journal Entries
- Delete Journal Entries
- View Personal Entries

#### ADMIN

- View All Users
- Create Admin Users
- Refresh Application Cache

---

## Journal Management

- Create Journal Entries
- Update Journal Entries
- Delete Journal Entries
- View All Entries
- Fetch Entry by ID

---

## Sentiment Analysis

- Sentiment classification of journal entries
- Weekly sentiment aggregation
- Personalized emotional insights

---

## Kafka Integration

Implemented asynchronous event-driven communication using Apache Kafka.

### Workflow

1. Weekly scheduler executes every Sunday.
2. User sentiments are aggregated.
3. Sentiment event is published to Kafka.
4. Consumer receives event.
5. Email notification is sent.

### Fallback Mechanism

If Kafka becomes unavailable:

```java
try {
    kafkaTemplate.send(...);
} catch(Exception e) {
    emailService.sendEmail(...);
}
```

Email delivery continues without interruption.

---

## Redis Integration

Redis is used as an application cache to:

- Reduce database calls
- Improve response time
- Store frequently accessed configuration data

Cache is refreshed periodically using Spring Scheduler.

---

## Weather API Integration

Integrated external weather API to provide:

- Current weather conditions
- Personalized greeting messages

Example:

```text
Hi Tejas, Weather feels like 36°C
```

---

## Email Service

Automated email delivery using SMTP.

### Weekly Sentiment Report

Example:

```text
Subject:
Weekly Sentiment Analysis

Body:
Sentiment for last 7 days: HAPPY
```

---

## Swagger Documentation

Interactive API documentation using OpenAPI.

Features:

- Endpoint Discovery
- Request/Response Documentation
- JWT Authorization Support
- Live API Testing

Swagger URL:

```text
http://localhost:8080/api/swagger-ui/index.html
```

---

# API Endpoints

## Public APIs

| Method | Endpoint | Description |
|----------|-----------|-------------|
| POST | /api/public/signup | Register User |
| POST | /api/public/login | Generate JWT |
| GET | /api/public/health-check | Health Check |

---

## User APIs

| Method | Endpoint |
|----------|-----------|
| GET | /api/user |
| PUT | /api/user |
| DELETE | /api/user |

---

## Journal APIs

| Method | Endpoint |
|----------|-----------|
| GET | /api/journal |
| POST | /api/journal |
| GET | /api/journal/id/{id} |
| PUT | /api/journal/id/{id} |
| DELETE | /api/journal/id/{id} |

---

## Admin APIs

| Method | Endpoint |
|----------|-----------|
| GET | /api/admin/all-users |
| POST | /api/admin/create-admin-user |
| GET | /api/admin/clear-app-cache |

---

# Security Workflow

1. User logs in.
2. Credentials are authenticated.
3. JWT token is generated.
4. Client stores token.
5. Token is attached to Authorization header.
6. Spring Security validates token.
7. Protected APIs become accessible.

---

# Project Screenshots

## Swagger API Documentation

*(Insert Swagger Screenshot)*

## MongoDB Atlas Database

*(Insert MongoDB Screenshot)*

## Kafka Consumer Output

*(Insert Kafka Screenshot)*

## Redis Cloud Dashboard

*(Insert Redis Screenshot)*

---

# Running Locally

## Prerequisites

- Java 17+
- Maven
- MongoDB Atlas
- Redis
- Apache Kafka
- Zookeeper

---

## Clone Repository

```bash
git clone https://github.com/<your-username>/<repo-name>.git

cd journalApp
```

---

## Start Kafka

```bash
bin/zookeeper-server-start.sh config/zookeeper.properties
```

```bash
bin/kafka-server-start.sh config/server.properties
```

---

## Start Redis

```bash
redis-server
```

---

## Run Application

```bash
mvn spring-boot:run
```

---

# Key Learnings

Through this project I gained hands-on experience with:

- Spring Boot Application Development
- REST API Design
- JWT Authentication
- Spring Security
- MongoDB Atlas
- Redis Caching
- Kafka Messaging
- Event-Driven Architecture
- API Documentation with Swagger
- Scheduled Background Jobs
- SMTP Email Services
- Git & GitHub Workflows
- SonarQube Code Analysis

---

# Future Enhancements

- Docker Containerization
- Docker Compose
- Google OAuth Login
- CI/CD Pipeline Improvements
- Monitoring with Prometheus & Grafana
- Kubernetes Deployment

---

# Author

**Tejas Tomar**

Software Engineering Undergraduate  
Delhi Technological University (DTU)

Interested in Backend Development, Distributed Systems, Spring Boot, Kafka, Redis, and Scalable Software Engineering.

<img width="2932" height="1602" alt="image" src="https://github.com/user-attachments/assets/b09854eb-16fa-4ced-93ca-3877b601ee54" />
<img width="1028" height="1158" alt="image" src="https://github.com/user-attachments/assets/e205623d-4203-4c04-8945-b6fb3f080569" />
<img width="1228" height="710" alt="image" src="https://github.com/user-attachments/assets/cdb55ecd-0c0c-493b-90e1-70a63eff8fc9" />
<img width="2934" height="1490" alt="image" src="https://github.com/user-attachments/assets/aebd6b49-b129-427d-aebb-40bf68f6290b" />


