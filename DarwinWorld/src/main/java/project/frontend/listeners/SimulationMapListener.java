package project.frontend.listeners;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import project.backend.backend.extras.Vector2d;
import project.backend.backend.listeners.MapChangeListener;
import project.backend.backend.model.enums.BiomeField;
import project.backend.backend.model.maps.WorldMap_able;
import project.backend.backend.model.maps.mapsUtil.RectangleBoundary;
import project.backend.backend.model.sprites.WorldElement_able;

public class SimulationMapListener implements MapChangeListener {

    private final WorldMap_able worldMap;
    private final GridPane mapGrid;

    public static final int SQUARE_SIZE = 20;
//    px
    public SimulationMapListener(GridPane mapGrid, WorldMap_able worldMap){
        this.worldMap = worldMap;
        this.mapGrid = mapGrid;
    }


    @Override
    public void mapChanged(WorldMap_able worldMap_able, String message) {
        Platform.runLater(() ->{
            System.out.println("map changed");
//            this.textLabel.setText(worldMap_able.toString());
            drawMap();
        });
    }

    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().get(0)); // hack to retain visible grid lines
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }

    public void drawMap(){
        clearGrid();
        RectangleBoundary rectangleBox = this.worldMap.getBoundary();
        int columns = rectangleBox.width();
        int rows = rectangleBox.height();
        for(int x=0; x<columns; x++){
            for(int y=0; y<rows; y++){
                Vector2d position = new Vector2d(x,y);
                WorldElement_able el = worldMap.getOccupantFrom(position);
                Rectangle square = new Rectangle(SQUARE_SIZE, SQUARE_SIZE);
                mapGrid.add(square, x, y);
                if (el != null){
                    square.getStyleClass().add(el.toString());


                }
                else {
                    BiomeField biomeField = worldMap.getBiomeRepresentation(position);
                    square.getStyleClass().add(biomeField.toString());
                }

            }
        }
    }

}
