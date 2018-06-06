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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import rifkinfinalproject.RifkinFinalProject;
import rifkinfinalproject.model.Inhouse;
import rifkinfinalproject.model.Inventory;
import static rifkinfinalproject.model.Inventory.getPartData;
import static rifkinfinalproject.model.Inventory.getProductData;
import rifkinfinalproject.model.Outsourced;
import rifkinfinalproject.model.Part;
import rifkinfinalproject.model.Product;

public class MainController implements Initializable {

    @FXML
    private Button MAINpartsSearchButton;
    @FXML
    private TableView<Part> MAINpartsTableView;
    @FXML
    private TableColumn<Part, Integer> MAINpartIDColumn;
    @FXML
    private TableColumn<Part, String> MAINpartNameColumn;
    @FXML
    private TableColumn<Part, Integer> MAINpartsInventoryLevelColumn;
    @FXML
    private TableColumn<Part, Double> MAINpartsPricePerUnitColumn;
    @FXML
    private TextField MAINpartsSearchField;
    @FXML
    private Button MAINpartsAddButton;
    @FXML
    private Button MAINpartsModifyButton;
    @FXML
    private Button MAINpartsDeleteButton;
    @FXML
    private Button MAINproductsSearchButton;
    @FXML
    private TableView<Product> MAINproductsTableView;
    @FXML
    private TableColumn<Product, Integer> MAINproductIDColumn;
    @FXML
    private TableColumn<Product, String> MAINproductNameColumn;
    @FXML
    private TableColumn<Product, Integer> MAINproductsInventoryLevelColumn;
    @FXML
    private TableColumn<Product, Double> MAINproductsPricePerUnitColumn;
    @FXML
    private TextField MAINproductsSearchField;
    @FXML
    private Button MAINproductsAddButton;
    @FXML
    private Button MAINproductsModifyButton;
    @FXML
    private Button MAINproductsDeleteButton;
    @FXML
    private Button MAINexitButton;
    private static Part modifyPart;
    private static Product modifyProduct;
    private static int modifyPartInt;
    private static int modifyProductInt;
    private RifkinFinalProject mainApp;
    static boolean isAdded;
    @FXML
    private Button MAINpartsClearButton;
    @FXML
    private Button MAINproductsClearButton;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(!isAdded) {
            Inventory.allParts.add(new Inhouse(0, "Pedal", 5.0, 6, 2, 10, 123));
            Inventory.allParts.add(new Outsourced(1, "Seat", 12.0, 8, 1, 20, "Seat Co."));
            Inventory.allParts.add(new Outsourced(2, "Handlebar", 3.0, 5, 1, 10, "Bikes LLC"));
            Inventory.allParts.add(new Inhouse(3, "Wheel", 10.0, 8, 1, 15, 22));
            Inventory.allParts.add(new Inhouse(4, "Spoke", 7.5, 5, 2, 18, 777));
            Inventory.products.add(new Product(0, "Mountain Bike", 100.0, 15, 5, 30, Inventory.allParts.get(0)));
            Inventory.products.add(new Product(1, "Road Bike", 150.0, 25, 5, 40, Inventory.allParts.get(1)));
            Inventory.products.add(new Product(2, "Beach Bike", 75.0, 5, 2, 20, Inventory.allParts.get(2)));
            Inventory.products.add(new Product(3, "10-Speed Bike", 90.0, 10, 3, 20, Inventory.allParts.get(3)));
            isAdded = true;
        }
        MAINpartIDColumn.setCellValueFactory(cellData -> cellData.getValue().partIDProperty().asObject());
        MAINpartNameColumn.setCellValueFactory(cellData -> cellData.getValue().partNameProperty());
        MAINpartsInventoryLevelColumn.setCellValueFactory(cellData -> cellData.getValue().partInStockProperty().asObject());
        MAINpartsPricePerUnitColumn.setCellValueFactory(cellData -> cellData.getValue().partPriceProperty().asObject());
        MAINpartsTableView.setItems(Inventory.allParts);
        MAINproductIDColumn.setCellValueFactory(cellData -> cellData.getValue().productIDProperty().asObject());
        MAINproductNameColumn.setCellValueFactory(cellData -> cellData.getValue().productNameProperty());
        MAINproductsInventoryLevelColumn.setCellValueFactory(cellData -> cellData.getValue().productInStockProperty().asObject());
        MAINproductsPricePerUnitColumn.setCellValueFactory(cellData -> cellData.getValue().productPriceProperty().asObject());
        MAINproductsTableView.setItems(Inventory.products);
    }    

    @FXML
    private void MAINpartsSearchButtonHandler(ActionEvent event) {
        String searchItem = MAINpartsSearchField.getText().toLowerCase();
        boolean found = false;
        try{
            int itemNumber = Integer.parseInt(searchItem);
            for(Part p : Inventory.allParts){
                if(p.getPartID() == itemNumber){
                    System.out.println("This is part "+ itemNumber);
                    found = true;

                    ObservableList<Part> tempPartsList = FXCollections.observableArrayList(); 
                    tempPartsList.add(p);
                    MAINpartsTableView.setItems(tempPartsList);

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
                    MAINpartsTableView.setItems(tempPartsList);
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
    private void MAINpartsAddButtonHandler(ActionEvent event) throws IOException {
        Parent addParts = FXMLLoader.load(getClass().getResource("AddPart.fxml"));
        Scene scene = new Scene(addParts);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    @FXML
    private void MAINpartsModifyButtonHandler(ActionEvent event) throws IOException {
        modifyPart = MAINpartsTableView.getSelectionModel().getSelectedItem();
        modifyPartInt = getPartData().indexOf(modifyPart);
        Stage stage; 
        Parent root;
        if(event.getSource()== MAINpartsModifyButton){
            //get reference to the button's stage         
            stage=(Stage) MAINpartsModifyButton.getScene().getWindow();
            //load up OTHER FXML document
            root = FXMLLoader.load(getClass().getResource("ModifyPart.fxml"));
        } else{
            stage=(Stage) MAINpartsModifyButton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        }
        //create a new scene with root and set the stage
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void MAINpartsDeleteButtonHandler(ActionEvent event) {
        Part selectedPart = MAINpartsTableView.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {
        
        //Confirm delete
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText("Deleting...");
        alert.setContentText("Are you sure you want to delete " + selectedPart.getName()+"?");        
        alert.showAndWait()
            
            .filter(response -> response == ButtonType.OK)
            .ifPresent(response -> Inventory.getPartData().remove(selectedPart));

           
        //Update parts table
        MAINpartsTableView.setItems(Inventory.getPartData());
        
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
    private void MAINproductsSearchButtonHandler(ActionEvent event) {
        String searchItem = MAINproductsSearchField.getText().toLowerCase();
        boolean found = false;
        try{
            int itemNumber = Integer.parseInt(searchItem);
            for(Product p : Inventory.products){
                if(p.getProductID() == itemNumber){
                    System.out.println("This is product "+ itemNumber);
                    found = true;

                    ObservableList<Product> tempProductsList = FXCollections.observableArrayList(); 
                    tempProductsList.add(p);
                    MAINproductsTableView.setItems(tempProductsList);
                }
            }
                if (found == false){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Error!");
                alert.setContentText("Product was not found. Please try again.");

                alert.showAndWait();
            }
        }
        catch(NumberFormatException e){
            for(Product p: Inventory.products){
                if(p.getName().toLowerCase().equals(searchItem)){
                    System.out.println("This is product " + p.getProductID());
                    found = true;

                    ObservableList<Product> tempProductsList = FXCollections.observableArrayList(); 
                    tempProductsList.add(p);
                    MAINproductsTableView.setItems(tempProductsList);
                }

            }
                if (found == false){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Error");
                alert.setContentText("Product not found");

                alert.showAndWait();
            }
        }
    }

    @FXML
    private void MAINproductsAddButtonHandler(ActionEvent event) throws IOException {
        Parent addParts = FXMLLoader.load(getClass().getResource("AddProduct.fxml"));
        Scene scene = new Scene(addParts);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    @FXML
    private void MAINproductsModifyButtonHandler(ActionEvent event) throws IOException {
        modifyProduct = MAINproductsTableView.getSelectionModel().getSelectedItem();
        modifyProductInt = getProductData().indexOf(modifyProduct);
        Stage stage; 
        Parent root;
        if(event.getSource()== MAINproductsModifyButton){
            //get reference to the button's stage         
            stage=(Stage) MAINproductsModifyButton.getScene().getWindow();
            //load up OTHER FXML document
            root = FXMLLoader.load(getClass().getResource("ModifyProduct.fxml"));
        } else{
            stage=(Stage) MAINproductsModifyButton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        }
        //create a new scene with root and set the stage
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void MAINproductsDeleteButtonHandler(ActionEvent event) {
        Product selectedProduct = MAINproductsTableView.getSelectionModel().getSelectedItem();
        
        if (selectedProduct != null) {

            //Confirm delete
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Delete");
            alert.setHeaderText("Deleting...");
            alert.setContentText("Are you sure you want to delete " + selectedProduct.getName()+"? It has " + selectedProduct.getAssociatedParts().size() + " part(s) affiliated with it.");        
            alert.showAndWait()

                        .filter(response -> response == ButtonType.OK)
                        .ifPresent(response -> Inventory.getProductData().remove(selectedProduct));


            //Update products table
            MAINproductsTableView.setItems(Inventory.getProductData());

            } else {

            // Nothing selected
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Product Selected");
            alert.setContentText("Please select a product from the table.");

            alert.showAndWait();
        }
    }

    @FXML
    private void MAINexitButtonHandler(ActionEvent event) {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirmation ");
        alert.setHeaderText("Please confirm");
        alert.setContentText("Click OK to exit or Cancel to stay");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            System.exit(0);
        } 
        
    }
    
    public void setMainApp(RifkinFinalProject mainApp) {
        this.mainApp = mainApp;

        MAINpartsTableView.setItems(Inventory.getPartData());
        MAINproductsTableView.setItems(Inventory.getProductData());
    }

    @FXML
    private void MAINpartsClearButtonHandler(ActionEvent event) {
        MAINpartsSearchField.setText("");
        MAINpartsTableView.setItems(Inventory.getPartData());
    }

    @FXML
    private void MAINproductsClearButtonHandler(ActionEvent event) {
        MAINproductsSearchField.setText("");
        MAINproductsTableView.setItems(Inventory.getProductData());
    }
    
    public static int choosePartToModify() {
        return modifyPartInt;
    }

    public static int chooseProductToModify() {
        return modifyProductInt;
    }
    
}
