# Library Management System API

This is a RESTful API for a Library Management System developed using Spring Boot, Spring Data JPA, Spring Security, and PostgreSQL.

## Table of Contents

- [Features](#features)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
  - [Configuration](#configuration)
- [Usage](#usage)
- [API Endpoints](#api-endpoints)
- [Authentication](#authentication)
- [License](#license)

## Features

- Create, read, update, and delete books, patrons and borrows.
- User authentication using Basic Authentication.
- Secure password storage using Bcrypt.
- Persistent storage of blog data in a PostgreSQL database.

## Getting Started

### Prerequisites

Make sure you have the following installed on your machine:

- [Java](https://www.oracle.com/java/technologies/javase-downloads.html) (version 17 or higher)
- [Maven](https://maven.apache.org/download.cgi)
- [PostgreSQL](https://www.postgresql.org/download/)

### Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/moamenhady/library-api.git
   ```
   
2. Build the project using Maven:

   ```bash
   cd library-api
   mvn clean install
   ```

#### Configuration

1. Configure the database connection in `src/main/resources/application.yml`:

   ```properties
    datasource:
        url: jdbc:postgresql://localhost:5432/your_database
        username: your_username
        password: your_password
   ```
   
2. Configure other properties as needed (e.g., server port, logging).

## Usage

```bash
java -jar target/library-api-1.0.0.jar
```

Access the API at http://localhost:8080/

## API Endpoints

A default admin user with username `admin` and password `admin` is created in the databse on application run.

Patrons:

- **GET /api/patrons :** Retrieve a list of all patrons. (Requires admin authentication)
- **GET /api/patrons/{id} :** Retrieve details of a specific patron by ID. (Requires admin authentication)
- **POST /api/patrons :** Add a new patron to the system. (Requires no authentication - any one can signup as a patron)
- **PUT /api/patrons/{id} :** Update an existing patron's information. (Requires admin authentication)
- **DELETE /api/patrons/{id} :** Remove a patron from the system. (Requires admin authentication)

Books:

- **GET /api/books :** Retrieve a list of all books. (Requires no authentication)
- **GET /api/books/{id} :** Retrieve details of a specific book by ID. (Requires no authentication)
- **POST /api/books :** Add a new book to the library. (Requires admin authentication)
- **PUT /api/books/{id} :** Update an existing book's information. (Requires admin authentication)
- **DELETE /api/books/{id} :** Remove a book from the library. (Requires admin authentication)

Borrowing records:

- **POST /api/borrow/{bookId}/patron/{patronId}: :** Borrow a book. (Requires patron authentication)
- **PUT /api/return/{bookId}/patron/{patronId} :** Return a borrowed book. (Requires patron authentication)

## Authentication

This API uses Basic Authentication. Include the Authorization header in your requests with the format Basic base64(username:password).

Example using curl:

```bash
curl -X GET -H "Authorization: Basic base64(username:password)" http://localhost:8080/api/books
```

## License

> This project is licensed under the [MIT License](LICENSE).
