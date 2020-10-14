package br.com.brovetech.fileprocess.mapper;

import br.com.brovetech.fileprocess.dto.ProcessedDataDTO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProcessDataMapper {

    public static String toFileOutput(ProcessedDataDTO processedDataDTO){
        return String.format("Quantidade de clientes: %d", processedDataDTO.getNumberOfCustomers()).concat(System.lineSeparator())
                .concat(String.format("Quantidade de vendedores: %d", processedDataDTO.getNumberOfSalesman())).concat(System.lineSeparator())
                .concat(String.format("Id venda mais cara: %d", processedDataDTO.getMostExpensiveSale().getId())).concat(System.lineSeparator())
                .concat(String.format("Pior vendedor: %s", processedDataDTO.getWorstSalesman().getName()));
    }

}
