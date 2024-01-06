package project.frontend.listeners;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;
import project.backend.backend.extras.Vector2d;
import project.backend.backend.listeners.MapChangeListener;
import project.backend.backend.model.enums.BiomeField;
import project.backend.backend.model.maps.WorldMap_able;
import project.backend.backend.model.maps.mapsUtil.RectangleBoundary;
import project.backend.backend.model.sprites.WorldElement_able;

public class SimulationMapListener implements MapChangeListener {

    private final WorldMap_able worldMap;
    private final Label textLabel;
    private final GridPane mapGrid;
    public SimulationMapListener(Label label , GridPane mapGrid, WorldMap_able worldMap){
        this.textLabel = label;
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
        Text label = new Text("y/x");
        mapGrid.setHalignment(label, HPos.CENTER);
        mapGrid.add(label, 0, 0);

        for(int i=0; i<rows; i++) {
            mapGrid.getRowConstraints().add(new RowConstraints(30));
            Text coordinateY = new Text(Integer.toString(i));
            mapGrid.setHalignment(coordinateY, HPos.CENTER);
            mapGrid.add(coordinateY, 0, i+1);
        }
        for(int i=0; i<columns; i++) {
            mapGrid.getColumnConstraints().add(new ColumnConstraints(30));
            Text coordinateX = new Text(Integer.toString(i));
            mapGrid.setHalignment(coordinateX, HPos.CENTER);
            mapGrid.add(coordinateX, i+1, 0);
        }
        mapGrid.getRowConstraints().add(new RowConstraints(30));
        mapGrid.getColumnConstraints().add(new ColumnConstraints(30));

        for(int x=0; x<rows; x++){
            for(int y=0; y<columns; y++){
                Vector2d position = new Vector2d(x,y);
                WorldElement_able el = worldMap.getOccupantFrom(position);

                Text cellText;
                if (el != null){
                    cellText = new Text(el.toString());

                }
                else {
                    BiomeField biomeField = worldMap.getBiomeRepresentation(position);
                    if (biomeField == null){
                        cellText = new Text("@");
                    }
                    else{
                        cellText = new Text(biomeField.toString());
                    }

                }

                mapGrid.setHalignment(cellText, HPos.CENTER);
                mapGrid.add(cellText, position.getX()+1, position.getY()+1);
            }
        }
    }

}
