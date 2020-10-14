package br.com.brovetech.fileprocess.enumeration;

import br.com.brovetech.fileprocess.exception.FileTypeNotFound;

import java.util.Arrays;

public enum FileTypeEnum {
    SALESMAN("001"), CUSTOMER("002"), SALE("003");

    private final String fileCode;

    FileTypeEnum(String fileCode) {
        this.fileCode = fileCode;
    }

    public static FileTypeEnum fromFileCode(String fileCode) {
        return Arrays.stream(FileTypeEnum.values()).filter(f -> f.fileCode.equals(fileCode)).findFirst().orElseThrow(() -> new FileTypeNotFound(String.format("No file type with code %s", fileCode)));
    }
}
