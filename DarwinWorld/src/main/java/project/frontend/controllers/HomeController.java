package project.frontend.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ChoiceBox;

public class HomeController {

    @FXML
    private TextField mapWidthField;

    @FXML
    private TextField mapHeightField;

    @FXML
    private ChoiceBox<String> mapTypeChoiceBox;

    @FXML
    private ChoiceBox<String> mutationTypeChoiceBox;

    @FXML
    private TextField initAnimalEnergyField;

    @FXML
    private TextField initPlantEnergyField;

    @FXML
    private TextField initAnimalsNoField;

    @FXML
    private TextField genotypeLengthField;

    @FXML
    private TextField energyPerPlantField;

    @FXML
    private TextField plantsPerDayField;

    @FXML
    private TextField energyToBeFeedField;

    @FXML
    private TextField energyToBreedingField;

    @FXML
    private TextField minMutationsNoField;

    @FXML
    private TextField maxMutationsNoField;

    @FXML
    private Button startButton;

    public boolean verifyFields(){
        boolean isValid = true;
        isValid = isValid && isValidIntField(mapWidthField, "Map width");
        isValid = isValid && isValidIntField(mapHeightField, "Map height");
        isValid = isValid && isValidIntField(initAnimalEnergyField, "Initial animal energy");
        isValid = isValid && isValidIntField(initPlantEnergyField, "Initial plant energy");
        isValid = isValid && isValidIntField(initAnimalsNoField, "Initial animals number");
        isValid = isValid && isValidIntField(genotypeLengthField, "Genotype length");
        isValid = isValid && isValidIntField(energyPerPlantField, "Energy per plant");
        isValid = isValid && isValidIntField(plantsPerDayField, "Plants per day");
        isValid = isValid && isValidIntField(energyToBeFeedField, "Energy to be feed");
        isValid = isValid && isValidIntField(energyToBreedingField, "Energy to breeding");
        isValid = isValid && isValidIntField(minMutationsNoField, "Min mutations number");
        isValid = isValid && isValidIntField(maxMutationsNoField, "Max mutations number");
        return isValid;
    }

    private boolean isValidIntField(TextField field, String fieldName) {
        try {
            int value = Integer.parseInt(field.getText());
            if (value <= 0) {
                showAlert(Alert.AlertType.ERROR, fieldName + " must be a positive integer.");
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, fieldName + " must be a valid integer.");
            return false;
        }
    }

    public void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Input Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void onStartButtonClicked(ActionEvent actionEvent) {
        System.out.println("Start button clicked");
        if (verifyFields()) {
            System.out.println("Verification passed!");
        }
        else {
            System.out.println("Verification failed!");
        }
    }


}
