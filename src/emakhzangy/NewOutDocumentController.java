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
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author Omar
 */
public class NewOutDocumentController implements Initializable {
    @FXML
    private TextField tfTo;
    @FXML
    private TextField tfFrom;
    @FXML
    private DatePicker dpDocumentDate;
    @FXML
    private TextArea taComments;
    @FXML
    private ComboBox<Project> cmbProjects;
    @FXML
    private ComboBox<Item> cmbItems;
    @FXML
    private Spinner spnQty;
    @FXML
    private TableView<Item> tblItems;

    @FXML
    private TableColumn<Item, Integer> colID;
    @FXML
    private TableColumn<Item, String> colName;
    @FXML
    private TableColumn<Item, Integer> colQty;
    @FXML
    private TableColumn<Item, Item> colRemoveItem;

    private ObservableList<Item> tblItemsList;      //for the items showed in the table
    private ObservableList<Project> listProjects;   //for the projects 
    private ObservableList<Item> listItems;         //for the items showed in combobox.

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initComboBoxes();
        spnQty.setValueFactory(new IntegerSpinnerValueFactory(0, 1000));
        spnQty.increment(1);
        spnQty.decrement(1);

        listItems = FXCollections.observableArrayList();
        tblItemsList = FXCollections.observableArrayList();

        colID.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        colName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        colQty.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());
        colRemoveItem.setCellFactory(param -> new TableCell<Item, Item>() {
            private final Button btnDelete = new Button("", new ImageView(new Image(getClass().getResource("images/delete-icon.png").toExternalForm())));

            @Override
            protected void updateItem(Item item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null) {
                    setGraphic(null);
                    return;
                }

                setGraphic(btnDelete);

                btnDelete.setOnAction(event -> {
                    Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION);
                    alertConfirm.setContentText("Do you really want to remove item(#" + item.getId() + ")?");
                    alertConfirm.setTitle("Removing Item...");

                    Optional<ButtonType> result = alertConfirm.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        tblItemsList.remove(item);
                    }

                });
            }
        });
        colRemoveItem.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));

        tblItems.itemsProperty().bindBidirectional(new SimpleObjectProperty<>(tblItemsList));

        tblItems.setItems(tblItemsList);

    }
    
    /**
     * Save the document to DB.
     * @param event 
     */
    public void save(ActionEvent event){
        String toName = tfTo.getText();
        String fromName = tfFrom.getText();
        LocalDate documentDate = dpDocumentDate.getValue();
        String comments = taComments.getText();
        
        
    }

    /**
     * Hide the form
     *
     * @param event
     */
    public void cancel(ActionEvent event) {
        Utils.hideForm(((Node) event.getSource()).getScene().getWindow());
    }

    /**
     * Add the item to the document and show it in the table.
     *
     * @param event
     */
    public void addItem(ActionEvent event) {
        //check if there is available qty for this item before adding it.
        int qty = ((Integer) spnQty.getValue());
        int availableQty = cmbItems.getValue().getQuantity();
        if (availableQty >= qty) {
            Item item = new Item();
            item.setId(cmbItems.getValue().getId());
            item.setName(cmbItems.getValue().getName());
            item.setQuantity(qty);

            tblItemsList.add(item);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error...");
            alert.setContentText("The item(# " + cmbItems.getValue().getId() + ") has not enough quantity...");
            alert.show();
        }

    }

    /**
     * Initiating the comboboxes with data.
     */
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
