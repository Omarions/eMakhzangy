/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emakhzangy;

import emakhzangy.controller.DBConnection;
import emakhzangy.controller.cDocument;
import emakhzangy.controller.cInventory;
import emakhzangy.controller.cInventoryOperation;
import emakhzangy.controller.cItem;
import emakhzangy.controller.cProject;
import emakhzangy.model.Document;
import emakhzangy.model.Inventory;
import emakhzangy.model.InventoryOperation;
import emakhzangy.model.Item;
import emakhzangy.model.Operation;
import emakhzangy.model.Project;
import emakhzangy.util.Utils;
import java.io.InputStream;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JRPropertiesHolder;
import net.sf.jasperreports.engine.JRPropertiesMap;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.base.JRBaseField;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

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
    private InputStream outDocReport = null;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        initComboBoxes();
        spnQty.setValueFactory(new IntegerSpinnerValueFactory(1, 1000));
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
     *
     * @param event
     */
    public void save(ActionEvent event) {
        String toName = tfTo.getText();
        String fromName = tfFrom.getText();
        LocalDate documentDate = dpDocumentDate.getValue();
        String comments = taComments.getText();
        Project project = cmbProjects.getValue();
        Operation operation = new Operation(2, "out", "");

        Document outDocument = new Document(operation, fromName, toName, documentDate, comments);

        int result = new cDocument().add(outDocument);

        if (result != -1) {
            outDocument.setId(result);
            //add inventory or update it in db first.
            tblItemsList.stream().forEach((i) -> {
                Optional<Inventory> inventory = new cInventory().getByItem(i);
                if (inventory.isPresent()) {
                    inventory.get().setAvailableQty(i.getQuantity());
                    InventoryOperation io = new InventoryOperation(inventory.get(), outDocument, operation, project, i.getQuantity(), comments);
                    int finalResult = new cInventoryOperation().add(io);
                    if (finalResult != -1) {
                        Alert alert = new Alert(AlertType.CONFIRMATION);
                        alert.setTitle("Save Operation...");
                        alert.setContentText("Operation has bees saved successfully...");
                        alert.show();
                    }
                }
            });

        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error...");
            alert.setContentText("The document has not been saved due to some error...");
            alert.show();
        }

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
        int qty = (Integer) spnQty.getValue();
        int availableQty = cmbItems.getValue().getQuantity();
        if (availableQty >= qty) {
            Item item = new Item();
            item.setId(cmbItems.getValue().getId());
            item.setName(cmbItems.getValue().getName());
            item.setQuantity(qty);

            tblItemsList.add(item);
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error...");
            alert.setContentText("The item(# " + cmbItems.getValue().getId() + ") has not enough quantity...");
            alert.show();
        }

    }

    public void print(ActionEvent event) {

        try {
            List<ItemsToPrint> itemsToPrintList = new ArrayList<>();
            tblItemsList.stream().forEach(i -> {
                ItemsToPrint item = new ItemsToPrint();
                item.setId(i.getId());
                item.setName(i.getName());
                item.setUnit(i.getUnit());
                item.setQuantity(i.getQuantity());
                item.setComments(i.getComments());
                
                itemsToPrintList.add(item);
            });
            JRBeanCollectionDataSource cds = new JRBeanCollectionDataSource(tblItemsList);

            outDocReport = getClass().getResourceAsStream("reports/outDocReport.jrxml");

            Map params = new HashMap();
            params.put("FROM", tfFrom.getText());
            params.put("TO", tfTo.getText());
            params.put("PROJECT", cmbProjects.getValue().getName());
            params.put("DOC_DATE", dpDocumentDate.getValue().toString());
            params.put("DOC_COMMENTS", taComments.getText());

            JasperReport jr = JasperCompileManager.compileReport(outDocReport);
            JRParameter[] jparams = jr.getParameters();
            for (JRParameter param : jparams) {
                System.out.println("Parameter: " + param.getName());
            }
            JasperPrint jp;
            try {
                jp = JasperFillManager.fillReport(jr, params, cds);
                JasperViewer.viewReport(jp, false);

            } catch (Exception ex) {
                Logger.getLogger(NewOutDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (JRException ex) {
            Logger.getLogger(NewOutDocumentController.class.getName()).log(Level.SEVERE, null, ex);
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

    private static class ItemsToPrint {

        private int id;
        private String  name;
        private int quantity;
        private String unit;
        private String comments;

        public ItemsToPrint() {
            
        }

        public ItemsToPrint(int id, String name, int quantity, String unit, String comments) {
            this.id = id;
            this.name = name;
            this.quantity = quantity;
            this.unit = unit;
            this.comments = comments;
        }

        public int getId() {
            return id;
        }

        /**
         * @param id the id to set
         */
        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        /**
         * @param name the name to set
         */
        public void setName(String name) {
            this.name = name;
        }
        
        /**
         *
         * @return quantity as integer value.
         */
        public int getQuantity() {
            return quantity;
        }

        /**
         * @param quantity the value to be set
         */
        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        /**
         * 
         * @return 
         */
        public String getUnit() {
            return unit;
        }

        /**
         * @param unit the unit to set
         */
        public void setUnit(String unit) {
            this.unit = unit;
        }

        /**
         * 
         * @return 
         */
        public String getComments() {
            return comments;
        }

        /**
         * @param comments the comments to set
         */
        public void setComments(String comments) {
            this.comments = comments;
        }

        @Override
        public String toString() {
            return getName();
        }

    }

}
