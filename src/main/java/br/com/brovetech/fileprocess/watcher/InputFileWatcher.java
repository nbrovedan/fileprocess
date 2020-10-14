package br.com.brovetech.fileprocess.watcher;

import br.com.brovetech.fileprocess.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;

import static java.lang.Thread.sleep;

@Component
public class InputFileWatcher {

    private static final int TIMELOAD = 3000;
    private static final String USER_HOME = "user.home";
    private static final String DIR_DATA = "data";
    private static final String DIR_IN = "in";

    private final FileService fileService;
    @Value("${default.process.extension.file:.dat}")
    private String extensionFile;

    public InputFileWatcher(FileService fileService){
        this.fileService = fileService;
    }

    @PostConstruct
    private void start() throws InterruptedException, IOException {
        String path = System.getProperty(USER_HOME).concat(File.separator).concat(DIR_DATA);

        this.manageFolder(path);

        WatchService watchService = FileSystems.getDefault().newWatchService();

        Path source = Paths.get(path.concat(File.separator).concat(DIR_IN));

        source.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

        WatchKey key;
        while ((key = watchService.take()) != null) {
            for (WatchEvent<?> event : key.pollEvents()) {
                sleep(TIMELOAD);
                this.callProcess(path, String.valueOf(event.context()));
            }
            key.reset();
        }
    }

    private void callProcess(String path, String filename) {
        if(filename.endsWith(extensionFile)){
            fileService.processFile(path, filename);
        }
    }

    private void manageFolder(String path){
        File directory = new File(path);
        if(!directory.exists()){
            directory.mkdir();
        }

        directory = new File(path.concat(File.separator).concat(DIR_IN));
        if(!directory.exists()){
            directory.mkdir();
        }
    }
}
