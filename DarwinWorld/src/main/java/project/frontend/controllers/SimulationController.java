package project.frontend.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import project.backend.backend.Simulation;
import project.frontend.listeners.*;

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

    private Simulation worldMapSimulation;

    public void setWorldMapSimulation(Simulation worldMapSimulation, int squareSize) {
    	this.worldMapSimulation = worldMapSimulation;
        worldMapSimulation.addListenerToMap( new SimulationMapListener(mapGrid, worldMapSimulation.getWorldMap(), squareSize, clickedAnimalHeader, clickedAnimalgrassEatenN0, clickedAnimalEnergyNo, clickedAnimalGenome, clickedAnimalDescendantsNo));
        worldMapSimulation.addListenerToMap(new SimulationDayListener(simulationDayLabel));
        worldMapSimulation.addListenerToMap(new SimulationAnimalNoListener(simulationAnimalsNoLabel));
        worldMapSimulation.addListenerToMap(new SimulationGrassesNoListener(simulationGrassesNoLabel));

        simulationStatusLabel.setText("Initialized & Running");
        worldMapSimulation.addListener(new SimulationStatusListener(simulationStatusLabel));
    }

    public void onStartSimulationButtonClicked(ActionEvent actionEvent) {
        worldMapSimulation.startSimulation();
    }

    public void onStopSimulationButtonClicked(ActionEvent actionEvent) {
        worldMapSimulation.stopSimulation();
    }


}
