# Price Service

## Introduction

This project is a Price Service application developed in Java using Spring Boot. The main objective is to provide a REST API that allows querying the applicable price of a product for a specific brand at a given date and time, applying priority rules according to different rate tariffs.

The development follows an **API First** approach using **OpenAPI Generator**, and the application has been developed using **Test-Driven Development (TDD)** practices.

Note: To keep this exercise focused and concise, certain production-level features have been intentionally omitted, including:

- **Security**: Authentication and authorization mechanisms (e.g., OAuth 2.0 with Spring Security) have not been implemented. 
- **Monitoring and Observability**: Tools and practices for monitoring, logging, and tracing (e.g., Spring Boot Actuator, Micrometer) are not included.
- **Configuration Management**: Externalized configuration using tools like Spring Cloud Config is not set up.
- **Containerization and Orchestration**: Dockerization and deployment configurations for orchestration platforms like Kubernetes are not provided.
- **Database Migrations**: Managing database schema changes with Flyway for version-controlled migrations.

## Technologies Used

- **Java 17**
- **Spring Boot 3**
- **Maven**
- **OpenAPI Generator**
- **H2 Database (in-memory)**
- **JUnit 5**
- **Spring Data JPA**
- **Spring MVC**
- **Lombok**
- **Hibernate**

## Prerequisites

To run this project, you need to have installed:

- **Java 17 JDK**
- **Maven 3.8+**
- **Git** (optional, for cloning the repository)

## Installation and Configuration

### Step 1: Clone the Repository

```bash
git clone https://github.com/carlex05/price-service.git
cd price-service
```

### Step 2: Generate Code from OpenAPI Specification

The project uses OpenAPI Generator to generate the API models and interfaces.

Run the following command to generate the code:

```bash
mvn clean generate-sources
```

This command will generate code based on the OpenAPI specification located in `src/main/resources/api/price-service.yaml`.

### Step 3: Build the Project

Compile the project using Maven:

```bash
mvn clean package
```

## Running the Application

You can run the application in two ways:

### Option 1: Using Maven

```bash
mvn spring-boot:run
```

### Option 2: Running the Generated JAR

First, build the project if you haven't already:

```bash
mvn clean package
```

Then, run the JAR file:

```bash
java -jar target/price-service-0.0.1-SNAPSHOT.jar
```

The application will be available at `http://localhost:8080`.

## Running Tests

The project was developed using **Test-Driven Development (TDD)**, and includes unit and integration tests.

To run all tests:

```bash
mvn clean test
```

Test results will be displayed in the console.

You can find a postman collection to test this running service in the file `price-service.postman_collection.json`.

## API Documentation

The application follows an **API First** approach, using an OpenAPI specification to define the API contract. The OpenAPI specification is located at `src/main/resources/price.yaml`.

OpenApi documentation could be accessed navigating into `http://localhost:8080/swagger-ui/index.html`.

### Endpoint to Get the Applicable Price

- **URL:** `/prices/applicable`
- **HTTP Method:** `GET`
- **Description:** Retrieves the applicable price for a product and brand at a specific date and time.

#### Query Parameters

- `productId` (Long, required): ID of the product.
- `brandId` (Integer, required): ID of the brand.
- `applicationDate` (String, required): Date and time of application in ISO 8601 format (`yyyy-MM-dd'T'HH:mm:ss'Z'`).

#### Example Request

```
GET http://localhost:8080/prices/applicable?productId=35455&brandId=1&applicationDate=2020-06-14T16:00:00Z
```

#### Example Successful Response (200 OK)

```json
{
  "productId": 35455,
  "brandId": 1,
  "priceList": 2,
  "startDate": "2020-06-14T15:00:00Z",
  "endDate": "2020-06-14T18:30:00Z",
  "price": 25.45,
  "currency": "EUR"
}
```

#### Response When No Applicable Price Is Found (404 Not Found)

```json
{
  "timestamp": "2023-10-20T10:00:00Z",
  "status": 404,
  "error": "Not Found",
  "message": "No price found for the given parameters",
  "path": "/prices/applicable"
}
```

#### Response Codes

- **200 OK:** An applicable price was found and returned in the response body.
- **400 Bad Request:** One or more parameters are invalid or missing.
- **404 Not Found:** No applicable price was found for the given parameters.

## Testing the REST Services

You can test the REST services using tools like **Postman**, **cURL**, or your web browser.

### Example using cURL

```bash
curl -X GET "http://localhost:8080/prices/applicable?productId=35455&brandId=1&applicationDate=2020-06-14T16:00:00Z" -H "accept: application/json"
```

