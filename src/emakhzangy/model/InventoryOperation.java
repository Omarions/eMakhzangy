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
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Omar
 */
public class InventoryOperation {
    private final IntegerProperty id;
    private final ObjectProperty<Inventory> inventory;
    private final ObjectProperty<Document> document;
    private final ObjectProperty<Project> project;
    private final IntegerProperty quantity;   
    private final StringProperty comments;

    public InventoryOperation() {
        this.id = new SimpleIntegerProperty();
        this.inventory = new SimpleObjectProperty<>();
        this.document = new SimpleObjectProperty<>();
        this.project = new SimpleObjectProperty<>();
        this.quantity = new SimpleIntegerProperty();
        this.comments = new SimpleStringProperty();
    }
    
    public InventoryOperation(Inventory inventory, Document document, Operation operation, Project project, int quantity, String comments) {
        this();
        this.inventory.set(inventory);
        this.document.set(document);
        this.project.set(project);
        this.quantity.set(quantity);
        this.comments.set(comments);
    }

    public InventoryOperation(int id,Inventory inventory, Document document, Operation operation, Project project, int quantity, String comments) {
        this();
        this.id.set(id);
        this.inventory.set(inventory);
        this.document.set(document);
        this.project.set(project);
        this.quantity.set(quantity);
        this.comments.set(comments);
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
     * @return the document
     */
    public ObjectProperty<Inventory> inventoryProperty() {
        return inventory;
    }

    public Inventory getInventory(){
        return inventory.get();
    }
    
    /**
     * @param inventory
     */
    public void setInventory(Inventory inventory) {
        this.inventory.set(inventory);
    }

    /**
     * @return the document
     */
    public ObjectProperty<Document> documentProperty() {
        return document;
    }

    public Document getDocument(){
        return document.get();
    }
    
    /**
     * @param document the document to set
     */
    public void setDocument(Document document) {
        this.document.set(document);
    }

    /**
     * @return the project
     */
    public ObjectProperty<Project> projectProperty() {
        return project;
    }

    public Project getProject(){
        return project.get();
    }
    
    /**
     * @param project the project to set
     */
    public void setProject(Project project) {
        this.project.set(project);
    }

    /**
     * @return the quantity
     */
    public IntegerProperty quantityProperty() {
        return quantity;
    }

    public int getQuantity(){
        return quantity.get();
    }
    
    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    /**
     * @return the comments
     */
    public StringProperty commentsProperty() {
        return comments;
    }

    public String getComments(){
        return comments.get();
    }
    
    /**
     * @param comments the comments to set
     */
    public void setComments(String comments) {
        this.comments.set(comments);
    }
    
    
}
