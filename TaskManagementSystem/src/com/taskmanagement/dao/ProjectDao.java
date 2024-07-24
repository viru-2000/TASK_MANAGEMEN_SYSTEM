package com.taskmanagement.dao;

import com.taskmanagement.model.Client;
import com.taskmanagement.model.Project;
import com.taskmanagement.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProjectDao {
    private Connection connection;

    public ProjectDao() {
        try {
            connection = DBConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    public void addProject(Project project) throws SQLException {
        String sql = "INSERT INTO projects (project_name, client_name) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, project.getProjectName());
            statement.setString(2, project.getClientName());
            statement.executeUpdate();
        }
    }

    public List<Project> getAllProjects() throws SQLException {
        List<Project> projects = new ArrayList<>();
        String sql = "SELECT * FROM projects";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Project project = new Project();
                project.setProjectId(resultSet.getInt("project_id"));
                project.setProjectName(resultSet.getString("project_name"));
                project.setClientName(resultSet.getString("client_name"));
                projects.add(project);
            }
        }
        return projects;
    }

    
    public void updateProject(Project project) throws SQLException {
        String sql = "UPDATE projects SET project_name = ?, client_name = ? WHERE project_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, project.getProjectName());
            statement.setString(2, project.getClientName());
            statement.setInt(3, project.getProjectId());
            statement.executeUpdate();
        }
    }

 
    public void deleteProject(int projectId) throws SQLException {
        String sql = "DELETE FROM projects WHERE project_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, projectId);
            statement.executeUpdate();
        }
    }

    
    public Project getProjectById(int projectId) throws SQLException {
        String sql = "SELECT * FROM projects WHERE project_id = ?";
        Project project = null;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, projectId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                project = new Project();
                project.setProjectId(resultSet.getInt("project_id"));
                project.setProjectName(resultSet.getString("project_name"));
                project.setClientName(resultSet.getString("client_name"));
            }
        }
        return project;
    }


    public List<Client> getAllClients() throws SQLException {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM clients";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Client client = new Client();
                client.setClientId(resultSet.getInt("client_id"));
                client.setClientName(resultSet.getString("client_name"));
                client.setClientEmail(resultSet.getString("client_email"));
                clients.add(client);
            }
        }
        return clients;
    }

    
    public void addTeamMemberToProject(int userId, int projectId) throws SQLException {
        String sql = "INSERT INTO project_team (project_id, user_id) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, projectId);
            statement.setInt(2, userId);
            statement.executeUpdate();
        }
    }
}
