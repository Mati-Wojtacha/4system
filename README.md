## 4 System

This application provides the following features:

### View for Uploading Data

This view allows users to upload XML or JSON files containing data.

### User List View

This view displays all users with the following capabilities:

- Pagination to prevent loading and transmitting all users at once.
- Sorting by first name, last name, and login.
- Searching by first name, last name, and login using a single search field.

### Database
This application uses MySQL as the database. Below is the configuration for the database to create in Docker.
If you have MySQL installed on your system, create a database with the name `java_4system`, user `user`,
and password `password`, or modify it in the `application.properties` file.


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


### Backend Setup

To run the backend, follow these steps:

1. Choose the appropriate branch:
   - `master` branch contains the version of the application using RestApi.
   - `Sevlet` branch contains the version of the application using Java Servlet.
2. Navigate to the project directory (backend).
3. Ensure that MySQL database is installed and running.
4. Run maven command `mvn clean install` to build the project.
5. Run the application from your IDE or using the command `mvn spring-boot:run`.
6. The application will be accessible at `http://localhost:8080`.


### Frontend Setup

To run the frontend, follow these steps:

1. Navigate to the project directory (frontend).
2. Make sure you have Node.js installed.
3. Run the command `npm install` to install the dependencies.
4. Run the command `npm start` to start the application.
5. Open the browser and navigate to `http://localhost:3000` to view the application.
