# KrishiMitra Backend (कृषिमित्र backend)

**KrishiMitra Backend** is a Spring Boot REST API service that serves as the central server for the KrishiMitra ecosystem. It handles user authentication, farmer profile management, farm registries, database persistence, and tracks AI-generated crop advisory history.

---

## 🌟 Core Services & API Endpoints

### 🔐 Authentication (`/auth`)
*   `POST /auth/register`: Registers a new user. Accepts name, email, password, and location.
*   `POST /auth/login`: Authenticates the user and returns login sessions/profile details.
*   `POST /auth/forgot-password`: Triggers password reset instructions.

### 👤 User Management (`/user`)
*   Provides endpoints for updating profiles, editing details, and retrieving user information.

### 🌾 Farm Management (`/farm`)
*   Allows farmers to register their land plots, define sizes, select crop types, and link plots to their profile.

### 📋 AI Crop Advisory (`/advisory`)
*   `GET /advisory/farm/{farmId}`: Fetches the latest personalized AI crop advisory plan generated for a farm.
*   `POST /advisory/farm/{farmId}`: Persists a new AI crop advisory plan (linked to Google Gemini or Groq outputs) to the database.

### 📜 Scan & Advisory History (`/history`)
*   Track and list past plant disease scans, diagnosed crop issues, and historical treatment recommendations.

---

## 🛠️ Technology Stack

*   **Language**: Java 17
*   **Framework**: Spring Boot 4.x
*   **Security**: Spring Security (password hashing, access controls)
*   **Database Access**: Spring Data JPA (Hibernate)
*   **Database**: MySQL (Compatible with local instances and Cloud-hosted MySQL like Aiven)
*   **Build Tool**: Maven (with included Maven Wrapper `mvnw`)
*   **Boilerplate reduction**: Project Lombok
*   **Containerization**: Docker (Multi-stage build)

---

## 🔑 Environment Variables Configuration

For security and cloud compliance, the application is configured to read database configurations from environment variables. Do **not** hardcode credentials in `application.properties`.

Configure the following environment variables before running the application:

| Variable Name | Description | Default Value |
| :--- | :--- | :--- |
| `PORT` | The port on which the Spring Boot server listens. | `8080` |
| `DB_URL` | The JDBC database connection string. | `jdbc:mysql://localhost:3306/krishimitra?useSSL=false` |
| `DB_USERNAME` | The database username. | `root` |
| `DB_PASSWORD` | The database password. | *(Empty)* |

---

## 🚀 Running the App Locally

### Prerequisites
*   Java Development Kit (JDK) 17 or higher
*   A running MySQL instance (database named `krishimitra` created)

### Step-by-Step Run:
1. Clone the repository:
   ```bash
   git clone https://github.com/harshghate9623-web/krishimitra-backend.git
   ```
2. Navigate to the folder:
   ```bash
   cd krishimitra-backend
   ```
3. Set your environment variables (optional if running on local default settings):
   *   **On Windows (Command Prompt)**:
       ```cmd
       set DB_URL=jdbc:mysql://localhost:3306/krishimitra
       set DB_USERNAME=root
       set DB_PASSWORD=your_password
       ```
   *   **On macOS/Linux/Bash**:
       ```bash
       export DB_URL="jdbc:mysql://localhost:3306/krishimitra"
       export DB_USERNAME="root"
       export DB_PASSWORD="your_password"
       ```
4. Run the Maven Spring Boot command:
   ```bash
   ./mvnw spring-boot:run
   ```

---

## 🐳 Docker Deployment

The application features a multi-stage Docker build that builds the jar and packages it into a minimal JRE container.

### Build the Docker Image:
```bash
docker build -t krishimitra-backend .
```

### Run the Container:
Inject your database configurations at run-time:
```bash
docker run -p 8080:8080 \
  -e DB_URL="jdbc:mysql://your-db-host:14892/defaultdb?sslMode=REQUIRED" \
  -e DB_USERNAME="your_username" \
  -e DB_PASSWORD="your_secure_password" \
  krishimitra-backend
```
