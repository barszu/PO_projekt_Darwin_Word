package project.frontend.exceptions;

/**
 * This class represents a custom exception that is thrown when there are errors in parsing strings.
 * It extends the Exception class, thus it is a checked exception.
 */
public class ParsingStringsException extends Exception{

    /**
     * The constructor for the ParsingStringsException.
     * It calls the superclass constructor with a specific message indicating the parsing error.
     * @param message The message indicating the parsing error.
     */
    public ParsingStringsException(String message) {
        super(message);
    }
}
