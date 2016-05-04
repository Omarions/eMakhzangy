/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emakhzangy.controller;

import emakhzangy.model.InventoryOperation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Omar
 */
public class cInventoryOperation {

    Connection conn;

    public cInventoryOperation() {
        try {
            conn = DBConnection.connect();
        } catch (SQLException ex) {
            Logger.getLogger(cInventoryOperation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public InventoryOperation getByID(int id) {
        return null;
    }

    public List<InventoryOperation> getByOperation(String operationName) {
        return null;
    }

    public List<InventoryOperation> getByProject(int projectId) {
        List<InventoryOperation> list = new ArrayList<>();
        for (InventoryOperation inventoryOperation : getAll()) {
            if (inventoryOperation.getProject().getId() == projectId) {
                list.add(inventoryOperation);
            }
        }
        return list;
    }

    public List<InventoryOperation> getByDate(LocalDate date) {
        return null;
    }

    public List<InventoryOperation> getByDate(LocalDate startDate, LocalDate endDate) {
        return null;
    }

    public List<InventoryOperation> getByDocument(String serial) {
        return null;
    }

    /**
     * Retrieve all inventory operation items.
     *
     * @return list of Inventory_Operation items.
     */
    public List<InventoryOperation> getAll() {
        List<InventoryOperation> list = new ArrayList<>();
        try {
            String query = "SELECT * FROM inventory_operation";
            PreparedStatement prep = conn.prepareStatement(query);
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                InventoryOperation io = new InventoryOperation();
                int id = rs.getInt("id");
                int inventoryId = rs.getInt("inventory_id");
                int documentId = rs.getInt("document_id");
                int projectId = rs.getInt("project_id");
                int qty = rs.getInt("quantity");
                String comments = rs.getString("comments");

                io.setId(id);
                io.setInventory(new cInventory().getInventory(inventoryId));
                io.setDocument(new cDocument().getDocument(documentId));
                io.setProject(new cProject().getProject(projectId));
                io.setQuantity(qty);
                io.setComments(comments);

                list.add(io);

            }
        } catch (SQLException ex) {
            Logger.getLogger(cInventoryOperation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    /**
     * Add new IO to database.
     *
     * @param io
     * @return the count of affected rows or -1 if fails.
     */
    public int add(InventoryOperation io) {
        int invResult = -1;

        invResult = new cInventory().add(io.getInventory(), io.getDocument().getOperation());

        if (invResult != -1) {
            String query = "INSERT INTO inventory_operation (inventory_id, document_id, project_id, quantity, comments) VALUES(?,?,?,?,?)";
            try {
                PreparedStatement prep = conn.prepareStatement(query);
                prep.setInt(1, invResult);
                prep.setInt(2, io.getDocument().getId());
                prep.setInt(3, io.getProject().getId());
                prep.setInt(4, io.getQuantity());
                prep.setString(5, io.getComments());

                return prep.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(cInventoryOperation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return -1;
    }

    public int update(int id, InventoryOperation io) {
        return -1;
    }

    public int remove(int id) {
        return -1;
    }
}
