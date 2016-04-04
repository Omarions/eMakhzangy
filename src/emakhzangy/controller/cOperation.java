/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emakhzangy.controller;

import emakhzangy.model.Operation;
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
public class cOperation {
    Connection  conn;

    public cOperation() {
        try {
            conn = DBConnection.connect();
        } catch (SQLException ex) {
            Logger.getLogger(cOperation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Operation getOperation(int id){
        Operation operation = new Operation();
        String query ="SELECT * FROM operation WHERE id=?";
        PreparedStatement prep;
        try {
            prep = conn.prepareStatement(query);
            prep.setInt(1, id);
            ResultSet rs = prep.executeQuery();
            while(rs.next()){
                operation.setId(rs.getInt("id"));
                operation.setName(rs.getString("name"));
                operation.setComments(rs.getString("comments"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(cOperation.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return operation;
    }
    
    public List<Operation> getOperations(){
        List<Operation> list = new ArrayList<>();
        String query = "SELECT * FROM operation";
        try {
            PreparedStatement prep = conn.prepareStatement(query);
            ResultSet rs = prep.executeQuery();
            while(rs.next()){
                Operation operation = new Operation();
                operation.setId(rs.getInt("id"));
                operation.setName(rs.getString("name"));
                operation.setComments(rs.getString("comments"));
                
                list.add(operation);
            }
        } catch (SQLException ex) {
            Logger.getLogger(cOperation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
}
