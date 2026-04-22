# Smart Campus Sensor & Room Management API

## Overview
This project is a RESTful API developed using JAX-RS for the Client-Server Architectures coursework.  
It manages rooms, sensors, and sensor readings for a Smart Campus system.

The API supports:
- Room creation, retrieval, and deletion
- Sensor creation, retrieval, and filtering
- Nested sensor readings
- Error handling using custom exceptions and exception mappers

## Technology Used
- Java
- JAX-RS (Jakarta RESTful Web Services)
- Maven
- GlassFish Server
- In-memory data structures (`HashMap`, `ArrayList`)

## API Design
The API is structured around three core resources:
- `Room`
- `Sensor`
- `SensorReading`

### Main Endpoints
- `GET /api/v1`
- `GET /api/v1/rooms`
- `POST /api/v1/rooms`
- `GET /api/v1/rooms/{roomId}`
- `DELETE /api/v1/rooms/{roomId}`
- `GET /api/v1/sensors`
- `POST /api/v1/sensors`
- `GET /api/v1/sensors/{sensorId}`
- `GET /api/v1/sensors?type=Temperature`
- `GET /api/v1/sensors/{sensorId}/readings`
- `POST /api/v1/sensors/{sensorId}/readings`

## How to Build and Run
1. Open the project in NetBeans.
2. Make sure GlassFish Server is configured.
3. Clean and Build the project.
4. Run the project.
5. Access the API using:
   - `http://localhost:8080/AirlineAPII/api/v1/`

## Sample curl Commands

### 1. Get API discovery endpoint
```bash
curl -X GET http://localhost:8080/AirlineAPII/api/v1/

## project structure
the main project files are located inside "airlineapi3' folder 


