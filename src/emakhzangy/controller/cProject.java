/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emakhzangy.controller;

import emakhzangy.model.Project;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Omar
 */
public class cProject {
    Connection conn;

    public cProject() {
        try {
            conn = DBConnection.connect();
        } catch (SQLException ex) {
            Logger.getLogger(cProject.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Project getProject(int id){
        Project project = new Project();
        String query = "SELECT * FROM project WHERE id=?";
        try {
            PreparedStatement prep = conn.prepareStatement(query);
            prep.setInt(1, id);
            ResultSet rs = prep.executeQuery();
            while(rs.next()){
                project.setId(id);
                project.setName(rs.getString("name"));
                project.setManagerName(rs.getString("manager_name"));
                project.setComments(rs.getString("comments"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(cProject.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return project;
    }
    
    public List<Project> getProjects(){
        List<Project> list = new ArrayList<>();
        String query = "SELECT * FROM project";
        try {
            PreparedStatement prep = conn.prepareStatement(query);
            ResultSet rs = prep.executeQuery();
            while(rs.next()){
                Project project = new Project();
                project.setId(rs.getInt("id"));
                project.setName(rs.getString("name"));
                project.setManagerName(rs.getString("manager_name"));
                project.setComments(rs.getString("comments"));
                
                list.add(project);
            }
                    
        } catch (SQLException ex) {
            Logger.getLogger(cProject.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    public String getProjectName(int id){
        return null;
    }
    
    public List<Project> getProjectsByManager(String manager){
        return null;
    }
    
    public String getProjectManager(int id){
        return null;
    }
    
    public String getProjectManager(String name){
        return null;
    }
}
