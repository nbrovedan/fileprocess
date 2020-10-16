package br.com.brovetech.fileprocess.exception;

public class FileTypeNotFoundException extends RuntimeException {

    public FileTypeNotFoundException(String message){
        super(message);
    }
}
