/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emakhzangy;

import emakhzangy.controller.cItem;
import emakhzangy.model.Item;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Omar
 */
public class NewItemFormController implements Initializable {

    @FXML
    private TextField tfName;
    @FXML
    private TextField tfUnit;
    @FXML
    private TextField tfUnitPrice;
    @FXML
    private TextArea taTags;
    @FXML
    private TextArea taComments;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void save(ActionEvent event) {
        String name = tfName.getText();
        String unit = tfUnit.getText();
        double unitPrice = Double.parseDouble(tfUnitPrice.getText());
        String tags = taTags.getText();
        String comments = taComments.getText();

        Item item = new Item(0, name, tags, unit, unitPrice, comments);

        int addResult = new cItem().add(item);
        if (addResult > 0) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setHeaderText("New Item Added...");
            alert.setContentText("Adding new Item is successfull..., Do you want to add more?");
            alert.setResult(ButtonType.NO);
            alert.setResult(ButtonType.YES);
            ButtonType choice = alert.getResult();
            if (choice == ButtonType.YES) {
                clearFields();
            } else {
                ((Node) event.getSource()).getScene().getWindow().hide();
            }
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(null);
            alert.setTitle("Saving Error...");
            alert.setHeaderText("Error With Database...");
            alert.setContentText("There is something wrong happened while saving to database...");

            alert.showAndWait();
            
        }
    }

    public void cancel(ActionEvent event) {
        ((Node) event.getSource()).getScene().getWindow().hide();
    }

    private void clearFields() {
        tfName.clear();
        tfUnit.clear();
        tfUnitPrice.clear();
        taTags.clear();
        taComments.clear();
    }

}
