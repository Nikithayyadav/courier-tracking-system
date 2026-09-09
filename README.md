# Courier Tracking System
  
A backend REST API built with **Java and Spring Boot** for managing customers, staff, courier shipments, delivery agents, shipment tracking, delivery status, delivery charges, and tracking history.

The system follows a real-world courier/post-office counter workflow where staff create shipments by collecting sender, receiver, and package details. Customers can log in using their mobile number and OTP to view their shipment information.

---

## 📌 Project Overview

The Courier Tracking System is designed to simplify and manage the complete courier shipment lifecycle.

A staff member at the courier counter can:

- Register and manage customers
- Create courier shipments
- Automatically create customers when they do not already exist
- Reuse existing customers using their mobile number
- Generate unique tracking numbers
- Calculate delivery charges
- Assign delivery agents
- Update shipment status
- Cancel shipments
- Maintain shipment tracking history

Customers can:

- Log in using mobile number and OTP
- View their customer information
- View shipments they have sent
- View shipments they have received
- View their shipment history with pagination
- Track shipments using a tracking number

---

## ✨ Features

### 👤 Customer Management

- Customer registration
- Unique mobile number for each customer
- Customer information management
- OTP-based customer login
- View sent shipments
- View received shipments
- Paginated customer shipment history
- Customer deactivation instead of physical deletion

### 👨‍💼 Staff Management

- Staff registration
- Staff role management
- Admin role
- Delivery Agent role
- Staff active/inactive status
- Unique staff mobile number

### 📦 Shipment Management

- Create shipment
- Sender and receiver management
- Automatic customer creation
- Existing customer reuse using mobile number
- Unique tracking number generation
- Domestic and International package types
- Weight management
- Distance management
- Automatic delivery charge calculation
- Delivery agent assignment
- Shipment status updates
- Shipment cancellation
- Delivery timestamp tracking

### 🚚 Shipment Tracking

- Track shipment using tracking number
- View current shipment status
- View complete tracking history
- Store tracking location
- Store tracking remarks
- Maintain status history for every shipment

### 📄 Pagination

Pagination is implemented for list-based GET APIs where the amount of data can grow.

Paginated APIs include:

- Customer shipment history
- Shipment tracking/delivery history

The individual shipment tracking API is not paginated because it represents one shipment and returns its complete journey.

### ✅ Validation

The application uses Jakarta Bean Validation for request validation.

Examples include:

- Required customer fields
- Required sender and receiver details
- Required package type
- Positive weight
- Positive distance
- Required shipment status
- Required tracking location

### 🔄 Common API Response

All APIs use a common response wrapper:

```json
{
  "success": true,
  "data": {},
  "error": null,
  "meta": null
}
```

---

# 🛠️ Technology Stack

| Technology | Purpose |
|---|---|
| Java 17 | Programming Language |
| Spring Boot | Backend Framework |
| Spring Web | REST API Development |
| Spring Data JPA | Database Access |
| Hibernate | ORM |
| MySQL | Database |
| Lombok | Boilerplate Code Reduction |
| Jakarta Validation | Request Validation |
| Swagger / OpenAPI | API Documentation and Testing |
| Maven | Build and Dependency Management |
| Git | Version Control |
| GitHub | Source Code Management |

---

# 🏗️ Architecture

The project follows a layered backend architecture:

```text
Client
   │
   ▼
Controller Layer
   │
   ▼
Service Layer
   │
   ▼
Repository Layer
   │
   ▼
MySQL Database
```

### Controller Layer

Responsible for:

- Receiving HTTP requests
- Request validation
- Calling service methods
- Returning API responses

### Service Layer

Contains the application's business logic.

Examples:

- Customer creation
- OTP generation and verification
- Shipment creation
- Customer lookup
- Delivery charge calculation
- Shipment status management
- Delivery agent assignment
- Shipment cancellation
- Shipment tracking

### Repository Layer

Responsible for communication with the database using Spring Data JPA.

### Entity Layer

Represents the database tables and their relationships.

---

# 📂 Project Structure

