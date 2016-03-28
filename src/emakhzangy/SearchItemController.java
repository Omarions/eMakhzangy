/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emakhzangy;

import emakhzangy.controller.cItem;
import emakhzangy.model.Item;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Omar
 */
public class SearchItemController implements Initializable {

    @FXML
    private ComboBox<Integer> cmbID;
    @FXML
    private ComboBox<String> cmbName;
    @FXML
    private ComboBox<String> cmbUnit;
    @FXML
    private TextField tfMinPrice;
    @FXML
    private TextField tfMaxPrice;
    @FXML
    private TextArea taTags;
    @FXML
    private Button btnCancel;
    @FXML
    private TableView<Item> tblItems;
    @FXML
    private TableColumn<Item, Integer> colID;
    @FXML
    private TableColumn<Item, String> colName;
    @FXML
    private TableColumn<Item, String> colUnit;
    @FXML
    private TableColumn<Item, Double> colUnitPrice;
    @FXML
    private TableColumn<Item, String> colTags;
    @FXML
    private TableColumn<Item, String> colComments;

    //Lists for info of parts, id, name, unit.
    private ObservableList<Item> itemsList;
    private ObservableList<Integer> idList;
    private ObservableList<String> namesList;
    private ObservableList<String> unitsList;
    private ObservableList<String> distinctUnitsList;

    private static String keyPressed = "";

    private Callback<TableColumn<Item, String>, TableCell<Item, String>> cellFactory;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //init lists
        itemsList = FXCollections.observableArrayList();
        idList = FXCollections.observableArrayList();
        namesList = FXCollections.observableArrayList();
        unitsList = FXCollections.observableArrayList();
        distinctUnitsList = FXCollections.observableArrayList();

        //load items list with data from db
        itemsList.addAll(new cItem().getAll());
        //fill each list of (id, names and units) with data from main list.
        itemsList.stream().forEach((i) -> {
            idList.add(i.getId());
            namesList.add(i.getName());
            unitsList.add(i.getUnit());
        });
        //fill only distict units in new list.
        unitsList.stream().distinct().forEach(u -> distinctUnitsList.add(u));

        //assign lists with comboboxes
        cmbID.setItems(idList);
        cmbName.setItems(namesList);
        cmbUnit.setItems(distinctUnitsList);    //assign distinct values to combobox.

        //cellFactory = (TableColumn<Item, String> p) -> new EditingCell();

        //assign every cell with its data. (asObject() required for any type except String)
        colID.setCellValueFactory(
                cellData -> cellData.getValue().idProperty().asObject());
        colName.setCellValueFactory(
                cellData -> cellData.getValue().nameProperty());
        
        colUnit.setCellValueFactory(
                cellData -> cellData.getValue().unitProperty());
        colUnitPrice.setCellValueFactory(
                cellData -> cellData.getValue().unitPriceProperty().asObject());
        colTags.setCellValueFactory(
                cellData -> cellData.getValue().tagsProperty());
        colComments.setCellValueFactory(
                cellData -> cellData.getValue().commentsProperty());

        //Add new columen for delete button for every row.
        TableColumn<Item, Item> colDeleteItem = new TableColumn<>();
        colDeleteItem.setMinWidth(20);
        colDeleteItem.setCellValueFactory(
                param -> new ReadOnlyObjectWrapper<>(param.getValue()));

