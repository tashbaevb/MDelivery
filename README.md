# MDelivery

**MDelivery** is a RESTful web application designed to manage a delivery system. It supports two types of users: customers (`User`) and couriers (`Delivery`). Customers can place orders for product deliveries, while couriers can accept and manage these orders through various stages.

---

## Features

### 1. Authentication and Authorization
- Secure login and registration for both users and couriers.
- JWT-based authentication ensures secure access to API endpoints.
- Unified authentication endpoint for both roles.

### 2. User and Courier Profiles
- View personal profiles for users and couriers.
- Retrieve details such as email, name, surname, and phone number.

### 3. Order Management
#### For Customers:
- Create orders specifying:
  - Delivery destination (`toX`, `toY`).
  - Transport type (`WALK`, `BICYCLE`, `CAR`).
  - List of product IDs.
  - Distance in kilometers.
- Automatic calculation of delivery and total price.
- Validation of user balance before order creation.

#### For Couriers:
- Accept orders and update statuses:
  - `ACCEPTED`, `ONTHEWAY`, `COMPLETED`, `CANCELLED`.
- Manage delivery status from pickup to drop-off.

### 4. Reporting and Listing
- Retrieve all users and couriers.
- Fetch all orders with detailed information about:
  - Status (`PROCESSING`, `ACCEPTED`, etc.).
  - Associated products and prices.

---

## Technologies Used

- **Java 17+**: Core programming language.
- **Spring Boot**: Framework for building RESTful APIs.
- **Spring Security**: For role-based authentication and authorization.
- **Hibernate**: ORM for seamless database interactions.
- **PostgreSQL**: Relational database for storing user, order, and product data.
- **JWT**: Token-based authentication for secure session management.
- **Maven**: Build and dependency management.

---

## Installation and Setup

### Prerequisites
- **Java 17+** installed on your machine.
- **PostgreSQL** database running locally.
- **Maven** for building the project.

### Steps

#### 1. Clone the Repository
```bash
git clone https://github.com/your-username/mdelivery.git
cd mdelivery
