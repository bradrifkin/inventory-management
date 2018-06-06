package rifkinfinalproject.view;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import rifkinfinalproject.model.Inhouse;
import rifkinfinalproject.model.Inventory;
import rifkinfinalproject.model.Outsourced;
import rifkinfinalproject.model.Part;

public class AddPartController implements Initializable {

    @FXML
    private RadioButton ADDPARTInhouseRadioButton;
    @FXML
    private ToggleGroup partLocation;
    @FXML
    private RadioButton ADDPARTOutsourcedRadioButton;
    @FXML
    private Label ADDPARTIDLabel;
    @FXML
    private TextField ADDPARTIDTextField;
    @FXML
    private Label ADDPARTNameLabel;
    @FXML
    private TextField ADDPARTNameTextField;
    @FXML
    private Label ADDPARTInvLabel;
    @FXML
    private TextField ADDPARTInvTextField;
    @FXML
    private Label ADDPARTPriceLabel;
    @FXML
    private TextField ADDPARTPriceTextField;
    @FXML
    private Label ADDPARTMaxLabel;
    @FXML
    private TextField ADDPARTMaxTextField;
    @FXML
    private Label ADDPARTLocationLabel;
    @FXML
    private TextField ADDPARTLocationTextField;
    @FXML
    private Label addPartMinLabel;
    @FXML
    private TextField ADDPARTMinTextField;
    @FXML
    private Button ADDPARTSaveButton;
    @FXML
    private Button ADDPARTCancelButton;
    
    private boolean isInhouse;
    private int partID;
    
    private String errMessage = "";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        partID = Inventory.getPartIDCount();
        ADDPARTIDTextField.setText("Auto Gen: " + partID);
    }    

    @FXML
    private void ADDPARTradioButtonInhouse(ActionEvent event) {
        isInhouse = true;
        ADDPARTLocationLabel.setText("Machine ID");
        ADDPARTLocationTextField.setPromptText("Machine ID");
    }

    @FXML
    private void ADDPARTradioButtonOutsourced(ActionEvent event) {
        isInhouse = false;
        ADDPARTLocationLabel.setText("Company Name");
        ADDPARTLocationTextField.setPromptText("Company Name");
    }

    @FXML
    private void ADDPARTSaveButtonHandler(ActionEvent event) throws IOException {
        String partName = ADDPARTNameTextField.getText();
        String partInv = ADDPARTInvTextField.getText();
        String partPrice = ADDPARTPriceTextField.getText();
        String partMin = ADDPARTMinTextField.getText();
        String partMax = ADDPARTMaxTextField.getText();
        String partLocation = ADDPARTLocationTextField.getText();
        
        try {
            errMessage = Part.isPartValid(partName, Integer.parseInt(partMin), Integer.parseInt(partMax), Integer.parseInt(partInv), Double.parseDouble(partPrice), errMessage);
            if (errMessage.length() != 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error!");
                alert.setHeaderText("Error adding part");
                alert.setContentText(errMessage);
                alert.showAndWait();
                errMessage = "";
            } else {
                if (isInhouse == true) {
                    Inhouse newInhousePart = new Inhouse();
                    newInhousePart.setPartID(partID);
                    newInhousePart.setName(partName);
                    newInhousePart.setPrice(Double.parseDouble(partPrice));
                    newInhousePart.setInStock(Integer.parseInt(partInv));
                    newInhousePart.setMin(Integer.parseInt(partMin));
                    newInhousePart.setMax(Integer.parseInt(partMax));
                    newInhousePart.setMachineID(Integer.parseInt(partLocation));
                    Inventory.addPart(newInhousePart);
                } else {
                    Outsourced newOutsourcedPart = new Outsourced();
                    newOutsourcedPart.setPartID(partID);
                    newOutsourcedPart.setName(partName);
                    newOutsourcedPart.setPrice(Double.parseDouble(partPrice));
                    newOutsourcedPart.setInStock(Integer.parseInt(partInv));
                    newOutsourcedPart.setMin(Integer.parseInt(partMin));
                    newOutsourcedPart.setMax(Integer.parseInt(partMax));
                    newOutsourcedPart.setCompanyName(partLocation);
                    Inventory.addPart(newOutsourcedPart);
                }

                Parent addParts = FXMLLoader.load(getClass().getResource("Main.fxml"));
                Scene scene = new Scene(addParts);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error!");
            alert.setHeaderText("Error adding part");
            alert.setContentText("Please fill out all fields before saving");
            alert.showAndWait();
        }
    }

    @FXML
    private void ADDPARTCancelButtonHandler(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Please confirm cancelation");
        alert.setContentText("Are you sure you want to cancel the addition of the part " + ADDPARTNameTextField.getText() + "?");
        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.get() == ButtonType.OK) {
            Parent addParts = FXMLLoader.load(getClass().getResource("Main.fxml"));
            Scene scene = new Scene(addParts);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        }
    }
    
}
