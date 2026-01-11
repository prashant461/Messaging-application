# 📩 Messaging Application

A **Spring Boot–based real-time messaging backend** that supports **one-to-one communication**, **message persistence**, and **real-time delivery** using WebSocket.

This project focuses purely on **backend implementation** and demonstrates how real-time messaging systems can be built using Spring Boot and WebSocket.

---

## 🚀 Features

* 🔄 Real-time message delivery using **WebSocket (STOMP)**
* 💬 One-to-one messaging between users
* 💾 Messages persisted in database
* 👤 Sender–Receiver based communication
* 📬 Fetch chat history using REST APIs
* 🧱 Clean layered architecture (Controller → Service → Repository)

---

## 🛠 Tech Stack

* **Java 17**
* **Spring Boot**
* **Spring WebSocket (STOMP)**
* **Spring Data JPA**
* **Hibernate**
* **REST APIs**
* **Maven**
* **H2 / MySQL** (configurable)

---

## 🧱 Architecture Overview

```
Client
  ↓
REST / WebSocket Controllers
  ↓
Service Layer
  ↓
Repository Layer
  ↓
Database
```

---

## 🚀 How to Run the Application

### 1️⃣ Build the Project

```bash
mvn clean install
```

### 2️⃣ Run the Application

```bash
mvn spring-boot:run
```

Application starts on:

```
http://localhost:8080
```

---

## 🔌 WebSocket Details

### WebSocket Endpoint

```
ws://localhost:8080/ws
```

### Send Message Destination

```
/app/chat.sendMessage
```

### Subscribe Destination

```
/user/queue/messages
```

---

## 📡 REST API Endpoints

### Create Chat

```
POST /api/v1/chats?sender-id={senderId}&receiver-id={receiverId}
```

### Get Messages by Chat ID

```
GET /api/v1/messages/{chatId}
```

---

## 🛡 Error Handling

* Centralized exception handling
* Proper HTTP status codes
* Consistent error responses

---

This project is intended to demonstrate **real-time backend messaging concepts** using Spring Boot.
