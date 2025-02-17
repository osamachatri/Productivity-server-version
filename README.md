# 📌 Productivity - Ktor Backend

## 🚀 Overview
**Productivity** is a backend application built with **Ktor** to support a productivity-focused app. It provides APIs for managing:
- 🧑‍💻 User accounts (authentication & authorization using JWT)
- 📝 Notes
- ✅ To-Do Lists
- 📅 Events
- ⏳ Pomodoro Timer

This project ensures secure user authentication and data management using **JWT** and **Ktorm** (ORM for MySQL).

---

## 🏗️ Tech Stack
- **Ktor** - Asynchronous Kotlin backend framework
- **MySQL** - Relational database for storing structured data
- **Ktorm** - Lightweight ORM for database interaction
- **JWT** - Secure user authentication
- **Docker** - For containerized deployment (planned)
- **Dependency Injection** - Using Ktor features

---

## 📂 Project Structure
```
Productivity-server-version/
│── src/
│   ├── models/          # Data models using Ktorm
│   ├── routes/          # API routes (accounts, notes, tasks, events, pomodoro)
│   ├── security/        # JWT authentication setup
│   ├── database/        # Database connection and setup
│   ├── plugins/         # Ktor plugins (CORS, Logging, Monitoring, etc.)
│   ├── Application.kt   # Entry point of the Ktor server
│── resources/           # Configuration files (database, JWT secrets, etc.)
│── Dockerfile (soon)    # Containerization setup
│── README.md            # Project documentation
```

---

## 🔧 Installation & Setup
### 1️⃣ Clone the Repository
```bash
git clone https://github.com/osamachatri/Productivity-server-version.git
cd Productivity-server-version
```

### 2️⃣ Setup MySQL Database
Make sure MySQL is installed and create a database:
```sql
CREATE DATABASE productivity;
```

### 3️⃣ Configure Environment Variables
Update `application.conf` with:
```properties
DB_URL=jdbc:mysql://localhost:3306/productivity
DB_USER=root
DB_PASSWORD=your_password
JWT_SECRET=your_secret_key
```

### 4️⃣ Run the Server
```bash
graduate - run
```
Server will start at **http://localhost:8080**.

---

## 📡 API Endpoints
### 🔑 Authentication
- `POST auth/register` - Create a new user
- `POST auth/login?type=` - Authenticate and receive JWT token

### 📝 Notes
- `GET /notes` - Fetch all notes
- `POST /notes` - Create a new note

### ✅ To-Do Lists
- `GET /tasks` - Get all tasks
- `POST /tasks` - Create a new task

### 📅 Events
- `GET /events` - Retrieve all events
- `POST /events` - Add a new event

### ⏳ Pomodoro
- `GET /pomodoro` - Fetch Pomodoro session details
- `POST /pomodoro` - Add a new pomodoro session details

---

## 🛠️ Future Improvements
- ✅ Add Docker support
- ✅ Implement refresh tokens for JWT
- ✅ Improve database indexing for faster queries
- ✅ Add WebSockets for real-time collaboration

---

## 🤝 Contributing
Feel free to fork this repository, submit pull requests, or suggest features via issues!

---

## 📜 License
This project is licensed under the **MIT License**.

