package project.frontend.listeners;

import javafx.application.Platform;
import javafx.scene.control.Label;
import project.backend.backend.listeners.MapChangeListener;
import project.backend.backend.model.maps.WorldMap_able;

public class SimulationDayListener implements MapChangeListener {
    private final Label dayLabel;

    public SimulationDayListener(Label dayLabel) {
        this.dayLabel = dayLabel;
    }

    @Override
    public void mapChanged(WorldMap_able worldMap_able, String message) {
        Platform.runLater(() ->{
            this.dayLabel.setText(Integer.toString(worldMap_able.getGlobalVariables().getDate()));
        });
    }
}