```text
courier-tracking-system
│
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com.couriertracking
│   │   │       │
│   │   │       ├── ApiResponse.java
│   │   │       │
│   │   │       ├── customer
│   │   │       │   ├── Customer.java
│   │   │       │   ├── CustomerRepository.java
│   │   │       │   ├── CustomerService.java
│   │   │       │   ├── CustomerController.java
│   │   │       │   ├── CustomerRequest.java
│   │   │       │   ├── CustomerResponse.java
│   │   │       │   ├── CustomerLoginRequest.java
│   │   │       │   ├── CustomerLoginResponse.java
│   │   │       │   ├── CustomerShipmentResponse.java
│   │   │       │   └── VerifyOtpRequest.java
│   │   │       │
│   │   │       ├── staff
│   │   │       │   ├── Staff.java
│   │   │       │   ├── StaffRole.java
│   │   │       │   ├── StaffRepository.java
│   │   │       │   ├── StaffService.java
│   │   │       │   ├── StaffController.java
│   │   │       │   ├── StaffRequest.java
│   │   │       │   └── StaffResponse.java
│   │   │       │
│   │   │       └── shipment
│   │   │           ├── Shipment.java
│   │   │           ├── ShipmentStatus.java
│   │   │           ├── PackageType.java
│   │   │           ├── ShipmentRepository.java
│   │   │           ├── ShipmentService.java
│   │   │           ├── ShipmentController.java
│   │   │           ├── CreateShipmentRequest.java
│   │   │           ├── ShipmentResponse.java
│   │   │           ├── ShipmentTrackingResponse.java
│   │   │           ├── TrackingHistory.java
│   │   │           ├── TrackingHistoryRepository.java
│   │   │           ├── TrackingHistoryResponse.java
│   │   │           ├── UpdateShipmentStatusRequest.java
│   │   │           └── AssignDeliveryAgentRequest.java
│   │   │
│   │   └── resources
│   │       └── application.yml
│   │
│   └── test
│
├── pom.xml
└── README.md
```

---

# 🗄️ Database Design

The application uses **MySQL** as the relational database.

The main entities are:

- Customer
- Staff
- Shipment
- TrackingHistory

---

## 👤 Customer Entity

The `Customer` entity stores customer information.

```text
Customer
----------------
id
name
mobile
address
active
createdAt
updatedAt
```

### Important Design

The mobile number is unique and is the main business identifier for a customer.

The same customer can act as:

- Sender in one shipment
- Receiver in another shipment

This avoids creating separate Sender and Receiver entities.

---

# 👨‍💼 Staff Entity

The `Staff` entity represents employees who operate the courier system.

```text
Staff
----------------
id
name
mobile
role
address
active
createdAt
updatedAt
```

### Staff Roles

```text
ADMIN
DELIVERY_AGENT
```

Both Admin and Delivery Agent are represented using the same `Staff` entity with different roles.

---

# 📦 Shipment Entity

The `Shipment` entity is the central entity of the application.

```text
Shipment
----------------
id
trackingNumber
sender
receiver
packageType
weight
distance
deliveryCharge
status
deliveryAgent
createdAt
updatedAt
deliveredAt
```

A shipment contains:

- Sender
- Receiver
- Package type
- Weight
- Distance
- Delivery charge
- Current status
- Assigned delivery agent
- Creation/update timestamps
- Delivery timestamp

---

# 📍 TrackingHistory Entity

`TrackingHistory` stores every status update made for a shipment.

```text
TrackingHistory
----------------
id
shipment
status
location
remarks
createdAt
```

For example:

```text
BOOKED
   ↓
PICKED_UP
   ↓
IN_TRANSIT
   ↓
OUT_FOR_DELIVERY
   ↓
DELIVERED
```

Each status update creates a new tracking history record.

---

# 🔗 Entity Relationships

```text
                   ┌───────────────┐
                   │   Customer    │
                   └───────┬───────┘
                           │
                    ┌──────┴──────┐
                    │             │
                  sender       receiver
                    │             │
                    └──────┬──────┘
                           │
                    ┌──────▼───────┐
                    │   Shipment   │
                    └──────┬───────┘
                           │
             ┌─────────────┼──────────────┐
             │             │              │
             ▼             ▼              ▼
        Delivery       Tracking        Status
         Agent          History
             │             │
             ▼             ▼
          Staff      TrackingHistory
```

