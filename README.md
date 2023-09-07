# TEnmo
Tech Elevator, Module 2 Capstone

**TEnmo** is a simple money transfer application that allows users to send and request money from their friends. 
This project demonstrates the server-side functionality for a basic banking app. 
The application is implemented in Java and uses a PostgreSQL database to store user accounts, transfers, and user data.

## Table of Contents

- [Features](#features)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
- [Database](#database)
- [Project Structure](#project-structure)
- [Endpoints](#endpoints)
- [Usage](#usage)

## Features

TEnmo offers the following features:

1. **Register and Login:** Users can create an account and log in securely.
2. **View Balance:** Users can view their current account balance.
3. **Send Money:** Users can send money to other registered users.
4. **View Transfer History:** Users can view their past transfer history.
5. **Request Money:** Users can request money from other registered users.
6. **View Pending Requests:** Users can view pending money transfer requests.

This project focused on the server-side implementation of features 1 - 4.

## Technologies Used

- Java
- Spring Boot
- PostgreSQL
- JDBC (Java Database Connectivity)
- RESTful API
- Git and GitHub

## Getting Started

To run TEnmo on your local machine, follow these steps:

1. Clone this repository to your local environment:

   ```bash
   git clone https://github.com/nuhasiddiqui/TEnmo.git
2. Configure the database
3. Open the project in your favorite Java development environment (e.g., IntelliJ IDEA, Ecliopse)
4. Build and run the application

## Database
TEnmo uses a PostgreSQL database to store user accounts and transfer history. The database schema is provided in the database folder.
Create a new Postgres database called `tenmo`. Run the `database/tenmo.sql` script in pgAdmin to set up the database.
- **module-2-capstone-team-4/capstone/database**: Contains application properties and SQL scripts.
  - `tenmo.sql`: Database schema

## Project Structure

The project is organized into two main components: the Tenmo Server and the Tenmo Client.

### Tenmo Server

The server-side of the application is hosted within the Tenmo Server folder:

- **src/main/java**: Contains the Java source code.
  - `com.techelevator.tenmo.controller`: Controllers for handling HTTP requests.
  - `com.techelevator.tenmo.datasource`: Data source and database access.
    - `dao`: Data access objects for interacting with the database.
    - `jdbcdao`: JDBC implementation of data access objects.
    - `model`: Data models.
  - `com.techelevator.tenmo.model`: Data models.
  - `com.techelevator.tenmo.exception`: Custom exception classes.
  - `com.techelevator.tenmo.javasecurity`: Security configurations (if applicable).
  - `TenmoApplicationServer.java`: The main application class to run the server.


### Tenmo Client

The client-side of the application is hosted within the Tenmo Client folder:

- **src/main/java**: Contains the Java source code.
  - `com.techelevator.tenmo.services`: Business logic and services.
  - `com.techelevator.tenmo.model`: Data models for the client-side.
  - `com.techelevator.tenmo.exception`: Custom exception classes for the client-side.
  - `com.techelevator.tenmo.javasecurity.model`: Security-related models for the client-side.
  - `App.java`: Main application class to run the client.

### Running the Application

To run the application, start the Tenmo Server by running `TenmoApplication.java` within the Tenmo Server folder. Ensure the Postgres database 'tenmo' is set up and configured.

Next, run the client-side of the application by executing `App.java` within the Tenmo Client folder. The client will connect to the running server to provide the user interface for interacting with the application.

Make sure to follow any specific instructions in the README for setting up and configuring the application.


## Endpoints

The server-side functionality is exposed through the following API endpoints:

- **POST /account**: Create a new account.
- **GET /account/{accountId}**: Retrieve an account by ID.
- **GET /account**: Retrieve all accounts or accounts for a specific user.
- **GET /user**: Retrieve a list of all users.
- **PUT /account**: Update an account's balance.
- **POST /transfer**: Create a money transfer.
- **GET /transfer**: Retrieve transfers for a specific user.

## Usage

### Register and Login

- **Register**: Users can create an account by providing a unique username and password.

- **Login**: After registering, users can log in using their registered username and password.

### View Balance

- **View Current Balance**: Once logged in, users can view their current account balance, which displays the available funds.

### Send Money

- **Send Money**: Users can initiate money transfers to other users. They can select another user from the list and specify the amount they want to send. The transfer will deduct the specified amount from the sender's account and credit it to the recipient's account.

Please follow the on-screen prompts and menu options to perform these actions within the application.

For more details on using specific features or functionalities, consult the accompanying documentation.
