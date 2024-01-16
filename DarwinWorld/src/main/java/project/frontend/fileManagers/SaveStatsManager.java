package project.frontend.fileManagers;

import project.frontend.listeners.listenerUtil.SimulationStats;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class SaveStatsManager {

    private final File path;

    public SaveStatsManager(File path) {
        this.path = path;
    }

    public void saveToFile(List<SimulationStats> simulationStatsList){
        try{
            FileWriter writer = new FileWriter(path, true);

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
