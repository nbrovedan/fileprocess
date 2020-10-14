package br.com.brovetech.fileprocess.dto;

import br.com.brovetech.fileprocess.model.Sale;
import br.com.brovetech.fileprocess.model.Salesman;
import lombok.Builder;
import lombok.Getter;
import lombok.With;

@Getter
@Builder
@With
public class ProcessedDataDTO {

    private Integer numberOfCustomers;
    private Integer numberOfSalesman;
    private Sale mostExpensiveSale;
    private Salesman worstSalesman;
}