### Test Cases

The following test cases were implemented to validate the service using the provided sample data:

1. **Test Case 1:** Request at 10:00 on June 14th for product 35455 and brand 1.
    - **Expected Price List:** 1
    - **Expected Price:** 35.50

2. **Test Case 2:** Request at 16:00 on June 14th for product 35455 and brand 1.
    - **Expected Price List:** 2
    - **Expected Price:** 25.45

3. **Test Case 3:** Request at 21:00 on June 14th for product 35455 and brand 1.
    - **Expected Price List:** 1
    - **Expected Price:** 35.50

4. **Test Case 4:** Request at 10:00 on June 15th for product 35455 and brand 1.
    - **Expected Price List:** 3
    - **Expected Price:** 30.50

5. **Test Case 5:** Request at 21:00 on June 16th for product 35455 and brand 1.
    - **Expected Price List:** 4
    - **Expected Price:** 38.95

#### Sample Data Used

| **BRAND_ID** | **START_DATE**          | **END_DATE**            | **PRICE_LIST** | **PRODUCT_ID** | **PRIORITY** | **PRICE** | **CURR** |
|--------------|-------------------------|-------------------------|----------------|----------------|--------------|-----------|----------|
| 1            | 2020-06-14 00:00:00     | 2020-12-31 23:59:59     | 1              | 35455          | 0            | 35.50     | EUR      |
| 1            | 2020-06-14 15:00:00     | 2020-06-14 18:30:00     | 2              | 35455          | 1            | 25.45     | EUR      |
| 1            | 2020-06-15 00:00:00     | 2020-06-15 11:00:00     | 3              | 35455          | 1            | 30.50     | EUR      |
| 1            | 2020-06-15 16:00:00     | 2020-12-31 23:59:59     | 4              | 35455          | 1            | 38.95     | EUR      |

#### How to Run the Test Cases

These test cases are implemented as integration tests using JUnit 5 and can be run with the following command:

```bash
mvn clean test
```

Alternatively, you can manually perform the requests using Postman or cURL as shown in the examples.

## Project Structure

The project follows a layered architecture that promotes separation of concerns and facilitates maintenance.

### Main Packages

- **`domain`**: Contains domain models and repository interfaces.
    - **`model`**: Classes representing business objects, such as `Price` and `PriceFilter`.
    - **`repository`**: Interfaces defining contracts for data access. (Driven ports)
    - **`usecase`**: Use cases encapsulating business logic. (Driving ports)

- **`application`**: Implementations of use cases and application services.

- **`infrastructure`**: Implementation details, such as JPA repositories, database entities, and REST controllers.
    - **`controller`**: REST controllers exposing the API.
    - **`repository`**: Implementations of domain repositories using JPA.

### Overall Architecture

- **API First Approach**: The API contract is defined using OpenAPI specification before implementation.
- **Hexagonal Architecture**: This microservice is structured in a hexagonal architecture
- **REST Controller**: Receives HTTP requests and converts them into calls to use cases.
- **Use Cases**: Contain business logic and orchestrate necessary operations.
- **Repositories**: Provide data access, abstracted in the domain layer and implemented in the infrastructure layer.
- **Domain Model**: Objects representing data and business rules.

## Additional Notes

- **Time Zone Handling**: The application and database are configured to handle dates and times in UTC, avoiding discrepancies due to time zones.
- **H2 In-Memory Database**: An in-memory database is used for ease of testing and execution without the need for an external database server.
- **SQL Scripts**: The `schema.sql` and `data.sql` files in `src/main/resources` initialize the database structure and data when the application starts.
- **Java Version**: The project uses **Java 17**. Ensure you have Java 17 installed and configured.

## Author

Developed by Carlos Gómez (Carlex).

- **Email**: carlosramirez05@gmail.com
- **LinkedIn**: [https://www.linkedin.com/in/carlex3/](https://www.linkedin.com/in/carlex3/)
- **GitHub**: [https://github.com/carlex05](https://github.com/carlex05)
- **Blog**: [https://blog.seart.dev](https://blog.seart.dev)

## Contribution Guidelines

If you wish to contribute to this project:

1. Fork the repository.
2. Create a branch for your feature (`git checkout -b feature/new-feature`).
3. Make the necessary changes and commit (`git commit -m 'Add new feature'`).
4. Push to the branch (`git push origin feature/new-feature`).
5. Open a Pull Request on GitHub.

## Contact

If you have any questions or suggestions, feel free to contact me via email or LinkedIn.
