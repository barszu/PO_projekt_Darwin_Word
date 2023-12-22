package agh.ics.oop.presenter;

import project.backend.OptionsParser;
import project.backend.Simulation;
import project.backend.SimulationEngine;
import project.backend.model.maps.WorldMapable;
import agh.ics.oop.model.MoveDirection;
import project.backend.model.models.Vector2d;
import project.backend.model.observers.MapChangeListener;
import project.backend.model.util.GridMapDrawer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

public class SimulationPresenter implements MapChangeListener {

    private WorldMapable worldMapable;
    private GridMapDrawer gridMapDrawer;

    public void setWorldMap(WorldMapable worldMapable) {
        this.worldMapable = worldMapable;
    }

    public void setGridMapDrawer(GridMapDrawer gridMapDrawer) {
        this.gridMapDrawer = gridMapDrawer;
    }

    @FXML
    private Label infoLabel;
    @FXML
    private TextField movesListTextField;
    @FXML
    private Label moveInfoLabel;
    @FXML
    private Button startButton;
    @FXML
    private GridPane mapGrid;
    @FXML
    public void drawMap() { //setter for infoLabel?
        System.out.println("drawMap w SimulationPresenter zadzialalo");
        infoLabel.setText(worldMapable.toString());
    }

    @Override
    public void mapChanged(WorldMapable worldMapable, String message) {
        Platform.runLater(() -> {
//            drawMap();
            moveInfoLabel.setText(message);
            gridMapDrawer.draw();
            System.out.println("zmieniono mape");
        });
    }

    public void onSimulationStartClicked(ActionEvent actionEvent) {
       startSimulation();
    }

    private void startSimulation() {
//        String[] args = {"f","f","f","r","l","f","f","f","f","f","f","f","f","f","f","f","f","f"};
        String[] args = movesListTextField.getText().split(" ");
        List<MoveDirection> movesList = OptionsParser.parse(args);

        List<Vector2d> positionsList = List.of(new Vector2d(2, 2) , new Vector2d(20,20));
        Simulation simulation = new Simulation(movesList, positionsList, worldMapable);

        setGridMapDrawer(new GridMapDrawer(mapGrid, worldMapable));

        SimulationEngine simulationEngine = new SimulationEngine(new ArrayList<>(List.of(simulation)));
//        simulationEngine.runAsyncInThreadPool();
        simulationEngine.runASync();

        Platform.runLater(() -> startButton.setDisable(true));
    }

}
