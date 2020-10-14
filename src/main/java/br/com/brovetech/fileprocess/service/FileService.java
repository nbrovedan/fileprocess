package br.com.brovetech.fileprocess.service;

import br.com.brovetech.fileprocess.dto.ProcessedDataDTO;
import br.com.brovetech.fileprocess.dto.SalesDTO;
import br.com.brovetech.fileprocess.enumeration.FileTypeEnum;
import br.com.brovetech.fileprocess.exception.FileException;
import br.com.brovetech.fileprocess.exception.FileTypeNotFound;
import br.com.brovetech.fileprocess.mapper.ProcessDataMapper;
import br.com.brovetech.fileprocess.utils.CustomFileFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Stream;

import static br.com.brovetech.fileprocess.mapper.CustomerMapper.toCustomer;
import static br.com.brovetech.fileprocess.mapper.ProcessDataMapper.toFileOutput;
import static br.com.brovetech.fileprocess.mapper.SaleMapper.toSale;
import static br.com.brovetech.fileprocess.mapper.SalesmanMapper.toSalesman;
import static java.time.LocalDateTime.now;
import static java.time.format.DateTimeFormatter.ofPattern;

@Service
public class FileService {

    private static final String EXTENSION_PROCESSED = ".old";
    private static final String DIR_IN = "in";
    private static final String DIR_OUT = "out";
    private static final String FORMAT_DATE_PATTERN = "yyyyMMdd_HHmmss";
    private static final String MIDDLE_FILENAME = ".done";

    private final DataService dataService;
    private SalesDTO salesDTO;
    @Value("${default.process.extension.file:.dat}")
    private String extensionFile;

    public FileService(DataService dataService){
        this.dataService = dataService;
        this.salesDTO = buildSalesDTO();
    }

    private static SalesDTO buildSalesDTO() {
        return SalesDTO.builder()
                .customers(new ArrayList<>())
                .salemans(new ArrayList<>())
                .sales(new ArrayList<>())
                .build();
    }

    public void processFile(String path, String fileName) {
        String sourcePath = path.concat(File.separator).concat(DIR_IN).concat(File.separator).concat(fileName);
        Path source = Paths.get(sourcePath);
        try {
            try (Stream<String> stream = Files.lines(source)) {
                stream.forEach(this::loadLine);
            } finally {
                Path dest = Paths.get(sourcePath.replace(extensionFile, EXTENSION_PROCESSED));
                Files.move(source, dest, StandardCopyOption.REPLACE_EXISTING);
                this.saveResult(path);
            }
        } catch (IOException io){
            throw new FileException(io.getMessage(), io);
        }
    }

    private void loadLine(String line) {
        String fileCode = line.substring(0,3);
        FileTypeEnum fileType = FileTypeEnum.fromFileCode(line.substring(0,3));

        switch (fileType){
            case SALESMAN:
                salesDTO.getSalemans().add(toSalesman(line));
                break;
            case CUSTOMER:
                salesDTO.getCustomers().add(toCustomer(line));
                break;
            case SALE:
                salesDTO.getSales().add(toSale(line));
                break;
            default:
                throw new FileTypeNotFound(String.format("No file type with code %s", fileCode));
        }
    }

    private void saveResult(String path){
        if(isAllFileProcessed(path.concat(File.separator).concat(DIR_IN))){
            this.manageFolder(path);

            String fileName = now().format(ofPattern(FORMAT_DATE_PATTERN)).concat(MIDDLE_FILENAME).concat(extensionFile);
            ProcessedDataDTO processedDataDTO = dataService.processData(this.salesDTO);
            Path dest = Paths.get(path.concat(File.separator).concat(DIR_OUT).concat(File.separator).concat(fileName));
            byte[] strToBytes = toFileOutput(processedDataDTO).getBytes();

            try {
                Files.write(dest, strToBytes);
            } catch (IOException e) {
                throw new FileException(e.getMessage(), e);
            } finally {
                this.salesDTO = buildSalesDTO();
            }
        }
    }

    private boolean isAllFileProcessed(String path){
        return Objects.requireNonNull(new File(path).listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(extensionFile);
            }
        })).length == 0;
    }

    private void manageFolder(String path){
        File directory = new File(path.concat(File.separator).concat(DIR_OUT));
        if(!directory.exists()){
            directory.mkdir();
        }
    }
}
