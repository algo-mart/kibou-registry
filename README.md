# Backend Project for Admin Interface (Java)

This project is the Java-based backend component of the Admin Interface, which provides RESTful API endpoints for managing participants, recording attendance, processing daily payments, and generating summary reports.

## Getting Started

### Prerequisites

- Java JDK 11 or later
- Maven 3.6.0 or later
- MySQL 8.0 or PostgreSQL 12 (or another RDBMS of your choice)

### Setting Up the Database

1. Create a new database in your RDBMS.
2. Update the `src/main/resources/application.properties` file with your database connection details:

   ```
   spring.datasource.url=jdbc:mysql://localhost:3306/your_database
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

   For PostgreSQL, use the following format for the URL:

   ```
   spring.datasource.url=jdbc:postgresql://localhost:5432/your_database
   ```

### Building the Project

Navigate to the project directory and run the following command to build the project:

```
mvn clean install
```

### Running the Application

Start the application with the following command:

```
mvn spring-boot:run
```

The server will start running on `http://localhost:8080`.

## API Endpoints

### Participants

- `POST /api/participants`: Create a new participant
- `GET /api/participants`: Retrieve all participants
- `GET /api/participants/{id}`: Retrieve a specific participant
- `PUT /api/participants/{id}`: Update a specific participant
- `DELETE /api/participants/{id}`: Delete a specific participant

### Attendance

- `POST /api/attendance`: Record attendance for a meeting
- `GET /api/attendance`: Retrieve all attendance records
- `GET /api/attendance/{id}`: Retrieve a specific attendance record

### Payments

- `POST /api/payments`: Record a daily payment
- `GET /api/payments`: Retrieve all payment records
- `GET /api/payments/monthly-summary`: Retrieve monthly payment summary

### Summary Reports

- `GET /api/reports/attendance-summary`: Generate a monthly attendance summary report

## Testing

Run the automated tests with the following command:

```
mvn test
```

## Deployment

(Provide instructions for deploying the backend to a server or a cloud platform like Heroku or AWS.)

## Contributing

(Provide guidelines for contributing to the project, if applicable.)

## License

(Include licensing information, if applicable.)

---
