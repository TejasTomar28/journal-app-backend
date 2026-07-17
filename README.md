# DKD (Dimaag Ka Darpan) 🧠🪞

DKD (Dimaag Ka Darpan) is an AI-powered journaling platform built with Spring Boot that helps users capture their daily thoughts, reflect on emotions, and gain meaningful insights from their journal entries.

Rather than acting as a simple diary, DKD aims to become a personal reflection companion by combining journaling, emotional analytics, AI-generated reflections, and personalized weekly reports.

The backend is designed using modern software engineering principles including JWT Authentication, Spring Security, MongoDB Atlas, Redis Caching, Apache Kafka, Scheduled Jobs, RESTful APIs, and OpenAI integration.

Version 2 introduces AI-powered journal insights, dashboards, search & filtering, reflection prompts, analytics, and Kafka-driven weekly reflection emails while maintaining a scalable backend architecture.

---

# 🚀 Project Highlights

- ✅ Secure Journaling Platform
- ✅ JWT Authentication & Role-Based Authorization
- ✅ AI-Powered Journal Insights
- ✅ Personalized Dashboard
- ✅ Weekly Analytics
- ✅ Journal Search & Filtering
- ✅ Reflection Prompts
- ✅ Weekly Reflection Emails
- ✅ Apache Kafka Event-Driven Architecture
- ✅ Redis Caching
- ✅ MongoDB Atlas
- ✅ Scheduled Background Jobs
- ✅ Weather API Integration
- ✅ Swagger / OpenAPI Documentation
- ✅ SonarQube Code Quality Analysis
- ✅ GitHub Actions CI

---

# 🛠 Tech Stack

## Backend
- Java 17
- Spring Boot 2.7.16
- Maven

## Security
- Spring Security
- JWT Authentication
- BCrypt Password Encoder

## Database
- MongoDB Atlas

## AI
- OpenAI API

## Messaging
- Apache Kafka

## Caching
- Redis

## Documentation
- Swagger / OpenAPI 3

## Other Tools
- Git & GitHub
- GitHub Actions
- Spring Scheduler
- Spring Mail
- SonarQube

---

# 🏗 System Architecture

```text
                    Client
                       │
             JWT Authentication
                       │
                Spring Security
                       │
                 REST Controllers
                       │
                 Service Layer
     ┌──────────────┼──────────────┐
     ▼              ▼              ▼
 MongoDB        Redis Cache     OpenAI API
     ▲
     │
 Scheduled Jobs
     │
 Kafka Producer
     │
 Kafka Topic
     │
 Kafka Consumer
     │
 Weekly Reflection Email
```

---

# ✨ Features

## Authentication & Authorization

- User Registration
- Secure Login
- JWT Authentication
- Role-Based Authorization
- BCrypt Password Encryption

---

## Journal Management

- Create Journal Entries
- Update Journal Entries
- Delete Journal Entries
- View Personal Journals
- Search Journal Entries
- Filter Journal Entries
- Weather-assisted journaling

---

## Dashboard

Provides a personalized dashboard containing:

- Current Streak
- Weekly Activity
- Total Journal Entries
- Personalized Greeting
- Current Weather
- Recent Journal History

---

## AI Features

Users can generate AI insights for selected journal entries.

Generated insights include:

- AI Summary
- Positive Observation
- Reflection Question
- Personalized Encouragement

---

## Reflection Prompts

Supports category-based reflection prompts to encourage meaningful journaling.

Examples include:

- Gratitude
- Productivity
- Mental Wellness
- Self Reflection

---

## Weekly Analytics

Generates analytics such as:

- Entries This Week
- Journal Consistency
- Average Entries Per Day
- Dominant Emotion

---

## Weekly Reflection Emails

Every Sunday, users who opt into weekly reflections receive an automated email containing:

- Current Streak
- Entries This Week
- Journal Consistency
- Dominant Emotion
- AI-generated Weekly Summary
- Positive Observation
- Reflection Question
- Personalized Encouragement

---

# ⚡ Kafka Event Processing

DKD uses Apache Kafka to process asynchronous background tasks.

Current workflow:

```text
Scheduler
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
Weekly Reflection Email
```

### Kafka Fallback

If Kafka becomes unavailable, the application automatically falls back to direct email delivery, ensuring uninterrupted report generation.

---

# ⚡ Redis Integration

Redis is used to:

- Reduce database calls
- Improve response time
- Cache frequently accessed application data

The cache is refreshed periodically using Spring Scheduler.

---

# 🌤 Weather Integration

DKD integrates an external Weather API to provide personalized greetings.

Example:

```
Good Morning Tejas ☀️

Delhi
Feels Like: 36°C
```

---

# 📧 Email Service

Automated weekly emails are sent using Spring Mail.

Example subject:

```
DKD Weekly Reflection 🌿
```

---

