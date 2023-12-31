package project.frontend.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import project.backend.backend.Simulation;
import project.frontend.listeners.SimulationMapListener;

public class SimulationController { //strikte zajmuje sie kontrolowaniem symulacji zatrzymywanie sterowanie fxml itd
    @FXML
    private GridPane mapGrid;

    @FXML
    private Button startButton;

    @FXML
    private Button stopButton;

    private Simulation worldMapSimulation;

    public void setWorldMapSimulation(Simulation worldMapSimulation) {
    	this.worldMapSimulation = worldMapSimulation;
        worldMapSimulation.addListenerToMap( new SimulationMapListener(mapGrid, worldMapSimulation.getWorldMap()));
    }

    public void onStartSimulationButtonClicked(ActionEvent actionEvent) {
        worldMapSimulation.startSimulation();
    }

    public void onStopSimulationButtonClicked(ActionEvent actionEvent) {
        worldMapSimulation.stopSimulation();
    }
}
