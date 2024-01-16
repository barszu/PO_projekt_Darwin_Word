package project.frontend.fileManagers;

import project.backend.backend.globalViaSimulation.GlobalOptions;
import project.frontend.fileManagers.util.ConfigTranslator;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

/**
 * This class manages the configuration files for the simulation.
 * It provides methods to read, add, delete, and remove configurations.
 */
public class ConfigFileManager {
    private static final String CSV_FILE = "src/main/resources/config.csv";
    private static final String CSV_SPLIT_BY = ",";

    /**
     * This method reads all configurations from the configuration file.
     * It returns a list of GlobalOptions objects representing the configurations.
     * @return A list of GlobalOptions objects representing the configurations.
     */
    public static List<GlobalOptions> readAllConfigs(){
        List<GlobalOptions> optionsList = new LinkedList<>();
        String line = "";
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(CSV_FILE));
            while ((line = reader.readLine()) != null){
                String row[] = line.split(CSV_SPLIT_BY); //1row -> 1option
                try {
                    GlobalOptions globalOptions = ConfigTranslator.translateFromStringArrayToGlobalOptions(row);
                    // without smart validation -> if it's saved in csv, it's valid RULE
                    optionsList.add(globalOptions);
                }
                catch (Exception ignored) {}

            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found (config.csv)");
            e.printStackTrace();
        } catch (Exception e) { //any other exception
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                System.out.println("Error while closing reader");
                e.printStackTrace();
            }
        }
        return optionsList;
    }

    /**
     * This method adds a new configuration to the configuration file.
     * It translates the GlobalOptions object to a string array and appends it to the file.
     * @param globalOptions The GlobalOptions object representing the configuration to add.
     * @throws Exception if an error occurs while writing to the file.
     */
    public static void add(GlobalOptions globalOptions) throws Exception{
        // vithout smart validation -> if it's will be saved in csv, it's valid RULE
        String[] newConfig = ConfigTranslator.translateFromGlobalOptionsToStringArray(globalOptions);

        FileWriter writer = new FileWriter(CSV_FILE, true);

        writer.append("\n" + String.join(CSV_SPLIT_BY, newConfig));

//            writer.flush();
        writer.close();
    }

    /**
     * This method deletes all configurations from the configuration file.
     * It overwrites the file with an empty string.
     * @throws IOException if an error occurs while writing to the file.
     */
    public static void deleteAllConfigs() throws IOException {
        FileWriter writer = new FileWriter(CSV_FILE, false);
        writer.write("");
        writer.close();
    }


    /**
     * This method removes a specific configuration from the configuration file.
     * It reads all configurations, removes the specified one, deletes all configurations from the file, and then adds back the remaining ones.
     * @param globalOptions The GlobalOptions object representing the configuration to remove.
     * @throws Exception if an error occurs while reading from or writing to the file.
     */
    public static void remove(GlobalOptions globalOptions) throws Exception{
        List<GlobalOptions> globalOptionsList = readAllConfigs();
        globalOptionsList.remove(globalOptions);
        deleteAllConfigs();
        for (GlobalOptions g : globalOptionsList) {
            add(g);
        }

    }

}
