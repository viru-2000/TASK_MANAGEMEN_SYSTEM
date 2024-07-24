package com.taskmanagement.service;

import com.taskmanagement.dao.UserDao;
import com.taskmanagement.model.User;
import java.sql.SQLException;
import java.util.List;

public class UserService {
    private UserDao userDao;

    public UserService() {
        this.userDao = new UserDao(); 
    }

    
    public void addUser(User user) throws SQLException {
        userDao.addUser(user);
    }

  
    public User getUserByUsernameAndPassword(String username, String password) throws SQLException {
        return userDao.getUserByUsernameAndPassword(username, password);
    }

  
    public User getUserById(int userId) throws SQLException {
        return userDao.getUserById(userId);
    }

   
    public void updateUser(User user) throws SQLException {
        userDao.updateUser(user);
    }

  
    public void deactivateUser(int userId) throws SQLException {
        userDao.deactivateUser(userId);
    }

  
    public List<User> getAllUsers() throws SQLException {
        return userDao.getAllUsers();
    }

  
    public List<User> getTeamMembers() throws SQLException {
        return userDao.getTeamMembers();
    }
}
