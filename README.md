# DKD (Dimaag Ka Darpan) 🧠🪞

DKD (Dimaag Ka Darpan) is a production-oriented backend application built using **Spring Boot** that helps users maintain personal journals, reflect on their emotions, and receive automated sentiment-based insights.

The long-term vision of DKD is to evolve into a self-awareness platform where users can track emotions, identify behavioral patterns, complete mental wellness surveys, and gain meaningful insights from their journaling data.

The current version focuses on secure journaling, sentiment tracking, and scalable backend architecture.

The project demonstrates modern backend engineering concepts including **JWT Authentication, Kafka Messaging, Redis Caching, MongoDB Atlas, Swagger Documentation, Scheduled Jobs, and Role-Based Authorization**.

---

## Project Highlights

✅ Secure Journaling Platform

✅ JWT Authentication & Authorization

✅ Role-Based Access Control (USER / ADMIN)

✅ MongoDB Atlas Cloud Database

✅ Apache Kafka Producer–Consumer Architecture

✅ Kafka Failure Fallback Mechanism

✅ Redis Caching

✅ Scheduled Background Jobs

✅ Weekly Sentiment Analysis Reports

✅ Email Notification Service

✅ Weather API Integration

✅ Swagger/OpenAPI Documentation

✅ SonarQube Code Quality Analysis

---

# Tech Stack

### Backend

* Java 17
* Spring Boot 2.7.16
* Maven

### Security

* Spring Security
* JWT Authentication
* BCrypt Password Encoding

### Database

* MongoDB Atlas

### Messaging

* Apache Kafka

### Caching

* Redis

### Documentation

* Swagger / OpenAPI 3

### Other Tools

* SonarQube
* Git & GitHub
* GitHub Actions
* Spring Scheduler
* Spring Mail

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

DKD currently enables users to securely maintain personal journals while receiving sentiment-based emotional summaries through an asynchronous event-driven architecture.

## Authentication & Authorization

* User Registration
* Secure Login
* JWT Token Generation
* Role-Based Authorization
* BCrypt Password Encryption

### Roles

#### USER

* Create Journal Entries
* Update Journal Entries
* Delete Journal Entries
* View Personal Entries

#### ADMIN

* View All Users
* Create Admin Users
* Refresh Application Cache

---

## Journal Management

* Create Journal Entries
* Update Journal Entries
* Delete Journal Entries
* View All Entries
* Fetch Entry by ID

---

## Sentiment Analysis

Users can opt in for weekly sentiment analysis.

The application:

* Tracks sentiments associated with journal entries
* Aggregates emotional trends from the past 7 days
* Identifies the dominant sentiment
* Generates personalized weekly reports

Example sentiments:

* HAPPY
* ANXIOUS
* SAD
* ANGRY

These insights are delivered through automated email notifications every Sunday.

---

## Kafka Integration

DKD uses Apache Kafka to decouple sentiment processing from email delivery.

### Workflow

1. Weekly scheduler executes every Sunday.
2. Journal sentiments from the last 7 days are aggregated.
3. Sentiment event is published to Kafka.
4. Kafka Consumer receives the event.
5. Personalized email notification is delivered.

### Fallback Mechanism

To prevent message delivery failures, a fallback strategy has been implemented.

If Kafka becomes unavailable:

```java
try {
    kafkaTemplate.send(...);
}
catch(Exception e){
    emailService.sendEmail(...);
}
```

The application directly sends the email, ensuring uninterrupted report delivery.

This approach improves reliability while demonstrating fault-tolerant system design.

---

## Redis Integration

Redis is used as an application cache to:

* Reduce database calls
* Improve response time
* Store frequently accessed configuration data

Cache is refreshed periodically using Spring Scheduler.

---

## Weather API Integration

DKD integrates external weather services to provide personalized greetings.

Example:

```text
Hi Tejas, Weather feels like 36°C
```

This feature demonstrates third-party API integration and external service consumption using Spring Boot.

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

* Endpoint Discovery
* Request/Response Documentation
* JWT Authorization Support
* Live API Testing

Swagger URL:

```text
http://localhost:8080/api/swagger-ui/index.html
```

---

# API Endpoints

## Public APIs

| Method | Endpoint                 | Description   |
| ------ | ------------------------ | ------------- |
| POST   | /api/public/signup       | Register User |
| POST   | /api/public/login        | Generate JWT  |
| GET    | /api/public/health-check | Health Check  |

---

## User APIs

| Method | Endpoint  |
| ------ | --------- |
| GET    | /api/user |
| PUT    | /api/user |
| DELETE | /api/user |

---

## Journal APIs

| Method | Endpoint             |
| ------ | -------------------- |
| GET    | /api/journal         |
| POST   | /api/journal         |
| GET    | /api/journal/id/{id} |
| PUT    | /api/journal/id/{id} |
| DELETE | /api/journal/id/{id} |

---

## Admin APIs

| Method | Endpoint                     |
| ------ | ---------------------------- |
| GET    | /api/admin/all-users         |
| POST   | /api/admin/create-admin-user |
| GET    | /api/admin/clear-app-cache   |

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

*<img width="1028" height="1158" alt="image" src="https://github.com/user-attachments/assets/e205623d-4203-4c04-8945-b6fb3f080569" />*

## MongoDB Atlas Database

*<img width="2932" height="1602" alt="image" src="https://github.com/user-attachments/assets/b09854eb-16fa-4ced-93ca-3877b601ee54" />*

## Kafka Consumer Output

*<img width="1228" height="710" alt="image" src="https://github.com/user-attachments/assets/cdb55ecd-0c0c-493b-90e1-70a63eff8fc9" />*

## Redis Cloud Dashboard

*<img width="2934" height="1490" alt="image" src="https://github.com/user-attachments/assets/aebd6b49-b129-427d-aebb-40bf68f6290b" />*

---

# Running Locally

## Prerequisites

* Java 17+
* Maven
* MongoDB Atlas
* Redis
* Apache Kafka
* Zookeeper

---

## Clone Repository

```bash
git clone https://github.com/<your-username>/DKD.git

cd DKD
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

# Why DKD?

Most journaling applications focus only on storing entries.

DKD aims to move beyond storage and help users understand themselves better through reflection and emotional awareness.

The long-term vision is to combine:

* Journaling
* Emotional Analytics
* Mood Tracking
* Mental Wellness Surveys
* Personalized Insights

The current version serves as the engineering foundation for that journey.

---

# What I Learned

Through this project I gained hands-on experience with:

* Spring Boot Application Development
* REST API Design
* JWT Authentication
* Spring Security
* MongoDB Atlas
* Redis Caching
* Apache Kafka
* Event-Driven Architecture
* Swagger/OpenAPI
* Background Scheduling
* SMTP Email Services
* GitHub Actions
* SonarQube Analysis

---

# Future Enhancements

* AI-Powered Sentiment Analysis
* Mood Tracking Dashboard
* Mental Wellness Surveys
* Emotional Trend Visualization
* Docker & Docker Compose
* CI/CD Pipeline Improvements
* Monitoring with Prometheus & Grafana
* Kubernetes Deployment
* Google OAuth Login

---

# Author

**Tejas Tomar**

Software Engineering Undergraduate
Delhi Technological University (DTU)

Interested in Backend Engineering, Distributed Systems, Kafka, Redis, Spring Boot, and Scalable Software Development.




