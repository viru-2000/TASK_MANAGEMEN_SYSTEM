package com.taskmanagement.dao;

import com.taskmanagement.model.Task;
import com.taskmanagement.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TaskDao {
    private Connection connection;

    public TaskDao() {
        try {
            connection = DBConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
    }

    public void addTask(Task task) throws SQLException {
        String sql = "INSERT INTO tasks (task_name, project_id, assigned_to, status) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, task.getTaskName());
            statement.setInt(2, task.getProjectId());
            statement.setInt(3, task.getAssignedTo());
            statement.setString(4, task.getStatus());
            statement.executeUpdate();
        }
    }

    public List<Task> getAllTasks() throws SQLException {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
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
        return tasks;
    }

    public void updateTask(Task task) throws SQLException {
        String sql = "UPDATE tasks SET task_name = ?, project_id = ?, assigned_to = ?, status = ? WHERE task_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, task.getTaskName());
            statement.setInt(2, task.getProjectId());
            statement.setInt(3, task.getAssignedTo());
            statement.setString(4, task.getStatus());
            statement.setInt(5, task.getTaskId());
            statement.executeUpdate();
        }
    }

    public void deleteTask(int taskId) throws SQLException {
        String sql = "DELETE FROM tasks WHERE task_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, taskId);
            statement.executeUpdate();
        }
    }

    public Task getTaskById(int taskId) throws SQLException {
        String sql = "SELECT * FROM tasks WHERE task_id = ?";
        Task task = null;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, taskId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    task = new Task();
                    task.setTaskId(resultSet.getInt("task_id"));
                    task.setTaskName(resultSet.getString("task_name"));
                    task.setProjectId(resultSet.getInt("project_id"));
                    task.setAssignedTo(resultSet.getInt("assigned_to"));
                    task.setStatus(resultSet.getString("status"));
                }
            }
        }
        return task;
    }

    public List<Task> getTasksByUserId(int userId) throws SQLException {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks WHERE assigned_to = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
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
}
