package br.com.brovetech.fileprocess.utils;

import java.io.File;
import java.io.FilenameFilter;

public class CustomFileFilter implements FilenameFilter {

    private final String extensionFile;

    public CustomFileFilter(String extensionFile) {
        this.extensionFile = extensionFile;
    }

    @Override
    public boolean accept(File dir, String name) {
        return name.toLowerCase().endsWith(extensionFile);
    }
}
