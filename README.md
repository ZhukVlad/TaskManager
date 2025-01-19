# TaskManager

A simple Spring Boot application to manage tasks with a MySQL database. 
This application allows you to create, read, update, and delete tasks using a REST API.

---

### MySQL Configuration

To set up the MySQL database for the application, follow these steps:

1. Create the database:
   ```sql
   CREATE DATABASE task_manager;
   ```

2. Create a user and grant privileges:
   ```sql
   CREATE USER 'test'@'localhost' IDENTIFIED BY 'test';
   GRANT ALL PRIVILEGES ON task_manager.* TO 'test'@'localhost';
   FLUSH PRIVILEGES;
   ```

---

### Build and Run the Service

1. Clean and build the project:
   ```bash
   mvn clean install
   ```

2. Run the application:
   ```bash
   mvn spring-boot:run
   ```

---

### How to Use the Task API

- **Get all tasks**
  ```http
  GET http://localhost:8081/tasks
  ```

- **Get sorted tasks**
  ```http
  GET http://localhost:8081/tasks/sorted?attribute={title/description/completed}
  ```

- **Search tasks**
  ```http
  GET http://localhost:8081/tasks/search?attribute={text}
  ```

- **Create a task**
  ```http
  POST http://localhost:8081/tasks
  Content-Type: application/json
  
  {
    "title": "Title",
    "description": "Some description"
  }
  ```

- **Update a task**
  ```http
  PUT http://localhost:8081/tasks/{id}
  Content-Type: application/json
  
  {
    "title": "new title",
    "description": "new description"
  }
  ```

- **Toggle task completion**
  ```http
  PUT http://localhost:8081/tasks/{id}/toggle
  ```

- **Delete a task**
  ```http
  DELETE http://localhost:8081/tasks/{id}
  ```

### How to Use the metrics  API
- **Get all metrics**
  ```http
  GET http://localhost:8081/actuator/metrics/http.server.requests
  ```
- **Get task request count metric**
  ```http
  GET http://localhost:8081/custom-metrics/tasks-request-count
  ```