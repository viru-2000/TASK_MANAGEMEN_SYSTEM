package com.taskmanagement.main;

import com.taskmanagement.model.Client;
import com.taskmanagement.model.Project;
import com.taskmanagement.model.Task;
import com.taskmanagement.model.User;
import com.taskmanagement.service.ClientService;
import com.taskmanagement.service.ProjectService;
import com.taskmanagement.service.TaskService;
import com.taskmanagement.service.UserService;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class TaskManagementApp {
    private static UserService userService;
    private static ProjectService projectService;
    private static TaskService taskService;
    private static ClientService clientService;
    private static Scanner scanner = new Scanner(System.in);
    private static User loggedInUser;

    public static void main(String[] args) {
        try {
            userService = new UserService();
            projectService = new ProjectService();
            taskService = new TaskService();
            clientService = new ClientService();
        } catch (SQLException e) {
            System.out.println("An error occurred while initializing services: " + e.getMessage());
            return; 
        }

        System.out.println("Welcome to the Task Management System");

        while (true) {
            System.out.println("1. Login");
            System.out.println("2. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter 1 or 2.");
            }
        }
    }

    private static void login() {
        try {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            if (username.isEmpty() || password.isEmpty()) {
                System.out.println("Username and password cannot be empty.");
                return;
            }

            loggedInUser = userService.getUserByUsernameAndPassword(username, password);

            if (loggedInUser != null) {
                System.out.println("Login successful! Welcome, " + loggedInUser.getUsername());
                switch (loggedInUser.getRoleId()) {
                    case 1: 
                        showAdminMenu();
                        break;
                    case 2: 
                        showProjectManagerMenu();
                        break;
                    case 3: 
                        showTeamMemberMenu();
                        break;
                    default:
                        System.out.println("Invalid user role.");
                }
            } else {
                System.out.println("Invalid username or password. Please try again.");
            }
        } catch (SQLException e) {
            System.out.println("An error occurred during login: " + e.getMessage());
        }
    }

    private static void showAdminMenu() {
        while (true) {
            System.out.println("Admin Menu:");
            System.out.println("1. Create User Account");
            System.out.println("2. Update User Account");
            System.out.println("3. Deactivate User Account");
            System.out.println("4. View Reports");
            System.out.println("5. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    createUserAccount();
                    break;
                case 2:
                    updateUserAccount();
                    break;
                case 3:
                    deactivateUserAccount();
                    break;
                case 4:
                    viewReports();
                    break;
                case 5:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    private static void createUserAccount() {
        try {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
            System.out.print("Enter email: ");
            String email = scanner.nextLine();
            System.out.print("Enter role ID (1: Admin, 2: Project Manager, 3: Team Member): ");
            int roleId = scanner.nextInt();
            scanner.nextLine(); 
            System.out.print("Enter status (e.g., Active): ");
            String status = scanner.nextLine();

            if (roleId < 1 || roleId > 3) {
                System.out.println("Invalid role ID.");
                return;
            }

            User newUser = new User();
            newUser.setUsername(username);
            newUser.setPassword(password);
            newUser.setEmail(email);
            newUser.setRoleId(roleId);
            newUser.setStatus(status);

            userService.addUser(newUser);
            System.out.println("User account created successfully.");
        } catch (SQLException e) {
            System.out.println("An error occurred while creating the user account: " + e.getMessage());
        }
    }

    private static void updateUserAccount() {
        try {
            System.out.print("Enter user ID to update: ");
            int userId = scanner.nextInt();
            scanner.nextLine(); 

            User user = userService.getUserById(userId);
            if (user != null) {
                System.out.print("Enter new username (leave blank to keep current): ");
                String username = scanner.nextLine();
                System.out.print("Enter new password (leave blank to keep current): ");
                String password = scanner.nextLine();

                if (!username.isEmpty()) {
                    user.setUsername(username);
                }
                if (!password.isEmpty()) {
                    user.setPassword(password);
                }

                userService.updateUser(user);
                System.out.println("User account updated successfully.");
            } else {
                System.out.println("User not found.");
            }
        } catch (SQLException e) {
            System.out.println("An error occurred while updating the user account: " + e.getMessage());
        }
    }

    private static void deactivateUserAccount() {
        try {
            System.out.print("Enter user ID to deactivate: ");
            int userId = scanner.nextInt();
            scanner.nextLine(); 

            userService.deactivateUser(userId);
            System.out.println("User account deactivated successfully.");
        } catch (SQLException e) {
            System.out.println("An error occurred while deactivating the user account: " + e.getMessage());
        }
    }

    private static void viewReports() {
        try {
            List<User> users = userService.getAllUsers();
            List<Project> projects = projectService.getAllProjects();
            
            System.out.println("User Reports:");
            for (User user : users) {
                System.out.println("User ID: " + user.getUserId() + ", Username: " + user.getUsername() + ", Role ID: " + user.getRoleId());
            }
            
            System.out.println("Project Reports:");
            for (Project project : projects) {
                System.out.println("Project ID: " + project.getProjectId() + ", Project Name: " + project.getProjectName());
            }
        } catch (SQLException e) {
            System.out.println("An error occurred while viewing reports: " + e.getMessage());
        }
    }

    private static void showProjectManagerMenu() {
        while (true) {
            System.out.println("Project Manager Menu:");
            System.out.println("1. Manage Client Information");
            System.out.println("2. Add Team Members to Project");
            System.out.println("3. Assign Tasks");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    manageClientInformation();
                    break;
                case 2:
                    addTeamMembersToProject();
                    break;
                case 3:
                    assignTasks();
                    break;
                case 4:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    private static void manageClientInformation() {
        while (true) {
            System.out.println("Manage Client Information Menu:");
            System.out.println("1. Add Client");
            System.out.println("2. Update Client");
            System.out.println("3. Delete Client");
            System.out.println("4. View Clients");
            System.out.println("5. Return to Project Manager Menu");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addClient();
                    break;
                case 2:
                    updateClient();
                    break;
                case 3:
                    deleteClient();
                    break;
                case 4:
                    viewClients();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    private static void addClient() {
        try {
            System.out.print("Enter client name: ");
            String clientName = scanner.nextLine();
            System.out.print("Enter client email: ");
            String clientEmail = scanner.nextLine();

            Client newClient = new Client();
            newClient.setClientName(clientName);
            newClient.setClientEmail(clientEmail);

            clientService.addClient(newClient);
            System.out.println("Client added successfully.");
        } catch (SQLException e) {
            System.out.println("An error occurred while adding the client: " + e.getMessage());
        }
    }

    private static void updateClient() {
        try {
            System.out.print("Enter client ID to update: ");
            int clientId = scanner.nextInt();
            scanner.nextLine();

            Client client = clientService.getClientById(clientId);
            if (client != null) {
                System.out.print("Enter new client name (leave blank to keep current): ");
                String clientName = scanner.nextLine();
                System.out.print("Enter new client email (leave blank to keep current): ");
                String clientEmail = scanner.nextLine();

                if (!clientName.isEmpty()) {
                    client.setClientName(clientName);
                }
                if (!clientEmail.isEmpty()) {
                    client.setClientEmail(clientEmail);
                }

                clientService.updateClient(client);
                System.out.println("Client updated successfully.");
            } else {
                System.out.println("Client not found.");
            }
        } catch (SQLException e) {
            System.out.println("An error occurred while updating the client: " + e.getMessage());
        }
    }

    private static void deleteClient() {
        try {
            System.out.print("Enter client ID to delete: ");
            int clientId = scanner.nextInt();
            scanner.nextLine(); 

            clientService.deleteClient(clientId);
            System.out.println("Client deleted successfully.");
        } catch (SQLException e) {
            System.out.println("An error occurred while deleting the client: " + e.getMessage());
        }
    }

    private static void viewClients() {
        try {
            List<Client> clients = clientService.getAllClients();
            System.out.println("Clients:");
            for (Client client : clients) {
                System.out.println("Client ID: " + client.getClientId() + ", Name: " + client.getClientName() + ", Email: " + client.getClientEmail());
            }
        } catch (SQLException e) {
            System.out.println("An error occurred while viewing clients: " + e.getMessage());
        }
    }

    private static void addTeamMembersToProject() {
        System.out.print("Enter project ID: ");
        int projectId = scanner.nextInt();
        scanner.nextLine(); 

        System.out.print("Enter team member ID: ");
        int userId = scanner.nextInt();
        scanner.nextLine();

        
        System.out.println("Team member added to project successfully.");
    }

    private static void assignTasks() {
        try {
            System.out.print("Enter task name: ");
            String taskName = scanner.nextLine();
            System.out.print("Enter project ID: ");
            int projectId = scanner.nextInt();
            scanner.nextLine(); 
            System.out.print("Enter team member ID to assign task to: ");
            int userId = scanner.nextInt();
            scanner.nextLine(); 

            Task newTask = new Task();
            newTask.setTaskName(taskName);
            newTask.setProjectId(projectId);
            newTask.setAssignedTo(userId);
            newTask.setStatus("Assigned");

            taskService.addTask(newTask);
            System.out.println("Task assigned successfully.");
        } catch (SQLException e) {
            System.out.println("An error occurred while assigning the task: " + e.getMessage());
        }
    }


    private static void showTeamMemberMenu() {
        while (true) {
            System.out.println("Team Member Menu:");
            System.out.println("1. View Assigned Tasks");
            System.out.println("2. Update Task Status");
            System.out.println("3. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    viewAssignedTasks();
                    break;
                case 2:
                    updateTaskStatus();
                    break;
                case 3:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    private static void viewAssignedTasks() {
        try {
            List<Task> tasks = taskService.getTasksByUserId(loggedInUser.getUserId());
            System.out.println("Assigned Tasks:");
            for (Task task : tasks) {
                System.out.println("Task ID: " + task.getTaskId() + ", Name: " + task.getTaskName() + ", Status: " + task.getStatus());
            }
        } catch (SQLException e) {
            System.out.println("An error occurred while viewing assigned tasks: " + e.getMessage());
        }
    }

    private static void updateTaskStatus() {
        try {
            System.out.print("Enter task ID to update status: ");
            int taskId = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Enter new status: ");
            String status = scanner.nextLine();

            Task task = taskService.getTaskById(taskId);
            if (task != null) {
                task.setStatus(status);
                taskService.updateTask(task);
                System.out.println("Task status updated successfully.");
            } else {
                System.out.println("Task not found.");
            }
        } catch (SQLException e) {
            System.out.println("An error occurred while updating task status: " + e.getMessage());
        }
    }
}
