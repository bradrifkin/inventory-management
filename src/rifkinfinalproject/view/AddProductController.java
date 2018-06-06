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
import rifkinfinalproject.model.Inhouse;
import rifkinfinalproject.model.Inventory;
import rifkinfinalproject.model.Outsourced;
import rifkinfinalproject.model.Part;
import rifkinfinalproject.model.Product;

public class AddProductController implements Initializable {

    @FXML
    private Button ADDPRODSearchButton;
    @FXML
    private Button ADDPRODAddButton;
    @FXML
    private Button ADDPRODDeleteButton;
    @FXML
    private Button ADDPRODCancelButton;
    @FXML
    private Button ADDPRODSaveButton;
    @FXML
    private TableView<Part> addProductTotalInventoryTable;
    @FXML
    private TableColumn<Part, Integer> ADDPRODTotalInventoryPartIDColumn;
    @FXML
    private TableColumn<Part, String> ADDPRODTotalInventoryPartNameColumn;
    @FXML
    private TableColumn<Part, Integer> ADDPRODTotalInventoryInventoryLevelColumn;
    @FXML
    private TableColumn<Part, Double> ADDPRODTotalInventoryPricePerUnitColumn;
    @FXML
    private TableView<Part> addProductAssociatedPartsTable;
    @FXML
    private TableColumn<Part, Integer> ADDPRODAssociatedPartsPartIDColumn;
    @FXML
    private TableColumn<Part, String> ADDPRODAssociatedPartsPartNameColumn;
    @FXML
    private TableColumn<Part, Integer> ADDPRODAssociatedPartsInventoryLevelColumn;
    @FXML
    private TableColumn<Part, Double> ADDPRODAssociatedPartsPricePerUnitColumn;
    @FXML
    private TextField ADDPRODPartsSearchField;
    @FXML
    private Label ADDPRODIDLabel;
    @FXML
    private TextField ADDPRODIDTextField;
    @FXML
    private Label ADDPRODNameLabel;
    @FXML
    private TextField ADDPRODNameTextField;
    @FXML
    private Label ADDPRODInvLabel;
    @FXML
    private TextField ADDPRODInvTextField;
    @FXML
    private Label ADDPRODPriceLabel;
    @FXML
    private TextField ADDPRODPriceTextField;
    @FXML
    private Label ADDPRODMaxLabel;
    @FXML
    private TextField ADDPRODMaxTextField;
    @FXML
    private Label ADDPRODMinLabel;
    @FXML
    private TextField ADDPRODMinTextField;
    @FXML
    private Button ADDPRODClearButton;
    
    private ObservableList<Part> partsOfTheProduct = FXCollections.observableArrayList();
    private int productID;
    
    private String errMessage = "";
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        productID = Inventory.getProductIDCount();
        ADDPRODIDTextField.setText("Auto Gen: " + productID);   
        
        ADDPRODTotalInventoryPartIDColumn.setCellValueFactory(cellData -> cellData.getValue().partIDProperty().asObject());
        ADDPRODTotalInventoryPartNameColumn.setCellValueFactory(cellData -> cellData.getValue().partNameProperty());
        ADDPRODTotalInventoryInventoryLevelColumn.setCellValueFactory(cellData -> cellData.getValue().partInStockProperty().asObject());
        ADDPRODTotalInventoryPricePerUnitColumn.setCellValueFactory(cellData -> cellData.getValue().partPriceProperty().asObject());
        addProductTotalInventoryTable.setItems(Inventory.allParts);
        
