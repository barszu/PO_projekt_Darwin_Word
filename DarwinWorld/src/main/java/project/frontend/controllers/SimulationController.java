package project.frontend.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import project.backend.backend.Simulation;
import project.frontend.fileManagers.SaveStatsManager;
import project.frontend.listeners.*;

import java.io.File;

public class SimulationController {

    //strikte zajmuje sie kontrolowaniem symulacji zatrzymywanie sterowanie fxml itd
    @FXML
    public Label clickedAnimalgrassEatenN0;
    @FXML
    public Label clickedAnimalEnergyNo;
    public Label clickedAnimalHeader;
    public Label clickedAnimalGenome;
    public Label clickedAnimalDescendantsNo;
    @FXML
    private Label simulationStatusLabel;
    @FXML
    private Label simulationDayLabel;
    @FXML
    private Label simulationAnimalsNoLabel;
    @FXML
    private Label simulationGrassesNoLabel;


    @FXML
    private GridPane mapGrid;

    @FXML
    private Button startButton;

    @FXML
    private Button stopButton;

    @FXML
    private Button saveStatsButton;

    private Simulation worldMapSimulation;

    private SimulationStatsListener simulationStatsListener ;


    public void setWorldMapSimulation(Simulation worldMapSimulation, int squareSize) {
    	this.worldMapSimulation = worldMapSimulation;
        worldMapSimulation.addListenerToMap( new SimulationMapListener(mapGrid, worldMapSimulation.getWorldMap(), squareSize, clickedAnimalHeader, clickedAnimalgrassEatenN0, clickedAnimalEnergyNo, clickedAnimalGenome, clickedAnimalDescendantsNo));

        this.simulationStatsListener = new SimulationStatsListener()
                .toBuild()
                .setDayLabel(simulationDayLabel)
                .setAnimalsNoLabel(simulationAnimalsNoLabel)
                .setPlantsNoLabel(simulationGrassesNoLabel)
                .build();

        worldMapSimulation.addListenerToMap(this.simulationStatsListener);

        simulationStatusLabel.setText("Initialized & Running");
        worldMapSimulation.addListener(new SimulationStatusListener(simulationStatusLabel));
    }

    public void onStartSimulationButtonClicked(ActionEvent actionEvent) {
        worldMapSimulation.startSimulation();
    }

    public void onStopSimulationButtonClicked(ActionEvent actionEvent) {
        worldMapSimulation.stopSimulation();
    }


    public void onSaveStatsButtonClicked(ActionEvent event) {
        worldMapSimulation.stopSimulation();

        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);

        fileChooser.setInitialDirectory(new File("./stats_export")); //TODO: stworzyc folder na te smieci i go oznaczyc ze to smieci
        fileChooser.setInitialFileName("stats.csv");
        fileChooser.setTitle("Save stats to file");


        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            SaveStatsManager saveStatsManager = new SaveStatsManager(file);
            saveStatsManager.saveToFile(simulationStatsListener.getSimulationStatsList());
        }


//        System.out.print(simulationStatsListener.getSimulationStatsList());
    }
}
