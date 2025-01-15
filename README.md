# TaskManager
A simple Spring Boot application to manage tasks with a MySQL database. This application allows you to create, read, update, and delete tasks using a REST API.

---

## MySQL Configuration
To set up the MySQL database for the application, follow these steps:
- CREATE DATABASE task_manager;
- CREATE USER 'test'@'localhost' IDENTIFIED BY 'test';
- GRANT ALL PRIVILEGES ON task_manager.* TO 'test'@'localhost';
- FLUSH PRIVILEGES;
