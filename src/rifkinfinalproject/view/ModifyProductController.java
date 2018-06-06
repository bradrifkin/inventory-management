package rifkinfinalproject.view;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import rifkinfinalproject.model.Inventory;
import static rifkinfinalproject.model.Inventory.getPartData;
import static rifkinfinalproject.model.Inventory.getProductData;
import rifkinfinalproject.model.Part;
import rifkinfinalproject.model.Product;
import static rifkinfinalproject.view.MainController.chooseProductToModify;

public class ModifyProductController implements Initializable {

    @FXML
    private Button MODPRODSearchButton;
    @FXML
    private Button MODPRODAddButton;
    @FXML
    private Button MODPRODDeleteButton;
    @FXML
    private Button addProductCancelButton;
    @FXML
    private Button MODPRODSaveButton;
    @FXML
    private TableView<Part> modifyProductTotalInventoryTable;
    @FXML
    private TableColumn<Part, Integer> MODPRODTotalInventoryPartIDColumn;
    @FXML
    private TableColumn<Part, String> MODPRODTotalInventoryPartNameColumn;
    @FXML
    private TableColumn<Part, Integer> MODPRODTotalInventoryInventoryLevelColumn;
    @FXML
    private TableColumn<Part, Double> MODPRODTotalInventoryPricePerUnitColumn;
    @FXML
    private TableView<Part> modifyProductAssociatedPartsTable;
    @FXML
    private TableColumn<Part, Integer> MODPRODAssociatedPartsPartIDColumn;
    @FXML
    private TableColumn<Part, String> MODPRODAssociatedPartsPartNameColumn;
    @FXML
    private TableColumn<Part, Integer> MODPRODAssociatedPartsInventoryLevelColumn;
    @FXML
    private TableColumn<Part, Double> MODPRODAssociatedPartsPricePerUnitColumn;
    @FXML
    private TextField MODPRODPartsSearchField;
    @FXML
    private Label MODPRODIDLabel;
    @FXML
    private TextField MODPRODIDTextField;
    @FXML
    private Label MODPRODNameLabel;
    @FXML
    private TextField MODPRODNameTextField;
    @FXML
    private Label MODPRODInvLabel;
    @FXML
    private TextField MODPRODInvTextField;
    @FXML
    private Label MODPRODPriceLabel;
    @FXML
    private TextField MODPRODPriceTextField;
    @FXML
    private Label MODPRODMaxLabel;
    @FXML
    private TextField MODPRODMaxTextField;
    @FXML
    private Label MODPRODMinLabel;
    @FXML
    private TextField MODPRODMinTextField;
    @FXML
    private Button MODPRODClearButton;
    
    private final int productIndex = chooseProductToModify();
    private int productID;
    private ObservableList<Part> partsOfTheProduct = FXCollections.observableArrayList();
    
    private String errMessage = "";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Product product = getProductData().get(productIndex);
        productID = getProductData().get(productIndex).getProductID();
        
        MODPRODIDTextField.setText("AUTO GEN: " + productID);
        MODPRODNameTextField.setText(product.getName());
        MODPRODInvTextField.setText(Integer.toString(product.getInStock()));
        MODPRODPriceTextField.setText(Double.toString(product.getPrice()));
        MODPRODMinTextField.setText(Integer.toString(product.getMin()));
        MODPRODMaxTextField.setText(Integer.toString(product.getMax()));
        
        partsOfTheProduct = product.getAssociatedParts();
        
        MODPRODTotalInventoryPartIDColumn.setCellValueFactory(cellData -> cellData.getValue().partIDProperty().asObject());
        MODPRODTotalInventoryPartNameColumn.setCellValueFactory(cellData -> cellData.getValue().partNameProperty());
        MODPRODTotalInventoryInventoryLevelColumn.setCellValueFactory(cellData -> cellData.getValue().partInStockProperty().asObject());
        MODPRODTotalInventoryPricePerUnitColumn.setCellValueFactory(cellData -> cellData.getValue().partPriceProperty().asObject());
        MODPRODAssociatedPartsPartIDColumn.setCellValueFactory(cellData -> cellData.getValue().partIDProperty().asObject());
        MODPRODAssociatedPartsPartNameColumn.setCellValueFactory(cellData -> cellData.getValue().partNameProperty());
        MODPRODAssociatedPartsInventoryLevelColumn.setCellValueFactory(cellData -> cellData.getValue().partInStockProperty().asObject());
        MODPRODAssociatedPartsPricePerUnitColumn.setCellValueFactory(cellData -> cellData.getValue().partPriceProperty().asObject());
        
