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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Omar
 */
public class EditItemFormController implements Initializable {

    @FXML
    private TitledPane titledPane;
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

    private Stage dialogStage;
    private Item item;
    private boolean saveClicked = false;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void start(Item item) {

    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Sets the item to be edited in the dialog.
     *
     * @param item
     */
    public void setPerson(Item item) {
        this.item = item;
        //loading the form with item's info.
        titledPane.setText("Update Item(# " + item.getId() + ")...");
        tfName.setText(item.getName());
        tfUnit.setText(item.getUnit());
        tfUnitPrice.setText(String.valueOf(item.getUnitPrice()));
        taTags.setText(item.getTags());
        taComments.setText(item.getComments());

    }

    /**
     * Returns true if the user clicked OK, false otherwise.
     *
     * @return
     */
    public boolean isSaveClicked() {
        return saveClicked;
    }

    /**
     * Called when the user clicks save.
     */
    @FXML
    private void handleSave() {
        if (isInputValid()) {
            item.setName(tfName.getText());
            item.setUnit(tfUnit.getText());
            item.setUnitPrice(Double.parseDouble(tfUnitPrice.getText()));
            item.setTags(taTags.getText());
            item.setComments(taComments.getText());

            saveClicked = true;

            int result = new cItem().update(item.getId(), item);
            if (result > 0) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setHeaderText("Updating Item...");
                alert.setContentText("Item(#" + item.getId() + ") has been updated successfully!");
                alert.showAndWait();
            } else {
                // Show the error message.
                Alert alert = new Alert(AlertType.ERROR);
                alert.initOwner(dialogStage);
                alert.setTitle("Saving Error...");
                alert.setHeaderText("Error With Database...");
                alert.setContentText("There is something wrong happened while saving to database...");

                alert.showAndWait();

            }
            dialogStage.close();
        }
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    /**
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (tfName.getText() == null || tfName.getText().length() == 0) {
            errorMessage += "No valid name!\n";
        }
        if (tfUnit.getText() == null || tfUnit.getText().length() == 0) {
            errorMessage += "No valid unit!\n";
        }
        if (tfUnitPrice.getText() == null || tfUnitPrice.getText().length() == 0) {
            errorMessage += "No valid price!\n";
        } else {
            // try to parse the postal code into an int.
            try {
                Double.parseDouble(tfUnitPrice.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid price (must be an integer)!\n";
            }
        }

        if (taTags.getText() == null || taTags.getText().length() == 0) {
            errorMessage += "No valid tags!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }

}
