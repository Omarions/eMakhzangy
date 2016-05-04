/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emakhzangy.controller;

import emakhzangy.model.Inventory;
import emakhzangy.model.Item;
import emakhzangy.model.Operation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Omar
 */
public class cInventory {

    Connection conn;

    public cInventory() {
        try {
            conn = DBConnection.connect();
        } catch (SQLException ex) {
            Logger.getLogger(cInventory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Inventory getInventory(int id) {
        Inventory inventory = new Inventory();
        String query = "SELECT * FROM inventory WHERE id=?";

        try {
            PreparedStatement prep = conn.prepareStatement(query);
            prep.setInt(1, id);
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                inventory.setId(id);
                inventory.setItem(new cItem().getByID(rs.getInt("item_id")));
                inventory.setAvailableQty(rs.getInt("available_qty"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(cInventory.class.getName()).log(Level.SEVERE, null, ex);
        }

        return inventory;
    }

    public List<Inventory> getInventoryList() {
        List<Inventory> list = new ArrayList<>();
        String query = "SELECT * FROM inventory";
        try {
            PreparedStatement prep = conn.prepareStatement(query);
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                Inventory inventory = new Inventory();
                inventory.setId(rs.getInt("id"));
                inventory.setItem(new cItem().getByID(rs.getInt("item_id")));
                inventory.setAvailableQty(rs.getInt("available_qty"));

                list.add(inventory);
            }
        } catch (SQLException ex) {
            Logger.getLogger(cInventory.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }

    public Optional<Inventory> getByItem(Item item) {
        Inventory inventory = new Inventory();
        String query = "SELECT * FROM inventory WHERE item_id=?";

        try {
            PreparedStatement prep = conn.prepareStatement(query);
            prep.setInt(1, item.getId());
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                inventory.setId(rs.getInt("id"));
                inventory.setItem(item);
                inventory.setAvailableQty(rs.getInt("available_qty"));
            }
        } catch (SQLException ex) {
            return null;
        }

        return Optional.of(inventory);
    }

    public Item getItem(int itemId) {
        return new cItem().getByID(itemId);
    }

    public boolean isItemExist(int itemId) {
        boolean isExist = false;
        String getItemQuery = "SELECT item_id FROM inventory WHERE item_id=?";
        try {
            PreparedStatement psCheckItem = conn.prepareStatement(getItemQuery);
            psCheckItem.setInt(1, itemId);

            ResultSet rsGetItemID = psCheckItem.executeQuery();
            if (rsGetItemID.next()) {
                isExist = true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(cInventory.class.getName()).log(Level.SEVERE, null, ex);
        }

        return isExist;
    }

    public int add(Inventory inventory, Operation operation) {
        String insertInventoryQuery = "INSERT INTO inventory (item_id, available_qty) VALUES(?,?)";
        int itemId = inventory.getItem().getId();
        
        if (isItemExist(itemId)) {
            int inventoryId = getByItem(inventory.getItem()).get().getId();
            return update(inventoryId, inventory, operation);
        } else {
            try {
                PreparedStatement prep = conn.prepareStatement(insertInventoryQuery, Statement.RETURN_GENERATED_KEYS);
                prep.setInt(1, itemId);
                prep.setInt(2, inventory.getAvailableQty());
                prep.executeUpdate();
                ResultSet rs = prep.getGeneratedKeys();
                if(rs.next()){
                    return rs.getInt("GENERATED_KEY");
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(cInventory.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return -1;
    }

    public int update(int id, Inventory inventory, Operation operation) {
        String insertInventoryQuery = "UPDATE inventory SET  available_qty = available_qty + ? WHERE item_id=?";
        int itemId = inventory.getItem().getId();
        
        try {
            PreparedStatement prep = conn.prepareStatement(insertInventoryQuery);
            switch(operation.getName().toLowerCase()){
                case "out":
                    prep.setInt(1, Math.negateExact(inventory.getAvailableQty()));
                    break;
                case "in":
                    prep.setInt(1, inventory.getAvailableQty());
                    break;
            }
            
            prep.setInt(2, itemId);

            if (prep.executeUpdate() > 0) {
                return id;
            }
            return -1;
        } catch (SQLException ex) {
            Logger.getLogger(cInventory.class.getName()).log(Level.SEVERE, null, ex);
        }

        return -1;
    }

    public int remove(int id) {
        return -1;
    }
}
