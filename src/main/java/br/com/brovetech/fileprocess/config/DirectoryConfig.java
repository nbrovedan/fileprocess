package br.com.brovetech.fileprocess.config;

import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.File;

import static br.com.brovetech.fileprocess.config.Constants.*;

@Configuration
public class DirectoryConfig {

    @PostConstruct
    private void config(){
        String path = System.getProperty(USER_HOME).concat(File.separator).concat(DIR_DATA);
        File directory = new File(path);
        if(!directory.exists()){
            directory.mkdir();
        }

        directory = new File(path.concat(File.separator).concat(DIR_IN));
        if(!directory.exists()){
            directory.mkdir();
        }

        directory = new File(path.concat(File.separator).concat(DIR_OUT));
        if(!directory.exists()){
            directory.mkdir();
        }
    }
}