---

# 🔄 Shipment Lifecycle

The normal shipment lifecycle is:

```text
BOOKED
   ↓
PICKED_UP
   ↓
IN_TRANSIT
   ↓
OUT_FOR_DELIVERY
   ↓
DELIVERED
```

Available shipment statuses:

```text
BOOKED
PICKED_UP
IN_TRANSIT
OUT_FOR_DELIVERY
DELIVERED
CANCELLED
```

Cancellation is handled as a separate business operation.

---

# 📦 Package Types

The application supports two package types:

```text
DOMESTIC
INTERNATIONAL
```

---

# 👥 Customer Workflow

The system follows a real courier counter workflow.

```text
Customer visits courier counter
             ↓
Provides sender details
             ↓
Provides receiver details
             ↓
Provides package details
             ↓
Staff creates shipment
             ↓
Check sender mobile number
             ↓
Customer exists?
        ┌────┴────┐
       Yes        No
        ↓          ↓
      Reuse      Create
        └────┬─────┘
             ↓
Check receiver mobile number
             ↓
Customer exists?
        ┌────┴────┐
       Yes        No
        ↓          ↓
      Reuse      Create
        └────┬─────┘
             ↓
Generate Tracking Number
             ↓
Calculate Delivery Charge
             ↓
Create Shipment
             ↓
Set Status = BOOKED
             ↓
Create Tracking History
```

---

# 🔐 Customer OTP Login

Customer authentication is implemented using mobile number and OTP.

There is no password-based customer login.

### Login Flow

```text
Customer enters mobile number
            ↓
System checks customer
            ↓
Generate OTP
            ↓
OTP verification
            ↓
Customer authenticated
            ↓
Return customer information
            ↓
Return sent shipments
            ↓
Return received shipments
```

### Generate OTP

```http
POST /api/customers/login
```

### Verify OTP

```http
POST /api/customers/verify-otp
```

The current implementation stores OTPs temporarily in application memory for development purposes.

---

# 🔢 Tracking Number Generation

Every shipment receives a unique tracking number.

Example:

```text
CT-0C73DB55
```

The tracking number is generated using a UUID-based approach.

Customers can use this tracking number to track their shipment.

---

# 💰 Delivery Charge Calculation

Delivery charges are calculated using:

- Package type
- Weight
- Distance

For domestic shipments, the application uses configured weight and distance slabs.

Distance categories include:

```text
0 - 100 km
101 - 500 km
501 - 1000 km
Above 1000 km
```

The calculation logic is implemented in the shipment service.

---

# 🚚 Delivery Agent Assignment

A shipment can be assigned to a staff member whose role is:

```text
DELIVERY_AGENT
```

The system validates the staff role before assigning the agent to the shipment.

```text
Shipment
    │
    ▼
Assign Delivery Agent
    │
    ▼
Check Staff
    │
    ▼
Role = DELIVERY_AGENT
    │
    ▼
Assign Agent
```

---

# 📍 Shipment Tracking

Shipment tracking is performed using the unique tracking number.

```http
GET /api/shipments/track/{trackingNumber}
```

The response contains:

- Shipment information
- Sender
- Receiver
- Current status
- Delivery agent
- Delivery charge
- Tracking history

The tracking history provides the complete shipment journey.

Tracking by tracking number is **not paginated**, because one tracking number identifies a single shipment.

---

# 📜 Delivery History

The shipment history API provides the tracking history of a shipment.

```http
GET /api/shipments/{shipmentId}/history?page=0&size=10
```

Pagination is supported because tracking history is a list that can grow over time.

---

# 📄 Customer Shipment History

Customers can view both sent and received shipments using:

```http
GET /api/customers/{customerId}/shipments?page=0&size=10
```

The API supports pagination.

The response uses `CustomerShipmentResponse` so that unnecessary internal shipment information is not exposed.

