package br.com.brovetech.fileprocess.enumeration;

public enum ErrorMessageEnum {

    FILE_CODE_IS_NOT_VALID("File code %s is not valid!"),
    ERROR_ON_SAVE_RESULTS("Error on save results: %s"),
    ERROR_ON_PROCESS_FILE("Error on process file: %s"),
    ERROR_ON_READ_FILE("Error on read file: %s"),
    ERROR_INVALID_LINE("Invalid line: %s"),
    ERROR_INVALID_TYPE("Invalid type: %s"),
    ERROR_EMPTY_FILE("The file is empty: %s"),
    ;

    private final String message;

    ErrorMessageEnum(String message) {
        this.message = message;
    }

    @Override
    public String toString(){
        return this.message;
    }
}
