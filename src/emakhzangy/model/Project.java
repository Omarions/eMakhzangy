/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emakhzangy.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Omar
 */
public class Project {
    private final IntegerProperty id;
    private final StringProperty name;
    private final StringProperty managerName;
    private final StringProperty comments;

    public Project() {
        this.id = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        this.managerName = new SimpleStringProperty();
        this.comments = new SimpleStringProperty();
    }

    public Project(int id, String name, String managerName, String comments) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.managerName = new SimpleStringProperty(managerName);
        this.comments = new SimpleStringProperty(comments);
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
     * @return the name
     */
    public StringProperty nameProperty() {
        return name;
    }

    public String getName(){
        return name.get();
    }
    
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name.set(name);
    }

    /**
     * @return the managerName
     */
    public StringProperty managerNameProperty() {
        return managerName;
    }

    public String getManagerName(){
        return managerName.get();
    }
    
    /**
     * @param managerName the managerName to set
     */
    public void setManagerName(String managerName) {
        this.managerName.set(managerName);
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

    @Override
    public String toString() {
        return getName();
    }
    
    
    
}
