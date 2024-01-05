package project.frontend.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import project.backend.backend.Simulation;
import project.backend.backend.global.GlobalOptions;
import project.backend.backend.global.GlobalVariables;
import project.frontend.listeners.SimulationMapListener;
public class SimulationController {
    @FXML
    private GridPane mapGrid;
    @FXML
    private Label testLabel;

    public void start(GlobalOptions G_OPTIONS) {
        GlobalVariables G_VARIABLES = new GlobalVariables();
        Simulation worldMapSimulation = new Simulation(G_OPTIONS , 1);
        worldMapSimulation.addListenerToMap( new SimulationMapListener(testLabel, mapGrid, worldMapSimulation.getWorldMap()));
        worldMapSimulation.start();
    }
}
