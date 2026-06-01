# DKD (Dimaag Ka Darpan) 

**DKD** is a backend-driven personal reflection platform designed to help users understand their thoughts, emotions, and behavioral patterns through journaling.

Users can securely maintain personal journals, receive automated weekly emotional summaries, and track their mental well-being over time through sentiment analysis.

The long-term vision is to evolve DKD into a digital self-awareness platform that combines journaling, emotional analytics, mental health surveys, and personalized insights.

---

## Why Dimaag Ka Darpan?

Most people write journals but rarely revisit them to understand how they have been feeling over time.

DKD aims to answer questions like:

* Am I generally happy, anxious, stressed, or overwhelmed?
* How has my emotional state changed over the past few weeks?
* What recurring patterns appear in my journal entries?
* Can journaling help improve self-awareness?

The current version focuses on secure journaling and automated sentiment reporting, laying the foundation for future emotional intelligence features.

---

# Core Features

✅ Secure User Registration & Authentication

✅ JWT-Based Authorization

✅ Role-Based Access Control (USER / ADMIN)

✅ Personal Journal Management

✅ Weekly Sentiment Analysis Reports

✅ Apache Kafka Event Processing

✅ Redis-Based Caching

✅ MongoDB Atlas Cloud Persistence

✅ Swagger/OpenAPI Documentation

✅ Email Notification Service

✅ Scheduled Background Jobs

✅ Weather API Integration

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

### Additional Tools

* SonarQube
* Git & GitHub
* GitHub Actions
* Spring Scheduler
* Spring Mail

---

# System Architecture

Client
↓
JWT Authentication
↓
Spring Security
↓
Controllers
↓
Services
↙              ↘
MongoDB Atlas    Redis Cache

↓
Scheduled Jobs
↓
Kafka Producer
↓
Kafka Topic
↓
Kafka Consumer
↓
Email Service

---

# User Workflow

### 1. Register & Login

* User creates an account
* JWT token is generated upon successful login

### 2. Maintain Journal Entries

Users can:

* Create entries
* Update entries
* Delete entries
* View all entries
* Fetch entries by ID

### 3. Opt for Sentiment Analysis

Users can enable weekly sentiment analysis while creating their account.

### 4. Weekly Emotional Summary

Every Sunday:

* Journal entries from the last 7 days are analyzed
* Dominant sentiment is calculated
* Sentiment event is published to Kafka
* Consumer processes the event
* Personalized email report is delivered

Example:

Subject:
Weekly Sentiment Analysis

Body:
Sentiment for last 7 days: HAPPY

---

# Kafka Integration

DKD uses Apache Kafka to decouple sentiment processing from email delivery.

### Workflow

1. Scheduler identifies eligible users
2. Sentiments are aggregated
3. Event is pushed to Kafka Topic
4. Consumer receives event
5. Email is sent asynchronously

### Fault Tolerance

If Kafka is unavailable:

```java
try {
    kafkaTemplate.send(...);
} catch(Exception e){
    emailService.sendEmail(...);
}
```

Email delivery continues through fallback logic.

---

# Redis Integration

Redis is used as an application cache to:

* Reduce repetitive database calls
* Improve response time
* Store frequently used application data

Application cache is refreshed automatically using scheduled jobs.

---

# Weather API Integration

The platform integrates external weather data to personalize user greetings.

Example:

Hi Tejas, Weather feels like 36°C

---

# Swagger Documentation

Interactive API documentation is available through Swagger/OpenAPI.

Features:

* JWT Authorization Support
* Live API Testing
* Request & Response Schemas
* Endpoint Discovery

---

# API Modules

### Public APIs

* User Registration
* Login
* Health Check

### User APIs

* Update Profile
* Delete Account
* Personalized Greeting

### Journal APIs

* Create Journal Entry
* Update Journal Entry
* Delete Journal Entry
* View Journal Entries
* Fetch Entry By ID

### Admin APIs

* Create Admin User
* View All Users
* Refresh Cache

---

# Project Screenshots

### Swagger Documentation

<img width="100%" src="docs/swagger.png">

### MongoDB Atlas

<img width="100%" src="docs/mongodb.png">

### Kafka Consumer Output

<img width="100%" src="docs/kafka.png">

### Redis Cloud

<img width="100%" src="docs/redis.png">

---

# Running Locally

## Prerequisites

* Java 17+
* Maven
* Apache Kafka
* Zookeeper
* Redis
* MongoDB Atlas

### Clone Repository

```bash
git clone https://github.com/<your-username>/dimaag-ka-darpan.git

cd dimaag-ka-darpan
```

### Start Zookeeper

```bash
bin/zookeeper-server-start.sh config/zookeeper.properties
```

### Start Kafka

```bash
bin/kafka-server-start.sh config/server.properties
```

### Start Redis

```bash
redis-server
```

### Run Application

```bash
mvn spring-boot:run
```

---

# What I Learned

This project helped me gain practical experience with:

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
* GitHub Workflows
* SonarQube Analysis

---

# Future Roadmap

The vision for DKD extends beyond journaling.

Planned enhancements include:

* AI-Powered Sentiment Analysis
* Mental Health Surveys
* Mood Tracking Dashboard
* Emotional Trend Visualization
* Personalized Recommendations
* Docker & Docker Compose
* CI/CD Pipelines
* Monitoring & Observability
* Kubernetes Deployment

---

# Author

**Tejas Tomar**

Software Engineering Undergraduate
Delhi Technological University (DTU)

Interested in Backend Engineering, Distributed Systems, Kafka, Redis, Spring Boot, and Scalable Software Development.


<img width="2932" height="1602" alt="image" src="https://github.com/user-attachments/assets/b09854eb-16fa-4ced-93ca-3877b601ee54" />
<img width="1028" height="1158" alt="image" src="https://github.com/user-attachments/assets/e205623d-4203-4c04-8945-b6fb3f080569" />
<img width="1228" height="710" alt="image" src="https://github.com/user-attachments/assets/cdb55ecd-0c0c-493b-90e1-70a63eff8fc9" />
<img width="2934" height="1490" alt="image" src="https://github.com/user-attachments/assets/aebd6b49-b129-427d-aebb-40bf68f6290b" />


