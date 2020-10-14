package br.com.brovetech.fileprocess.utils;

import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.FileFilter;

public class CustomFileFilter implements FileFilter {

    @Value("${default.process.extension.file:.dat}")
    private String extensionFile;

    @Override
    public boolean accept(File pathname) {
        return pathname.getName().toLowerCase().endsWith(extensionFile);
    }
}