        ADDPRODAssociatedPartsPartIDColumn.setCellValueFactory(cellData -> cellData.getValue().partIDProperty().asObject());
        ADDPRODAssociatedPartsPartNameColumn.setCellValueFactory(cellData -> cellData.getValue().partNameProperty());
        ADDPRODAssociatedPartsInventoryLevelColumn.setCellValueFactory(cellData -> cellData.getValue().partInStockProperty().asObject());
        ADDPRODAssociatedPartsPricePerUnitColumn.setCellValueFactory(cellData -> cellData.getValue().partPriceProperty().asObject());
                
    }    

    @FXML
    private void ADDPRODSearchButtonHandler(ActionEvent event) {
        String searchItem = ADDPRODPartsSearchField.getText().toLowerCase();
        boolean found = false;
        try{
            int itemNumber = Integer.parseInt(searchItem);
            for(Part p : Inventory.allParts){
                if(p.getPartID() == itemNumber){
                    System.out.println("This is part "+ itemNumber);
                    found = true;

                    ObservableList<Part> tempPartsList = FXCollections.observableArrayList(); 
                    tempPartsList.add(p);
                    addProductTotalInventoryTable.setItems(tempPartsList);

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
                    addProductTotalInventoryTable.setItems(tempPartsList);
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
    private void ADDPRODAddButtonHandler(ActionEvent event) {      
        Part selectedPart = addProductTotalInventoryTable.getSelectionModel().getSelectedItem();
        partsOfTheProduct.add(selectedPart);
        addProductAssociatedPartsTable.setItems(partsOfTheProduct);
    }

    @FXML
    private void ADDPRODDeleteButtonHandler(ActionEvent event) {
        Part selectedPart = addProductAssociatedPartsTable.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete...");
        alert.setHeaderText("Deleting...");
        alert.setContentText("Are you sure you want to delete " + selectedPart.getName()+"?");        
        alert.showAndWait()
            
            .filter(response -> response == ButtonType.OK)
            .ifPresent(response -> partsOfTheProduct.remove(selectedPart));

        addProductAssociatedPartsTable.setItems(partsOfTheProduct);
        
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
    private void ADDPRODCancelButtonHandler(ActionEvent event) throws IOException {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Please confirm cancelation");
        alert.setContentText("Are you sure you want to cancel the addition of the product " + ADDPRODNameTextField.getText() + "?");
        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.get() == ButtonType.OK) {
            Parent addParts = FXMLLoader.load(getClass().getResource("Main.fxml"));
            Scene scene = new Scene(addParts);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        }
    }

    @FXML
    private void ADDPRODSaveButtonHandler(ActionEvent event) throws IOException {
        String prodName = ADDPRODNameTextField.getText();
        String prodPrice = ADDPRODPriceTextField.getText();
        String prodInv = ADDPRODInvTextField.getText();
        String prodMin = ADDPRODMinTextField.getText();
        String prodMax = ADDPRODMaxTextField.getText();
        
        try {
            errMessage = Product.isProductValid(prodName, Integer.parseInt(prodMin), Integer.parseInt(prodMax), Integer.parseInt(prodInv), Double.parseDouble(prodPrice), partsOfTheProduct, errMessage);
            if (errMessage.length() != 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error!");
                alert.setHeaderText("Error adding product");
                alert.setContentText(errMessage);
                alert.showAndWait();
                errMessage = "";
            } else {       
                Product newProd = new Product();

                newProd.setProductID(productID);
                newProd.setName(prodName);
                newProd.setPrice(Double.parseDouble(prodPrice));
                newProd.setInStock(Integer.parseInt(prodInv));
                newProd.setMin(Integer.parseInt(prodMin));
                newProd.setMax(Integer.parseInt(prodMax));
                newProd.setAssociatedParts(partsOfTheProduct);
                Inventory.addProduct(newProd);

                Parent addProducts = FXMLLoader.load(getClass().getResource("Main.fxml"));
                Scene scene = new Scene(addProducts);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error!");
            alert.setHeaderText("Error adding product");
            alert.setContentText("Please fill out all fields before saving");
            alert.showAndWait();
        }
    }

    @FXML
    private void ADDPRODClearButtonHandler(ActionEvent event) {
        ADDPRODPartsSearchField.setText("");
        addProductTotalInventoryTable.setItems(Inventory.getPartData());
        
    }
    
}
