/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emakhzangy.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 *
 * @author Omar
 */
public class Inventory {
    private final IntegerProperty id;
    private final ObjectProperty<Item> item;
    private final IntegerProperty availableQty;

    public Inventory() {
        this.id = new SimpleIntegerProperty();
        this.item =new SimpleObjectProperty<>();
        this.availableQty = new SimpleIntegerProperty() ;
    }
    
    public Inventory(Item item, int availableQty) {
        this();
        this.item.set(item);
        this.availableQty.set(availableQty) ;
    }

    /**
     *
     * @param id
     * @param item
     * @param availableQty
     */
    public Inventory(int id, Item item, int availableQty) {
        this();
        this.id.set(id);
        this.item.set(item);
        this.availableQty.set(availableQty) ;
    }

    /**
     * @return the id
     */
    public IntegerProperty idProperty() {
        return id;
    }

    public int getId(){
        return id.get();
    }
    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id.set(id);
    }

    /**
     * @return the itemId
     */
    public ObjectProperty<Item> itemProperty() {
        return item;
    }
    
    public Item getItem(){
        return item.get();
    }

    /**
     * @param item
     */
    public void setItem(Item item) {
        this.item.set(item);
    }

    /**
     * @return the availableQty
     */
    public IntegerProperty availableQtyProperty() {
        return availableQty;
    }

    /**
     * 
     * @return 
     */
    public int getAvailableQty(){
        return availableQty.get();
    }
    
    /**
     * @param availableQty
     */
    public void setAvailableQty(int availableQty) {
        this.availableQty.set(availableQty);
    }

    @Override
    public String toString() {
        return super.toString(); //To change body of generated methods, choose Tools | Templates.
    }

    
}
