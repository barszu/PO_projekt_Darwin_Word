package project.frontend.listeners;

import javafx.application.Platform;
import javafx.scene.control.Label;
import project.backend.backend.listeners.MapChangeListener;
import project.backend.backend.model.maps.WorldMap_able;

public class SimulationAnimalNoListener implements MapChangeListener {

    private final Label animalNoLabel;

    public SimulationAnimalNoListener(Label animalNoLabel) {
        this.animalNoLabel = animalNoLabel;
    }

    @Override
    public void mapChanged(WorldMap_able worldMap_able, String message) {
        Platform.runLater(() ->{
            this.animalNoLabel.setText(Integer.toString(worldMap_able.getAnimalsNo()));
        });

    }
}
