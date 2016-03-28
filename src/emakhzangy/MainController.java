/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emakhzangy;

import emakhzangy.controller.cInventoryOperation;
import emakhzangy.controller.cOperation;
import emakhzangy.controller.cProject;
import emakhzangy.model.InventoryOperation;
import emakhzangy.model.Operation;
import emakhzangy.model.Project;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Omar
 */
public class MainController implements Initializable {

    @FXML
    private TableView<InventoryOperation> tblMain;
    @FXML
    private TableColumn<InventoryOperation, Integer> tcInvenOpID;
    @FXML
    private TableColumn<InventoryOperation, String> tcItemName;
    @FXML
    private TableColumn<InventoryOperation, String> tcOperationType;
    @FXML
    private TableColumn<InventoryOperation, Integer> tcDocumentSerial;
    @FXML
    private TableColumn<InventoryOperation, String> tcProjectName;
    @FXML
    private ComboBox<Project> cmbProjects;
    @FXML
    private ComboBox<Operation> cmbOperations;
    @FXML
    private DatePicker dpStartDate;
    @FXML
    private DatePicker dpEndDate;
    @FXML
    private Label lblCountRows;
    
    private Stage primaryStage;
    
    private ObservableList<InventoryOperation> list;
    private ObservableList<Project> listProjects;
    private ObservableList<Operation> listOperations;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        //initialize the comboboxes of operations and projects.
        initComboBoxes();
        //load the inventory_operation list from database.
        list = FXCollections.observableArrayList(new cInventoryOperation().getAll());
        //set the columns with values from the list.
        tcInvenOpID.setCellValueFactory(
                cellData -> cellData.getValue().idProperty().asObject()
        );
        tcItemName.setCellValueFactory(
                cellData -> cellData.getValue()
                .getInventory().getItem().nameProperty()
        );
        tcOperationType.setCellValueFactory(
                cellData -> cellData.getValue()
                .getDocument().getOperation().nameProperty()
        );
        tcDocumentSerial.setCellValueFactory(
                cellData -> cellData.getValue().getDocument().idProperty().asObject()
        );
        tcProjectName.setCellValueFactory(
                cellData -> cellData.getValue().getProject().nameProperty()
        );

        tblMain.setItems(list);

        lblCountRows.textProperty().bind(tblMain.getSelectionModel().selectedIndexProperty().asString());
    }

    private void initComboBoxes() {
        //load list of projects
        listProjects
                = FXCollections.observableList(new cProject().getProjects());
        //load list of operations
        listOperations
                = FXCollections.observableArrayList(new cOperation().getOperations());
        cmbProjects.getItems().add(new Project());
        cmbOperations.getItems().add(new Operation());
        //assign list of project to comboBox of projects
        cmbProjects.setItems(listProjects);
        //assign list of operations to comboBox of operations
        cmbOperations.setItems(listOperations);
    }

    public void search(ActionEvent event) {
        //list for filterd items from origin list.
        ObservableList<InventoryOperation> listFiltered
                = FXCollections.observableArrayList();
        //start and last date from datepickers.
        LocalDate startDate = dpStartDate.getValue();
        LocalDate endDate = dpEndDate.getValue();

        if (startDate != null) {
            if (endDate != null) {
                //Get the stream of list and filter it to the start date selected 
                //until the end date selected 
                //and then add all matched InventoryOperation to the new list.
                list.stream()
                        .filter((io) -> ((io.getDocument().getDate().equals(startDate)
                                || io.getDocument().getDate().isAfter(startDate))
                                && ((io.getDocument().getDate().equals(endDate)
                                || io.getDocument().getDate().isBefore(endDate)))))
                        .forEach((e) -> listFiltered.add(e));

                //Assign the new list to the table
                tblMain.setItems(listFiltered);
                return;
            }
            //Get the stream of list and filter it to the date selected 
            //and then add all matched InventoryOperation to the new list.
            list.stream()
                    .filter((io) -> io.getDocument().getDate().equals(startDate))
                    .forEach((e) -> listFiltered.add(e));

            //Assign the new list to the table
            tblMain.setItems(listFiltered);
        }
    }

    /**
     * Change table content when change project
     *
     * @param event
     */
    public void projectChanged(ActionEvent event) {
        ObservableList<InventoryOperation> listFiltered
                = FXCollections.observableArrayList();
        //check if the selected value is null or not(this because Reset button 
        //sets the value to null)
        if (((ComboBox<Project>) event.getSource()).getValue() != null) {
            //Get the selected project
            String projectName
                    = ((ComboBox<Project>) event.getSource()).getValue().getName();
            //Get the stream of list and filter it to project selected and then
            //add all matched InventoryOperation to the new list.
            list.stream()
                    .filter((io) -> io.getProject().getName().equals(projectName))
                    .forEach((e) -> listFiltered.add(e));

            //Assign the new list to the table.
            tblMain.setItems(listFiltered);
        }
    }

    /**
     * Filter table content by changing Operation
     *
     * @param event
     */
    public void OperationChanged(ActionEvent event) {
        ObservableList<InventoryOperation> listFilterd
                = FXCollections.observableArrayList();
        //check if the selected value is null or not(this because Reset button 
        //sets the value to null)
        if (((ComboBox<Operation>) event.getSource()).getValue() != null) {
            //Get the selected Operation
            String operationName
                    = ((ComboBox<Operation>) event.getSource()).getValue().getName();

            //Get the stream of list and filter it to the operation selected and then
            //add all matched InventoryOperation to the new list.
            list.stream()
                    .filter((io) -> io.getDocument().getOperation().getName().equals(operationName))
                    .forEach((e) -> listFilterd.add(e));

            //Assign the new list to the table
            tblMain.setItems(listFilterd);
        }
    }

    /**
     * Clear search criteria.
     *
     * @param event
     */
    public void reset(ActionEvent event) {
        //Reset comboboxes.
        cmbOperations.setValue(null);
        cmbProjects.setValue(null);
        //Reset datepickers.
        dpStartDate.setValue(null);
        dpEndDate.setValue(null);

        //assign original list to the table
        tblMain.setItems(list);
    }
    
    /**
     * Show the form for adding new part.
     * @param event 
     */
    public void showNewItemForm(ActionEvent event){
        try {
            //load NewItemForm fxml file.
            Parent root = FXMLLoader.load(getClass().getResource("NewItemForm.fxml"));
            Scene scene = new Scene(root);
            scene.setFill(null);
            Stage stage = new Stage();
            stage.setTitle("New Item...");
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(primaryStage);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    /**
     * Show the form of searching in parts table.
     * @param event 
     */
    public void showSearchItemForm(ActionEvent event){
        try {
            //load NewItemForm fxml file.
            Parent root = FXMLLoader.load(getClass().getResource("SearchItemForm.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Search Item...");
            scene.setFill(null);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
