/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emakhzangy.model;

import java.time.LocalDate;
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
public class Document {
    private final IntegerProperty id;
    private final ObjectProperty<Operation> operation;
    private final StringProperty from;
    private final StringProperty to;
    private final ObjectProperty<LocalDate> date;  
    private final StringProperty comments;

    public Document() {
        this.id = new SimpleIntegerProperty();
        this.operation = new SimpleObjectProperty<>();
        this.from = new SimpleStringProperty();
        this.to = new SimpleStringProperty();
        this.date = new SimpleObjectProperty<>();
        this.comments = new SimpleStringProperty();
    }
    
    public Document(Operation operation, String from, String to
            , LocalDate date, String comments) {
        
        this();

        this.operation.set(operation);
        this.from.set(from);
        this.to.set(to);
        this.date.set(date);
        this.comments.set(to);
    }

    public Document(int id, Operation operation, String from, String to
            , LocalDate date, String comments) {
        
        this();

        this.id.set(id);
        this.operation.set(operation);
        this.from.set(from);
        this.to.set(to);
        this.date.set(date);
        this.comments.set(to);
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
        return comments;
    }

    public String getComments(){
        return comments.get();
    }
    
    /**
     * @param comments
     */
    public void setComments(String comments) {
        this.comments.set(comments);
    }
}
