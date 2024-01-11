package project.frontend.fileManagers;

import project.backend.backend.global.GlobalOptions;
import project.frontend.exceptions.ParsingStringsException;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class ConfigFileManager {
    private static final String CSV_FILE = "src/main/resources/config.csv";
    private static final String CSV_SPLIT_BY = ",";

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

    public static void add(GlobalOptions globalOptions) throws Exception{
        // vithout smart validation -> if it's will be saved in csv, it's valid RULE
        String[] newConfig = ConfigTranslator.translateFromGlobalOptionsToStringArray(globalOptions);

        FileWriter writer = new FileWriter(CSV_FILE, true);

        writer.append("\n" + String.join(CSV_SPLIT_BY, newConfig));

//            writer.flush();
        writer.close();
    }

    public static void deleteAllConfigs() throws IOException {
        FileWriter writer = new FileWriter(CSV_FILE, false);
        writer.write("");
        writer.close();
    }


    public static void remove(GlobalOptions globalOptions) throws Exception{
        List<GlobalOptions> globalOptionsList = readAllConfigs();
        globalOptionsList.remove(globalOptions);
        deleteAllConfigs();
        for (GlobalOptions g : globalOptionsList) {
            add(g);
        }

    }

}
