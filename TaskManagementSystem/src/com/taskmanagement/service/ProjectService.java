package com.taskmanagement.service;

import com.taskmanagement.dao.ProjectDao;
import com.taskmanagement.model.Project;
import com.taskmanagement.model.Client;

import java.sql.SQLException;
import java.util.List;

public class ProjectService {
    private ProjectDao projectDao;

    public ProjectService() {
        this.projectDao = new ProjectDao();
    }

   
    public void addProject(Project project) {
        try {
            projectDao.addProject(project);
        } catch (SQLException e) {
            System.out.println("Error adding project: " + e.getMessage());
        }
    }

    
    public List<Project> getAllProjects() {
        try {
            return projectDao.getAllProjects();
        } catch (SQLException e) {
            System.out.println("Error fetching projects: " + e.getMessage());
            return null;
        }
    }

   
    public void updateProject(Project project) {
        try {
            projectDao.updateProject(project);
        } catch (SQLException e) {
            System.out.println("Error updating project: " + e.getMessage());
        }
    }

    
    public void deleteProject(int projectId) {
        try {
            projectDao.deleteProject(projectId);
        } catch (SQLException e) {
            System.out.println("Error deleting project: " + e.getMessage());
        }
    }

    
    public Project getProjectById(int projectId) {
        try {
            return projectDao.getProjectById(projectId);
        } catch (SQLException e) {
            System.out.println("Error fetching project: " + e.getMessage());
            return null;
        }
    }

   
    public List<Client> getAllClients() {
        try {
            return projectDao.getAllClients();
        } catch (SQLException e) {
            System.out.println("Error fetching clients: " + e.getMessage());
            return null;
        }
    }

    
    public void addTeamMemberToProject(int userId, int projectId) {
        try {
            projectDao.addTeamMemberToProject(userId, projectId);
        } catch (SQLException e) {
            System.out.println("Error adding team member to project: " + e.getMessage());
        }
    }
}
