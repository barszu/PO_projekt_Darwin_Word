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

    Simulation worldMapSimulation;

    public void start(GlobalOptions G_OPTIONS) { //also constructor?
        GlobalVariables G_VARIABLES = new GlobalVariables();

        this.worldMapSimulation = new Simulation(G_OPTIONS , 1);

        worldMapSimulation.addListenerToMap( new SimulationMapListener(mapGrid, worldMapSimulation.getWorldMap()));
        worldMapSimulation.start();
    }

    public void stop() {
    	worldMapSimulation.stopSimulation();
    }


}
