package com.taskmanagement.dao;

import com.taskmanagement.model.Client;
import com.taskmanagement.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientDao {
    private Connection connection;

    public ClientDao() {
        try {
            connection = DBConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
    }

   
    public void addClient(Client client) throws SQLException {
        String sql = "INSERT INTO clients (client_name, contact_info) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, client.getClientName());

            statement.executeUpdate();
        }
    }

  
    public void updateClient(Client client) throws SQLException {
        String sql = "UPDATE clients SET client_name = ?, contact_info = ? WHERE client_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, client.getClientName());

            statement.setInt(3, client.getClientId());
            statement.executeUpdate();
        }
    }

   
    public void deleteClient(int clientId) throws SQLException {
        String sql = "DELETE FROM clients WHERE client_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, clientId);
            statement.executeUpdate();
        }
    }

    // Get a client by ID
    public Client getClientById(int clientId) throws SQLException {
        String sql = "SELECT * FROM clients WHERE client_id = ?";
        Client client = null;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, clientId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    client = new Client();
                    client.setClientId(resultSet.getInt("client_id"));
                    client.setClientName(resultSet.getString("client_name"));

                }
            }
        }
        return client;
    }

    
    public List<Client> getAllClients() throws SQLException {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM clients";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Client client = new Client();
                client.setClientId(resultSet.getInt("client_id"));
                client.setClientName(resultSet.getString("client_name"));
                clients.add(client);
            }
        }
        return clients;
    }
}