        modifyProductTotalInventoryTable.setItems(getPartData());
        modifyProductAssociatedPartsTable.setItems(partsOfTheProduct);
    }    

    @FXML
    private void MODPRODSearchButtonHandler(ActionEvent event) {
        String searchItem = MODPRODPartsSearchField.getText().toLowerCase();
        boolean found = false;
        try{
            int itemNumber = Integer.parseInt(searchItem);
            for(Part p : Inventory.allParts){
                if(p.getPartID() == itemNumber){
                    System.out.println("This is part "+ itemNumber);
                    found = true;

                    ObservableList<Part> tempPartsList = FXCollections.observableArrayList(); 
                    tempPartsList.add(p);
                    modifyProductTotalInventoryTable.setItems(tempPartsList);

                }
            }
                if (found == false){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Error!");
                alert.setContentText("Part was not found. Please try again.");

                alert.showAndWait();
            }
        }
        catch(NumberFormatException e){
            for(Part p: Inventory.allParts){
                if(p.getName().toLowerCase().equals(searchItem)){
                    System.out.println("This is part " + p.getPartID());
                    found = true;

                    ObservableList<Part> tempPartsList = FXCollections.observableArrayList(); 
                    tempPartsList.add(p);
                    modifyProductTotalInventoryTable.setItems(tempPartsList);
                }

            }
                if (found == false){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Error");
                alert.setContentText("Part not found");

                alert.showAndWait();
            }
        }
    }

    @FXML
    private void MODPRODAddButtonHandler(ActionEvent event) {
        Part selectedPart = modifyProductTotalInventoryTable.getSelectionModel().getSelectedItem();
        partsOfTheProduct.add(selectedPart);
        modifyProductAssociatedPartsTable.setItems(partsOfTheProduct);
    }

    @FXML
    private void MODPRODDeleteButtonHandler(ActionEvent event) {
        Part selectedPart = modifyProductAssociatedPartsTable.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {
        
        //Confirm delete
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete...");
        alert.setHeaderText("Deleting...");
        alert.setContentText("Are you sure you want to delete " + selectedPart.getName()+"?");        
        alert.showAndWait()
            
                    .filter(response -> response == ButtonType.OK)
                    .ifPresent(response -> partsOfTheProduct.remove(selectedPart));

           
        //Update associated parts table
        modifyProductAssociatedPartsTable.setItems(partsOfTheProduct);
        
        } else {
            
        // Nothing selected
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("No Selection");
        alert.setHeaderText("No Part Selected");
        alert.setContentText("Please select a part from the table.");

        alert.showAndWait();
        }
    }

    @FXML
    private void MODPRODCancelButtonHandler(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Please confirm cancelation");
        alert.setContentText("Are you sure you want to cancel the modification of the product " + MODPRODNameTextField.getText() + "?");
        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.get() == ButtonType.OK) {
            Parent modProducts = FXMLLoader.load(getClass().getResource("Main.fxml"));
            Scene scene = new Scene(modProducts);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        }
    }

    @FXML
    private void MODPRODSaveButtonHandler(ActionEvent event) throws IOException {
        String prodName = MODPRODNameTextField.getText();
        String prodInv = MODPRODInvTextField.getText();
        String prodPrice = MODPRODPriceTextField.getText();
        String prodMin = MODPRODMinTextField.getText();
        String prodMax = MODPRODMaxTextField.getText();
        
        try {
            errMessage = Product.isProductValid(prodName, Integer.parseInt(prodMin), Integer.parseInt(prodMax), Integer.parseInt(prodInv), Double.parseDouble(prodPrice), partsOfTheProduct, errMessage);
            if (errMessage.length() > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error Adding Product!");
                alert.setHeaderText("Error!");
                alert.setContentText(errMessage);
                alert.showAndWait();
                errMessage = "";
            } else {
                Product newProduct = new Product();
                newProduct.setProductID(productID);
                newProduct.setName(prodName);
                newProduct.setPrice(Double.parseDouble(prodPrice));
                newProduct.setInStock(Integer.parseInt(prodInv));
                newProduct.setMin(Integer.parseInt(prodMin));
                newProduct.setMax(Integer.parseInt(prodMax));
                newProduct.setAssociatedParts(partsOfTheProduct);
                Inventory.updateProduct(productIndex, newProduct);

                Parent modProducts = FXMLLoader.load(getClass().getResource("Main.fxml"));
                Scene scene = new Scene(modProducts);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
            }
        } catch (NumberFormatException e) {
            System.out.println("Blank Field");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error!");
            alert.setHeaderText("Error adding product");
            alert.setContentText("Please fill out all fields before saving");
            alert.showAndWait();
        }
    }

    @FXML
    private void MODPRODClearButtonHandler(ActionEvent event) {
        MODPRODPartsSearchField.setText("");
        modifyProductTotalInventoryTable.setItems(Inventory.getPartData());
    }
    
}
