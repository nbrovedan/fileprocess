package br.com.brovetech.fileprocess.dto;

import br.com.brovetech.fileprocess.model.Customer;
import br.com.brovetech.fileprocess.model.Sale;
import br.com.brovetech.fileprocess.model.Salesman;
import lombok.Builder;
import lombok.Getter;
import lombok.With;

import java.util.List;

@Getter
@With
@Builder
public class SalesDTO {

    private List<Salesman> salemans;
    private List<Customer> customers;
    private List<Sale> sales;
}
