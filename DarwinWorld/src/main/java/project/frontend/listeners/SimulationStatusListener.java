package project.frontend.listeners;

import javafx.scene.control.Label;

public class SimulationStatusListener {

    private final Label simulationStatusLabel;
    public SimulationStatusListener(Label simulationStatusLabel){
        this.simulationStatusLabel = simulationStatusLabel;
    }

    public void notify(boolean isRunning){
        if (isRunning){
            simulationStatusLabel.setText("Running");
        } else {
            simulationStatusLabel.setText("Stopped");
        }
    }
}
