package br.com.brovetech.fileprocess.utils;

import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.FilenameFilter;

public class CustomFileFilter implements FilenameFilter {

    @Value("${default.process.extension.file:.dat}")
    private String extensionFile;

    @Override
    public boolean accept(File dir, String name) {
        return name.toLowerCase().endsWith(extensionFile);
    }
}
