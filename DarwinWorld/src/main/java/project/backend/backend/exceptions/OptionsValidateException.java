package project.backend.backend.exceptions;

import project.backend.backend.global.GlobalOptions;

public class OptionsValidateException extends Exception{

    public String getFieldName() {
        return fieldName;
    }

    private final String fieldName;
    public OptionsValidateException(String fieldName , String message) {
        super(message);
        this.fieldName = fieldName;
    }



}
