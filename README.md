
# 📘 UserBlogApplication_RestAPI

A secure, RESTful Spring Boot API that powers a multi-user blogging platform. It includes role-based access control, JWT authentication, user registration/login, and blog post CRUD operations.

---


## 🚀 Project Setup Instructions

This project is a Spring Boot REST API built using **Spring Tool Suite (STS)** and tested using **Postman**.

---

### 🧰 Prerequisites

Make sure you have the following installed:

* **Java JDK 17** (or 11)
* **Maven** (bundled with STS)
* **Spring Tool Suite (STS)** or **IntelliJ / Eclipse**
* **Postman** (for API testing)
* **Git** (to clone the repository)
* **MySQL/PostgreSQL** (or the database your app uses)

---

### 📁 Clone the Project

```bash
git clone https://github.com/shrugi/UserBlogApplication_RestAPI.git
cd UserBlogApplication_RestAPI

```

---

### ⚙️ Import Project into STS

1. Open **Spring Tool Suite**.
2. Go to `File > Import > Maven > Existing Maven Projects`.
3. Browse to the cloned project folder.
4. Select the project and click **Finish**.

---

### ⚙️ Configure Database (if needed)

1. Open `application.properties` or `application.yml`.
2. Update the following properties:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/your_db_name
spring.datasource.username=your_db_user
spring.datasource.password=your_db_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

3. Make sure the database exists in MySQL/PostgreSQL.

---

### ▶️ Run the Application

* In STS: Right-click the main class (with `@SpringBootApplication`) > `Run As` > `Spring Boot App`.
* OR via terminal:

```bash
./mvnw spring-boot:run
```

---

### 🔍 Testing the API with Postman

You can test the API endpoints using **Postman**.

#### Example Requests:

* **Register User**

```http
POST http://localhost:8080/public/register
Content-Type: application/json

{
    "username": "Shruti",
    "email": "shruti@gmail.com",
    "password": "2428aab",
    "roles": [
        {
            "name": "ROLE_USER"
        },
        {
            "name": "ROLE_ADMIN"
        }
    ]
}
```


### 🛠️ Common Commands

* **Clean & Build Project**

```bash
./mvnw clean install
```

* **Run Tests**

```bash
./mvnw test
```




---

## 📚 API Reference

---

### 👤 User Endpoints

#### 🔹 Register a New User

```http
POST /public/register
```

**Request Body (JSON):**

```json
{
  "username": "Shraddha",
  "email": "shraddha@gmail.com",
  "password": "2428aab",
  "roles": [
    { "name": "ROLE_USER" },
    { "name": "ROLE_ADMIN" }
  ]
}
```

---

#### 🔹 Get Logged-In User Info

```http
GET /users/self
```

> Requires **Basic Authentication**

---

#### 🔹 Update Logged-In User

```http
PUT /users/self
```

> Requires **Basic Authentication**

**Request Body (JSON):**

```json
{
  "username": "UpdatedName",
  "email": "shraddha@gmail.com",
  "password": "updatedpassword"
}
```

---

### ✍️ Blog Endpoints

#### 🔹 Create Blog with Image

```http
POST /blogs
```

> Requires **Basic Authentication**

**Form Data (multipart/form-data):**

| Key  | Type | Value                                         |
| ---- | ---- | --------------------------------------------- |
| file | File | (Upload an image file)                        |
| blog | Text | `{"title":"LOVE","description":"wallpaper."}` |

---

#### 🔹 Update Blog by ID

```http
PUT /blogs/{id}
```

> Requires **Basic Authentication**
> Example: `/blogs/1`

---

#### 🔹 Get All Blogs by User

```http
GET /blogs
```

> Requires **Basic Authentication**

---

#### 🔹 Delete Blog by ID

```http
DELETE /blogs/{id}
```

> Requires **Basic Authentication**
> Example: `/blogs/1`

---

### 💬 Comment Endpoints

#### 🔹 Add Comment to a Blog

```http
POST /comments/{blogId}
```

**Request Body (JSON):**

```json
{
  "commentText": "This is a great blog! I really liked the insights."
}
```

> Example: `/comments/2` (to comment on blog with ID 2)

---

#### 🔹 Get Comments on a Blog

