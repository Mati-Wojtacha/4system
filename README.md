# 4system Polska

## Overview

This application was designed to manage user data. Its functions, views, database configuration and how to run the application are described below.
## Features

- User Data Management:
   - Allows browsing and processing of user data from the database.
   - Displays user information such as name, surname, and login.
   - Added MD5 hash to display user's first name along with last name in 1st column.
   - Allows sorting and searching across all user fields.
- Data Upload Functionality:
   - Enables users to upload data from XML or JSON files for seamless integration.
- Backend API Endpoints:
   - Provides a set of RESTful API endpoints for various operations.
- Swagger Documentation:
   - Offers Swagger UI for easy exploration and testing of API endpoints.

## Views

Data Upload View:
    
- Facilitates the upload of XML or JSON files.

User List View:

- Displays a paginated list of users with sorting and searching options.

## Database
This application uses MySQL as the database. Below is the configuration for the database to create in Docker.
If you have MySQL installed on your system, create a database with the name: `java_4system`, user: `user`,
and password: `password`, or modify it in the `application.properties` file.

```yaml
version: '3.3'
services:
  mysql:
    image: mysql:latest
    container_name: mysql_database
    environment:
      MYSQL_DATABASE: java_4system
      MYSQL_USER: user
      MYSQL_PASSWORD: password
      MYSQL_ROOT_PASSWORD: password
    ports:
      - "3306:3306"
    volumes:
      - mysql_data:/var/lib/mysql

volumes:
  mysql_data:
```

## Backend Setup

1. Choose the appropriate branch:
   - `master` branch contains the version of the application using RestApi.
   - `Servlet` branch contains the version of the application using Java Servlet.
2. Navigate to the project directory (backend).
3. Ensure that MySQL database is installed and running.
4. Execute maven command `mvn clean install` to build the project.
5. Run the application from your IDE or using the command `mvn spring-boot:run`.
6. The application will be accessible at `http://localhost:8080`.
7. Explore API documentation and test endpoints via Swagger UI at http://localhost:8080/swagger-ui.html.

## Frontend Setup

1. Navigate to the project directory (frontend).
2. Ensure Node.js is installed.
3. Run `npm install` to install the dependencies.
4. Start the application with `npm start`.
5. Open the browser and navigate to `http://localhost:3000` to interact with the application.

## API Endpoints:

| Method | Endpoint                | Description                                       |
|--------|-------------------------|---------------------------------------------------|
| PUT    | `/user`                 | Updates user details. Requires user object.       |
| POST   | `/user`                 | Saves a new user. Requires user object.           |
| POST   | `/user/saveFromFile`    | Saves users from a file. Requires json or xml file. |
| GET    | `/user/list`            | Retrieves a paginated list of users. Requires query parameters: page, size, searchTerm, sortCriteria. |
| GET    | `/user/{id}`            | Retrieves a user by id. Requires user id.         |
| DELETE | `/user/{id}`            | Deletes a user by id. Requires user id.           |