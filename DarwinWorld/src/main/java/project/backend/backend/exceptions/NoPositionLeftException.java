package project.backend.backend.exceptions;

/**
 * This class represents a custom exception that is thrown when there are no free positions left in Biomes.
 * It extends the Exception class, thus it is a checked exception.
 * This exception is specifically for Biomes.
 */
public class NoPositionLeftException extends Exception{

        /**
         * The default constructor for the NoPositionLeftException.
         * It calls the superclass constructor with a specific message indicating that there are no free positions left in Biomes.
         */
        public NoPositionLeftException() {
            super("No free position left in Biomes.");
        }
}
