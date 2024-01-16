package project.backend.backend.model.enums;

/**
 * This enum represents the different types of Biome Fields that can exist in the application.
 * It provides a method to convert the enum values to their string representations.
 */
public enum BiomeField {
    STEP,
    JUNGLE,
    WATER;

    /**
     * This method returns the string representation of the BiomeField.
     * It uses a switch statement to determine the string representation based on the enum value.
     * @return A string representing the BiomeField.
     */
    public String toString(){
        return switch(this) {
            case STEP -> "step";
            case JUNGLE -> "jungle";
            case WATER -> "water";
        };
    }

}


