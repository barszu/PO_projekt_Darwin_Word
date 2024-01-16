package project.frontend.listeners;

import javafx.application.Platform;
import javafx.scene.control.Label;

/**
 * This class represents a listener for simulation status changes.
 * It updates a label with the current status of the simulation.
 */
public class SimulationStatusListener {

    // The label to update with the simulation status
    private final Label simulationStatusLabel;

    /**
     * The constructor for the SimulationStatusListener class.
     * It initializes the simulationStatusLabel.
     * @param simulationStatusLabel The label to update with the simulation status.
     */
    public SimulationStatusListener(Label simulationStatusLabel){
        this.simulationStatusLabel = simulationStatusLabel;
    }

    /**
     * This method updates the simulationStatusLabel with the current status of the simulation.
     * It runs on the JavaFX Application Thread to ensure that it can safely update the label.
     * If the simulation is running, it sets the text of the label to "Running".
     * If the simulation is not running, it sets the text of the label to "Stopped".
     * @param isRunning A boolean indicating whether the simulation is running.
     */
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