For sent shipments, the other party is the receiver.

For received shipments, the other party is the sender.

---

# 🗑️ Delete / Deactivation

The system provides delete options while preserving important historical data.

### Customer

```http
DELETE /api/customers/{customerId}
```

Customer records are deactivated using:

```text
active = false
```

instead of physically deleting the database record.

This helps preserve shipment history and existing relationships.

---

# 🌐 REST API Endpoints

## Customer APIs

### 1. Register Customer

```http
POST /api/customers
```

Example request:

```json
{
  "name": "Nikitha",
  "mobile": "8522952603",
  "address": "Hyderabad"
}
```

---

### 2. Generate OTP

```http
POST /api/customers/login
```

Example request:

```json
{
  "mobile": "8522952603"
}
```

---

### 3. Verify OTP

```http
POST /api/customers/verify-otp
```

Example request:

```json
{
  "mobile": "8522952603",
  "otp": "123456"
}
```

The response contains:

- Customer details
- Sent shipments
- Received shipments

---

### 4. Get Customer Shipment History

```http
GET /api/customers/{customerId}/shipments?page=0&size=10
```

Returns paginated sent and received shipments.

---

### 5. Deactivate Customer

```http
DELETE /api/customers/{customerId}
```

---

# Staff APIs

### 1. Create Staff

```http
POST /api/staff
```

Example request:

```json
{
  "name": "Rahul",
  "mobile": "9876543210",
  "role": "DELIVERY_AGENT",
  "address": "Hyderabad"
}
```

Supported roles:

```text
ADMIN
DELIVERY_AGENT
```

---

# Shipment APIs

### 1. Create Shipment

```http
POST /api/shipments
```

Example request:

```json
{
  "senderName": "Nikitha",
  "senderMobile": "8522952603",
  "senderAddress": "Hyderabad",
  "receiverName": "Pavani",
  "receiverMobile": "7386900222",
  "receiverAddress": "Adilabad",
  "packageType": "DOMESTIC",
  "weight": 5,
  "distance": 98
}
```

The system:

1. Checks whether the sender exists
2. Creates the sender if required
3. Checks whether the receiver exists
4. Creates the receiver if required
5. Generates a tracking number
6. Calculates delivery charge
7. Creates the shipment
8. Sets status to `BOOKED`
9. Creates the initial tracking history

---

### 2. Update Shipment Status

```http
PUT /api/shipments/{shipmentId}/status
```

Example request:

```json
{
  "status": "IN_TRANSIT",
  "location": "Hyderabad Sorting Center",
  "remarks": "Shipment is in transit"
}
```

Every status update creates a tracking history record.

When the status becomes `DELIVERED`, the system records `deliveredAt`.

---

### 3. Track Shipment

```http
GET /api/shipments/track/{trackingNumber}
```

Example:

```http
GET /api/shipments/track/CT-0C73DB55
```

---

### 4. Assign Delivery Agent

```http
PUT /api/shipments/{shipmentId}/assign-agent
```

Example request:

```json
{
  "deliveryAgentId": "9128dc44-3938-4c44-a0fb-cbbe9e5dc800"
}
```

---

### 5. Cancel Shipment

```http
PUT /api/shipments/{shipmentId}/cancel
```

Cancellation creates a tracking history record with the `CANCELLED` status.

---

### 6. Get Delivery History

```http
GET /api/shipments/{shipmentId}/history?page=0&size=10
```

Returns paginated tracking history.

---

# 📋 API Summary

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/customers` | Register customer |
| POST | `/api/customers/login` | Generate customer OTP |
| POST | `/api/customers/verify-otp` | Verify OTP and login |
| GET | `/api/customers/{customerId}/shipments` | Get paginated customer shipments |
| DELETE | `/api/customers/{customerId}` | Deactivate customer |
| POST | `/api/staff` | Create staff |
| POST | `/api/shipments` | Create shipment |
| PUT | `/api/shipments/{shipmentId}/status` | Update shipment status |
| GET | `/api/shipments/track/{trackingNumber}` | Track shipment |
| PUT | `/api/shipments/{shipmentId}/assign-agent` | Assign delivery agent |
| PUT | `/api/shipments/{shipmentId}/cancel` | Cancel shipment |
| GET | `/api/shipments/{shipmentId}/history` | Get paginated tracking history |

---

# ✅ Request Validation

The application uses Jakarta Bean Validation.

## Customer Validation

```text
Name is required
Mobile is required
Address is required
```

## Shipment Validation

```text
Sender name is required
Sender mobile is required
Sender address is required

