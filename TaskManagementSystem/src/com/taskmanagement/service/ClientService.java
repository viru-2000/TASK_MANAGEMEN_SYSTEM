package com.taskmanagement.service;

import com.taskmanagement.model.Client;
import com.taskmanagement.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientService {

    public void addClient(Client client) throws SQLException {
        String query = "INSERT INTO clients (client_name, client_email) VALUES (?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, client.getClientName());
            preparedStatement.setString(2, client.getClientEmail());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
           
            System.err.println("Error adding client: " + e.getMessage());
            throw e;
        }
    }

    public void updateClient(Client client) throws SQLException {
        String query = "UPDATE clients SET client_name = ?, client_email = ? WHERE client_id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, client.getClientName());
            preparedStatement.setString(2, client.getClientEmail());
            preparedStatement.setInt(3, client.getClientId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
          
            System.err.println("Error updating client: " + e.getMessage());
            throw e;
        }
    }

    public void deleteClient(int clientId) throws SQLException {
        String query = "DELETE FROM clients WHERE client_id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, clientId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {

            System.err.println("Error deleting client: " + e.getMessage());
            throw e;
        }
    }

    public List<Client> getAllClients() throws SQLException {
        String query = "SELECT * FROM clients";
        List<Client> clients = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Client client = new Client();
                client.setClientId(resultSet.getInt("client_id"));
                client.setClientName(resultSet.getString("client_name"));
                client.setClientEmail(resultSet.getString("client_email"));
                clients.add(client);
            }
        } catch (SQLException e) {
            
            System.err.println("Error retrieving all clients: " + e.getMessage());
            throw e;
        }
        return clients;
    }

    public Client getClientById(int clientId) throws SQLException {
        String query = "SELECT * FROM clients WHERE client_id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, clientId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Client client = new Client();
                    client.setClientId(resultSet.getInt("client_id"));
                    client.setClientName(resultSet.getString("client_name"));
                    client.setClientEmail(resultSet.getString("client_email"));
                    return client;
                }
            }
        } catch (SQLException e) {
            
            System.err.println("Error retrieving client by ID: " + e.getMessage());
            throw e;
        }
        return null;
    }
}
