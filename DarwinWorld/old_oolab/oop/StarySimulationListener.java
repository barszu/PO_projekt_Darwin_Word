package project.backend.backend.listeners;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;
import project.backend.backend.listeners.MapChangeListener;
import project.backend.backend.model.maps.WorldMap_able;
import project.backend.backend.model.maps.mapsUtil.RectangleBoundary;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimulationPresenter implements MapChangeListener {
    private WorldMap_able worldMap;
    @FXML
    private TextField movesListTextField;
    //    @FXML
//    private Label moveInfoLabel;
//    @FXML
//    private Button startButton;
    @FXML
    private GridPane mapGrid;
    @FXML
    private Label infoLabel;
    public void setWorldMap(WorldMap_able map){
        this.worldMap = map;
    }

    public void onSimulationStartClicked(ActionEvent actionEvent) {
        startSimulation();
    }
    @Override
    public void mapChanged(WorldMap_able worldMap, String message) {
        Platform.runLater(() ->{
            System.out.println("zmiana mapy");
            drawMap();
        });

    }

    public void drawMap(){
        clearGrid();
        RectangleBoundary mapBoundaries = worldMap.getRectangleBox();
        int columns = mapBoundaries.upperRightCorner().getX()-mapBoundaries.lowerLeftCorner().getX();
        int rows = mapBoundaries.upperRightCorner().getY()-mapBoundaries.lowerLeftCorner().getY();
        int offsetX = mapBoundaries.lowerLeftCorner().getX();
        int offsetY = mapBoundaries.lowerLeftCorner().getY();
        Text label = new Text("y/x");
        mapGrid.setHalignment(label, HPos.CENTER);
        mapGrid.add(label, 0, 0);

        for(int i=0; i<rows+1; i++) {
            mapGrid.getRowConstraints().add(new RowConstraints(30));
            Text coordinateY = new Text(Integer.toString(i+offsetY));
            mapGrid.setHalignment(coordinateY, HPos.CENTER);
            mapGrid.add(coordinateY, 0, i+1);
        }
        for(int i=0; i<columns+1; i++) {
            mapGrid.getColumnConstraints().add(new ColumnConstraints(30));
            Text coordinateX = new Text(Integer.toString(i+offsetX));
            mapGrid.setHalignment(coordinateX, HPos.CENTER);
            mapGrid.add(coordinateX, i+1, 0);
        }
        mapGrid.getRowConstraints().add(new RowConstraints(30));
        mapGrid.getColumnConstraints().add(new ColumnConstraints(30));
        for(WorldElement el : worldMap.getElements()) {
            Text worldElement = new Text(el.toString());
            mapGrid.setHalignment(worldElement, HPos.CENTER);
            mapGrid.add(worldElement, el.getPosition().getX()+1-offsetX, el.getPosition().getY()+1-offsetY);
        }



    }

    public void startSimulation() {
        String[] args = movesListTextField.getText().split(" ");
//        String[] args = {"f","f","f","r","l","f","f","f","f","f","f","f","f","f","f","f","f","f"};
        List<MoveDirection> movesList = OptionsParser.parse(args);
        List<Vector2d> positionsList = List.of(new Vector2d(2, 2) , new Vector2d(20,20));
        Simulation simulation = new Simulation(positionsList, movesList, worldMap);
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        SimulationEngine simulationEngine = new SimulationEngine(new ArrayList<>(List.of(simulation)), executorService);
        simulationEngine.runAsync();
    }

    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().get(0)); // hack to retain visible grid lines
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }
}
