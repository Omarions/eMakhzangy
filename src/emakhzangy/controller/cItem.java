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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Omar
 */
public class cItem {

    Connection conn;

    public cItem() {
        try {
            conn = DBConnection.connect();
        } catch (SQLException ex) {
            Logger.getLogger(cItem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Item> getAll() {
        List<Item> list = new ArrayList<>();
        String query = "SELECT i.*, iv.available_qty FROM item AS i INNER JOIN inventory AS iv ON(i.id = iv.item_id)";
        try {
            PreparedStatement prep = conn.prepareStatement(query);
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                //get item info from each row.
                int id = rs.getInt("i.id");
                String name = rs.getString("i.name");
                String tags = rs.getString("i.tags");
                int quantity = rs.getInt("iv.available_qty");
                String unit = rs.getString("i.unit");
                double unitPrice = rs.getDouble("i.unit_price");
                String comments = rs.getString("i.comments");
                //create new object of item with its data
                Item item = new Item(id, name, tags, quantity, unit, unitPrice, comments);
                //add new item to the list.
                list.add(item);
            }
        } catch (SQLException ex) {
            Logger.getLogger(cItem.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    /**
     * Get the item by its ID
     *
     * @param id you looking for
     * @return Item with specified ID
     */
    public Item getByID(int id) {
        Item item = new Item();
        String query = "SELECT * FROM item WHERE id=?";
        try {
            PreparedStatement prep = conn.prepareStatement(query);
            prep.setInt(1, id);
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                item.setId(id);
                item.setName(rs.getString("name"));
                item.setTags(rs.getString("tags"));
                item.setUnit(rs.getString("unit"));
                item.setUnitPrice(rs.getDouble("unit_price"));
                item.setComments(rs.getString("comments"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(cItem.class.getName()).log(Level.SEVERE, null, ex);
        }
        return item;
    }

    /**
     * Get the item by its name.
     *
     * @param name
     * @return Item with specified name
     */
    public Item getByName(String name) {
        return null;
    }

    /**
     * Get all items with specified tag
     *
     * @param tag
     * @return List of items with specified tag.
     */
    public List<Item> getByTag(String tag) {
        return null;
    }

    /**
     * Get all items with specified tags
     *
     * @param tag
     * @return List of items with specified tags.
     */
    public List<Item> getByTag(String[] tag) {
        return null;
    }

    /**
     * Get all items with specified unit.
     *
     * @param unit
     * @return List of items with specified unit.
     */
    public List<Item> getByUnit(String unit) {
        return null;
    }

    /**
     * Adding new Item to db
     *
     * @param item
     * @return
     */
    public int add(Item item) {
        String query = "INSERT INTO item(name,tags,unit,unit_price,comments) VALUES(?,?,?,?,?)";
        try {
            PreparedStatement prep = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            //set the parameters of query.
            prep.setString(1, item.getName());
            prep.setString(2, item.getTags());
            prep.setString(3, item.getUnit());
            prep.setDouble(4, item.getUnitPrice());
            prep.setString(5, item.getComments());
            prep.executeUpdate();
            ResultSet rs = prep.getGeneratedKeys();
            if (rs.next()) {
                item.setId(rs.getInt("GENERATED_KEY"));
                new cInventory().add(new Inventory(item, 0), new Operation(1, "in", ""));
                return item.getId();
            }

        } catch (SQLException ex) {
            return -1;
        }

        return -1;
    }

    public int update(int id, Item item) {
        String query = "UPDATE item SET name=?, tags=?, unit=?, unit_price=?, comments=? WHERE id=?";
        try {
            PreparedStatement prep = conn.prepareStatement(query);
            prep.setString(1, item.getName());
            prep.setString(2, item.getTags());
            prep.setString(3, item.getUnit());
            prep.setDouble(4, item.getUnitPrice());
            prep.setString(5, item.getComments());
            prep.setInt(6, id);

            return prep.executeUpdate();
        } catch (SQLException ex) {
            return -1;
        }
    }

    /**
     * Remove an item from db with its id
     *
     * @param id
     * @return no of affected rows (which is 1) or if fails return -1.
     */
    public int remove(int id) {
        String query = "DELETE FROM item WHERE id=?";
        try {
            PreparedStatement prep = conn.prepareStatement(query);
            //set the parameters of query.
            prep.setInt(1, id);

            //return the result of execution 
            //(row count affectd)
            return prep.executeUpdate();

        } catch (SQLException ex) {
            //if error happend return -1.
            return -1;
        }

    }
}