```http
GET /comments/{blogId}
```

> Example: `/comments/2`

---

### 🛡️ Admin Endpoints

#### 🔹 Get All Users

```http
GET /admin/users
```

> Requires **Basic Authentication** with admin role

---

#### 🔹 Get User by Email

```http
GET /admin/users/email/{email}
```

> Example: `/admin/users/email/shruti@gmail.com`

---

#### 🔹 Delete User by Email

```http
DELETE /admin/users/email/{email}
```

> Example: `/admin/users/email/shruti@gmail.com`

---




## 🧠 Design Decisions & Assumptions

This section outlines the key design decisions and assumptions made during the development of this REST API.

---

### ✅ Design Decisions

1. **Spring Boot Framework**
   Chosen for its fast setup, embedded server, and integration with Spring Security, JPA, and RESTful services.

2. **Layered Architecture (MVC)**

   * **Controller** → Handles HTTP requests/responses
   * **Service** → Contains business logic
   * **Repository** → Communicates with the database
     Promotes separation of concerns and cleaner maintainability.

3. **Basic Authentication**
   Implemented secure stateless user authentication using **Basic Auth**, allowing users to log in and get access for protected endpoints.

4. **Exception Handling**
   Used `@ControllerAdvice` and custom exception classes to handle and return user-friendly error responses (400, 401, 404, etc.).

5. **Validation**
   Used `@Valid`, `@NotBlank`, `@Email`, and other Bean Validation annotations for input validation.

6. **API Status Codes**

   * `200 OK` for successful requests
   * `201 Created` for successful POST
   * `400 Bad Request` for validation errors
   * `401 Unauthorized` for authentication failures
   * `403 Forbidden` for access control
   * `404 Not Found` for missing resources

7. **Database Configuration**

   * Using **MySQL** with JPA and Hibernate.
   * Set `spring.jpa.hibernate.ddl-auto=update` during development.
   * Entity relationships (e.g., OneToMany, ManyToOne) defined based on logical data structure.

8. **Postman for Testing**
   All APIs were manually tested using Postman and organized into collections for easier testing and demo.

---

### 📌 Assumptions

1. **User roles** are predefined (e.g., `ROLE_USER`, `ROLE_ADMIN`) and assigned at registration or through admin privileges.
2. **Username and email are unique** per user to avoid duplicate entries.
3. The application runs locally on `http://localhost:8080`.
4. **Admin-level access** is required for operations like deleting other users or managing accounts globally. 
5. Project uses a **single-database instance** for simplicity; no multi-tenant or sharded DB setups.

---




## 🗃️ Database Schema

This project uses a **relational database** (like MySQL or PostgreSQL) and follows a normalized schema design using **JPA/Hibernate** in a Spring Boot application.

### 👤 `users`

| Column   | Type      | Description                 |
| -------- | --------- | --------------------------- |
| id       | Long      | Primary key, auto-generated |
| username | String    | Unique, not null            |
| email    | String    | Unique, not null            |
| password | String    | Encrypted                   |
| roles    | Set<Role> | Many-to-Many relationship   |

---

### 🛡️ `roles`

| Column | Type   | Description              |
| ------ | ------ | ------------------------ |
| id     | Long   | Primary key              |
| name   | String | ROLE\_USER, ROLE\_ADMIN… |

---

### 📄 `blogs`

| Column      | Type   | Description                         |
| ----------- | ------ | ----------------------------------- |
| id          | Long   | Primary key                         |
| title       | String | Blog title                          |
| description | String | Blog content                        |
| imageUrl    | String | Optional (file path if image added) |
| user        | User   | Many-to-One relationship (creator)  |

---

### 💬 `comments`

| Column      | Type   | Description                        |
| ----------- | ------ | ---------------------------------- |
| id          | Long   | Primary key                        |
| commentText | String | Comment content                    |
| blog        | Blog   | Many-to-One (each blog's comments) |
| user        | User   | Many-to-One (who posted comment)   |

---

### 🔗 Relationships Overview

* One **User** can have many **Blogs**
* One **Blog** can have many **Comments**
* One **User** can have many **Comments**
* One **User** can have multiple **Roles** (Many-to-Many)

---



## License

[MIT](https://choosealicense.com/licenses/mit/)

