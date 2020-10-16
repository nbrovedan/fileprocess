package br.com.brovetech.fileprocess.mapper;

import br.com.brovetech.fileprocess.dto.ProcessedDataDTO;
import br.com.brovetech.fileprocess.model.Sale;
import br.com.brovetech.fileprocess.model.Salesman;
import lombok.experimental.UtilityClass;

import java.util.Optional;

import static br.com.brovetech.fileprocess.config.Constants.ZERO;
import static org.apache.logging.log4j.util.Strings.EMPTY;

@UtilityClass
public class ProcessDataMapper {

    public static String toFileOutput(ProcessedDataDTO processedDataDTO){
        return String.format("Quantidade de clientes: %d", processedDataDTO.getNumberOfCustomers()).concat(System.lineSeparator())
                .concat(String.format("Quantidade de vendedores: %d", processedDataDTO.getNumberOfSalesman())).concat(System.lineSeparator())
                .concat(String.format("Id venda mais cara: %d", Optional.ofNullable(processedDataDTO.getMostExpensiveSale()).orElse(Sale.builder().id(ZERO).build()).getId())).concat(System.lineSeparator())
                .concat(String.format("Pior vendedor: %s", Optional.ofNullable(processedDataDTO.getWorstSalesman()).orElse(Salesman.builder().name(EMPTY).build()).getName()));
    }
}
