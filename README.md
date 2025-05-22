# Web & Mobile Final Project - QR Code-based Restaurant Ordering System

A full-stack restaurant ordering system with:

**Frontend (React):** Customer-facing menu, cart, and staff dashboards  
**Backend (Spring Boot + Gradle):** REST APIs, PostgreSQL persistence, RabbitMQ messaging

## Project Structure

```
Web_Mobile_Final_Project/
├── frontend/       # React app (Customer & Staff UI)
├── backend/        # Spring Boot service (APIs, messaging, DB)
└── README.md       # Project documentation
```

## Setup & Run

### 1. Clone the Repository

```bash
git clone https://github.com/turkanhajiyeva/Web_Mobile_Final_Project.git
cd Web_Mobile_Final_Project
```

### 2. Backend (Spring Boot)

- Ensure you have **Java 17** and **Gradle** installed.
- Create a PostgreSQL database named `ilpalazzo`.
- Update credentials in `backend/src/main/resources/application.properties` if needed.

#### Run the Backend

```bash
cd backend
./gradlew bootRun
```

- The API will be available at: http://localhost:8080

### 3. Frontend (React)

- Ensure you have **Node.js (v14+)** and **npm** installed.

#### Start the Frontend

```bash
cd frontend
npm install
npm run start
```

- The UI will launch at: http://localhost:3000
- React proxy will forward API calls to port 8080.

## Prerequisites

- PostgreSQL database `ilpalazzo`
- RabbitMQ broker running on default ports
- Java 17, Gradle
- Node.js & npm

## Configuration

- `backend/application.properties` – DB connection, RabbitMQ config
- `frontend/package.json` – proxy settings for API

## Features

- Customers can:
  - Browse the menu
  - Add items to cart
  - Place orders by scanning a QR or online

- Kitchen staff can:
  - View real-time incoming orders
  - Change status: Pending → In Preparation → Ready → Delivered

- Real-time communication via WebSocket + RabbitMQ
