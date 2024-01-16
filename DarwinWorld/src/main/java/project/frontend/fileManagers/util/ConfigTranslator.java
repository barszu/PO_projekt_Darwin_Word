package project.frontend.fileManagers.util;

import project.backend.backend.globalViaSimulation.GlobalOptions;
import project.backend.backend.model.enums.MapType;
import project.backend.backend.model.enums.MutationType;
import project.frontend.exceptions.ParsingStringsException;

/**
 * This class provides methods to translate between GlobalOptions objects and string arrays.
 * This is used for reading and writing configurations to a file.
 */
public class ConfigTranslator {

    /**
     * This method translates a string array to a GlobalOptions object.
     * It parses the elements of the string array and uses them to create a new GlobalOptions object.
     * @param config The string array to translate. It should contain the values for the fields of the GlobalOptions object in the correct order.
     * @return A GlobalOptions object with the values from the string array.
     * @throws ParsingStringsException if an error occurs while parsing the string array.
     */
    public static GlobalOptions translateFromStringArrayToGlobalOptions(String[] config) throws ParsingStringsException {
        GlobalOptions res = null;
        try {
            MapType mapType = MapType.valueOf(config[2]);
            MutationType mutationType = MutationType.valueOf(config[3]);
            res = new GlobalOptions(
                Integer.parseInt(config[0]),
                Integer.parseInt(config[1]),
                mapType,
                mutationType,
                Integer.parseInt(config[4]),
                Integer.parseInt(config[5]),
                Integer.parseInt(config[6]),
                Integer.parseInt(config[7]),
                Integer.parseInt(config[8]),
                Integer.parseInt(config[9]),
                Integer.parseInt(config[10]),
                Integer.parseInt(config[11]),
                Integer.parseInt(config[12]),
                Integer.parseInt(config[13])
            );
            return res;
            } catch (Exception e) {
            throw new ParsingStringsException("Error while parsing String[] to GlobalOptions");
        }

    }

    /**
     * This method translates a GlobalOptions object to a string array.
     * It gets the values of the fields of the GlobalOptions object and converts them to strings.
     * @param globalOptions The GlobalOptions object to translate.
     * @return A string array containing the values of the fields of the GlobalOptions object.
     */
    public static String[] translateFromGlobalOptionsToStringArray(GlobalOptions globalOptions) {
        String[] res = new String[14];
        res[0] = String.valueOf(globalOptions.mapWidth());
        res[1] = String.valueOf(globalOptions.mapHeight());
        res[2] = String.valueOf(globalOptions.mapType());
        res[3] = String.valueOf(globalOptions.mutationType());
        res[4] = String.valueOf(globalOptions.initAnimalEnergy());
        res[5] = String.valueOf(globalOptions.initGrassNo());
        res[6] = String.valueOf(globalOptions.initAnimalsNo());
        res[7] = String.valueOf(globalOptions.genotypeLength());
        res[8] = String.valueOf(globalOptions.energyPerPlant());
        res[9] = String.valueOf(globalOptions.plantsPerDay());
        res[10] = String.valueOf(globalOptions.energyToBeFeed());
        res[11] = String.valueOf(globalOptions.energyToBreeding());
        res[12] = String.valueOf(globalOptions.minMutationsNo());
        res[13] = String.valueOf(globalOptions.maxMutationsNo());
        return res;
    }
}
