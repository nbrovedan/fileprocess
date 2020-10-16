package br.com.brovetech.fileprocess.exception;

public class FileEmptyException extends RuntimeException {
    public FileEmptyException(String message) {
        super(message);
    }
}
