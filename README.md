# Food Delivery Service

A scalable food delivery service platform built with Java and Spring Boot, designed to handle large numbers of users and data with high availability.

## 🚀 Features

### ✅ Implemented
- **Authentication & Authorization**
  - JWT-based authentication
  - Role-based access control (CUSTOMER, COURIER, ADMIN)
  - User registration and login

- **Restaurant Management**
  - CRUD operations for restaurants
  - Public restaurant listing

- **Menu Management** 🆕
  - CRUD operations for menu items
  - Stock management with optimistic locking
  - Restaurant-specific menu listing
  - Price and inventory tracking

- **Order Management**
  - Order creation with menu items
  - Optimistic locking for stock management
  - Outbox pattern for reliable event processing
  - Asynchronous order processing

- **User Management**
  - User profiles and account management
  - Role-based permissions

### 🔄 In Progress
- Order status workflow
- Courier assignment and tracking
- Payment integration
- Real-time notifications

### 📋 Planned
- Search and filtering
- Performance optimization (Redis caching)
- Docker containerization
- CI/CD pipeline
- Monitoring and metrics

## 🛠 Technology Stack

- **Backend**: Java 17, Spring Boot 2.7.9
- **Database**: JPA/Hibernate with MySQL
- **Security**: Spring Security with JWT
- **Build Tool**: Gradle
- **Architecture**: Microservices-ready with outbox pattern

## 📚 API Documentation

### Menu Management API

#### Endpoints
| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/api/menus` | Create a new menu item | No |
| GET | `/api/menus/{id}` | Get menu by ID | No |
| GET | `/api/menus` | List all menus | No |
| GET | `/api/menus/restaurant/{restaurantId}` | List menus by restaurant | No |
| PUT | `/api/menus/{id}` | Update menu item | No |
| PUT | `/api/menus/{id}/stock` | Update stock quantity | No |
| DELETE | `/api/menus/{id}` | Delete menu item | No |

#### Example Requests

**Create Menu Item**
```json
POST /api/menus
{
  "restaurantId": 1,
  "name": "Margherita Pizza",
  "price": 12.99,
  "stock": 50
}
```

**Update Menu Item**
```json
PUT /api/menus/1
{
  "name": "Margherita Pizza (Large)",
  "price": 15.99,
  "stock": 30
}
```

**Update Stock**
```
PUT /api/menus/1/stock?stock=25
```

### Authentication API

#### Endpoints
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/auth/signup` | User registration |
| POST | `/api/auth/login` | User login |

### Restaurant API

#### Endpoints
| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/api/restaurants` | Create restaurant | No |
| GET | `/api/restaurants/{id}` | Get restaurant | No |
| GET | `/api/restaurants` | List restaurants | No |
| PUT | `/api/restaurants/{id}` | Update restaurant | No |
| DELETE | `/api/restaurants/{id}` | Delete restaurant | No |

### Order API

#### Endpoints
| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/api/orders` | Create order | Yes (CUSTOMER, ADMIN) |
| GET | `/api/orders/{id}` | Get order | Yes (CUSTOMER, ADMIN) |

## 🏗 Project Structure

```
delivery-service/
├── api/                    # Main API module
│   ├── src/main/java/org/delivery/api/
│   │   ├── auth/          # Authentication
│   │   ├── menu/          # Menu management 🆕
│   │   ├── order/         # Order management
│   │   ├── restaurant/    # Restaurant management
│   │   ├── account/       # User account management
│   │   └── security/      # Security configuration
│   └── src/test/          # Test files
├── db/                     # Database module
│   └── src/main/java/org/delivery/db/
│       ├── menu/          # Menu entities 🆕
│       ├── order/         # Order entities
│       ├── restaurant/    # Restaurant entities
│       └── user/          # User entities
└── build.gradle           # Build configuration
```

## 🚀 Getting Started

### Prerequisites
- Java 17+
- Gradle 7+
- MySQL 8+

### Running the Application
```bash
# Build the project
./gradlew build

# Run the application
./gradlew :api:bootRun
```

### Environment Configuration
Create `application.yaml` with your database configuration:
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/delivery_service
    username: your_username
    password: your_password
  jpa:
    hibernate:
      ddl-auto: update
```

## 🔧 Development

### Adding New Features
1. Create entity in `db` module
2. Create repository interface
3. Create service in `api` module
4. Create controller with REST endpoints
5. Add security configuration if needed
6. Write tests

### Code Style
- Follow Java naming conventions
- Use Lombok for boilerplate reduction
- Implement proper error handling
- Add validation annotations
- Use optimistic locking for concurrent operations

## 📈 Performance Considerations

- **Optimistic Locking**: Used for menu stock management
- **Outbox Pattern**: Ensures reliable event processing
- **Asynchronous Processing**: Order events processed asynchronously
- **Connection Pooling**: Configured for database connections

## 🔒 Security

- JWT-based authentication
- Role-based authorization
- Input validation
- SQL injection prevention via JPA
- CORS configuration

## 📝 License

This project is part of a learning exercise and is not intended for production use.

---

**Status**: Work in Progress (Expected completion: October 2024)
