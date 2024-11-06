#### Overview
This repository is a sample implementation of a Contact Management System built using Java Spring Boot. It provides a robust framework for managing contact information, allowing users to perform CRUD (Create, Read, Update, Delete) operations efficiently. The application showcases best practices in Spring Boot development, including RESTful API design, data persistence with JPA, and pagination for querying large datasets.

#### Features
- Authentication User (register, login and logout)
- User Management (get, update, delete)
- Contact Management (create, get, get pagination, update, delete by uid, delete by phone)
- Address Management (create, get, update, delete)

##### Aditional Feature
- Data Validation
- Pagination and Filtering
- Authentication using JWT token

#### Technologies Used
- Java: The primary programming language for building the application.
- Spring Boot: A framework for developing Java applications quickly and easily, providing built-in features for dependency injection, REST APIs, and more.
- JDBC Template: Custom query for MYSQL repository
- H2 Database: An in-memory database for development and testing purposes (can be replaced with other databases like MySQL or PostgreSQL).
- Maven: For project management and dependency management.
- Lombok: A library used to reduce boilerplate code (e.g., getters, setters).
- Swagger: API Documentation

