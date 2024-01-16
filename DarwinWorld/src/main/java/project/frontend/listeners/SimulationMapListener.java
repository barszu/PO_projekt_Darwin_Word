package project.frontend.listeners;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
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
import project.backend.backend.model.sprites.Animal;
import project.backend.backend.model.sprites.WorldElement_able;
import javafx.stage.Stage;

import java.util.Arrays;

public class SimulationMapListener implements MapChangeListener { //TODO: 2 razy jest worldMap ??? poprawic -> Simon

    private final WorldMap_able worldMap;
    private final GridPane mapGrid;

    private final Label clickedAnimalHeader;
    private final Label clickedAnimalGrassEatenNo;
    private final Label clickedAnimalEnergyNo;
    private final Label clickedAnimalGenome;
    private final Label clickedAnimalDescendantsNo;
    public final int squareSize;
//    px
    public SimulationMapListener(GridPane mapGrid, WorldMap_able worldMap, int squareSize,Label clickedAnimalHeader, Label clickedAnimalGrassEatenNo, Label clickedAnimalEnergyNo, Label clickedAnimalGenome, Label clickedAnimalDescendantsNo){
        this.worldMap = worldMap;
        this.mapGrid = mapGrid;
        this.squareSize = squareSize;
        this.clickedAnimalHeader = clickedAnimalHeader;
        this.clickedAnimalGrassEatenNo = clickedAnimalGrassEatenNo;
        this.clickedAnimalEnergyNo = clickedAnimalEnergyNo;
        this.clickedAnimalGenome = clickedAnimalGenome;
        this.clickedAnimalDescendantsNo = clickedAnimalDescendantsNo;
    }


    @Override
    public void mapChanged(WorldMap_able worldMap_able, String message) {
        Platform.runLater(() ->{
            System.out.println("map UI has changed, day: " + worldMap_able.getDay() );
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
                Rectangle square = new Rectangle(squareSize, squareSize);
                square.setOnMouseClicked(event -> handleSquareClick(position, event));
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

    private void handleSquareClick(Vector2d position, MouseEvent event) {
        try{
            Animal animal = (Animal) worldMap.getOccupantFrom(position);
            clickedAnimalHeader.setText("---- <Clicked Animal statistics> ----");
            clickedAnimalEnergyNo.setText("Number of energy: "+animal.getEnergy());
//            clickedAnimalGrassEatenNo.setText(String.valueOf(animal.));
            clickedAnimalGenome.setText("Genome: "+Arrays.toString(animal.getGenotype()));
            clickedAnimalDescendantsNo.setText("Number of descendants: "+(animal.getSuccessorsNo()));
        } catch(ClassCastException | NullPointerException ignored){}

    }

}
