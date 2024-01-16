package project.frontend.fileManagers;

import project.frontend.statistics.SimulationStats;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * This class manages the saving of simulation statistics to a file.
 * It provides a method to save a list of SimulationStats objects to a file.
 */
public class SaveStatsManager {

    // The path to the file where the statistics will be saved
    private final File path;

    /**
     * The constructor for the SaveStatsManager class.
     * It initializes the path to the file where the statistics will be saved.
     * @param path The path to the file where the statistics will be saved.
     */
    public SaveStatsManager(File path) {
        this.path = path;
    }

    /**
     * This method saves a list of SimulationStats objects to a file.
     * It writes the attribute names of the SimulationStats objects to the file,
     * followed by the string representation of each SimulationStats object in the list.
     * If an error occurs while writing to the file, it prints an error message.
     * @param simulationStatsList The list of SimulationStats objects to save to the file.
     */
    public void saveToFile(List<SimulationStats> simulationStatsList){
        try{
            FileWriter writer = new FileWriter(path, false);

            writer.write(SimulationStats.getAtributesName());
            for (SimulationStats simulationStats : simulationStatsList) {
                writer.append("\n" + simulationStats.toString());
            }
//            writer.flush();
            writer.close();

        } catch (IOException e){
            System.out.print("Error occured while saving stats to file" + e.getMessage());

        }
    }

}
