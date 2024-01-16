package project.frontend;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import project.backend.backend.Simulation;
import project.backend.backend.global.GlobalOptions;
import project.frontend.controllers.SimulationController;

import java.io.IOException;

public class SimulationApp{ //strikte zajmuje sie sprawa watkow i okienek

    private final GlobalOptions G_OPTIONS;
    private final Simulation worldMapSimulation;

    public SimulationApp(GlobalOptions G_OPTIONS , int simulationId) {
        this.G_OPTIONS = G_OPTIONS;
        this.worldMapSimulation = new Simulation(G_OPTIONS , simulationId); //simulation armed & ready
    }


    public void show(Stage stage) throws IOException { //play -> show window (armed & ready)
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/simulationGUI.fxml"));
        Parent root = loader.load();

        SimulationController simulationController = loader.getController();

        stage.setTitle("Darwin's Evolution" + " - Simulation " + worldMapSimulation.getSimulationId());
        Scene scene = new Scene(root);
        stage.setScene(scene);
//                scene.getStylesheets().add("grip-cell-style.css");
        stage.show();
//                currentStage.close();
        int squareSize = (int) stage.getHeight()/ Math.max(G_OPTIONS.mapHeight(),G_OPTIONS.mapWidth());
        simulationController.setWorldMapSimulation(worldMapSimulation, squareSize);
        worldMapSimulation.start(); //simulation start now



//        zamkniecie okna z symulacja i symulacji
        stage.setOnCloseRequest(e -> {
            e.consume();
            worldMapSimulation.stopSimulation();
            stage.close();
        });

    }

}
