package project.frontend.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import project.backend.backend.global.GlobalOptions;
import project.backend.backend.model.enums.MapType;
import project.backend.backend.model.enums.MutationType;
import project.frontend.fileManagers.ConfigFileManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

public class ConfigLoaderController  {

    @FXML
    private ChoiceBox<GlobalOptions> configChoiceBox;

    public ChoiceBox<GlobalOptions> getConfigChoiceBox() {
        return configChoiceBox;
    }
    private HomeController homeController;

    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
    }


    public void loadSelectedConfig(ActionEvent event) {
        GlobalOptions globalOptions = configChoiceBox.getValue();
        homeController.setOptions(globalOptions);

        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void deleteSelectedConfig(ActionEvent event) {
        GlobalOptions globalOptions = configChoiceBox.getValue();
        try {
            configChoiceBox.setValue(null);
            ConfigFileManager.remove(globalOptions);
            renderConfigs();
        } catch (Exception e) {
            //TODO: alert?
            System.out.println("Error while deleting config");
            throw new RuntimeException(e);
        }
    }

    public void renderConfigs() {
        // it will be always at least 1 config
        configChoiceBox.getItems().clear();
        List<GlobalOptions> globalOptionsList = ConfigFileManager.readAllConfigs();
        if (globalOptionsList.isEmpty()) {
            createDummyConfigs();
            globalOptionsList = ConfigFileManager.readAllConfigs();
        }
        configChoiceBox.getItems().addAll(globalOptionsList);
        configChoiceBox.setValue(globalOptionsList.get(0));
//        configChoiceBox.setOnShowing(event -> configChoiceBox.setPrefHeight(200));

    }

    private void createDummyConfigs(){
        //TODO: info alert when it's happends
        GlobalOptions globalOptions = new GlobalOptions(
                10,
                10,
                MapType.WATER_MAP,
                MutationType.FULL_RANDOM,
                10,
                10,
                10,
                10,
                10,
                10,
                10,
                10,
                10,
                10
        );
        try {
            ConfigFileManager.add(globalOptions);
        } catch (Exception e) {
            //TODO: handle exception
            throw new RuntimeException(e);
        }
    }


}
