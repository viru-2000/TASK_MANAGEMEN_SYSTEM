# Task Management System

## Overview

The Task Management System is a Java-based application designed for managing tasks and client information. It uses JDBC for database operations and provides functionality for adding, updating, deleting, and retrieving both tasks and client details.

## Features

### Client Management
- **Add Client:** Insert new client records with name and email.
- **Update Client:** Modify existing client information.
- **Delete Client:** Remove client records based on client ID.
- **Retrieve All Clients:** Fetch a list of all clients.
- **Retrieve Client by ID:** Fetch a specific client by their ID.

### Task Management
- **Add Task:** Insert new tasks with details such as task name, project ID, assigned user, and status.
- **Update Task Status:** Change the status of a task.
- **Retrieve Tasks by User ID:** Get a list of tasks assigned to a specific user.
- **Retrieve Task by ID:** Fetch a specific task by its ID.

## Getting Started

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- A relational database system (e.g., MySQL, PostgreSQL)
- JDBC driver for your database
- Maven (optional, for dependency management)

### Installation

1. **Clone the repository:**

   ```bash
   git clone : https://github.com/viru-2000/TASK_MANAGEMEN_SYSTEM/tree/main
   cd task-management-system

2. **Configure the database:**

- Ensure you have a relational database setup.
- Create the necessary tables (clients and tasks).
- Update the database configuration in DBConnection class to match your database credentials.

3. **Build the project:**

If using Maven, run:
-  **mvn clean install**

4. **Run the application:**

- Compile and run the Java application using your IDE or command line.

**Usage**
1. Client Operations:
-Use the ClientService class for CRUD operations on client data.

2. Task Operations:
- Use the TaskService class for CRUD operations on task data.

**Example**
- Here is an example of how to add a new client:
  
   Client client = new Client();
   client.setClientName("John Doe");
   client.setClientEmail("john.doe@example.com");
   ClientService clientService = new ClientService();
   clientService.addClient(client);

**Contributing**
1.Fork the repository.
2.Create a new branch (git checkout -b feature-branch).
3.Commit your changes (git commit -am 'Add new feature').
4.Push to the branch (git push origin feature-branch).
5.Create a new Pull Request.

**Acknowledgments**
-JDBC for database connectivity.
-Java for building the application.
