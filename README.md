# Reverse Logistics Optimizer

## Smart Return Routing & Reverse Logistics Management Platform

Reverse Logistics Optimizer (RLO) is a full-stack platform designed to optimize the movement of returned products across collection, refurbishment, recycling, and warehouse facilities.

The system evaluates **distance, transportation cost, processing cost, facility compatibility, vehicle capacity, and utilization** to determine the lowest-cost feasible route for each return.

## Key Features

* Return lifecycle management
* Product and facility management
* Vehicle management
* Intelligent return-route optimization
* Facility compatibility checking
* Vehicle capacity validation
* Haversine-based distance calculation
* Transportation and processing cost calculation
* Facility utilization penalty
* Optimization result and route history
* REST APIs for backend operations
* React dashboard for monitoring logistics operations

## Tech Stack

* **Frontend:** React, Tailwind CSS
* **Backend:** Java, Spring Boot
* **Database:** MongoDB
* **Build Tool:** Maven
* **API:** REST API
* **Architecture:** Full-stack client-server architecture

## System Architecture

```text
                    ┌────────────────────┐
                    │   React Dashboard  │
                    └─────────┬──────────┘
                              │
                          REST APIs
                              │
                              ▼
                  ┌───────────────────────┐
                  │    Spring Boot API    │
                  └───────────┬───────────┘
                              │
               ┌──────────────┼──────────────┐
               ▼              ▼              ▼
         Return Service  Product Service  Facility Service
               │              │              │
               └──────────────┼──────────────┘
                              ▼
                     ┌──────────────────┐
                     │ Route Optimizer  │
                     └────────┬─────────┘
                              │
               ┌──────────────┼──────────────┐
               ▼              ▼              ▼
         Distance         Cost Engine    Compatibility
         Calculator       Calculator       Checker
               │              │              │
               └──────────────┼──────────────┘
                              ▼
                           MongoDB
```

## How Route Optimization Works

When a return is created, the optimizer evaluates available facilities and vehicles.

The route selection considers:

1. **Facility compatibility** with the product condition.
2. **Vehicle capacity** based on product weight.
3. **Distance** between the customer location and facility.
4. **Transportation cost** based on route distance.
5. **Processing cost** associated with the facility.
6. **Facility utilization** to avoid inefficiently loaded facilities.
7. The system selects the **lowest-cost feasible route**.

### Optimization Flow

```text
Return Created
      │
      ▼
Identify Product & Condition
      │
      ▼
Find Compatible Facilities
      │
      ▼
Check Facility Capacity
      │
      ▼
Check Vehicle Capacity
      │
      ▼
Calculate Distance
      │
      ▼
Calculate Total Cost
      │
      ▼
Apply Utilization Penalty
      │
      ▼
Select Feasible Lowest-Cost Route
      │
      ▼
Store Optimization Result
```

## Cost Model

The optimizer evaluates multiple factors instead of selecting a facility purely based on geographical distance.

Conceptually:

```text
Total Cost =
Transportation Cost
+ Processing Cost
+ Utilization Penalty
```

Distance is calculated using the **Haversine formula**, allowing geographical coordinates to be used for real-world distance estimation.

## Supported Product Conditions

```text
NEW
RESALABLE
REPAIRABLE
RECYCLABLE
DISPOSE
```

## Facility Types

```text
COLLECTION_CENTER
REFURBISHMENT_CENTER
RECYCLING_CENTER
WAREHOUSE
```

Facilities can maintain capacity and current-load information so that routing decisions consider available capacity.

## Return Lifecycle

```text
RECEIVED
   ↓
CREATED
   ↓
ASSIGNED
   ↓
IN_TRANSIT
   ↓
PROCESSED
   ↓
COMPLETED
```

## REST APIs

### Returns

```http
POST /api/returns
GET  /api/returns
```

Create and retrieve return records.

### Optimization

```http
POST /api/optimization/optimize/{returnId}
```

Runs the route optimization process for a return.

### Products

Product APIs manage product information such as:

```text
Name
Category
Weight
Value
```

### Facilities

Facility APIs manage:

```text
Name
Location
Latitude
Longitude
Type
Capacity
Current Load
```

### Vehicles

Vehicle APIs manage vehicle availability and capacity for transportation.

## Example Optimization

Example return:

```text
Product: Laptop
Condition: REPAIRABLE
Weight: 10 kg

Customer Location:
Chennai

Available Facility:
Chennai Refurbishment Hub

Facility Type:
REFURBISHMENT_CENTER
```

The optimizer verifies that the facility supports the `REPAIRABLE` condition and has sufficient capacity before calculating the route cost.

## Database

MongoDB is used for storing logistics entities.

Main collections include:

```text
returns
products
facilities
vehicles
```

Example return document:

```json
{
  "latitude": 13.0827,
  "longitude": 80.2707,
  "condition": "REPAIRABLE",
  "status": "CREATED"
}
```

## Frontend Dashboard

The React dashboard provides visibility into:

* Return lifecycle
* Route monitoring
* Optimization history
* Facility utilization
* Vehicle availability
* Return management

## Project Structure

```text
reverse-logistics-optimizer
│
├── src/main/java
│   └── com.rlo.reverselogisticsoptimizer
│       │
│       ├── controller
│       ├── service
│       ├── model
│       ├── repository
│       ├── optimizer
│       └── calculator
│
├── src/main/resources
│   └── application.properties
│
└── frontend
    └── React Dashboard
```

## Running the Project

### Prerequisites

* Java 21+
* Maven
* MongoDB
* Node.js
* npm

### Start MongoDB

Make sure MongoDB is running on:

```text
localhost:27017
```

### Start Backend

```bash
mvn spring-boot:run
```

Backend:

```text
http://localhost:8080
```

### Start Frontend

From the React frontend directory:

```bash
npm install
npm run dev
```

Frontend:

```text
http://localhost:5173
```

## Future Improvements

* Multi-stop route optimization
* Real-time vehicle tracking
* Advanced route optimization algorithms
* ETA prediction
* Analytics and reporting
* Authentication and role-based access
* Integration with mapping APIs
* Machine-learning-based return demand prediction

## Author

Sai Swaroop Moharana

## Repository

**Reverse Logistics Optimizer**

Built using React, Spring Boot, MongoDB, and Java.
