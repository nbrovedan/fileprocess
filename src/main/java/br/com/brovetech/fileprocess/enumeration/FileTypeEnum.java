package br.com.brovetech.fileprocess.enumeration;

import java.util.Arrays;

import static br.com.brovetech.fileprocess.config.Constants.EMPTY;

public enum FileTypeEnum {
    SALESMAN("001"), CUSTOMER("002"), SALE("003"), UNKNOWN(EMPTY);

    private final String fileCode;

    FileTypeEnum(String fileCode) {
        this.fileCode = fileCode;
    }

    public static FileTypeEnum fromFileCode(String fileCode) {
        return Arrays.stream(FileTypeEnum.values()).filter(f -> f.fileCode.equals(fileCode)).findFirst().orElse(UNKNOWN);
    }
}
