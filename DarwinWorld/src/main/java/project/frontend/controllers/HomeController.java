package project.frontend.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import project.backend.backend.exceptions.OptionsValidateException;
import project.backend.backend.global.GlobalOptions;
import project.backend.backend.global.GlobalOptionsValidator;
import project.backend.backend.model.enums.MapType;
import project.backend.backend.model.enums.MutationType;
import project.frontend.SimulationApp;

import java.io.IOException;

public class HomeController{

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

    private int openedSimulationWindowsNo = 1;

    public boolean verifyFieldsCarelessly(){
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

    private int getIntFromTextField(TextField textField, String fieldName) {
        try {
            String text = textField.getText().trim();
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, fieldName + " must be a valid integer.");
            return 0;
        }
    }

    public static <T extends Enum<T>> T stringToEnum(Class<T> enumType, String value) {
        if (value == null) {
            throw new IllegalArgumentException("Value cannot be null");
        }

        try {
            return Enum.valueOf(enumType, value);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid value for enum " + enumType.getSimpleName() + ": " + value);
        }
    }

    public GlobalOptions parseOptions() {
        GlobalOptions G_OPTIONS = null;
        try {
            int mapWidth = getIntFromTextField(mapWidthField, "Map Width");
            int mapHeight = getIntFromTextField(mapHeightField, "Map Height");
            MapType mapType = stringToEnum(MapType.class, mapTypeChoiceBox.getValue());
            MutationType mutationType = stringToEnum(MutationType.class, mutationTypeChoiceBox.getValue());
            int initAnimalEnergy = getIntFromTextField(initAnimalEnergyField, "Initial Animal Energy");
            int initPlantEnergy = getIntFromTextField(initPlantEnergyField, "Initial Plant Energy");
            int initAnimalsNo = getIntFromTextField(initAnimalsNoField, "Initial Animals Number");
            int genotypeLength = getIntFromTextField(genotypeLengthField, "Genotype Length");
            int energyPerPlant = getIntFromTextField(energyPerPlantField, "Energy Per Plant");
            int plantsPerDay = getIntFromTextField(plantsPerDayField, "Plants Per Day");
            int energyToBeFeed = getIntFromTextField(energyToBeFeedField, "Energy To Be Fed");
            int energyToBreeding = getIntFromTextField(energyToBreedingField, "Energy To Breeding");
            int minMutationsNo = getIntFromTextField(minMutationsNoField, "Min Mutations Number");
            int maxMutationsNo = getIntFromTextField(maxMutationsNoField, "Max Mutations Number");

            G_OPTIONS = new GlobalOptions(mapWidth, mapHeight, mapType, mutationType, initAnimalEnergy, initPlantEnergy, initAnimalsNo, genotypeLength, energyPerPlant, plantsPerDay, energyToBeFeed, energyToBreeding, minMutationsNo, maxMutationsNo);
            GlobalOptionsValidator.validate(G_OPTIONS);

        } catch (OptionsValidateException e) {
            e.getFieldName(); //TODO: mozna sporzytkowac do wyswietlenia komunikatu
            showAlert(Alert.AlertType.ERROR, e.getMessage());
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error parsing options");
            return null;
        }
        return G_OPTIONS;
    }


    public void onStartButtonClicked(ActionEvent actionEvent) {
        if (!verifyFieldsCarelessly()) {return;} //if no int's or < 0, stop
        GlobalOptions G_OPTIONS = parseOptions();
        if (G_OPTIONS != null) { //if options are valid
//            Stage currentStage = (Stage) startButton.getScene().getWindow();
            try {
                new SimulationApp(G_OPTIONS , openedSimulationWindowsNo).show(new Stage());
                openedSimulationWindowsNo++;
            } catch (IOException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error loading simulation app");
            }
        } else {
            System.out.println("Verification failed!");
            return;
        }
    }

    public void onCreateDummyDataButtonClicked(ActionEvent actionEvent) {
        mapWidthField.setText("20");
        mapHeightField.setText("20");
        initAnimalEnergyField.setText("10");
        initPlantEnergyField.setText("10");
        initAnimalsNoField.setText("10");
        genotypeLengthField.setText("10");
        energyPerPlantField.setText("10");
        plantsPerDayField.setText("10");
        energyToBeFeedField.setText("10");
        energyToBreedingField.setText("5");
        minMutationsNoField.setText("1");
        maxMutationsNoField.setText("3");
    }
}
