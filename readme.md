# Prophius Assesment API

This is the implementation of the prophius assesment API. It is a simple API that allows for the creation of CRUD of users, posts and comments. It is built using Spring Boot and uses an in-memory H2 database.
## Prerequisites

Before you begin, ensure you have met the following requirements:

- Java Development Kit (JDK) 17 or higher: [Download](https://www.oracle.com/java/technologies/javase-downloads.html)
- Apache Maven: [Download](https://maven.apache.org/download.cgi) (Optional, if not using the included Maven wrapper)

## Getting Started

Follow these steps to set up and run the application:

1. **Clone the repository:**

    ```shell
    git clone https://github.com/OlamireDev/Prophius_Assessment.git
    ```

2. **Navigate to the project directory:**

    ```shell
    cd your-spring-boot-app
    ```

3. **Run the application:**

   If you have Apache Maven installed:

    ```shell
    mvn spring-boot:run
    ```

   If you are not using Maven, you can use the provided Maven wrapper:

    ```shell
    ./mvnw spring-boot:run
    ```

4. **Access the application:**

   Open your web browser and visit [http://localhost:8080](http://localhost:8080). You should see the home page of the application.

## Configuration

The application configuration can be found in the `src/main/resources/application.properties` file. You can customize the configuration according to your needs.

## Usage

The application consists of the endpoints listed below:
1. **User Endpoints(/api/v1/user)**
    - `POST /create` - Create a new user with a json request body:`{ "username":"Blanka",
      "email":"test1@gmail.com",
      "password":"hope",
      "profile":"http://link.com"
      }`
    - `GET /login` - Login as a user with a json request body:`{ "username":"Blanka",
      "password":"hope"
      }` this returns a token which is used to access other endpoints
    - `PUT /editUser` - (Secured)Update a user by with a json request body:`{
      "userName": "Cortana",
      "password": "hope2", "profilePicture": "http://link.com"
      }`
    - `PUT /follow/{id}` - (Secured) Follow a user by ID
    - `PUT /unfollow/{id}` - (Secured) Unfollow a user by ID
    - `DELETE /delete` - (Secured) Delete a user, the user to be deleted is gotten for the security context so no ID is required.
2. **Post Endpoints(/api/v1/posts)**
    - `POST /create` - (Secured) Create a new post with a json request body:`{
      "title": "My first post",
      "content": "This is my first post"
      }`
    - `GET /{id}` - (Secured)Get a post by ID
    - `GET /all/{id}` - (Secured)Get all posts associated with the user indicated by ID.
    - `GET /all/paged` -(Secured) Get a paged list of posts associated with the user indicated by ID in the json request body:`{
      "userId":"1","page": 0,
      "size": 10, "sorted": true, "parameter": "LIKE_ASC"
      }`
    - `PUT /update` - (Secured) Update a post by ID with a json request body:`{ "id": "1
      "title": "My edited first post",
      "content": "This is my first post after edit"
      }`
    - `DELETE /delete/{id}` - (Secured) Delete a post by ID
   
3. **Comment Endpoints(/api/v1/comments)**
   - `GET /{id}` - (Secured) Get a comment by ID
   - `GET /post/{postId}` - (Secured) Get all comments associated with the user indicated by ID.
   - `PUT /update` - (Secured) Update a comment by ID with a json request body:`{ "commentId": "1
      "content": "This is my first comment after edit"
      }`
   - `DELETE /delete/{id}` - (Secured) Delete a comment by ID

#### Please note that to test secure end points, you need to add the token generated from the login endpoint to the header of the request as an authorization token.

