package project.frontend.listeners;

import javafx.application.Platform;
import javafx.scene.control.Label;

public class SimulationStatusListener {

    private final Label simulationStatusLabel;
    public SimulationStatusListener(Label simulationStatusLabel){
        this.simulationStatusLabel = simulationStatusLabel;
    }

    public void notify(boolean isRunning){
        if (isRunning){
            Platform.runLater(() ->{
                simulationStatusLabel.setText("Running");
            });

        } else {
            Platform.runLater(() ->{
                simulationStatusLabel.setText("Stopped");
            });

        }
    }
}
