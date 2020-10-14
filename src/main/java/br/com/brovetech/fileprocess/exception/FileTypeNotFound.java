package br.com.brovetech.fileprocess.exception;

public class FileTypeNotFound extends RuntimeException {

    public FileTypeNotFound(String message){
        super(message);
    }
}