        colDeleteItem.setCellFactory(param -> new TableCell<Item, Item>() {
            private final Button btnDelete = new Button("", new ImageView(new Image(getClass().getResource("images/delete-icon.png").toExternalForm())));
            private final Button btnEdit = new Button("", new ImageView(new Image(getClass().getResource("images/adwords_editor-32.png").toExternalForm())));
            private final HBox hbox = new HBox(10, btnDelete, btnEdit);

            @Override
            protected void updateItem(Item item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null) {
                    setGraphic(null);
                    return;
                }

                setGraphic(hbox);
                
                btnEdit.setOnAction(event -> {
                    try {
                        //load the form of Edit Item, and pass to it the item to be updated.
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("EditItemForm.fxml"));
                        AnchorPane page = (AnchorPane) loader.load();

                        //crate the dialog stage
                        Stage dialogStage = new Stage();
                        dialogStage.initModality(Modality.WINDOW_MODAL);
                        dialogStage.initOwner(((Node) event.getSource()).getScene().getWindow());
                        dialogStage.initStyle(StageStyle.TRANSPARENT);
                        
                        Scene scene = new Scene(page);
                        scene.setFill(null);
                        dialogStage.setScene(scene);
                        
                        //set the item in the controller
                        EditItemFormController editItemController = loader.getController();
                        editItemController.setDialogStage(dialogStage);
                        editItemController.setPerson(item);

                        // Show the dialog and wait until the user closes it
                        dialogStage.showAndWait();

                    } catch (IOException ex) {
                        Logger.getLogger(SearchItemController.class.getName()).log(Level.SEVERE, null, ex);
                    }
/*
                    if (tgEdit.isSelected()) {
                        tgEdit.setGraphic(
                                new ImageView(
                                        new Image(getClass()
                                                .getResource("images/save_edit.png")
                                                .toExternalForm())));
                        //make the table editable.
                        tblItems.setEditable(true);
                        //To make cells etidable
                        colName.setCellFactory(cellFactory);
                        colUnit.setCellFactory(cellFactory);
                        colUnitPrice.setCellFactory(
                                TextFieldTableCell.forTableColumn(
                                        new StringConverter<Double>() {
                                    @Override
                                    public String toString(Double object) {
                                        return String.valueOf(object);
                                    }

                                    @Override
                                    public Double fromString(String string) {
                                        return Double.valueOf(string);
                                    }
                                })
                        );
                        colTags.setCellFactory(cellFactory);
                        colComments.setCellFactory(cellFactory);

                    } else {
                        tgEdit.setGraphic(new ImageView(new Image(getClass().getResource("images/adwords_editor-32.png").toExternalForm())));
                    }
                    */
                });

                btnDelete.setOnAction(event -> {

                    Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION);
                    alertConfirm.setContentText("Do you really want to remove item(#" + item.getId() + ")?");
                    alertConfirm.setTitle("Removing Item...");

                    Optional<ButtonType> result = alertConfirm.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        int removeResult = new cItem().remove(item.getId());
                        if (removeResult == 1) {
                            itemsList.remove(item);
                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error...");
                            alert.setContentText("The item(# " + item.getId() + ") failed to be removed due to Database Error...");
                            alert.show();
                        }
                    }

                });
            }

        });

        tblItems.getColumns().add(0, colDeleteItem);

        //assign the main list to tableview.
        tblItems.setItems(itemsList);
        btnCancel.setFocusTraversable(false);

    }

    public void save(ActionEvent event) {

    }

    public void cancel(ActionEvent event) {
        System.out.println("Cancel pressed");
        //int rowPos = tblItems.getSelectionModel().getTableView().getEditingCell().getRow();
        //tblItems.getColumns().get(rowPos).onEditCancelProperty();

        colName.setOnEditCancel((CellEditEvent<Item, String> cee) -> {
            cee.getRowValue().setName(cee.getOldValue());
        });

        colUnit.setOnEditCancel((CellEditEvent<Item, String> cee) -> {
            cee.getRowValue().setUnit(cee.getOldValue());
        });

        colUnitPrice.setOnEditCancel((CellEditEvent<Item, Double> cee) -> {
            ((Item) cee.getTableView().getItems().get(cee.getTablePosition().getRow()))
                    .setUnitPrice(cee.getOldValue());
        });
        colTags.setOnEditCancel((CellEditEvent<Item, String> cee) -> {
            ((Item) cee.getTableView().getItems().get(cee.getTablePosition().getRow()))
                    .setTags(cee.getOldValue());
        });
        colComments.setOnEditCancel((CellEditEvent<Item, String> cee) -> {
            ((Item) cee.getTableView().getItems().get(cee.getTablePosition().getRow()))
                    .setComments(cee.getOldValue());
        });

    }

    public void print(ActionEvent event) {

    }

    /**
     * Close the window and return to main window.
     *
     * @param event
     */
    public void close(ActionEvent event) {
        //hide the form.
        ((Node) event.getSource()).getScene().getWindow().hide();
    }

    /**
     * Action event handler for combobox of IDs It filters unit combobox of
     * units to selected ID
     *
     * @param event
     */
    public void idChanged(ActionEvent event) {
        ObservableList<Item> filteredList = FXCollections.observableArrayList();
        //get the selected value
        int id = (int) ((ComboBox) event.getSource()).getValue();
        //filter list to the selected value and fill new list with filtered items.
        itemsList.stream().filter((p) -> ((Item) p).getId() == id)
                .forEach((i) -> filteredList.add(i));
        //assign the new list to the table
        tblItems.setItems(filteredList);
        reset();
    }

    /**
     * Action event handler for combobox of names It filters unit combobox of
     * units to selected name
     *
     * @param event
     */
    public void nameChanged(ActionEvent event) {
        ObservableList<Item> filteredList = FXCollections.observableArrayList();
        //get the selected value.
        String name = (String) ((ComboBox) event.getSource()).getValue();
        //filter list to the selected value and fill new list with filtered items.
        itemsList.stream().filter((p) -> ((Item) p).getName().equalsIgnoreCase(name))
                .forEach((i) -> filteredList.add(i));
        //assign the new list to the table
        tblItems.setItems(filteredList);
        reset();
    }

    /**
     * Action event handler for combobox of units It filters unit combobox of
     * units to selected unit
     *
     * @param event
     */
    public void unitChanged(ActionEvent event) {
        ObservableList<Item> filteredList = FXCollections.observableArrayList();
        //get the selected value.
        String unit = (String) ((ComboBox) event.getSource()).getValue();
        //filter list to the selected value and fill new list with filtered items.
        itemsList.stream().filter((p) -> ((Item) p).getUnit().equals(unit))
                .forEach((i) -> filteredList.add(i));
        //assign the new list to the table
        tblItems.setItems(filteredList);
        reset();
    }

    /**
     * Key released event handler for combobox of IDs It filters combobox to
     * written characters.
     *
     * @param event
     */
    public void idKeyReleased(KeyEvent event) {
        ObservableList<Integer> filteredList = FXCollections.observableArrayList();
        //check if no item selected (in case that clear the value and try to write new value)
        if (cmbID.getEditor().getText().isEmpty()) {
            cmbID.setItems(idList);
            keyPressed = "";
        } else {
            keyPressed += event.getText();      //append key pressed to previous keys.

            idList.stream().filter(
                    (i) -> String.valueOf(i).startsWith(keyPressed))
                    .forEach(
                            (i) -> filteredList.add(i)
                    );                          //filter to written charachters.
            cmbID.setItems(filteredList);       //assign filtered list to combobox.
            cmbID.show();                       //to the list down.
        }
    }

    /**
     * Key released event handler for combobox of names It filters combobox to
     * written characters.
     *
     * @param event
     */
    public void nameKeyReleased(KeyEvent event) {
        ObservableList<String> filteredList = FXCollections.observableArrayList();
        //check if no item selected (in case that clear the value and try to write new value)
        if (cmbName.getEditor().getText().isEmpty()) {
            cmbName.setItems(namesList);        //assign original list.
            keyPressed = "";                    //reset keyPressed variable.
        } else {

            keyPressed += event.getText();      //append key pressed to previous keys.
            namesList.stream().filter(
                    (i) -> i.toLowerCase().startsWith(keyPressed.toLowerCase()))
                    .forEach(
                            (i) -> filteredList.add(i)
                    );                          //filter to written charachters.
            cmbName.setItems(filteredList);     //assign filtered list to combobox.
            cmbName.show();                     //to the list down.
        }
    }

    /**
     * Reset Comboboxes.
     */
    private void reset() {
        cmbID.setItems(idList);
        cmbName.setItems(namesList);
        cmbUnit.setItems(unitsList);

    }

    class EditingCell extends TableCell<Item, String> {

        private TextField textField;

        public EditingCell() {
        }

        @Override
        public void startEdit() {
            if (!isEmpty()) {
                super.startEdit();
                createTextField();
                setText(null);
                setGraphic(textField);
                textField.selectAll();
            }
        }

        @Override
        public void cancelEdit() {
            //super.cancelEdit();

            setText((String) getItem());
            setGraphic(null);
        }

        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);

            if (empty) {
                setText(null);
                setGraphic(null);
            } else if (isEditing()) {
                if (textField != null) {
                    textField.setText(getString());
                }
                setText(null);
                setGraphic(textField);
            } else {
                setText(getString());
                setGraphic(null);
            }
        }

        private void createTextField() {
            textField = new TextField(getString());
            textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);

        }

        private String getString() {
            return getItem() == null ? "" : getItem();
        }
    }

}
