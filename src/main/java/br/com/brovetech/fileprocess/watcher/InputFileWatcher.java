package br.com.brovetech.fileprocess.watcher;

import br.com.brovetech.fileprocess.exception.FileException;
import br.com.brovetech.fileprocess.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.Objects;

import static br.com.brovetech.fileprocess.config.Constants.*;
import static br.com.brovetech.fileprocess.enumeration.ErrorMessageEnum.ERROR_ON_PROCESS_FILE;
import static br.com.brovetech.fileprocess.enumeration.ErrorMessageEnum.ERROR_ON_READ_FILE;
import static java.lang.String.format;
import static java.lang.Thread.sleep;

@Component
@Slf4j
public class InputFileWatcher {

    private final FileService fileService;
    @Value("${default.process.extension.file:.dat}")
    private String extensionFile;

    public InputFileWatcher(FileService fileService){
        this.fileService = fileService;
    }

    @PostConstruct
    private void start() throws IOException {
        String path = System.getProperty(USER_HOME).concat(File.separator).concat(DIR_DATA);

        WatchService watchService = null;
        try {
            watchService = FileSystems.getDefault().newWatchService();
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
        } catch (IOException ioe) {
            log.error(format(ERROR_ON_READ_FILE.toString(), ioe.getMessage()));
        } catch (Exception e) {
            throw new FileException(format(ERROR_ON_PROCESS_FILE.toString(), e.getMessage()), e);
        } finally {
            if(Objects.nonNull(watchService)){
                watchService.close();
            }
        }
    }

    private void callProcess(String path, String filename) {
        if(filename.endsWith(extensionFile)){
            fileService.processFile(path, filename);
        }
    }
}
