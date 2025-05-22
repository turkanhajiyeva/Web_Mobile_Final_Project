A full-stack restaurant ordering system with:

Frontend (React): Customer-facing menu, cart, and kitchen staff dashboards.

Backend (Spring Boot + Gradle): REST APIs, PostgreSQL persistence, RabbitMQ messaging.

🗂️ Project Structure

Web_Mobile_Final_Project/
├── frontend/       # React app (Customer & Staff UI)
├── backend/        # Spring Boot service (APIs, messaging, DB)
└── README.md       # This file

🔧 Setup & Run

1. Clone the repo

git clone https://github.com/turkanhajiyeva/Web_Mobile_Final_Project.git
cd Web_Mobile_Final_Project

2. Backend (Spring Boot)

Ensure you have Java 17+ & Gradle installed.

Configure PostgreSQL:

Create database ilpalazzo.

Update credentials in backend/src/main/resources/application.properties if needed.

Run the server:

cd backend
./gradlew build
./gradlew bootRun

The API will listen on http://localhost:8080.

3. Frontend (React)

Ensure you have Node.js (v14+) & npm.

Install dependencies and start:

cd frontend
npm install
npm run start

The UI will launch at http://localhost:3000 and proxy API calls to port 8080.

📦 Prerequisites

PostgreSQL database ilpalazzo

RabbitMQ broker running on default ports

Java 17+, Gradle

Node.js & npm

⚙️ Configuration

backend/application.properties: database URL, credentials, RabbitMQ settings

frontend/package.json: React proxy setting

🚀 Features

Customer can browse menu, add to cart, place orders

Kitchen staff dashboard with real-time incoming orders

Order status transitions: Pending → In Preparation → Ready → Delivered

WebSocket/RabbitMQ integration for notifications
