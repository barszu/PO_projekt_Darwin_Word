package project.frontend.listeners;

import javafx.application.Platform;
import javafx.scene.control.Label;
import project.backend.backend.listeners.MapChangeListener;
import project.backend.backend.model.maps.WorldMap_able;

public class SimulationGrassesNoListener implements MapChangeListener {
    private final Label plantsNoLabel;
    public SimulationGrassesNoListener(Label plantsNoLabel) {
        this.plantsNoLabel = plantsNoLabel;
    }


    @Override
    public void mapChanged(WorldMap_able worldMap_able, String message) {
        Platform.runLater(() ->{
            this.plantsNoLabel.setText(Integer.toString(worldMap_able.getGrassesNo()));
        });

    }
}
