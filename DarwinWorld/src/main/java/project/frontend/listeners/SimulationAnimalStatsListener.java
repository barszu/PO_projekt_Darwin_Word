package project.frontend.listeners;

import javafx.application.Platform;
import javafx.scene.control.Label;
import project.backend.backend.listeners.IMapChangeListener;
import project.backend.backend.model.maps.IWorldMap;
import project.backend.backend.model.sprites.Animal;

import java.util.Arrays;

public class SimulationAnimalStatsListener implements IMapChangeListener {
    private final Label clickedAnimalHeader;
    private final Label clickedAnimalGrassEatenNo;
    private final Label clickedAnimalEnergyNo;
    private final Label clickedAnimalGenome;
    private final Label clickedAnimalDescendantsNo;
    private final Label clickedAnimalActivatedGene;
    private final Label clickedAnimalChildrenNo;
    private final Label clickedAnimalLifespan;
    private final Label clickedAnimalDeathDate;
    private Animal trackedAnimal = null;

    public SimulationAnimalStatsListener(Label clickedAnimalHeader, Label clickedAnimalGrassEatenNo, Label clickedAnimalEnergyNo, Label clickedAnimalGenome, Label clickedAnimalDescendantsNo, Label clickedAnimalActivatedGene, Label clickedAnimalChildrenNo, Label clickedAnimalLifespan, Label clickedAnimalDeathDate) {
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

    public void setTrackedAnimal(Animal trackedAnimal) {
        this.trackedAnimal = trackedAnimal;
    }

    @Override
    public void mapChanged(IWorldMap worldMap_able, String message) {
        Platform.runLater(() ->{
                if(trackedAnimal!=null) {
                    clickedAnimalHeader.setText("---- <Clicked Animal statistics> ----");
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
                }
                else {
                    clickedAnimalHeader.setText("");
                    clickedAnimalEnergyNo.setText("");
                    clickedAnimalGrassEatenNo.setText("");
                    clickedAnimalGenome.setText("");
                    clickedAnimalDescendantsNo.setText("");
                    clickedAnimalChildrenNo.setText("");
                    clickedAnimalActivatedGene.setText("");
                    clickedAnimalLifespan.setText("");
                    clickedAnimalDeathDate.setText("");
                }
        });
    }
}
