/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emakhzangy.model;

import java.time.LocalDate;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Omar
 */
public class Document {
    private IntegerProperty id;
    private ObjectProperty<Operation> operation;
    private StringProperty from;
    private StringProperty to;
    private ObjectProperty<LocalDate> date;  
    private StringProperty comments;

    public Document() {
        this.id = new SimpleIntegerProperty();
        this.operation = new SimpleObjectProperty<>();
        this.from = new SimpleStringProperty();
        this.to = new SimpleStringProperty();
        this.date = new SimpleObjectProperty<>();
        this.comments = new SimpleStringProperty();
    }

    public Document(int id, Operation operation, String from, String to
            , LocalDate date, String comments) {
        
        this.id = new SimpleIntegerProperty(id);
        this.operation = new SimpleObjectProperty<>(operation);
        this.from = new SimpleStringProperty(from);
        this.to = new SimpleStringProperty(to);
        this.date = new SimpleObjectProperty<>(date);
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
     * @return the operation
     */
    public ObjectProperty<Operation> operationProperty() {
        return operation;
    }

    public Operation getOperation(){
        return operation.get();
    }
    
    /**
     * @param operation the operation to set
     */
    public void setOperation(Operation operation) {
        this.operation.set(operation);
    }

    /**
     * @return the supplierName
     */
    public StringProperty fromProperty() {
        return from;
    }

    public String getFrom(){
        return from.get();
    }
    
    /**
     * @param from the supplierName to set
     */
    public void setFrom(String from) {
        this.from.set(from);
    }

    /**
     * @return the supplierName
     */
    public StringProperty toProperty() {
        return to;
    }

    public String getTo(){
        return to.get();
    }
    
    /**
     * @param to
     */
    public void setTo(String to) {
        this.to.set(to);
    }
    
    /**
     * @return the date
     */
    public ObjectProperty<LocalDate> dateProperty() {
        return date;
    }

    public LocalDate getDate(){
        return date.get();
    }
    
    /**
     * @param date the date to set
     */
    public void setDate(LocalDate date) {
        this.date.set(date);
    }

    /**
     * @return the supplierName
     */
    public StringProperty commentsProperty() {
        return from;
    }

    public String getComments(){
        return from.get();
    }
    
    /**
     * @param comments
     */
    public void setComments(String comments) {
        this.comments.set(comments);
    }
}
