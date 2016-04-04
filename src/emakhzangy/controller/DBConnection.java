/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emakhzangy.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Omar
 */
public class DBConnection {
    private static Connection conn;
    private final static String URL="jdbc:mysql://localhost:3306/stock_db";
    private final static String USER="root";
    private final static String PASSWORD="12345";
    
    public static Connection connect() throws SQLException{
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(URL,USER,PASSWORD);
            
        }catch(ClassNotFoundException e){
            
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return conn;
    }
    
    public static Connection getConnection() throws SQLException, ClassNotFoundException{
        if(conn != null && !conn.isClosed())
            return conn;
        
        connect();
        return conn;
    }
}
