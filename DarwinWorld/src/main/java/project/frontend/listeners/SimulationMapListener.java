package project.frontend.listeners;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import project.backend.backend.extras.Vector2d;
import project.backend.backend.listeners.IMapChangeListener;
import project.backend.backend.model.enums.BiomeField;
import project.backend.backend.model.maps.IWorldMap;
import project.backend.backend.model.maps.mapsUtil.RectangleBoundary;
import project.backend.backend.model.sprites.Animal;
import project.backend.backend.model.sprites.IWorldElement;

import java.util.Arrays;

public class SimulationMapListener implements IMapChangeListener { //TODO: 2 razy jest worldMap ??? poprawic -> Simon
    private final SimulationAnimalStatsListener animalListener;
    private final IWorldMap worldMap;
    private final GridPane mapGrid;
    public final int squareSize;

    private final Label clickedAnimalHeader;
    private final Label clickedAnimalGrassEatenNo;
    private final Label clickedAnimalEnergyNo;
    private final Label clickedAnimalGenome;
    private final Label clickedAnimalDescendantsNo;
    private final Label clickedAnimalActivatedGene;
    private final Label clickedAnimalChildrenNo;
    private final Label clickedAnimalLifespan;
    private final Label clickedAnimalDeathDate;
//    px
    public SimulationMapListener(GridPane mapGrid, IWorldMap worldMap, int squareSize, SimulationAnimalStatsListener animalListener, Label clickedAnimalHeader, Label clickedAnimalGrassEatenNo, Label clickedAnimalEnergyNo, Label clickedAnimalGenome, Label clickedAnimalDescendantsNo, Label clickedAnimalActivatedGene, Label clickedAnimalChildrenNo, Label clickedAnimalLifespan, Label clickedAnimalDeathDate){
        this.worldMap = worldMap;
        this.mapGrid = mapGrid;
        this.squareSize = squareSize;
        this.animalListener = animalListener;

        this.clickedAnimalHeader = clickedAnimalHeader;
        this.clickedAnimalGrassEatenNo = clickedAnimalGrassEatenNo;
        this.clickedAnimalEnergyNo = clickedAnimalEnergyNo;
        this.clickedAnimalGenome = clickedAnimalGenome;
        this.clickedAnimalDescendantsNo = clickedAnimalDescendantsNo;
        this.clickedAnimalActivatedGene = clickedAnimalActivatedGene;
        this.clickedAnimalChildrenNo = clickedAnimalChildrenNo;
        this.clickedAnimalLifespan = clickedAnimalLifespan;
        this.clickedAnimalDeathDate = clickedAnimalDeathDate;
    }


    @Override
    public void mapChanged(IWorldMap worldMap_able, String message) {
        Platform.runLater(() ->{
//            System.out.println("map UI has changed, day: " + worldMap_able.getDay() );
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
                IWorldElement el = worldMap.getOccupantFrom(position);
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
            Animal trackedAnimal = (Animal) worldMap.getOccupantFrom(position);
            animalListener.setTrackedAnimal(trackedAnimal);
            clickedAnimalEnergyNo.setText("  Number of energy: " + trackedAnimal.getEnergy()+"  ");
            clickedAnimalGrassEatenNo.setText("  Grass eaten number: " + trackedAnimal.getEatenGrassNo()+"  ");
            clickedAnimalGenome.setText("  Genome: " + Arrays.toString(trackedAnimal.getGenotype())+"  ");
            clickedAnimalDescendantsNo.setText("  Number of descendants: " + (trackedAnimal.getSuccessorsNo())+"  ");
            clickedAnimalChildrenNo.setText("  Number of children: " + trackedAnimal.getChildrenList().size()+"  ");
            clickedAnimalActivatedGene.setText("  Activated gene: " + trackedAnimal.getGenotype()[trackedAnimal.getCurrentGenotypeIndex()]+"  ");
            clickedAnimalLifespan.setText("  Age: " + trackedAnimal.getAge()+"  ");
            if (trackedAnimal.isDeadAlready()) {
                int deathDate = trackedAnimal.getSpawnDate() + trackedAnimal.getAge();
                clickedAnimalDeathDate.setText("  Death date: " + deathDate + " day"+"  ");
            } else {
                clickedAnimalDeathDate.setText("");
            }
            clickedAnimalHeader.setText("---- <Clicked Animal statistics> ----");
        } catch(ClassCastException | NullPointerException a){
            animalListener.setTrackedAnimal(null);
        }
    }

    public int getSquareSize() {
        return squareSize;
    }
}
