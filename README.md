# MDelivery

**MDelivery** is a RESTful web application designed for managing a delivery system. The application supports two types of users: customers (`User`) and couriers (`Delivery`). Customers can place delivery orders, and couriers can accept orders, update their statuses, and complete deliveries.

---

## Features

### Authentication and Authorization
- User and courier registration and login.
- JWT-based authentication for secure API requests.
- Unified endpoint for authentication of both users and couriers.

### User Profile
- Retrieve personal profile information for users and couriers.

### Order Management
- Customers can:
  - Create delivery orders by selecting the transport type (`WALK`, `BICYCLE`, `CAR`) and specifying the distance.
  - Specify the list of products to deliver.
- Couriers can:
  - Accept orders.
  - Update order statuses (e.g., `ACCEPTED`, `ONTHEWAY`, `COMPLETED`).
- Balance control:
  - Balance is checked before creating an order.
  - Funds are deducted for orders, with a penalty for canceled orders.

### Reporting
- Retrieve lists of all users, couriers, and orders.

---

## Technologies

- **Java 17+**
- **Spring Boot**: For building REST APIs.
- **Spring Security**: For authentication and authorization.
- **PostgreSQL**: Database for persisting user and order information.
- **Hibernate**: ORM for database interactions.
- **JWT**: For secure session management.

---

## Installation and Setup

### Prerequisites
- **Java 17+**
- **PostgreSQL**
- **Maven**

### Steps

1. **Clone the Repository**
   ```bash
   git clone https://github.com/your-username/mdelivery.git
   cd mdelivery
```

```sql
CREATE DATABASE mdelivery;
```

```bash
mvn clean install
```

```bash
java -jar target/mdelivery-0.0.1-SNAPSHOT.jar
```
