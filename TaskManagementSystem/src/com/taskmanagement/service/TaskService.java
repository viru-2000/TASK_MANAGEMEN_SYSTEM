package com.taskmanagement.service;

import com.taskmanagement.model.Task;
import com.taskmanagement.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TaskService {
    private Connection connection;

    public TaskService() throws SQLException {
        try {
            connection = DBConnection.getConnection();
        } catch (SQLException e) {
           
            throw new SQLException("Failed to connect to the database", e);
        }
    }

    public void addTask(Task task) throws SQLException {
        String query = "INSERT INTO tasks (task_name, project_id, assigned_to, status) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, task.getTaskName());
            preparedStatement.setInt(2, task.getProjectId());
            preparedStatement.setInt(3, task.getAssignedTo());
            preparedStatement.setString(4, task.getStatus());
            preparedStatement.executeUpdate();
        }
    }

    public void updateTaskStatus(int taskId, String status) throws SQLException {
        String query = "UPDATE tasks SET status = ? WHERE task_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, taskId);
            preparedStatement.executeUpdate();
        }
    }

    public List<Task> getTasksByUserId(int userId) throws SQLException {
        String query = "SELECT * FROM tasks WHERE assigned_to = ?";
        List<Task> tasks = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Task task = new Task();
                    task.setTaskId(resultSet.getInt("task_id"));
                    task.setTaskName(resultSet.getString("task_name"));
                    task.setProjectId(resultSet.getInt("project_id"));
                    task.setAssignedTo(resultSet.getInt("assigned_to"));
                    task.setStatus(resultSet.getString("status"));
                    tasks.add(task);
                }
            }
        }
        return tasks;
    }

    public Task getTaskById(int taskId) throws SQLException {
        String query = "SELECT * FROM tasks WHERE task_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, taskId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Task task = new Task();
                    task.setTaskId(resultSet.getInt("task_id"));
                    task.setTaskName(resultSet.getString("task_name"));
                    task.setProjectId(resultSet.getInt("project_id"));
                    task.setAssignedTo(resultSet.getInt("assigned_to"));
                    task.setStatus(resultSet.getString("status"));
                    return task;
                }
            }
        }
        return null;
    }

    public void updateTask(Task task) throws SQLException {
        String query = "UPDATE tasks SET task_name = ?, project_id = ?, assigned_to = ?, status = ? WHERE task_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, task.getTaskName());
            preparedStatement.setInt(2, task.getProjectId());
            preparedStatement.setInt(3, task.getAssignedTo());
            preparedStatement.setString(4, task.getStatus());
            preparedStatement.setInt(5, task.getTaskId());
            preparedStatement.executeUpdate();
        }
    }
}
