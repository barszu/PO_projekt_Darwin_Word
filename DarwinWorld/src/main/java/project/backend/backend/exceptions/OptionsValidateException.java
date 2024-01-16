package project.backend.backend.exceptions;

/**
 * This class represents a custom exception that is thrown when there are validation errors in GlobalOptions.
 * It extends the Exception class, thus it is a checked exception.
 */
public class OptionsValidateException extends Exception{

    /**
     * This method returns the name of the field that caused the validation error.
     * @return A string representing the name of the field.
     */
    public String getFieldName() {
        return fieldName;
    }

    private final String fieldName;

    /**
     * The constructor for the OptionsValidateException.
     * It calls the superclass constructor with a specific message indicating the validation error and sets the field name that caused the error.
     * @param fieldName The name of the field that caused the validation error.
     * @param message The message indicating the validation error.
     */
    public OptionsValidateException(String fieldName , String message) {
        super(message);
        this.fieldName = fieldName;
    }
}

