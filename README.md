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
```

### 2. Create a room

```bash
curl -X POST http://localhost:8080/AirlineAPII/api/v1/rooms \
-H "Content-Type: application/json" \
-d '{"id":"LIB-301","name":"Library Quiet Study","capacity":40}'
```

### 3. Get all rooms

```bash
curl -X GET http://localhost:8080/AirlineAPII/api/v1/rooms
```

### 4. Create a sensor

```bash
curl -X POST http://localhost:8080/AirlineAPII/api/v1/sensors \
-H "Content-Type: application/json" \
-d '{"id":"TEMP-001","type":"Temperature","status":"ACTIVE","currentValue":21.5,"roomId":"LIB-301"}'
```

### 5. Filter sensors by type

```bash
curl -X GET "http://localhost:8080/AirlineAPII/api/v1/sensors?type=Temperature"
```



## Design Decisions

The API follows RESTful principles by using resource-based endpoints for rooms, sensors, and readings.

Sensors are linked to rooms using a `roomId` field, ensuring referential integrity. Before a sensor is created, the system checks that the referenced room exists.

The sub-resource locator pattern is used for sensor readings, allowing nested routes such as:

```
/sensors/{sensorId}/readings
```

This keeps the design modular and easier to maintain.


## Exception Handling

Custom exceptions are used to handle different error cases:

* **409 Conflict** → when attempting to delete a room that still contains sensors
* **422 Unprocessable Entity** → when a sensor references a non-existent room
* **403 Forbidden** → when a sensor in maintenance cannot accept readings
* **500 Internal Server Error** → handled by a global exception mapper to prevent leaking stack traces

Exception mappers convert these exceptions into structured JSON responses with appropriate HTTP status codes.



## Notes

The application uses in-memory data structures (`HashMap`, `ArrayList`) instead of a database, as required by the coursework specification.
This means all data is reset when the server is restarted.

The main project files are located inside the `AirlineAPI 3` folder.


### 1. Get API discovery endpoint
```bash
curl -X GET http://localhost:8080/AirlineAPII/api/v1/

## project structure
the main project files are located inside "airlineapi3' folder 

### Create a Room
curl -X POST http://localhost:8080/AirlineAPII/api/v1/rooms \
-H "Content-Type: application/json" \
-d '{"id":"LIB-301","name":"Library","capacity":40}'

### Get all Rooms
curl -X GET http://localhost:8080/AirlineAPII/api/v1/rooms

### Create a Sensor
curl -X POST http://localhost:8080/AirlineAPII/api/v1/sensors \
-H "Content-Type: application/json" \
-d '{"id":"TEMP-001","type":"Temperature","status":"ACTIVE","currentValue":21.5,"roomId":"LIB-301"}'

