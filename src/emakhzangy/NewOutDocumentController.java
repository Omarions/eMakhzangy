/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emakhzangy;

import emakhzangy.controller.cItem;
import emakhzangy.controller.cProject;
import emakhzangy.model.Item;
import emakhzangy.model.Project;
import emakhzangy.util.Utils;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;

/**
 * FXML Controller class
 *
 * @author Omar
 */
public class NewOutDocumentController implements Initializable {
    @FXML
    private ComboBox<Project> cmbProjects;
    @FXML
    private ComboBox<Item> cmbItems;
    @FXML
    private Spinner spnQty;
    
    private ObservableList<Project> listProjects;
    private ObservableList<Item> listItems;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        initComboBoxes();
        spnQty.setValueFactory(new IntegerSpinnerValueFactory(0,1000));
        spnQty.increment(1);
        spnQty.decrement(1);
    }    
    
    public void cancel(ActionEvent event){
        Utils.hideForm(((Node)event.getSource()).getScene().getWindow());
    }
    
    private void initComboBoxes() {
        //load list of projects
        listProjects
                = FXCollections.observableList(new cProject().getProjects());
        //load list of projects
        listItems
                = FXCollections.observableList(new cItem().getAll());
        cmbProjects.getItems().add(new Project());
        cmbItems.getItems().add(new Item());
        //assign list of project to comboBox of projects
        cmbProjects.setItems(listProjects);
        cmbItems.setItems(listItems);
        
    }
    
}
