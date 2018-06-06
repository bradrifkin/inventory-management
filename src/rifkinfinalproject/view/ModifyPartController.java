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
import static rifkinfinalproject.model.Inventory.getPartData;
import rifkinfinalproject.model.Outsourced;
import rifkinfinalproject.model.Part;
import static rifkinfinalproject.view.MainController.choosePartToModify;

public class ModifyPartController implements Initializable {

    @FXML
    private RadioButton MODPARTInHouseRadioButton;
    @FXML
    private ToggleGroup partLocation;
    @FXML
    private RadioButton MODPARTOutsourcedRadioButton;
    @FXML
    private Label MODPARTIDLabel;
    @FXML
    private TextField MODPARTIDTextField;
    @FXML
    private Label MODPARTNameLabel;
    @FXML
    private TextField MODPARTNameTextField;
    @FXML
    private Label MODPARTInvLabel;
    @FXML
    private TextField MODPARTInvTextField;
    @FXML
    private Label MODPARTPriceCostLabel;
    @FXML
    private TextField MODPARTPriceTextField;
    @FXML
    private Label MODPARTMaxLabel;
    @FXML
    private TextField MODPARTMaxTextField;
    @FXML
    private Label MODPARTLocationLabel;
    @FXML
    private TextField MODPARTLocationTextField;
    @FXML
    private Label MODPARTCompanyNameLabel;
    @FXML
    private TextField MODPARTCompanyNameTextField;
    @FXML
    private Label modifyPartMinLabel;
    @FXML
    private TextField MODPARTMinTextField;
    @FXML
    private Button MODPARTSaveButton;
    @FXML
    private Button MODPARTCancelButton;
    private final int partIndex = choosePartToModify();
    private int partID;
    private boolean isInhouse;
    
    private String errMessage = "";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Part part = getPartData().get(partIndex);
        partID = getPartData().get(partIndex).getPartID();
        MODPARTIDTextField.setText("Part ID: " + partID);
        MODPARTNameTextField.setText(part.getName());
        MODPARTInvTextField.setText(Integer.toString(part.getInStock()));
        MODPARTPriceTextField.setText(Double.toString(part.getPrice()));
        MODPARTMinTextField.setText(Integer.toString(part.getMin()));
        MODPARTMaxTextField.setText(Integer.toString(part.getMax()));
        if (part instanceof Inhouse) {
            MODPARTLocationTextField.setText(Integer.toString(((Inhouse) getPartData().get(partIndex)).getMachineID()));
            MODPARTLocationLabel.setText("Machine ID");
            MODPARTInHouseRadioButton.setSelected(true);

        } else {
            MODPARTLocationTextField.setText(((Outsourced) getPartData().get(partIndex)).getCompanyName());
            MODPARTLocationLabel.setText("Company Name");
            MODPARTOutsourcedRadioButton.setSelected(true);
        }
    }    

    @FXML
    private void MODPARTSaveButtonHandler(ActionEvent event) throws IOException {
        String partName = MODPARTNameTextField.getText();
        String partInv = MODPARTInvTextField.getText();
        String partPrice = MODPARTPriceTextField.getText();
        String partMin = MODPARTMinTextField.getText();
        String partMax = MODPARTMaxTextField.getText();
        String partLocation = MODPARTLocationTextField.getText();
        
        try {
            errMessage = Part.isPartValid(partName, Integer.parseInt(partMin), Integer.parseInt(partMax), Integer.parseInt(partInv), Double.parseDouble(partPrice), errMessage);
            if (errMessage.length() != 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error!");
                alert.setHeaderText("Error modifying part");
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
                    Inventory.updatePart(partIndex, newInhousePart);
                } else {
                    Outsourced newOutsourcedPart = new Outsourced();

                    newOutsourcedPart.setPartID(partID);
                    newOutsourcedPart.setName(partName);
                    newOutsourcedPart.setPrice(Double.parseDouble(partPrice));
                    newOutsourcedPart.setInStock(Integer.parseInt(partInv));
                    newOutsourcedPart.setMin(Integer.parseInt(partMin));
                    newOutsourcedPart.setMax(Integer.parseInt(partMax));
                    newOutsourcedPart.setCompanyName(partLocation);
                    Inventory.updatePart(partIndex, newOutsourcedPart);
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
            alert.setHeaderText("Error modifying part");
            alert.setContentText("Please fill out all fields before saving");
            alert.showAndWait();
        }
    }

    @FXML
    private void MODPARTCancelButtonHandler(ActionEvent event) throws IOException {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Please confirm cancelation");
        alert.setContentText("Are you sure you want to cancel the modification of the part " + MODPARTNameTextField.getText() + "?");
        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.get() == ButtonType.OK) {
            Parent modParts = FXMLLoader.load(getClass().getResource("Main.fxml"));
            Scene scene = new Scene(modParts);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        }
    }
    
    @FXML
    private void MODPARTradioButtonInhouse(ActionEvent event) {
        isInhouse = true;
        MODPARTLocationLabel.setText("Machine ID");
        MODPARTLocationTextField.setPromptText("Machine ID");
    }

    @FXML
    private void MODPARTradioButtonOutsourced(ActionEvent event) {
        isInhouse = false;
        MODPARTLocationLabel.setText("Company Name");
        MODPARTLocationTextField.setPromptText("Company Name");
    }
    
}