Receiver name is required
Receiver mobile is required
Receiver address is required

Package type is required
Weight must be greater than zero
Distance must be greater than zero
```

## Status Update Validation

```text
Status is required
Location is required
```

---

# 📦 API Response Format

The project uses a generic `ApiResponse<T>` class.

### Successful Response

```json
{
  "success": true,
  "data": {},
  "error": null,
  "meta": null
}
```

### Error Response

```json
{
  "success": false,
  "data": null,
  "error": "Shipment not found",
  "meta": null
}
```

This provides a consistent response structure across the application.

---

# 🗃️ MySQL Configuration

Create the database:

```sql
CREATE DATABASE courier_tracking_db;
```

Configure the database in:

```text
src/main/resources/application.yml
```

Example:

```yaml
spring:
  application:
    name: courier-tracking-system

  datasource:
    url: jdbc:mysql://localhost:3306/courier_tracking_db
    username: root
    password: YOUR_MYSQL_PASSWORD

  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        format_sql: true

server:
  port: 8080
```

Replace:

```text
YOUR_MYSQL_PASSWORD
```

with your local MySQL password.

---

# 🚀 Getting Started

## Prerequisites

Install the following:

- Java 17 or later
- Maven
- MySQL
- IntelliJ IDEA, Eclipse, or VS Code
- Git

---

## Clone the Repository

```bash
git clone https://github.com/Nikithayyadav/courier-tracking-system.git
```

Navigate to the project:

```bash
cd courier-tracking-system
```

---

## Create the Database

Open MySQL and run:

```sql
CREATE DATABASE courier_tracking_db;
```

---

## Configure MySQL

Update the following file:

```text
src/main/resources/application.yml
```

Set your:

- Database URL
- Username
- Password

---

## Build the Project

Using Maven:

```bash
mvn clean install
```

---

## Run the Application

```bash
mvn spring-boot:run
```

The application runs on:

```text
http://localhost:8080
```

---

# 📚 Swagger API Documentation

Swagger/OpenAPI is integrated for API documentation and testing.

After starting the application, open:

```text
http://localhost:8080/swagger-ui/index.html
```

From Swagger UI, you can:

- View all endpoints
- View request models
- Enter request data
- Execute APIs
- View responses
- Test validation
- Test pagination
- Test shipment tracking

---

# 🧪 Testing Workflow

A recommended testing flow is:

```text
1. Create Staff
       ↓
2. Create Delivery Agent
       ↓
3. Create Customer
       ↓
4. Create Shipment
       ↓
5. Generate Tracking Number
       ↓
6. Assign Delivery Agent
       ↓
7. Update Shipment Status
       ↓
8. Track Shipment
       ↓
9. View Delivery History
       ↓
10. Test Customer OTP Login
       ↓
11. View Customer Sent/Received Shipments
       ↓
12. Test Customer Shipment Pagination
       ↓
13. Test Customer Deactivation
```

---

# 🔍 Example Shipment Flow

Example:

A customer named Nikitha wants to send a package from Hyderabad to Adilabad.

### Step 1 — Shipment Request

```json
{
  "senderName": "Nikitha",
  "senderMobile": "8522952603",
  "senderAddress": "Hyderabad",
  "receiverName": "Pavani",
  "receiverMobile": "7386900222",
  "receiverAddress": "Adilabad",
  "packageType": "DOMESTIC",
  "weight": 5,
  "distance": 98
}
```

### Step 2 — System Creates Shipment

The system generates a tracking number such as:

```text
CT-0C73DB55
```

### Step 3 — Initial Status

```text
BOOKED
```

### Step 4 — Tracking History

The first history entry is created:

```text
Status: BOOKED
Location: Hyderabad
Remarks: Shipment booked
```

### Step 5 — Agent Assignment

A delivery agent is assigned.

### Step 6 — Status Updates

```text
BOOKED
   ↓
