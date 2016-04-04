/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emakhzangy.controller;

import emakhzangy.model.Document;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Omar
 */
public class cDocument {
    Connection conn;

    public cDocument() {
        try {
            conn = DBConnection.connect();
        } catch (SQLException ex) {
            Logger.getLogger(cDocument.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public Document getDocument(int id){
        Document document = new Document();
        String query = "SELECT * FROM document WHERE id=?";
        try {
            PreparedStatement prep = conn.prepareStatement(query);
            prep.setInt(1, id);
            ResultSet rs = prep.executeQuery();
            while(rs.next()){
                document.setId(rs.getInt("id"));
                document.setOperation(new cOperation().getOperation(
                        rs.getInt("operation_id")));
                document.setFrom(rs.getString("from_name"));
                document.setTo(rs.getString("to_name"));
                document.setDate(rs.getDate("document_date").toLocalDate());
                document.setComments(rs.getString("comments"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(cDocument.class.getName()).log(Level.SEVERE, null, ex);
        }
        return document;
    }
    
    public List<Document> getByFrom(String from){
        return null;
    }
    
    public List<Document> getByTo(String to){
        return null;
    }
    
    public List<Document> getByDate(LocalDate date){
        return null;
    }
    
    public List<Document> getByDate(LocalDate date1, LocalDate date2){
        return null;
    }
    
    public List<Document> getDocuments(){
        return null;
    }
    
    public int add(Document document){
        String query = "INSERT INTO document (operation_id, from_name, to_name, document_date, comments) VALUES(?,?,?,?,?)";
        try {
            PreparedStatement prep = conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
            prep.setInt(1, document.getOperation().getId());
            prep.setString(2, document.getFrom());
            prep.setString(3, document.getTo());
            prep.setString(4, document.getDate().toString());
            prep.setString(5, document.getComments());
            
            return prep.getGeneratedKeys().getInt("id");
        } catch (SQLException ex) {
            Logger.getLogger(cDocument.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return -1;
    }
    
    public int update(int id, Document document){
        return -1;
    }
    
    public int remove(int id){
        return -1;
    }
    
}