# 📖 Swagger Documentation

Interactive API documentation is available through Swagger UI.

Features:

- Endpoint Discovery
- JWT Authorization Support
- Request / Response Documentation
- Live API Testing

Default URL:

```
http://localhost:8080/api/swagger-ui/index.html
```

---

# 🔗 REST APIs

## Public APIs

| Method | Endpoint |
|---------|----------|
| POST | /api/public/signup |
| POST | /api/public/login |
| GET | /api/public/health-check |

---

## User APIs

| Method | Endpoint |
|---------|----------|
| GET | /api/user |
| PUT | /api/user |
| DELETE | /api/user |

---

## Journal APIs

| Method | Endpoint |
|---------|----------|
| GET | /api/journal |
| POST | /api/journal |
| GET | /api/journal/id/{id} |
| PUT | /api/journal/id/{id} |
| DELETE | /api/journal/id/{id} |

---

## Dashboard APIs

| Method | Endpoint |
|---------|----------|
| GET | /api/dashboard |

---

## Analytics APIs

| Method | Endpoint |
|---------|----------|
| GET | /api/analytics/weekly |

---

## AI Insight APIs

| Method | Endpoint |
|---------|----------|
| POST | /api/ai-insights/generate/{journalId} |

---

## Reflection Prompt APIs

| Method | Endpoint |
|---------|----------|
| POST | /api/reflection-prompts |
| GET | /api/reflection-prompts |
| GET | /api/reflection-prompts/random |

---

## Admin APIs

| Method | Endpoint |
|---------|----------|
| GET | /api/admin/all-users |
| POST | /api/admin/create-admin-user |
| GET | /api/admin/clear-app-cache |

---

# 🔒 Security Workflow

```text
User Login
      │
      ▼
Authentication
      │
      ▼
JWT Generation
      │
      ▼
Client Stores Token
      │
      ▼
JWT Validation
      │
      ▼
Protected APIs
```

---

# 📷 Project Screenshots

- Swagger UI
<img width="740" height="1466" alt="image" src="https://github.com/user-attachments/assets/56db55d6-b06c-4537-8e53-bf74ec655356" />
- MongoDB Atlas
<img width="2940" height="1604" alt="image" src="https://github.com/user-attachments/assets/38698bca-6bc6-44fc-a3dd-c770d4180885" />
- Redis Dashboard
<img width="2940" height="1608" alt="image" src="https://github.com/user-attachments/assets/13bce3f5-8a10-454a-8a28-5525e8912500" />
- Weekly Reflection Email
<img width="2540" height="1586" alt="image" src="https://github.com/user-attachments/assets/8089b48e-94f9-40f4-8e6d-40338538c779" />
- Dashboard
<img width="2556" height="1602" alt="image" src="https://github.com/user-attachments/assets/e8e36803-234c-4f4e-8ca1-3006669495a3" />
- AI Insight Response
<img width="2546" height="1594" alt="image" src="https://github.com/user-attachments/assets/18eacd4f-7cfe-42fc-b6a3-a3d962b4b911" />
<img width="2562" height="1598" alt="image" src="https://github.com/user-attachments/assets/9990f714-f656-485c-99b9-8442693385dc" />


---

# ▶️ Running Locally

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
git clone https://github.com/TejasTomar28/DKD-journal-app-backend.git

cd DKD-journal-app-backend
```

---

## Start Kafka

```bash
bin/zookeeper-server-start.sh config/zookeeper.properties

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

# 🎯 Vision

Most journaling applications focus only on storing entries.

DKD aims to help users understand themselves better through AI-assisted reflection and emotional awareness.

The long-term vision is to evolve into a personal growth platform by combining:

- AI-powered Journaling
- Emotional Analytics
- Mood Tracking
- Mental Wellness Surveys
- Personalized Reflection
- Behavioral Pattern Recognition

Version 2 establishes the backend foundation for that vision.

---

# 📚 What I Learned

Through this project I gained practical experience with:

- Spring Boot Application Development
- REST API Design
- JWT Authentication
- Spring Security
- MongoDB Atlas
- Redis Caching
- Apache Kafka
- Event-Driven Architecture
- OpenAI Integration
- AI Prompt Engineering
- DTO Design
- Scheduled Jobs
- Spring Mail
- Unit Testing with Mockito
- GitHub Actions
- SonarQube

---

# 🚀 Future Enhancements

- Kafka-based AI Processing
- React Frontend
- Mobile Application
- Emotional Trend Visualization
- Habit Tracking
- Google OAuth Login
- Docker Compose
- Kubernetes Deployment
- Prometheus & Grafana Monitoring

---

# 👨‍💻 Author

**Tejas Tomar**

Software Engineering Undergraduate  
Delhi Technological University (DTU)

Interested in Backend Engineering, Spring Boot, AI Integration, and Scalable Software Development.

