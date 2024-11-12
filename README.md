# Keycloak RBAC Setup with Spring Boot 3.3.5

![My Image](https://gauthier-cassany.com/images/post/spring-boot-keycloak-admin-api/thumbnail.png)

This guide provides steps for setting up Role-Based Access Control (RBAC) in Keycloak, building and running a Spring Boot 3.3.5 application, and testing APIs using `curl` commands. It includes
configuration for user and admin roles.

## Prerequisites
* Java 17 or higher
* Spring-boot 3.3.4 or higher
* Keycloak 20 or higher

## Keycloak Server Setup

### 1. Install and Start Keycloak

- Download and install Keycloak from the [official Keycloak website](https://www.keycloak.org/downloads).
- Start the Keycloak server:
  ```bash
  ./bin/kc.sh start-dev
  ```
- Open the Keycloak admin console at [http://localhost:8080/admin](http://localhost:8080/admin) and log in with your admin credentials.

### 2. Configure the Keycloak Realm and Client

1. **Create a Realm**:
    - In the admin console, go to the "Create Realm" section and name your realm, e.g., `my-app-realm`.

2. **Create a Client**:
    - Under your new realm, navigate to **Clients** and create a new client named `my-app-client`.
    - Set **Access Type** to `confidential`.
    
3. **Define Roles**:
    - Under **Roles**, create the following roles:
        - `user`
        - `admin`

4. **Create a Test User**:
    - Go to **Users** and add a new user with roles.
    - Assign **Roles** under the **Role Mappings** tab:
        - Assign the `user` role to a standard user.
        - Assign both `user` and `admin` roles to an admin user.

## Building and Running the Application

### 1. Build the Application with Gradle

To build the application, use the following Gradle command:

```bash
./gradlew build
```

This will compile the code and create a `.jar` file in the `build/libs` directory.

### 2. Run the Application

To run the application, use:

```bash
./gradlew bootRun
```

Alternatively, if running as a `.jar`, use:

```bash
java -jar build/libs/your-app-name.jar
```

## Testing Endpoints with `curl`

### Obtain an Access Token

To authenticate, first obtain a token for a test user:

```bash
curl -X POST "http://localhost:8080/realms/my-app-realm/protocol/openid-connect/token"   -H "Content-Type: application/x-www-form-urlencoded"   -d "grant_type=password"   -d "client_id=my-app-client"   -d "client_secret=YOUR_CLIENT_SECRET"   -d "username=YOUR_USERNAME"   -d "password=YOUR_PASSWORD"
```

Replace:

- `YOUR_CLIENT_SECRET` with the client secret from Keycloak.
- `YOUR_USERNAME` and `YOUR_PASSWORD` with the credentials of the user in Keycloak.

Copy the `access_token` from the response to use in the following API requests.

### API Testing

#### 1. Get All Students (Accessible by Both `user` and `admin` Roles)

```bash
curl -X GET "http://localhost:8080/api/v1/students"   -H "Authorization: Bearer YOUR_ACCESS_TOKEN"
```

#### 2. Get Student by ID (Accessible by `user` Role Only)

```bash
curl -X GET "http://localhost:8080/api/v1/students/{id}"   -H "Authorization: Bearer YOUR_ACCESS_TOKEN"
```

Replace `{id}` with the student ID you want to retrieve.

#### 3. Delete Student by ID (Accessible by `admin` Role Only)

```bash
curl -X DELETE "http://localhost:8080/api/v1/students/{id}"   -H "Authorization: Bearer YOUR_ACCESS_TOKEN"
```

Replace `{id}` with the student ID to delete.

---

With this setup, you should be able to test different API endpoints with role-based access controls configured in Keycloak and enforced in the Spring Boot application.