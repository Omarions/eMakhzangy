/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emakhzangy.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import sun.awt.im.SimpleInputMethodWindow;

/**
 *
 * @author Omar
 */
public class Item {

    private final IntegerProperty id;
    private final StringProperty name;
    private final StringProperty tags;
    private final IntegerProperty quantity;
    private final StringProperty unit;
    private final DoubleProperty unitPrice;
    private final StringProperty comments;

    public Item() {
        this.id = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        this.tags = new SimpleStringProperty();
        this.quantity = new SimpleIntegerProperty();
        this.unit = new SimpleStringProperty();
        this.unitPrice = new SimpleDoubleProperty();
        this.comments = new SimpleStringProperty();
    }

    public Item(int id, String name, String tags,int quantity, String unit,double unitPrice, String comments) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.tags = new SimpleStringProperty(tags);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.unit = new SimpleStringProperty(unit);
        this.unitPrice = new SimpleDoubleProperty(unitPrice);
        this.comments = new SimpleStringProperty(comments);
    }

    /**
     * @return the id
     */
    public IntegerProperty idProperty() {
        return id;
    }

    public int getId() {
        return id.get();
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id.set(id);
    }

    /**
     * @return the name
     */
    public StringProperty nameProperty() {
        return name;
    }

    public String getName() {
        return name.get();
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name.set(name);
    }

    /**
     * @return the tags
     */
    public StringProperty tagsProperty() {
        return tags;
    }

    /**
     * 
     * @return tags
     */
    public String getTags() {
        return tags.get();
    }

    /**
     * @param tags the tags to set
     */
    public void setTags(String tags) {
        this.tags.set(tags);
    }
    
    /**
     * @return the tags
     */
    public IntegerProperty quantityProperty() {
        return quantity;
    }

    /**
     * 
     * @return quantity as integer value.
     */
    public int getQuantity() {
        return quantity.get();
    }

    /**
     * @param quantity the value to be set
     */
    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    /**
     * @return the unit
     */
    public StringProperty unitProperty() {
        return unit;
    }

    public String getUnit() {
        return unit.get();
    }

    /**
     * @param unit the unit to set
     */
    public void setUnit(String unit) {
        this.unit.set(unit);
    }
    
    /**
     * @return the id
     */
    public DoubleProperty unitPriceProperty() {
        return unitPrice;
    }

    public double getUnitPrice() {
        return unitPrice.get();
    }

    /**
     * @param unitPrice the id to set
     */
    public void setUnitPrice(double unitPrice) {
        this.unitPrice.set(unitPrice);
    }
    
    /**
     * @return the comments
     */
    public StringProperty commentsProperty() {
        return comments;
    }

    public String getComments() {
        return comments.get();
    }

    /**
     * @param comments the comments to set
     */
    public void setComments(String comments) {
        this.comments.set(comments);
    }

    @Override
    public String toString() {
        return getName();
    }
    
    

}
