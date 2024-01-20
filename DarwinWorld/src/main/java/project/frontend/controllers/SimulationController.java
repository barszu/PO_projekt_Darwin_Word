package project.frontend.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import project.backend.backend.Simulation;
import project.backend.backend.extras.Vector2d;
import project.backend.backend.model.maps.IWorldMap;
import project.backend.backend.model.sprites.Animal;
import project.frontend.fileManagers.SaveStatsManager;
import project.frontend.listeners.*;

import java.io.File;
import java.util.Arrays;
import java.util.Set;

public class SimulationController {

    //stricte zajmuje sie kontrolowaniem symulacji zatrzymywanie sterowanie fxml itd
    @FXML
    public Label clickedAnimalgrassEatenN0;
    @FXML
    public Label clickedAnimalEnergyNo;
    @FXML
    public Label clickedAnimalHeader;
    @FXML
    public Label clickedAnimalGenome;
    @FXML
    public Label clickedAnimalDescendantsNo;
    @FXML
    private Label simulationStatusLabel;
    @FXML
    private Label simulationDayLabel;
    @FXML
    private Label simulationAnimalsNoLabel;
    @FXML
    private Label simulationGrassesNoLabel;
    @FXML
    private Label clickedAnimalActivatedGene;
    @FXML
    private Label clickedAnimalChildrenNo;
    @FXML
    private Label clickedAnimalLifespan;
    @FXML
    private Label clickedAnimalDeathDate;

    @FXML
    private GridPane mapGrid;

    @FXML
    private Button startButton;

    @FXML
    private Button stopButton;

    @FXML
    private Button saveStatsButton;

    private Simulation worldMapSimulation;

    private SimulationStatsListener simulationStatsListener ;

    private SimulationAnimalStatsListener simulationAnimalStatsListener;
    private SimulationMapListener simulationMapListener;

    public void setWorldMapSimulation(Simulation worldMapSimulation, int squareSize) {
    	this.worldMapSimulation = worldMapSimulation;
        this.simulationAnimalStatsListener = new SimulationAnimalStatsListener(clickedAnimalHeader, clickedAnimalgrassEatenN0, clickedAnimalEnergyNo, clickedAnimalGenome, clickedAnimalDescendantsNo, clickedAnimalActivatedGene, clickedAnimalChildrenNo, clickedAnimalLifespan, clickedAnimalDeathDate);
        this.simulationMapListener =  new SimulationMapListener(mapGrid, worldMapSimulation.getWorldMap(), squareSize, simulationAnimalStatsListener, clickedAnimalHeader, clickedAnimalgrassEatenN0, clickedAnimalEnergyNo, clickedAnimalGenome, clickedAnimalDescendantsNo, clickedAnimalActivatedGene, clickedAnimalChildrenNo, clickedAnimalLifespan, clickedAnimalDeathDate);
        worldMapSimulation.addListenerToMap(simulationMapListener);
        worldMapSimulation.addListenerToMap(simulationAnimalStatsListener);
        this.simulationStatsListener = new SimulationStatsListener()
                .toBuild()
                .setDayLabel(simulationDayLabel)
                .setAnimalsNoLabel(simulationAnimalsNoLabel)
                .setPlantsNoLabel(simulationGrassesNoLabel)
                .build();

        worldMapSimulation.addListenerToMap(this.simulationStatsListener);

        simulationStatusLabel.setText("Initialized & Running");
        worldMapSimulation.addListener(new SimulationStatusListener(simulationStatusLabel));
    }

    public void onStartSimulationButtonClicked(ActionEvent actionEvent) {
        worldMapSimulation.startSimulation();
    }

    public void onStopSimulationButtonClicked(ActionEvent actionEvent) {
        worldMapSimulation.stopSimulation();
        IWorldMap worldMap = worldMapSimulation.getWorldMap();
        int topGene = worldMap.getTopGene();
            Set<Vector2d> positionsToColor = worldMap.getAnimalsPositionsHavingGene(topGene);
            for (Vector2d position : positionsToColor) {
                Rectangle square = new Rectangle(simulationMapListener.getSquareSize(), simulationMapListener.getSquareSize());
                square.setFill(Color.PURPLE);
                square.setOnMouseClicked(event -> handleSquareClick(position, event));
                mapGrid.add(square, position.getX(), position.getY());
            }
    }


    public void onSaveStatsButtonClicked(ActionEvent event) {
        worldMapSimulation.stopSimulation();

        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);

        fileChooser.setInitialDirectory(new File("./stats_export")); //TODO: stworzyc folder na te smieci i go oznaczyc ze to smieci
        fileChooser.setInitialFileName("stats.csv");
        fileChooser.setTitle("Save stats to file");


        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            SaveStatsManager saveStatsManager = new SaveStatsManager(file);
            saveStatsManager.saveToFile(simulationStatsListener.getSimulationStatsList());
        }


//        System.out.print(simulationStatsListener.getSimulationStatsList());
    }

    private void handleSquareClick(Vector2d position, MouseEvent event) {
        try{
            Animal trackedAnimal = (Animal) worldMapSimulation.getWorldMap().getOccupantFrom(position);
            clickedAnimalEnergyNo.setText("  Number of energy: " + trackedAnimal.getEnergy()+"  ");
            clickedAnimalgrassEatenN0.setText("  Grass eaten number: " + trackedAnimal.getEatenGrassNo()+"  ");
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
        }
    }

}
