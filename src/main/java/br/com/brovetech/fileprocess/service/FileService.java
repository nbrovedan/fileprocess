package br.com.brovetech.fileprocess.service;

import br.com.brovetech.fileprocess.dto.ProcessedDataDTO;
import br.com.brovetech.fileprocess.dto.SalesDTO;
import br.com.brovetech.fileprocess.enumeration.FileTypeEnum;
import br.com.brovetech.fileprocess.exception.FileException;
import br.com.brovetech.fileprocess.utils.CustomFileFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Stream;

import static br.com.brovetech.fileprocess.config.Constants.*;
import static br.com.brovetech.fileprocess.enumeration.ErrorMessageEnum.*;
import static br.com.brovetech.fileprocess.mapper.CustomerMapper.toCustomer;
import static br.com.brovetech.fileprocess.mapper.ProcessDataMapper.toFileOutput;
import static br.com.brovetech.fileprocess.mapper.SaleMapper.toSale;
import static br.com.brovetech.fileprocess.mapper.SalesmanMapper.toSalesman;
import static java.lang.String.format;
import static java.time.LocalDateTime.now;
import static java.time.format.DateTimeFormatter.ofPattern;

@Service
@Slf4j
public class FileService {

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
            throw new FileException(format(ERROR_ON_PROCESS_FILE.toString(), io.getMessage()), io);
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
                log.error(format(FILE_CODE_IS_NOT_VALID.toString(), fileCode));
        }
    }

    private void saveResult(String path){
        if(isAllFileProcessed(path.concat(File.separator).concat(DIR_IN))){
            String fileName = now().format(ofPattern(FORMAT_DATE_PATTERN)).concat(MIDDLE_FILENAME).concat(extensionFile);
            ProcessedDataDTO processedDataDTO = dataService.processData(this.salesDTO);
            Path dest = Paths.get(path.concat(File.separator).concat(DIR_OUT).concat(File.separator).concat(fileName));
            byte[] strToBytes = toFileOutput(processedDataDTO).getBytes();

            try {
                Files.write(dest, strToBytes);
            } catch (IOException e) {
                throw new FileException(format(ERROR_ON_SAVE_RESULTS.toString(), e.getMessage()), e);
            } finally {
                this.salesDTO = buildSalesDTO();
            }
        }
    }

    private boolean isAllFileProcessed(String path){
        return Objects.requireNonNull(new File(path).listFiles(new CustomFileFilter())).length == 0;
    }
}