PICKED_UP
   ↓
IN_TRANSIT
   ↓
OUT_FOR_DELIVERY
   ↓
DELIVERED
```

### Step 7 — Customer Tracking

The customer can use:

```text
CT-0C73DB55
```

to retrieve the shipment and its complete tracking history.

---

# 🧠 Key Design Decisions

## 1. Single Customer Entity

Sender and receiver are not separate entities.

Instead, both are represented using the `Customer` entity.

This allows the same person to send and receive shipments.

---

## 2. Mobile Number as Business Identifier

Customer mobile numbers are unique.

During shipment creation, the system checks the mobile number.

```text
Mobile exists?
    │
 ┌──┴──┐
Yes    No
 │      │
Reuse  Create
```

This prevents unnecessary duplicate customers.

---

## 3. Shipment as the Central Entity

Shipment connects:

- Sender
- Receiver
- Delivery Agent
- Package details
- Delivery charge
- Current status
- Tracking history

---

## 4. Tracking History as a Separate Entity

Instead of overwriting previous shipment statuses, every status change is stored.

For example:

```text
Shipment
   │
   ├── BOOKED
   ├── PICKED_UP
   ├── IN_TRANSIT
   ├── OUT_FOR_DELIVERY
   └── DELIVERED
```

This makes the complete shipment journey available.

---

## 5. Cancellation as a Separate Operation

Cancellation is treated as a separate business operation:

```http
PUT /api/shipments/{shipmentId}/cancel
```

Normal shipment progression is handled by:

```http
PUT /api/shipments/{shipmentId}/status
```

This keeps the business operations clearly separated.

---

## 6. Pagination

Pagination is used for APIs that return potentially large lists.

For example:

```text
?page=0&size=10
```

means:

- Page number = 0
- Number of records = 10

Tracking a single shipment does not use pagination because the user expects the complete journey of that shipment.

---

## 7. Soft Deactivation

Customers are deactivated instead of physically deleted because shipments may already reference them.

This helps preserve historical shipment information.

---

# 🔮 Future Enhancements

The current project implements the core courier management workflow. It can be further enhanced with:

- Real SMS OTP integration
- JWT authentication
- Role-based authorization
- Admin dashboard
- Delivery agent dashboard
- Customer frontend
- Real-time shipment tracking
- Email notifications
- SMS shipment notifications
- Online payment integration
- Advanced shipment search and filtering
- Configurable delivery charge rules
- Docker containerization
- Cloud deployment
- Unit testing
- Integration testing
- CI/CD pipeline
- Production-grade OTP expiry and rate limiting

---

# 🎓 Learning Outcomes

This project provided practical experience with:

- Java
- Spring Boot
- REST API development
- Spring Data JPA
- Hibernate
- MySQL
- Entity relationships
- DTO pattern
- Layered architecture
- UUID-based identifiers
- Enum-based business states
- Jakarta Bean Validation
- Pagination
- OTP authentication flow
- API response standardization
- Swagger/OpenAPI
- Git
- GitHub
- Backend application development

---

# 📌 Project Status

**Completed**

The Courier Tracking System implements the core courier management workflow, including:

- Customer registration
- OTP-based customer login
- Customer shipment history
- Staff management
- Delivery agent management
- Shipment creation
- Automatic customer creation/reuse
- Tracking number generation
- Delivery charge calculation
- Shipment status management
- Shipment cancellation
- Delivery agent assignment
- Shipment tracking
- Tracking history
- Pagination
- Request validation
- Customer deactivation
- Swagger API documentation

---

# 👩‍💻 Author

**Nikitha**

Computer Science and Engineering – Artificial Intelligence

GitHub:  
https://github.com/Nikithayyadav

Project Repository:  
https://github.com/Nikithayyadav/courier-tracking-system

---

# 📄 License

This project was developed for educational and project demonstration purposes.
